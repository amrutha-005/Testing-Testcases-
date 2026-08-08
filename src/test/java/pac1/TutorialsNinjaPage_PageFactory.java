package pac1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TutorialsNinjaPage_PageFactory {

    private WebDriver driver;

    @FindBy(name = "search")
    private WebElement searchBox;

    @FindBy(css = "button.btn.btn-default.btn-lg")
    private WebElement searchButton;

    @FindBy(xpath = "//h1[contains(text(),'Search - Mac')]")
    private WebElement searchHeader;

    @FindBy(linkText = "MacBook")
    private WebElement productLink;

    @FindBy(xpath = "//h1[text()='MacBook']")
    private WebElement productHeader;

    @FindBy(id = "button-cart")
    private WebElement addToCartButton;

    @FindBy(css = ".alert-success")
    private WebElement successAlert;

    public TutorialsNinjaPage_PageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initializes elements
    }

    public void searchForProduct(String product) {
        searchBox.clear();
        searchBox.sendKeys(product);
        searchButton.click();
    }

    public boolean isSearchHeaderDisplayed() {
        return searchHeader.isDisplayed();
    }

    public void selectProduct() {
        productLink.click();
    }

    public String getProductHeaderText() {
        return productHeader.getText();
    }

    public void addToCart() {
        addToCartButton.click();
    }

    public boolean isSuccessAlertDisplayed() {
        return successAlert.isDisplayed();
    }
}