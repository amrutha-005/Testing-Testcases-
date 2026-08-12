package pac1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC005_TestNG_PageFactory {

    WebDriver driver;

    @Test(dataProvider = "dp")
    public void f(String uname, String pword) {
        // Corrected reference to your existing login_POM class
        login_POM obj = PageFactory.initElements(driver, login_POM.class);

        // Perform login actions using page methods
        obj.enterusername(uname);
        obj.enterpassword(pword);
        obj.clicklogin();

        // Check if Dashboard header is displayed
        boolean isDashboardPresent = driver.findElements(By.xpath("//h6[text()='Dashboard']")).size() > 0;

        // Conditional assertions for positive and negative tests
        if (uname.equals("Admin")) {
            Assert.assertTrue(isDashboardPresent, "Login failed for valid user: " + uname);
        } else {
            Assert.assertFalse(isDashboardPresent, "Login unexpectedly succeeded for invalid user: " + uname);
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("@BeforeMethod");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("@AfterMethod");
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider
    public Object[][] dp() {
        return new Object[][] {
            new Object[] { "Admin", "admin123" },
            new Object[] { "pooja", "welcome" }
        };
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("@BeforeClass");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("@AfterClass");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("@BeforeTest");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("@AfterTest");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("@BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("@AfterSuite");
    }
}