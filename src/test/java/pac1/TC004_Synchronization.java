package pac1;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC004_Synchronization {

    public static void main(String[] args) {
        // Suppress CDP and Selenium logger warnings
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        
        // Implicit Wait setup
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Explicit Wait setup for dynamic OrangeHRM elements
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Explicit wait guarantees field readiness before sending inputs
            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            username.sendKeys("Admin");

            WebElement password = driver.findElement(By.name("password"));
            password.sendKeys("admin123");

            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // Verify login success by checking dashboard header
            WebElement dashboardHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
            System.out.println("Login Successful! Header: " + dashboardHeader.getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}