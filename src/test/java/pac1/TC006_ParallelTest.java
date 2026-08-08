package pac1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC006_ParallelTest {

    @Test
    public void test1() throws InterruptedException {
        System.out.println("test1 (Chrome) Thread ID: " + Thread.currentThread().getId());
        WebDriverManager.chromedriver().setup();
        
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://the-internet.herokuapp.com/dropdown");
            Thread.sleep(5000);
        } finally {
            driver.quit(); // Closes the browser and ends the driver process
        }
    }

    @Test
    public void test12() throws InterruptedException {
        System.out.println("test2 (Edge) Thread ID: " + Thread.currentThread().getId());
        WebDriverManager.edgedriver().setup();
        
        WebDriver driver = new EdgeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.amazon.in");
            Thread.sleep(5000);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void test3() throws InterruptedException {
        System.out.println("test3 (Edge) Thread ID: " + Thread.currentThread().getId());
        WebDriverManager.edgedriver().setup();
        
        WebDriver driver = new EdgeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.yahoo.com");
            Thread.sleep(5000);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void test4() throws InterruptedException {
        System.out.println("test4 (Firefox) Thread ID: " + Thread.currentThread().getId());
        WebDriverManager.firefoxdriver().setup();
        
        WebDriver driver = new FirefoxDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.rediffmail.com");
            Thread.sleep(5000);
        } finally {
            driver.quit();
        }
    }
}