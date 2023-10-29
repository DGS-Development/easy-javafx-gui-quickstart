package quickstart;

import eu.dgs_development.code.ejg.controllers.GuiController;
import eu.dgs_development.code.ejg.controllers.InstanceConsumer;
import eu.dgs_development.code.ejg.theme.ThemeManager;
import eu.dgs_development.code.ejg.theme.colors.DefaultDarkThemeColorSourceProvider;
import eu.dgs_development.code.ejg.theme.colors.DefaultThemeColorsProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainGuiController extends GuiController {
    @FXML
    private Button btnSwitchTheme;

    @InstanceConsumer
    private ThemeManager<DefaultThemeColorsProvider> themeManager;

    @Override
    public void onInitialized() {
        //Add MainGuiController to ThemeManager.
        themeManager.addParent(getRootNode());

        //Add theme-switch functionality.
        btnSwitchTheme.setOnMouseClicked(event -> {
            if(themeManager.isDarkThemeColorsActive()) {
                //Remove the dark ThemeColorSource.
                themeManager.removeCurrentThemeColorSource();
            }
            else {
                //Set the dark ThemeColorSource.
                themeManager.setThemeColorSource(DefaultDarkThemeColorSourceProvider.getThemeColorSource());
            }
        });
    }

    @Override
    public String getFxmlPath() {
        return "/fxml/main.fxml";
    }

    @Override
    public boolean isCacheableGuiController() {
        return true;
    }
}
