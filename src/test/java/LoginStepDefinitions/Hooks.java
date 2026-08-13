package LoginStepDefinitions;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Utilities.ConfigReader;
import Utilities.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

    @Before
    public void setup() {
        String browser = ConfigReader.getvalue("browser");
        if (browser == null) {
            browser = "chrome"; // Default fallback
        }

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            
            DriverManager.setDriver(driver);
            
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            DriverManager.getDriver().manage().window().maximize();
            
            String url = ConfigReader.getvalue("url");
            if (url == null) {
                url = "https://opensource-demo.orangehrmlive.com/";
            }

            DriverManager.getDriver().get(url);
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