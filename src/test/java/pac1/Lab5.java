package pac1;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab5 {

    public static void main(String[] args) {
        // Suppress CDP and Selenium logger output in console
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

        // Pre-declare variables prior to execution block
        WebDriver driver;
        WebDriverWait wait;
        ChromeOptions options = new ChromeOptions();

        String expectedTitle = "Your Store";
        String actualTitle;
        String invalidString33Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFG"; // 33 characters (boundary > 32)
        String validFirstName = "John";
        String validLastName = "Doe";
        String validTelephone = "9876543210";
        String validPassword = "SecurePass123!";
        String uniqueEmail;

        // Pre-declare WebElements
        WebElement myAccountDropdown;
        WebElement registerOption;
        WebElement heading;
        WebElement continueButton;
        WebElement warningAlert;
        WebElement firstNameInput;
        WebElement firstNameError;
        WebElement lastNameInput;
        WebElement lastNameError;
        WebElement emailInput;
        WebElement telephoneInput;
        WebElement passwordInput;
        WebElement confirmPasswordInput;
        WebElement newsletterYesRadio;
        WebElement privacyPolicyCheckbox;
        WebElement successHeading;
        WebElement successContinueBtn;
        WebElement orderHistoryLink;

        // Configure Chrome Options for clean automation run
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        driver = new ChromeDriver(options);

        // Inject CDP script to prevent webdriver detection
        Map<String, Object> params = new HashMap<>();
        params.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined});");
        ((ChromeDriver) driver).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", params);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // ==========================================
            // PART 1: LAUNCH APPLICATION & INITIAL CHECKS
            // ==========================================
            driver.get("https://tutorialsninja.com/demo/");

            actualTitle = driver.getTitle();
            if (actualTitle.contains(expectedTitle)) {
                System.out.println("Part 1 Passed: Title verified -> " + actualTitle);
            }

            myAccountDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='My Account']")
            ));
            myAccountDropdown.click();

            registerOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='Register']")
            ));
            registerOption.click();

            heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[text()='Register Account']")
            ));
            if (heading.isDisplayed()) {
                System.out.println("Part 1 Passed: Heading 'Register Account' verified.");
            }

            // Click Continue on empty form to trigger Privacy Policy alert
            continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Continue']")));
            continueButton.click();

            warningAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.alert-danger")
            ));
            System.out.println("Part 1 Passed: Privacy Policy warning message verified -> " + warningAlert.getText());


            
            // PART 2: FIELD BOUNDARY VALIDATION
           
            // 1. First Name > 32 characters validation
            firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));
            firstNameInput.clear();
            firstNameInput.sendKeys(invalidString33Chars);

            continueButton = driver.findElement(By.cssSelector("input[value='Continue']"));
            continueButton.click();

            firstNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='input-firstname']/following-sibling::div[@class='text-danger']")
            ));
            System.out.println("Part 2 Passed: First Name length error verified -> " + firstNameError.getText());

            // 2. Fix First Name & test Last Name > 32 characters validation
            firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));
            firstNameInput.clear();
            firstNameInput.sendKeys(validFirstName);

            lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-lastname")));
            lastNameInput.clear();
            lastNameInput.sendKeys(invalidString33Chars);

            continueButton = driver.findElement(By.cssSelector("input[value='Continue']"));
            continueButton.click();

            lastNameError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='input-lastname']/following-sibling::div[@class='text-danger']")
            ));
            System.out.println("Part 2 Passed: Last Name length error verified -> " + lastNameError.getText());

            // PART 3: FORM SUBMISSION WITH VALID DATA
            
            firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));
            firstNameInput.clear();
            firstNameInput.sendKeys(validFirstName);

            lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-lastname")));
            lastNameInput.clear();
            lastNameInput.sendKeys(validLastName);

            uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";
            emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email")));
            emailInput.clear();
            emailInput.sendKeys(uniqueEmail);

            telephoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-telephone")));
            telephoneInput.clear();
            telephoneInput.sendKeys(validTelephone);

            passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-password")));
            passwordInput.clear();
            passwordInput.sendKeys(validPassword);

            confirmPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-confirm")));
            confirmPasswordInput.clear();
            confirmPasswordInput.sendKeys(validPassword);

            // Subscribe to Newsletter
            newsletterYesRadio = driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']"));
            if (!newsletterYesRadio.isSelected()) {
                newsletterYesRadio.click();
            }

            // Accept Privacy Policy
            privacyPolicyCheckbox = driver.findElement(By.name("agree"));
            if (!privacyPolicyCheckbox.isSelected()) {
                privacyPolicyCheckbox.click();
            }

            // Submit Registration Form
            continueButton = driver.findElement(By.cssSelector("input[value='Continue']"));
            continueButton.click();


            // PART 4: VERIFY REGISTRATION & ORDER HISTORY
            
            successHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[text()='Your Account Has Been Created!']")
            ));
            if (successHeading.isDisplayed()) {
                System.out.println("Part 4 Passed: Heading 'Your Account Has Been Created!' verified.");
            }

            successContinueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Continue']")));
            successContinueBtn.click();

            orderHistoryLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='View your order history']")
            ));
            orderHistoryLink.click();

            System.out.println("LAB DEMO 5 EXECUTION COMPLETED SUCCESSFULLY!");

        } catch (Exception e) {
            System.err.println("Test Execution Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}