package pac1;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
public class login_POM {
	WebDriver driver;
	
	By username =By.name("username");
	By password=By.name("password");
	By button=By.xpath("//button[@type='submit']");
	By Dashboard1=By.xpath("//h6[text()='Dashboard']");
	
	public login_POM(WebDriver driver) {
	
		this.driver=driver;
		
	}
 
	public void enterusername(String uname)
	{
		driver.findElement(username).sendKeys(uname);
		
	}
	public void enterpassword(String pword)
	{
		driver.findElement(password).sendKeys(pword);
		
	}
	
	public void clicklogin()
	{
		driver.findElement(button).click();
		
	}
	
	public WebElement dashboard()
	{
		return driver.findElement(Dashboard1);
	}
	
	
}
 