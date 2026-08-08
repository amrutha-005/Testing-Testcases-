package pac1;

import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Lab11_PageFactory_Test {

    @Test
    public void testPageFactory() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get("https://tutorialsninja.com/demo/");

            TutorialsNinjaPage_PageFactory page = new TutorialsNinjaPage_PageFactory(driver);

            // Step 1: Search Product
            page.searchForProduct("Mac");
            Assert.assertTrue("Search header missing", page.isSearchHeaderDisplayed());

            // Step 2: Select Product
            page.selectProduct();
            Assert.assertEquals("MacBook", page.getProductHeaderText());

            // Step 3: Add to Cart
            page.addToCart();
            Assert.assertTrue("Success alert missing", page.isSuccessAlertDisplayed());

            System.out.println("Page Factory Execution Passed!");

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}