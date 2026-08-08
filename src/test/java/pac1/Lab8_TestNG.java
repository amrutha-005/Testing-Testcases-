package pac1;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
public class Lab8_TestNG {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void beforeMethod() {
        // Suppress CDP and Selenium logger warnings
        System.setProperty("webdriver.chrome.silentOutput", "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Reporter.log("Chrome Browser opened and maximized.", true);
        
        // Updated Target URL
        driver.get("https://tutorialsninja.com/demo/");
        Reporter.log("Navigated to TutorialsNinja demo site.", true);
    }

    @Test
    public void testOpenCartFlowTestNG() {
        // Lab 4 Step 3: Verify Page Title
        wait.until(ExpectedConditions.titleContains("Your Store"));
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Your Store", "Title validation failed!");
        Reporter.log("Verified page title: " + pageTitle, true);

        // Lab 3 Steps 3-4: Navigate Desktops -> Mac
        WebElement desktopsMenu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Desktops")));
        desktopsMenu.click();

        WebElement macOption = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Mac (1)")));
        macOption.click();
        Reporter.log("Navigated to Desktops -> Mac section.", true);

        // Lab 4 Step 13: Verify Heading
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Mac']")));
        Assert.assertEquals(heading.getText().trim(), "Mac", "Heading validation failed!");
        Reporter.log("Verified section heading: " + heading.getText(), true);

        // Lab 3 Step 5: Sort By Name (A - Z)
        WebElement sortSelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-sort")));
        Select sortDropdown = new Select(sortSelect);
        sortDropdown.selectByVisibleText("Name (A - Z)");
        Reporter.log("Sorted products by Name (A - Z).", true);

        // Lab 3 Step 6: Click Add to Cart
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Add to Cart']/parent::button")
        ));
        addToCartBtn.click();
        Reporter.log("Clicked Add to Cart button.", true);

        // Lab 4 Step 8 & 14: Search for 'Monitors'
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        searchBox.clear();
        searchBox.sendKeys("Monitors");

        WebElement searchButton = driver.findElement(By.xpath("//div[@id='search']//button"));
        searchButton.click();
        Reporter.log("Searched for product: Monitors", true);
    }

    @AfterMethod
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
            Reporter.log("Browser closed successfully.", true);
        }
    }
}