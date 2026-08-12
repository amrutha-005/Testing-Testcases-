
package pac1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Repository_readfromproperties {

    static Properties prob = new Properties();

    public static void loadproperties() throws IOException {
        String projectpath = System.getProperty("user.dir");
        String path = projectpath + "\\repository.properties";
        FileInputStream fis = new FileInputStream(path);

        prob.load(fis);
        fis.close();
    }

    public static String getlocator(String Key) {
        return prob.getProperty(Key);
    }
}