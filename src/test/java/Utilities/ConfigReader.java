package Utilities;

import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop = new Properties();

    static {
        try {
            String path = System.getProperty("user.dir") + File.separator + "repository.properties";
            FileInputStream fis = new FileInputStream(path);
            prop.load(fis);
            fis.close();
        } catch (Exception e) {
            // Fallback to config.property if repository.properties isn't found
            try {
                String path = System.getProperty("user.dir") + File.separator + "config.property";
                FileInputStream fis = new FileInputStream(path);
                prop.load(fis);
                fis.close();
            } catch (Exception ex) {
                System.out.println("Could not load config file: " + ex.getMessage());
            }
        }
    }

    public static String getvalue(String key) {
        return prop.getProperty(key);
    }
}