package pac1;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class login_POM_Properties {

    WebDriver driver;

    By username;
    By password;
    By button;
    By warningMsg;

    public login_POM_Properties(WebDriver driver) {
        this.driver = driver;

        this.username = getBy(Repository_readfromproperties.getlocator("username"));
        this.password = getBy(Repository_readfromproperties.getlocator("password"));
        this.button = getBy(Repository_readfromproperties.getlocator("loginbutton"));
        this.warningMsg = getBy(Repository_readfromproperties.getlocator("warning_msg"));
    }

    private By getBy(String locator) {
        if (locator == null || !locator.contains(":")) {
            throw new IllegalArgumentException("Locator string format must be 'type:value'. Received: " + locator);
        }

        String[] parts = locator.split(":", 2);
        String type = parts[0];
        String value = parts[1];

        if (type.equalsIgnoreCase("name")) return By.name(value);
        if (type.equalsIgnoreCase("id")) return By.id(value);
        if (type.equalsIgnoreCase("xpath")) return By.xpath(value);
        if (type.equalsIgnoreCase("css")) return By.cssSelector(value);

        throw new RuntimeException("Invalid Locator type: " + type);
    }

    public void enterusername(String uname) {
        driver.findElement(username).sendKeys(uname);
    }

    public void enterpassword(String pword) {
        driver.findElement(password).sendKeys(pword);
    }

    public void clicklogin() {
        driver.findElement(button).click();
    }

    // Fetches the warning banner when login fails
    public WebElement getWarningMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(warningMsg));
    }
}