package Manga;

import java.io.*;
import java.util.Properties;

public class PropertiesReader {
    public static String Reader(String Path, String KeyWord) {
        Properties properties = new Properties();
        String value;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(Path)));
            properties.load(in);
            value = properties.getProperty(KeyWord);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    return value;
    }
}
