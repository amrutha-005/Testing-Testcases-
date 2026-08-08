package pac1;
 
import java.util.List;
 
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
 
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class TC004_Alerts {
 
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
WebDriverManager.chromedriver().setup();
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://mail.rediff.com/cgi-bin/login.cgi");
		driver.manage().window().maximize();
		Thread.sleep(5000);
		driver.findElement(By.name("proceed")).click();
		Alert simplealert=driver.switchTo().alert();
		System.out.println("alerts message:"+simplealert.getText());
		simplealert.accept();
		
		
	
	}
 
}
 