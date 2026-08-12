package pac2;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class xmlobjectrepository {

    public static String getlocator(String elementname) {
        try {
            // Path relative to project root pointing to src/test/java
            File file = new File("src/test/java/login_details.xml"); 
            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);
            
            Node node = doc.selectSingleNode("//" + elementname);
            return node.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}