package quickstart;

import java.io.InputStream;
import java.util.Scanner;

public class InputStreamToStringUtil {
    /**
     * Reads a string from an input stream, delimited by "\\A" chars.
     * @param inputStream The {@link InputStream} to read the string resource.
     * @return The read string.
     */
    public static String stringFromInputStream(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

        StringBuilder stringBuilder = new StringBuilder();

        while(scanner.hasNext())
            stringBuilder.append(scanner.next());

        scanner.close();

        return stringBuilder.toString();
    }
}
