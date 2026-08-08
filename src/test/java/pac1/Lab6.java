package pac1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Lab6 {
    public static void main(String[] args) {
        // Suppress CDP / Selenium console logs
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // 1. Open Base URL
            driver.get("https://tutorialsninja.com/demo/");

            // Safe click on 'My Account' -> Login
            WebElement myAccount = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@title,'My Account') or .//span[normalize-space()='My Account']]")
            ));
            js.executeScript("arguments[0].click();", myAccount);

            WebElement loginOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Login']")));
            loginOption.click();

            // Credentials
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email"))).sendKeys("your_email@example.com");
            driver.findElement(By.id("input-password")).sendKeys("YourPassword123");
            driver.findElement(By.xpath("//input[@value='Login']")).click();

            // 2. Go to 'Components' tab and select 'Monitors'
            WebElement componentsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Components']")));
            componentsTab.click();
            
            WebElement monitorsOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Monitors')]")));
            monitorsOption.click();

            // 3. Select 25 from 'Show' dropdown
            WebElement showDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-limit")));
            Select selectShow = new Select(showDropdown);
            selectShow.selectByVisibleText("25");

            // 4. Click on 'Add to cart' for the first item
            WebElement firstAddToCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@onclick,'cart.add')])[1]")));
            firstAddToCart.click();

            // 5. Click on 'Specification' tab
            WebElement specTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Specification']")));
            specTab.click();

            // 6. Verify details present on the page
            WebElement specDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tab-specification']")));
            if (specDetail.isDisplayed()) {
                System.out.println("Specification details verified on page.");
            }

            // 7. Click on 'Add to Wish list' button & Verify message
            WebElement wishListBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@data-original-title,'Add to Wish List')]")));
            wishListBtn.click();

            WebElement wishListMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'alert-success') or contains(@class,'alert-info')]")));
            System.out.println("Wishlist Message: " + wishListMsg.getText());

            // 8. Search 'Mobile' in top search bar
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
            searchBox.clear();
            searchBox.sendKeys("Mobile");

            driver.findElement(By.xpath("//div[@id='search']//button")).click();

            // 9. Select 'Search in product descriptions' checkbox
            WebElement descCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("description")));
            if (!descCheckbox.isSelected()) {
                descCheckbox.click();
            }
            driver.findElement(By.id("button-search")).click();

            // 10. Click on link 'HTC Touch HD'
            WebElement htcLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='HTC Touch HD']")));
            htcLink.click();

            // 11. Clear Qty, set to '3', and Add to Cart
            WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-quantity")));
            qtyInput.clear();
            qtyInput.sendKeys("3");

            WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart")));
            js.executeScript("arguments[0].scrollIntoView(true);", cartButton);
            js.executeScript("arguments[0].click();", cartButton);

            // 12. Verify Cart success message
            WebElement cartMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'alert-success')]")));
            System.out.println("Cart Message: " + cartMsg.getText());

            // 13. Click 'View Cart' adjacent to search button & Verify Product
            WebElement viewCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='cart']//button")));
            viewCartBtn.click();

            WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='text-left']/a[text()='HTC Touch HD']")));
            System.out.println("Product in Cart Verified: " + cartItem.getText());

            // 14. Click 'Checkout' button
            driver.findElement(By.xpath("//a[contains(@href,'checkout/checkout')]")).click();

            // 15. Logout via Direct URL / Right Column Nav
            try {
                WebElement rightLogoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'account/logout') and text()='Logout']")));
                rightLogoutLink.click();
            } catch (Exception ex) {
                driver.get("https://tutorialsninja.com/demo/index.php?route=account/logout");
            }

            // 16. Verify 'Account Logout' heading
            WebElement logoutHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Account Logout']")));
            System.out.println("Logout Verified: " + logoutHeader.getText());

            // 17. Click Continue button
            driver.findElement(By.xpath("//a[text()='Continue']")).click();

            System.out.println("Execution completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}