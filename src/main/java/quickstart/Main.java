package quickstart;

import eu.dgs_development.code.ejg.controllers.*;
import eu.dgs_development.code.ejg.theme.ThemeManager;
import eu.dgs_development.code.ejg.theme.colors.DefaultThemeColorsProvider;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    public static void main(String[] args) {
        //Launch the actual JavaFX application.
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws DependencyInjectionException, ControllerInitializationException {
        //Configuration and setup of the GUI manager.
        GuiConfiguration guiConfiguration = new GuiConfiguration(primaryStage);
        guiConfiguration.setPackageScanPath("quickstart");

        GuiControllerManager guiControllerManager = new GuiControllerManager(guiConfiguration);

        //Show the scene of the GuiContainerController instance.
        Scene scene = guiControllerManager.getCachedSceneOrNull(MainGuiController.class);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @InstanceProvider
    public static ThemeManager<DefaultThemeColorsProvider> createThemeManager() {
        //Load the theme CSS file.

        InputStream cssInputStream = Main.class.getResourceAsStream("/css/theme.css");

        String cssString = InputStreamToStringUtil.stringFromInputStream(cssInputStream);

        //Customize the colors of the ThemeManager here, if desired.
        ThemeManager<DefaultThemeColorsProvider> themeManager = new ThemeManager<>(cssString,
                new DefaultThemeColorsProvider());

        //Setup JMetro theme change functionality.

        Map<Parent, JMetro> parentJMetroMap = new HashMap<>();

        themeManager.addParentChangeListener((addedParent, changedParent) -> {
            if(addedParent) {
                //The Parent was added. Style it with JMetro.

                Style style = themeManager.isDarkThemeColorsActive() ? Style.DARK : Style.LIGHT;

                JMetro jMetro = new JMetro(changedParent, style);

                parentJMetroMap.put(changedParent, jMetro);
            }
            else {
                //The parent was removed. Remove it from map.

                parentJMetroMap.remove(changedParent);
            }
        });

        themeManager.addThemeChangeListener(colorsProvider -> {
            //The theme colors provider changed!

            Style newStyle = themeManager.isDarkThemeColorsActive() ? Style.DARK : Style.LIGHT;

            parentJMetroMap.values().forEach(tmpJMetro -> {
                if(tmpJMetro.getStyle() != newStyle) {
                    tmpJMetro.setStyle(newStyle);
                    tmpJMetro.reApplyTheme();
                }
            });
        });

        return themeManager;
    }
}
