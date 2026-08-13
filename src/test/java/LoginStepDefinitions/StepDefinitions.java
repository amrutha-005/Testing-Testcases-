package LoginStepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Utilities.DriverManager;
import io.cucumber.java.en.*;

public class StepDefinitions {

    @Given("Open the OrangeHRM login page")
    public void open_the_orange_hrm_login_page() {
        // Page navigation is automatically handled by Hooks @Before
    }

    @When("Enter the username {string}")
    public void enter_the_username(String username) {
        DriverManager.getDriver().findElement(By.name("username")).sendKeys(username);
    }

    @And("Enter the password {string}")
    public void enter_the_password(String password) {
        DriverManager.getDriver().findElement(By.name("password")).sendKeys(password);
    }

    @And("Click on login button")
    public void click_on_login_button() {
        DriverManager.getDriver().findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Then("Dashboard page should be displayed")
    public void dashboard_page_should_be_displayed() {
        WebElement dashboard = DriverManager.getDriver().findElement(By.xpath("//h6[text()='Dashboard']"));
        Assert.assertTrue(dashboard.isDisplayed(), "Login Failed: Dashboard not displayed");
    }
}