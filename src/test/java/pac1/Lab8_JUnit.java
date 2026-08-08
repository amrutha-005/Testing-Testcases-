package pac1;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Lab8_JUnit {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        // Suppress CDP and Selenium logger warnings
        System.setProperty("webdriver.chrome.silentOutput", "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Updated Target URL
        driver.get("https://tutorialsninja.com/demo/");
    }

    @Test
    public void testOpenCartFlowJUnit() {
        // Lab 4 Step 3: Verify Page Title
        wait.until(ExpectedConditions.titleContains("Your Store"));
        Assert.assertEquals("Your Store", driver.getTitle());

        // Lab 3 Steps 3-4: Navigate Desktops -> Mac
        WebElement desktopsMenu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Desktops")));
        desktopsMenu.click();

        WebElement macOption = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Mac (1)")));
        macOption.click();

        // Lab 4 Step 13: Verify Heading
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Mac']")));
        Assert.assertEquals("Mac", heading.getText().trim());

        // Lab 3 Step 5: Sort By Name (A - Z)
        WebElement sortSelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-sort")));
        Select sortDropdown = new Select(sortSelect);
        sortDropdown.selectByVisibleText("Name (A - Z)");

        // Lab 3 Step 6: Click Add to Cart
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Add to Cart']/parent::button")
        ));
        addToCartBtn.click();

        // Lab 4 Step 8 & 14: Search for 'Monitors'
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
        searchBox.clear();
        searchBox.sendKeys("Monitors");

        WebElement searchButton = driver.findElement(By.xpath("//div[@id='search']//button"));
        searchButton.click();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}