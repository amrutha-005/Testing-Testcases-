package pac1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC001 {
	
	public static void main(String[] args) {
		
		
        WebDriverManager.chromedriver().setup();
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://google.com/");
		
		
		System.out.println("The tile of opencart page is:"+driver.getTitle());
		WebElement search=driver.findElement(By.id("APjFqb"));
		search.sendKeys("Automation Tesing Tools");
		search.submit();
	}


}
