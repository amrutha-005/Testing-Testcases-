package pac1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LabDemo14_OpenCartRegister {

    WebDriver driver;
    WebDriverWait wait;
    String projectPath = System.getProperty("user.dir");

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Step 1: Open the URL (TutorialsNinja Demo)
        driver.get("https://tutorialsninja.com/demo/");
    }

    @Test(dataProvider = "userData")
    public void registerUserTest(String firstName, String lastName, String email, String phone, String password, String confirmPassword) {
        
        // Step 2: Verify application title "Your Store"
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Your Store", "Title verification failed!");

        // Step 3: Click on "My Account" menu option
        WebElement myAccount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='My Account']")));
        myAccount.click();

        // Step 4: Select "Register" option
        WebElement registerOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Register']")));
        registerOption.click();

        // Step 5: Verify the text present on web page as "Register Account"
        WebElement pageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Register Account']")));
        Assert.assertTrue(pageHeader.isDisplayed(), "Page header 'Register Account' not displayed!");

        // Step 6: Enter all details from Excel sheet
        driver.findElement(By.id("input-firstname")).sendKeys(firstName);
        driver.findElement(By.id("input-lastname")).sendKeys(lastName);
        
        // Append unique timestamp to email so repeated test runs pass without duplicate email errors
        String uniqueEmail = System.currentTimeMillis() + "_" + email;
        driver.findElement(By.id("input-email")).sendKeys(uniqueEmail);
        
        driver.findElement(By.id("input-telephone")).sendKeys(phone);
        driver.findElement(By.id("input-password")).sendKeys(password);
        driver.findElement(By.id("input-confirm")).sendKeys(confirmPassword);

        // Step 7: Select "I have read and agree to the Privacy Policy" check box
        WebElement privacyPolicy = driver.findElement(By.name("agree"));
        if (!privacyPolicy.isSelected()) {
            privacyPolicy.click();
        }

        // Step 8: Click on "Continue" button
        WebElement continueBtn = driver.findElement(By.xpath("//input[@value='Continue']"));
        continueBtn.click();

        // Step 9: Verify acknowledgement message "Your Account Has Been Created!"
        WebElement successHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Your Account Has Been Created!']")));
        Assert.assertEquals(successHeader.getText(), "Your Account Has Been Created!", "Account creation failed!");
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData() throws IOException {
        File file = new File(projectPath + File.separator + "UserDetails.xlsx");
        FileInputStream fis = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        // Count valid non-empty data rows
        List<Object[]> validRows = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        for (int i = 1; i < rowCount; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                String firstCellText = formatter.formatCellValue(row.getCell(0)).trim();
                // Skip rows where First_Name is empty
                if (!firstCellText.isEmpty()) {
                    String[] rowData = new String[colCount];
                    for (int j = 0; j < colCount; j++) {
                        XSSFCell cell = row.getCell(j);
                        rowData[j] = formatter.formatCellValue(cell);
                    }
                    validRows.add(rowData);
                }
            }
        }

        workbook.close();
        fis.close();

        // Convert list to Object[][] expected by DataProvider
        return validRows.toArray(new Object[0][0]);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}