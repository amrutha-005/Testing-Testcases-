package pac1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TutorialsNinjaPage_POM {

    private WebDriver driver;

    // Locators
    private By searchBox = By.name("search");
    private By searchButton = By.cssSelector("button.btn.btn-default.btn-lg");
    private By searchHeader = By.xpath("//h1[contains(text(),'Search - Mac')]");
    private By productLink = By.linkText("MacBook");
    private By productHeader = By.xpath("//h1[text()='MacBook']");
    private By addToCartButton = By.id("button-cart");
    private By successAlert = By.cssSelector(".alert-success");

    public TutorialsNinjaPage_POM(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterSearchTerm(String product) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(product);
    }

    public void clickSearch() {
        driver.findElement(searchButton).click();
    }

    public boolean isSearchHeaderDisplayed() {
        return driver.findElement(searchHeader).isDisplayed();
    }

    public void clickProduct() {
        driver.findElement(productLink).click();
    }

    public String getProductHeaderText() {
        return driver.findElement(productHeader).getText();
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public boolean isSuccessAlertDisplayed() {
        return driver.findElement(successAlert).isDisplayed();
    }
}