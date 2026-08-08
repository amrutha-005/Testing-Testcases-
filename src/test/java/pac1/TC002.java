package pac1;



import java.util.List;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
 
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class  TC002  {
 
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
WebDriverManager.chromedriver().setup();
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://the-internet.herokuapp.com/dropdown");
		driver.manage().window().maximize();
		Thread.sleep(5000);
		WebElement dropdown=driver.findElement(By.id("dropdown"));
		
		Select select=new Select(dropdown);
		
		List<WebElement> options=select.getOptions();
		
		for(WebElement option:options)
		{
			System.out.println(option.getText());
		}
		
	}
 
}
 