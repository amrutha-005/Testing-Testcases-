package pac1;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC010_POM_Properties_Test {

    WebDriver driver;
    login_POM_Properties loginPage;

    @BeforeMethod
    public void setup() throws IOException {
        Repository_readfromproperties.loadproperties();

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://tutorialsninja.com/demo/index.php?route=account/login");

        loginPage = new login_POM_Properties(driver);
    }

    @Test
    public void testInvalidLoginWithPOMProperties() {
        loginPage.enterusername("invalid_user_123@test.com");
        loginPage.enterpassword("WrongPassword123");
        loginPage.clicklogin();

        String alertText = loginPage.getWarningMessage().getText();
        System.out.println("Alert Message Displayed: " + alertText);

        Assert.assertTrue(alertText.contains("Warning: No match for E-Mail Address and/or Password."));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}