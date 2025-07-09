package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class AccountLogoutPage extends RootPage {

	public AccountLogoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='My Account']")
	private WebElement myAccountDropMenu;

	@FindBy(linkText = "Login")
	private WebElement loginOption;

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Logout']")
	private WebElement logoutBreadCrumb;

	@FindBy(xpath = "//a[@class='btn btn-primary'][text()='Continue']")
	private WebElement continueButton;

	@FindBy(xpath = "//div[@id='content']/h1")
	private WebElement pageHeading;

	public String getPageHeading() {
		return getTextOfElement(pageHeading);
	}

	public LandingPage clickOnContinueButton() {
		continueButton.click();
		return new LandingPage(driver);
	}

	public boolean didWeNavigateToAccountLogoutPage() {
		return isElementDisplayed(logoutBreadCrumb);
	}

	public void clickOnMyAccountDropMenu() {
		myAccountDropMenu.click();
	}

	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}
}
