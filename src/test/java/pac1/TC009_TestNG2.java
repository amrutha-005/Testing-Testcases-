package pac1;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC009_TestNG2 {

    WebDriverWait wait;
    WebDriver driver;
    String projectpath = System.getProperty("user.dir");

    @Test(dataProvider = "dp")
    public void f(String uname, String pword) {
        login_POM obj = new login_POM(driver);

        obj.enterusername(uname);
        obj.enterpassword(pword);
        obj.clicklogin();

        // Check if Dashboard header is present without throwing NoSuchElementException
        boolean isDashboardDisplayed = driver.findElements(org.openqa.selenium.By.xpath("//h6[text()='Dashboard']")).size() > 0;

        if (uname.equals("Admin") && pword.equals("admin123")) {
            // Expecting successful login
            Assert.assertTrue(isDashboardDisplayed, "Valid credentials failed to log in.");
        } else {
            // Expecting failed login
            Assert.assertFalse(isDashboardDisplayed, "Invalid credentials logged in unexpectedly.");
        }
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

    @DataProvider
    public Object[][] dp() throws InvalidFormatException, IOException {
        File f1 = new File(projectpath + File.separator + "data.xlsx");

        if (!f1.exists()) {
            throw new IOException("File not found at path: " + f1.getAbsolutePath());
        }

        XSSFWorkbook workbook = new XSSFWorkbook(f1);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int totalRows = sheet.getPhysicalNumberOfRows();
        int colcount = sheet.getRow(0).getPhysicalNumberOfCells();

        // Skip row 0 (headers: Cell A, Cell B)
        String[][] data = new String[totalRows - 1][colcount];

        for (int i = 1; i < totalRows; i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < colcount; j++) {
                if (row != null) {
                    XSSFCell cell = row.getCell(j);
                    data[i - 1][j] = (cell != null) ? cell.getStringCellValue() : "";
                } else {
                    data[i - 1][j] = "";
                }
            }
        }

        workbook.close();
        return data;
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("@BeforeClass");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("@AfterClass");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("@BeforeTest");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("@AfterTest");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("@BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("@AfterSuite");
    }
}