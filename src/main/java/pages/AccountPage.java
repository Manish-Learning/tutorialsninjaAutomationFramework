package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class AccountPage extends RootPage {

	public AccountPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Edit your account information")
	private WebElement editYourAccountInformationOption;

	@FindBy(linkText = "Subscribe / unsubscribe to newsletter")
	private WebElement subscribeUnsubscribeNewsletterOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Logout']")
	private WebElement logoutOption;

	@FindBy(linkText = "Change Your Password")
	private WebElement changeYourPassowrd;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	private WebElement message;

	public String getMessage() {
		return getTextOfElement(message);
	}

	public ChangePasswordPage clickOnChangeYourPasswordOption() {
		changeYourPassowrd.click();
		return new ChangePasswordPage(driver);
	}

	public AccountLogoutPage clickOnLogoutOption() {
		logoutOption.click();
		return new AccountLogoutPage(driver);
	}

	public boolean isUserLoggedIn() {
		return isElementDisplayed(logoutOption);
	}

	public boolean isAccountPageDisplayed() {
		return isElementDisplayed(editYourAccountInformationOption);
	}

	public NewsSletterPage selectSubscribeUnsubscribeNewsletterOption() {
		subscribeUnsubscribeNewsletterOption.click();
		return new NewsSletterPage(driver);
	}

	public EditAccountInformationPage clickOnEditYourAccountInformationOption() {
		editYourAccountInformationOption.click();
		return new EditAccountInformationPage(driver);
	}
}
