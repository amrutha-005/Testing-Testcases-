package pac1;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC005_TestNG_Excel {

    ExtentReports extent = ExtentManager.getinstance();
    WebDriver driver;
    String projectpath = System.getProperty("user.dir");

    
    @Test(dataProvider = "dp")
    public void f(String uname, String pword) throws IOException {

        ExtentTest test = extent.createTest("Login Test - " + uname);

        login_POM obj = new login_POM(driver);

        obj.enterusername(uname);
        test.info("User name Entered");

        obj.enterpassword(pword);
        test.info("Password Entered");

        obj.clicklogin();
        test.info("Login button clicked");

        if (obj.isDashboardDisplayed()) {
            test.pass("Login Successful for valid user: " + uname)
                .addScreenCaptureFromPath(capturescreenshot("pass_" + System.currentTimeMillis()));
        } else if (obj.isErrorMessageDisplayed()) {
            test.pass("Login correctly blocked invalid user: " + uname)
                .addScreenCaptureFromPath(capturescreenshot("invalid_blocked_" + System.currentTimeMillis()));
        } else {
            test.fail("Unexpected error or page load failure for user: " + uname)
                .addScreenCaptureFromPath(capturescreenshot("error_" + System.currentTimeMillis()));
        }
    }

    public String capturescreenshot(String testname) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String spath = projectpath + File.separator + "Screenshots" + File.separator + testname + ".png";
        File destination = new File(spath);
        FileUtils.copyFile(source, destination);
        return spath;
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("@BeforeMethod");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("@AfterMethod");
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
    }

  

@DataProvider
public Object[][] dp() throws InvalidFormatException, IOException {

    File f1 = new File(projectpath + File.separator + "data.xlsx");
    XSSFWorkbook workbook = new XSSFWorkbook(f1);
    XSSFSheet sheet = workbook.getSheetAt(0);

    int rowcount = sheet.getPhysicalNumberOfRows();
    System.out.println("No of rows: " + rowcount);

    // Skip row 0 (headers), allocate array for data rows only
    String[][] data = new String[rowcount - 1][2];

    for (int i = 1; i < rowcount; i++) {
        data[i - 1][0] = sheet.getRow(i).getCell(0).getStringCellValue();
        data[i - 1][1] = sheet.getRow(i).getCell(1).getStringCellValue();
    }

    workbook.close();
    return data;
}
}