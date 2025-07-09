package pages;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class LoginPage extends RootPage {

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "input-email")
	private WebElement emailField;

	@FindBy(id = "input-password")
	private WebElement passwordField;

	@FindBy(xpath = "//input[@value='Login']")
	private WebElement loginButton;

	@FindBy(xpath = "//a[@class='btn btn-primary'][text()='Continue']")
	private WebElement continueButton;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Register']")
	private WebElement registerOption;

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Login']")
	private WebElement loginBreadCrumb;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	private WebElement loginWarningMessage;

	@FindBy(linkText = "Forgotten Password")
	private WebElement forgottenPasswordLink;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='My Account']")
	private WebElement myAccountRightColumnOption;

	@FindBy(xpath = "(//div[@id='content']//h2)[1]")
	private WebElement headingOne;

	@FindBy(xpath = "(//div[@id='content']//h2)[2]")
	private WebElement headingTwo;

	public AccountPage loginToApplication(String emailText, String passwordText) {
		enterEmail(emailText);
		enterPassword(passwordText);
		return clickOnLoginButton();
	}

	public String getPageHeadingOne() {
		return getTextOfElement(headingOne);
	}

	public String getPageHeadingTwo() {
		return getTextOfElement(headingTwo);
	}

	public LoginPage clickOnLoginBreadcrumb() {
		loginBreadCrumb.click();
		return new LoginPage(driver);
	}

	public void clearPassword() {
		passwordField.clear();
	}

	public WebDriver selectPasswordFieldTextAndCopy(WebDriver driver) {
		Actions action = new Actions(driver);
		action.doubleClick(passwordField).keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL);
		return driver;
	}

	public WebDriver pasteCopiedPasswordTextIntoEmailField(WebDriver driver) {
		Actions action = new Actions(driver);
		action.doubleClick(emailField).keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL);
		return driver;
	}

	public String getTextCopiedIntoEmailField() {
		return getDomPropertyOfElement(emailField, "value");
	}

	public String getPasswordFieldType() {
		return getDomAttributeOfElement(passwordField, "type");
	}

	public AccountPage clickOnRightSideMyAccountOption() {
		myAccountRightColumnOption.click();
		return new AccountPage(driver);
	}

	public String getEmailPlaceholder() {
		return getDomAttributeOfElement(emailField, "placeholder");
	}

	public String getPasswordPlaceholder() {
		return getDomAttributeOfElement(passwordField, "placeholder");
	}

	public ForgottonPasswordPage clickOnForgottonPassowrdLink() {
		forgottenPasswordLink.click();
		return new ForgottonPasswordPage(driver);
	}

	public boolean ifForgottenPasswordLinkAvailable() {
		return isElementDisplayed(forgottenPasswordLink);
	}

	public String getWarningMessage() {
		return getTextOfElement(loginWarningMessage);
	}

	public AccountPage clickOnLoginButton() {
		loginButton.click();
		return new AccountPage(driver);
	}

	public void enterPassword(String passwordText) {
		passwordField.sendKeys(passwordText);
	}

	public void enterEmail(String emailText) {
		emailField.sendKeys(emailText);
	}

	public RegisterPage clickOnContinueButton() {
		continueButton.click();
		return new RegisterPage(driver);
	}

	public void clickOnRegisterOption() {
		registerOption.click();
	}

	public boolean didWeNaviateToLoginPage() {
		return isElementDisplayed(loginBreadCrumb);
	}
}
