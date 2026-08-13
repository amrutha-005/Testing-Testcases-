package Base;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Utilities.ConfigReader;
import Utilities.DriverManager;

// Make sure these are io.cucumber imports!
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    @Before
    public void setup() {
        String browser = ConfigReader.getvalue("browser");
        
        if (browser != null && browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            
            DriverManager.setDriver(driver);
            
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            DriverManager.getDriver().manage().window().maximize();
            DriverManager.getDriver().get(ConfigReader.getvalue("url"));
        }
    }

    @After
    public void teardown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}