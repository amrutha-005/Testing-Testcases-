package pac1;

import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Lab11_StandardPOM_Test {

    @Test
    public void testStandardPOM() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get("https://tutorialsninja.com/demo/");

            TutorialsNinjaPage_POM page = new TutorialsNinjaPage_POM(driver);

            // Step 1: Search Product
            page.enterSearchTerm("Mac");
            page.clickSearch();
            Assert.assertTrue("Search header missing", page.isSearchHeaderDisplayed());

            // Step 2: Select Product
            page.clickProduct();
            Assert.assertEquals("MacBook", page.getProductHeaderText());

            // Step 3: Add to Cart
            page.clickAddToCart();
            Assert.assertTrue("Success alert missing", page.isSuccessAlertDisplayed());

            System.out.println("Standard POM Execution Passed!");

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}