package pac1;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC015_CSV_Registration_Test {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @DataProvider(name = "csvRegistrationData")
    public Iterator<Object[]> getCsvData() throws IOException, CsvException {
        List<Object[]> data = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "/UserDetails.csv"))) {
        		            List<String[]> allRows = reader.readAll();
            
            // Skip header row (row 0) and iterate through remaining rows
            for (int i = 1; i < allRows.size(); i++) {
                data.add(allRows.get(i));
            }
        }
        return data.iterator();
    }

    @Test(dataProvider = "csvRegistrationData")
    public void testUserRegistrationFromCSV(String firstName, String lastName, String email, 
                                           String telephone, String password, String confirmPassword) {
        
        // 1. Open URL
        driver.get("https://tutorialsninja.com/demo/");

        // 2. Verify Title "Your Store"
        Assert.assertEquals(driver.getTitle(), "Your Store", "Page title mismatch!");

        // 3. Click "My Account" menu option
        driver.findElement(By.xpath("//a[@title='My Account']")).click();

        // 4. Select "Register" option
        driver.findElement(By.linkText("Register")).click();

        // 5. Verify text present on web page as "Register Account"
        WebElement pageHeading = driver.findElement(By.xpath("//h1[text()='Register Account']"));
        Assert.assertTrue(pageHeading.isDisplayed(), "'Register Account' heading is not displayed!");

        // 6. Enter details from CSV (Append timestamp to email to keep it unique per run)
        String uniqueEmail = System.currentTimeMillis() + "_" + email;

        driver.findElement(By.id("input-firstname")).sendKeys(firstName);
        driver.findElement(By.id("input-lastname")).sendKeys(lastName);
        driver.findElement(By.id("input-email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("input-telephone")).sendKeys(telephone);
        driver.findElement(By.id("input-password")).sendKeys(password);
        driver.findElement(By.id("input-confirm")).sendKeys(confirmPassword);

        // 7. Select "Privacy Policy" checkbox
        driver.findElement(By.name("agree")).click();

        // 8. Click on "Continue" button
        driver.findElement(By.xpath("//input[@value='Continue']")).click();

        // 9. Verify acknowledgment message "Your Account Has Been Created!"
        WebElement successHeader = driver.findElement(By.xpath("//h1[text()='Your Account Has Been Created!']"));
        Assert.assertTrue(successHeader.isDisplayed(), "Account creation verification failed!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}