package pac1;

import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Lab9_JUnit_Test {

    // Common method executing the Lab 3 & Lab 4 flow
    public void runLab3Lab4Flow(WebDriver driver, String browserName) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            // 1. Navigate to URL
            driver.get("https://tutorialsninja.com/demo/");

            // 2. JUnit Assertion: Verify Home Page Title
            String expectedTitle = "Your Store";
            Assert.assertEquals("Page title verification failed on " + browserName, expectedTitle, driver.getTitle());

            // 3. Search for a product (e.g., Mac)
            WebElement searchBox = driver.findElement(By.name("search"));
            searchBox.clear();
            searchBox.sendKeys("Mac");

            WebElement searchButton = driver.findElement(By.cssSelector("button.btn.btn-default.btn-lg"));
            searchButton.click();

            // 4. JUnit Assertion: Verify Search Header
            WebElement searchHeader = driver.findElement(By.xpath("//h1[contains(text(),'Search - Mac')]"));
            Assert.assertTrue("Search heading not displayed on " + browserName, searchHeader.isDisplayed());

            // 5. Select Product
            WebElement macBookProduct = driver.findElement(By.linkText("MacBook"));
            macBookProduct.click();

            // 6. JUnit Assertion: Verify Product Name Page Header
            WebElement productHeader = driver.findElement(By.xpath("//h1[text()='MacBook']"));
            Assert.assertEquals("Product name mismatch on " + browserName, "MacBook", productHeader.getText());

            // 7. Add to Cart
            WebElement addToCartBtn = driver.findElement(By.id("button-cart"));
            addToCartBtn.click();

            // 8. JUnit Assertion: Verify Success Alert Banner
            WebElement successBanner = driver.findElement(By.cssSelector(".alert-success"));
            Assert.assertTrue("Success banner not displayed on " + browserName, successBanner.getText().contains("Success: You have added MacBook to your shopping cart!"));

            System.out.println("Lab 3 & Lab 4 test flow completed successfully on: " + browserName);

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @Test
    public void testOnChrome() {
        System.out.println("Executing Lab 9 on Chrome Driver...");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        runLab3Lab4Flow(driver, "Chrome");
    }

    @Test
    public void testOnEdge() {
        System.out.println("Executing Lab 9 on Edge Driver...");
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
        runLab3Lab4Flow(driver, "Edge");
    }
}