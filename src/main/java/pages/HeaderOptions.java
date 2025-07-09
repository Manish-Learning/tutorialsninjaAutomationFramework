package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class HeaderOptions extends RootPage {

	public HeaderOptions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a/i[@class='fa fa-phone']")
	private WebElement phoneIconOption;

	@FindBy(xpath = "//a/i[@class='fa fa-heart']")
	private WebElement heartIconOption;

	@FindBy(xpath = "//span[text()='Shopping Cart']")
	private WebElement shoppingCartOption;

	@FindBy(xpath = "//span[text()='Checkout']")
	private WebElement checkOutOption;

	@FindBy(xpath = "//div[@id='logo']//a")
	private WebElement logoOption;

	@FindBy(xpath = "//button[@class='btn btn-default btn-lg']")
	private WebElement clickOnSearchButton;

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Account']")
	private WebElement accountBreadCrumb;

	@FindBy(xpath = "//span[text()='My Account']")
	private WebElement myAccountDropMenu;

	@FindBy(linkText = "Login")
	private WebElement loginOption;

	@FindBy(linkText = "Logout")
	private WebElement logoutOption;

	@FindBy(name = "search")
	private WebElement searchBoxField;

	public String getPlaceHolderTextOfSearchBoxField() {
		return getDomAttributeOfElement(searchBoxField, "placeholder");
	}

	public void enterProductIntoSearchBoxField(String productName) {
		searchBoxField.sendKeys(productName);
	}

	public boolean isLogoutOptionAvaialable() {
		return isElementDisplayed(logoutOption);
	}

	public boolean isLoginOptionAvailable() {
		return isElementDisplayed(loginOption);
	}

	public AccountLogoutPage selectLogoutOption() {
		logoutOption.click();
		return new AccountLogoutPage(driver);
	}

	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}

	public void clickOnMyAccountDropMenu() {
		myAccountDropMenu.click();
	}

	public ContactUsPage clickOnPhoneIconOption() {
		phoneIconOption.click();
		return new ContactUsPage(driver);
	}

	public LoginPage clickOnWishListHeartOption() {
		heartIconOption.click();
		return new LoginPage(driver);
	}

	public ShoppingCartPage clickOnShoppingCartOption() {
		shoppingCartOption.click();
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage clickOnCheckOutOption() {
		checkOutOption.click();
		return new ShoppingCartPage(driver);
	}

	public LoginPage clickOnAccountBreadCrumb() {
		accountBreadCrumb.click();
		return new LoginPage(driver);
	}

	public SearchPage clickOnSearchButton() {
		clickOnSearchButton.click();
		return new SearchPage(driver);
	}

	public LandingPage clickOnLogoOption() {
		logoOption.click();
		return new LandingPage(driver);
	}

}
