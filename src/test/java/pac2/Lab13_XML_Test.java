package pac2;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Lab13_XML_Test {

    WebDriver driver;

    // Helper method to convert "type:value" string from XML into a By locator
    public By getBy(String locator) {
        if (locator == null) return null;
        
        if (locator.startsWith("name:")) {
            return By.name(locator.split(":", 2)[1]);
        } else if (locator.startsWith("xpath:")) {
            return By.xpath(locator.split(":", 2)[1]);
        } else if (locator.startsWith("id:")) {
            return By.id(locator.split(":", 2)[1]);
        }
        return null;
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginWithXMLRepository() {
        // Fetch locators dynamically from XML file via xmlobjectrepository
        String unameLocator = xmlobjectrepository.getlocator("username");
        String passLocator = xmlobjectrepository.getlocator("password");
        String btnLocator = xmlobjectrepository.getlocator("loginbutton");
        String dashLocator = xmlobjectrepository.getlocator("dashboard");

        // Perform login operations
        driver.findElement(getBy(unameLocator)).sendKeys("Admin");
        driver.findElement(getBy(passLocator)).sendKeys("admin123");
        driver.findElement(getBy(btnLocator)).click();

        // Verify successful login by checking Dashboard visibility
        boolean isDisplayed = driver.findElements(getBy(dashLocator)).size() > 0;
        Assert.assertTrue(isDisplayed, "Login failed using XML Object Repository!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}