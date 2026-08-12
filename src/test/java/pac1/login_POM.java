package pac1;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class login_POM {
    WebDriver driver;

    public login_POM(WebDriver driver) {
        this.driver = driver;
    }

    public void enterusername(String uname) {
        driver.findElement(By.name("username")).sendKeys(uname);
    }

    public void enterpassword(String pword) {
        driver.findElement(By.name("password")).sendKeys(pword);
    }

    public void clicklogin() {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    // Check if login succeeded
    public boolean isDashboardDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[text()='Dashboard']"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Check if OrangeHRM showed the invalid credentials message
    public boolean isErrorMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class,'oxd-alert-content-text')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}