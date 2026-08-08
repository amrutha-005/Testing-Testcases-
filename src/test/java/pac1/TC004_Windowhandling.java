package pac1;
 
import java.util.List;
import java.util.Set;
 
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
 
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class TC004_Windowhandling {
 
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
WebDriverManager.chromedriver().setup();
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://letcode.in/window");
		driver.manage().window().maximize();
		Thread.sleep(5000);
		String parentwindow=driver.getWindowHandle();
		System.out.println("parentwindow is:"+parentwindow);
		driver.findElement(By.id("multi")).click();
		Set<String> windows=driver.getWindowHandles();
		
		for(String window:windows)
		{
			if(!window.equals(parentwindow))
			{
				driver.switchTo().window(window);
				System.out.println("window title is:"+driver.getTitle());
				
			}
		}
		
	
		driver.quit();
	
	}
 
}