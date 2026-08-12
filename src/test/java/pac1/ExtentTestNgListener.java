package pac1;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestNgListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getinstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Getter to access current thread's ExtentTest instance inside test classes
    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().log(Status.INFO, "Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed");
        test.get().fail(result.getThrowable());

        // Automatically capture screenshot if WebDriver instance is available
        Object testObject = result.getInstance();
        Class<?> currentClass = result.getTestClass().getRealClass();

        try {
            WebDriver driver = (WebDriver) currentClass.getDeclaredField("driver").get(testObject);
            if (driver != null) {
                String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            test.get().info("Could not attach screenshot on failure: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    // Screenshot helper method using Commons IO
    private String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationPath = System.getProperty("user.dir") 
                + File.separator + "Screenshots" 
                + File.separator + screenshotName + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(destinationPath);
        FileUtils.copyFile(source, destination);
        return destinationPath;
    }
}