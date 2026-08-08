package pac1;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class Lab7 {

    public static void main(String[] args) {
        // Suppress CDP / Selenium console warnings
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // 1. Launch the Demo Alerts URL
            driver.get("https://demo.automationtesting.in/Alerts.html");

            // --- PART 1: Simple Alert (Alert with OK) ---
            // Click on "Alert with OK" tab
            WebElement alertWithOkTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Alert with OK')]")));
            alertWithOkTab.click();

            // Click the button to display alert box
            WebElement alertBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn-danger')]")));
            alertBtn.click();

            // Switch to alert, verify text, and accept
            Alert simpleAlert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert 1 Text: " + simpleAlert.getText());
            simpleAlert.accept();
            System.out.println("Alert 1 accepted (OK clicked).");

            // --- PART 2: Confirm Box (Alert with OK & Cancel) ---
            // Click on "Alert with OK & Cancel" tab
            WebElement alertWithOkCancelTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Alert with OK & Cancel')]")));
            alertWithOkCancelTab.click();

            // Click the button to display confirm box
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn-primary')]")));
            confirmBtn.click();

            // Switch to alert, verify text, and accept
            Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert 2 Text: " + confirmAlert.getText());
            confirmAlert.accept();
            
            // Verify message on page after accepting
            WebElement confirmResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("demo")));
            System.out.println("Result Message: " + confirmResult.getText());

            // --- PART 3: Prompt Box (Alert with Text Box) ---
            // Click on "Alert with Textbox" tab
            WebElement alertWithTextboxTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Alert with Textbox')]")));
            alertWithTextboxTab.click();

            // Click the button to display prompt box
            WebElement promptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn-info')]")));
            promptBtn.click();

            // Switch to prompt alert, send keys, and accept
            Alert promptAlert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert 3 Text: " + promptAlert.getText());
            promptAlert.sendKeys("Amrutha");
            promptAlert.accept();

            // Verify prompt result message on page
            WebElement promptResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("demo1")));
            System.out.println("Prompt Result Message: " + promptResult.getText());

            System.out.println("\nLab Demo 7 execution completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}