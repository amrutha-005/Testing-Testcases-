package pac1;
 
import java.io.File;
 
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Node;
public class xmlobjectrepository {
	public static String getlocator(String elementname)
	{
		try
		{
			File file=new File("objectrepository.xml");
			SAXReader reader=new SAXReader();
			Document doc=reader.read(file);
			Node node=doc.selectSingleNode(elementname);
			return node.getText();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
 
}