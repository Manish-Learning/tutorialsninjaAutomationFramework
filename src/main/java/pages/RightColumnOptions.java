package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class RightColumnOptions extends RootPage {

	public RightColumnOptions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Login']")
	private WebElement rightSideLoginOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Register']")
	private WebElement rightSideRegisterOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Forgotten Password']")
	private WebElement rightSideForgotPasswordOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='My Account']")
	private WebElement rightSideMyAccountOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Address Book']")
	private WebElement rightSideMyAddressBookOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Wish List']")
	private WebElement rightSideWishListOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Order History']")
	private WebElement rightSideOrderHistoryOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Downloads']")
	private WebElement rightSideDownloadsOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Recurring payments']")
	private WebElement rightSideRecurringPaymentsOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Reward Points']")
	private WebElement rightSideRewardPointsOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Returns']")
	private WebElement rightSideReturnOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Transactions']")
	private WebElement rightSideTransactionsOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Newsletter']")
	private WebElement rightSideNewsletterOption;

	@FindBy(xpath = "//a[@class='list-group-item'][text()='Logout']")
	private WebElement logoutOption;

	public boolean isLogoutRightColumnOptionAvailable() {
		return isElementDisplayed(logoutOption);
	}

	public AccountLogoutPage clickOnRightSideLogoutOption() {
		logoutOption.click();
		return new AccountLogoutPage(driver);
	}

	public LoginPage clickOnRightsideNewsletterOption() {
		rightSideNewsletterOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideTransactionsOption() {
		rightSideTransactionsOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideReturnOption() {
		rightSideReturnOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideRewardPointsOption() {
		rightSideRewardPointsOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideRecurringPaymentsOption() {
		rightSideRecurringPaymentsOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideDownloadsOption() {
		rightSideDownloadsOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideOrderHistoryOption() {
		rightSideOrderHistoryOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideWishListOption() {
		rightSideWishListOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideMyAddressBookOption() {
		rightSideMyAddressBookOption.click();
		return new LoginPage(driver);
	}

	public LoginPage clickOnRightSideMyAccountOption() {
		rightSideMyAccountOption.click();
		return new LoginPage(driver);
	}

	public ForgottonPasswordPage clicoOnRightSideForgotPasswordOption() {
		rightSideForgotPasswordOption.click();
		return new ForgottonPasswordPage(driver);
	}

	public RegisterPage clickOnRightSideRegisterOption() {
		rightSideRegisterOption.click();
		return new RegisterPage(driver);
	}

	public LoginPage clickOnRightSideLoginOption() {
		rightSideLoginOption.click();
		return new LoginPage(driver);
	}
}
