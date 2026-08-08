package pac1;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC005_TestNG_Multibrowser {

    // ThreadLocal ensures each parallel browser thread runs safely on its own driver
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    @Test(dataProvider = "dp")
    public void f(String uname, String pword) {
        getDriver().findElement(By.name("username")).sendKeys(uname);
        getDriver().findElement(By.name("password")).sendKeys(pword);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        WebElement dashboard = getDriver().findElement(By.xpath("//h6[text()='Dashboard']"));
        if (dashboard.isDisplayed()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail("Login Failed: Dashboard not displayed");
        }
    }

    @Parameters("browser")
    @BeforeMethod
    public void beforeMethod(String browser) {
        System.out.println("@BeforeMethod - Browser: " + browser);

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver.set(new EdgeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().get("https://opensource-demo.orangehrmlive.com/");
        getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("@AfterMethod");
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }

    @DataProvider
    public Object[][] dp() {
        return new Object[][] {
            new Object[] { "Admin", "admin123" }
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