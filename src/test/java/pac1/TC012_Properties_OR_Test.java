package pac1;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC012_Properties_OR_Test {

    WebDriver driver;
    Properties prop;

    @BeforeMethod
    public void setup() throws IOException {
        // Load the Properties file
        prop = new Properties();
        FileInputStream fis = new FileInputStream("config.property");
        prop.load(fis);

        // Initialize Driver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testLoginUsingPropertiesOR() {
        // 1. Navigate to Application URL loaded from Properties file
        driver.get(prop.getProperty("url"));

        // 2. Click My Account dropdown using XPath locator from Properties file
        driver.findElement(By.xpath(prop.getProperty("my_account"))).click();

        // 3. Click Login option using Link Text locator from Properties file
        driver.findElement(By.linkText(prop.getProperty("login_link"))).click();

        // 4. Enter Email ID using ID locator from Properties file
        driver.findElement(By.id(prop.getProperty("email_input"))).sendKeys("user1@testdomain.com");

        // 5. Enter Password using ID locator from Properties file
        driver.findElement(By.id(prop.getProperty("password_input"))).sendKeys("Password123!");

        // 6. Click Login button using XPath locator from Properties file
        driver.findElement(By.xpath(prop.getProperty("login_button"))).click();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}