package tutorialsninja.tests;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.AboutUsPage;
import pages.AccountPage;
import pages.AccountSuccessPage;
import pages.AffiliateLoginPage;
import pages.BrandPage;
import pages.CheckOutPage;
import pages.ContactUsPage;
import pages.DeliveryInformationPage;
import pages.EditAccountInformationPage;
import pages.FooterOptions;
import pages.ForgottonPasswordPage;
import pages.HeaderOptions;
import pages.LandingPage;
import pages.LoginPage;
import pages.NewsSletterPage;
import pages.PrivacyPolicyPage;
import pages.ProductReturnsPage;
import pages.PurchaseGiftCertificatePage;
import pages.RegisterPage;
import pages.RightColumnOptions;
import pages.SearchPage;
import pages.ShoppingCartPage;
import pages.SiteMapPage;
import pages.SpecialOffersPage;
import pages.TermsAndConditionsPage;
import tutorialsninja.base.Base;
import utils.CommonUtils;

public class Register extends Base {
	WebDriver driver;
	Properties prop;

	@BeforeMethod
	public void setUP() {
		driver = openBrowserAndApplication();
		prop = CommonUtils.loadProperties();
		landingPage = new LandingPage(driver);
		landingPage.clickOnMyAccount();
		registerPage = landingPage.selectRegisterOption();
	}

	@AfterMethod
	public void teatDown() {
		closeBrowser(driver);
	}

	@Test(priority = 1)
	public void verifyRegisterAccout() {
		// Ctrl + Shift + O - to import all the packages

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());

		String expectedHeading = "Your Account Has Been Created!";
		Assert.assertEquals(accountSuccessPage.getPageHeading(), expectedHeading);

		String expectedProperDetailsOne = "Congratulations! Your new account has been successfully created!";
		String expectedProperDetailsTwo = "You can now take advantage of member privileges to enhance your online shopping experience with us.";
		String expectedProperDetailsThree = "If you have ANY questions about the operation of this online shop, please e-mail the store owner.";
		String expectedProperDetailsFour = "contact us.";

		String actualProperDetails = accountSuccessPage.getPageContent();
		Assert.assertTrue(actualProperDetails.contains(expectedProperDetailsOne));
		Assert.assertTrue(actualProperDetails.contains(expectedProperDetailsTwo));
		Assert.assertTrue(actualProperDetails.contains(expectedProperDetailsThree));
		Assert.assertTrue(actualProperDetails.contains(expectedProperDetailsFour));

		accountPage = accountSuccessPage.clickOnContinueBUtton();
		Assert.assertTrue(accountPage.isAccountPageDisplayed());

	}

	// @Test(priority = 2)
	public void verifyGmailEmailDetails() throws InterruptedException {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		String emailAddress = CommonUtils.generateNewEmail();
		registerPage.enterEmail(emailAddress);
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String email = prop.getProperty("existingEmail");
		String gmailAppPassCode = "jwhx eban xmuy aqok";
		String link = null;

		driver.get("https://amazon.in");
		driver.findElement(By.xpath("//span[text()='Hello, sign in']")).click();
		driver.findElement(By.id("ap_email_login")).clear();
		driver.findElement(By.id("ap_email_login")).sendKeys(email);
		driver.findElement(By.xpath("//span[@id='continue']")).click();

		driver.findElement(By.linkText("Forgot password?")).click();
		driver.findElement(By.id("ap_email")).clear();
		driver.findElement(By.id("ap_email")).sendKeys(email);
		driver.findElement(By.id("continue")).click();

		driver.findElement(By.id("ap_zip")).sendKeys("800001");

		driver.findElement(By.id("continue")).click();

		System.out.println("Holding the program for 10 sec");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ChatGpt command : I want to make my java program access my gmail account
		 * using the app password and email, then what is the code I need to write for
		 * reading the subject of the email. retrieving the from email address and the
		 * body content of the email.
		 * 
		 */
		// MVN Repo link to add dependency :
		// https://mvnrepository.com/artifact/com.sun.mail/javax.mail

		// Gmail IMAP configuration
		String host = "imap.gmail.com";
		String port = "993";
		String username = email; // Your Gmail address
		String appPassword = gmailAppPassCode; // Your app password
		String expectedSubject = "amazon.in: Password recovery";
		String expectedFromEmail = "\"amazon.in\" <account-update@amazon.in>";
		String expectedBodyContent = "Someone is attempting to reset the password of your account.";

		boolean b = false;
		try {
			// Mail server connection properties
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.ssl.enable", "true");

			// Connect to the mail server
			Session emailSession = Session.getDefaultInstance(properties);
			Store store = emailSession.getStore("imaps");
			store.connect(host, username, appPassword); // replace email password with App password

			// Open the inbox folder
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			// Search for unread emails
			Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			boolean found = false;
			for (int i = messages.length - 1; i >= 0; i--) {

				Message message = messages[i];

				if (message.getSubject().contains(expectedSubject)) {
					found = true;
					/*
					 * System.out.println("Email Subject :" + message.getSubject());
					 * System.out.println("Email From : " + message.getFrom()[0].toString());
					 * System.out.println("Email Body : " + getTextFromMessage(message));
					 */
					Assert.assertEquals(message.getSubject(), expectedSubject);
					Assert.assertEquals(message.getFrom()[0].toString(), expectedFromEmail);
					String actualEmailBody = CommonUtils.getTextFromMessage(message);
					Assert.assertTrue(actualEmailBody.contains(expectedBodyContent));

					String[] ar = actualEmailBody.split("600\">");
					String linkPart = ar[1];
					String[] arr = linkPart.split("</a>");

					link = arr[0].trim();
					System.out.println("Link in Email : " + link);

					break;
				}
			}

			if (!found) {
				System.out.println("No confirmation email found.");
			}

			// Close the store and folder objects
			inbox.close(false);
			store.close();
			b = true;

		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		driver.navigate().to(link);
		Assert.assertTrue(b);
		Assert.assertTrue(driver.findElement(By.name("customerResponseDenyButton")).isDisplayed());
	}

	public String generateNewEmail() {
		return new Date().toString().replaceAll("\\s", "").replaceAll("\\:", "") + "@gmail.com";
	}

	@Test(priority = 3)
	public void VerifyRegisterAccountwithAllFields() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());

		String expectedHeading = "Your Account Has Been Created!";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='common-success']//h1")).getText(), expectedHeading);

		String actualProperDetailsOne = "Congratulations! Your new account has been successfully created!";
		String actualProperDetailsTwo = "You can now take advantage of member privileges to enhance your online shopping experience with us.";
		String actualProperDetailsThree = "If you have ANY questions about the operation of this online shop, please e-mail the store owner.";
		String actualProperDetailsFour = "contact us.";

		String expectedProperDetails = accountSuccessPage.getPageContent();
		Assert.assertTrue(expectedProperDetails.contains(actualProperDetailsOne));
		Assert.assertTrue(expectedProperDetails.contains(actualProperDetailsTwo));
		Assert.assertTrue(expectedProperDetails.contains(actualProperDetailsThree));
		Assert.assertTrue(expectedProperDetails.contains(actualProperDetailsFour));

		accountPage = accountSuccessPage.clickOnContinueBUtton();
		Assert.assertTrue(accountPage.isAccountPageDisplayed());
	}

	@Test(priority = 4)
	public void verifyRegistringAccountWithoutFillFields() {

		registerPage.clickOnContinueButton();
		String expectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
		String expectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
		String expectedEmailWarning = "E-Mail Address does not appear to be valid!";
		String expectedTelephoneWarning = "Telephone must be between 3 and 32 characters!";
		String expectedPasswordWarning = "Password must be between 4 and 20 characters!";
		String expectedPrivacyPolicyWarning = "Warning: You must agree to the Privacy Policy!";

		Assert.assertEquals(registerPage.getFirstNameWarning(), expectedFirstNameWarning);
		Assert.assertEquals(registerPage.getLastNameWarning(), expectedLastNameWarning);
		Assert.assertEquals(registerPage.getEmailIdWarning(), expectedEmailWarning);
		Assert.assertEquals(registerPage.getTeliphoneNumberWarning(), expectedTelephoneWarning);
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);
		Assert.assertEquals(registerPage.privacyPolicyWarning(), expectedPrivacyPolicyWarning);

	}

	@Test(priority = 5)
	public void verifyRegisteringAccountBySubscribingToNewsletter() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		accountPage = accountSuccessPage.clickOnContinueBUtton();

		newsSletterPage = accountPage.selectSubscribeUnsubscribeNewsletterOption();

		Assert.assertTrue(newsSletterPage.didWeNavigteToNewsletterPage());
		Assert.assertTrue(newsSletterPage.isYesNewsletterOptionSelected());

	}

	@Test(priority = 6)
	public void verifyRegisteringAccountBySayingNoToNewsletter() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectNoNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		accountPage = accountSuccessPage.clickOnContinueBUtton();

		newsSletterPage = accountPage.selectSubscribeUnsubscribeNewsletterOption();

		Assert.assertTrue(newsSletterPage.didWeNavigteToNewsletterPage());
		Assert.assertTrue(newsSletterPage.isNoNewsletterOptionSelected());

	}

	@Test(priority = 7)
	public void verifyNavigatingToRegisterAccountUsingMultipleWay() {

		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		driver = registerPage.getDriver();
		headerOptions = new HeaderOptions(driver);
		headerOptions.clickOnMyAccountDropMenu();
		loginPage = headerOptions.selectLoginOption();
		loginPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());

		headerOptions.clickOnMyAccountDropMenu();
		loginPage = headerOptions.selectLoginOption();
		loginPage.clickOnRegisterOption();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
	}

	@Test(priority = 8)
	public void verifyRegisteringAccountByProvidingMismatchingPasswords() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("missmatchConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String expectedWarningMessage = "Password confirmation does not match password!";
		Assert.assertEquals(registerPage.getPasswordConfirmWarning(), expectedWarningMessage);

	}

	@Test(priority = 9)
	public void verifyRegistringAccountUsingExistingEmail() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(prop.getProperty("existingEmail"));
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String expectedWarningMessage = "Warning: E-Mail Address is already registered!";
		Assert.assertEquals(registerPage.getExistingEmailWarning(), expectedWarningMessage);
	}

	@Test(priority = 10)
	public void verifyRegisteringAccountUsingInvalidEmail() throws InterruptedException, IOException {

		String browserName = prop.getProperty("browserName");
		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(prop.getProperty("invalidEmailOne"));
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		Thread.sleep(3000);

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(),
					"Please include an '@' in the email address. 'manishk' is missing an '@'.");
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(), "Please enter an email address.");
		}

		registerPage.clearEmailField();
		registerPage.enterEmail(prop.getProperty("invalidEmailTwo"));
		registerPage.clickOnContinueButton();

		Thread.sleep(2000);

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(),
					"Please enter a part following '@'. 'manish@' is incomplete.");
			// expected [Please enter a part following '@' . 'manish@' is incomplete.]
			// but found [Please enter a part following '@'. 'manish@' is incomplete.]
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(), "Please enter an email address.");
		}

		registerPage.clearEmailField();
		registerPage.enterEmail(prop.getProperty("invalidEmailThree"));
		registerPage.clickOnContinueButton();

		Thread.sleep(2000);
		String expectedWarningMessage = "E-Mail Address does not appear to be valid!";
		Thread.sleep(2000);
		Assert.assertEquals(registerPage.getEmailIdWarning(), expectedWarningMessage);

		registerPage.clearEmailField();
		registerPage.enterEmail(prop.getProperty("invalidEmailFour"));
		registerPage.clickOnContinueButton();

		Thread.sleep(3000);
		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(),
					"'.' is used at a wrong position in 'gmail.'.");
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(), "Please enter an email address.");
		}
	}

	@Test(priority = 11)
	public void verifyRegisterAccountByProvidingInvalidTelephoneNumber() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("invalidTelephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		registerPage.clickOnContinueButton();

		String expectedWarningMessage = "Telephone number does not appear to be valid";
		boolean state = false;
		try {
			String actualWarningMessage = registerPage.getTeliphoneNumberWarning();
			if (actualWarningMessage.equals(expectedWarningMessage)) {
				state = true;
			}
		} catch (NoSuchElementException e) {
			state = false;
		}
		//Assert.assertTrue(state);
	}

	@Test(priority = 12)
	public void verifyRegisteringAccountUsingKeyboardKeys() {
		driver = pressKeyMultipleTimes(driver, Keys.TAB, 23);
		driver = enterDetailsIntoRegistrationAccountPageFields(driver);
		accountSuccessPage = new AccountSuccessPage(driver);
		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());

	}

	@Test(priority = 13)
	public void verifyPlaceHoldersOfTextFieldsInRegisterAccountPage() {

		String expectedFirstNamePlaceHolderText = "First Name";
		Assert.assertEquals(registerPage.getPlaceHolderTextFromFirstNameField(), expectedFirstNamePlaceHolderText);

		String expectedLastNamePlaceHolderText = "Last Name";
		Assert.assertEquals(registerPage.getPlaceHolderTextFromLastNameField(), expectedLastNamePlaceHolderText);

		String expectedEmailPlaceHolderText = "E-Mail";
		Assert.assertEquals(registerPage.getPlaceHolderTextFromEmailField(), expectedEmailPlaceHolderText);

		String expectedTelephonePlaceHolderText = "Telephone";
		Assert.assertEquals(registerPage.getPlaceHolderTextFromTelephoneField(), expectedTelephonePlaceHolderText);

		String expectedPasswordPlaceHolderText = "Password";
		Assert.assertEquals(registerPage.getPlaceHolderTextFromPasswordField(), expectedPasswordPlaceHolderText);

		String expectedPasswordConfirmPlaceHolderText = "Password Confirm";
		Assert.assertEquals(registerPage.getPlaceHolderTextFromPasswordConfirmField(),
				expectedPasswordConfirmPlaceHolderText);

	}

	@Test(priority = 14)
	public void verifyMandatoryFieldsSymbolAndColorInRegisterAccountPage() {

		String expectedContent = "\"* \""; // escape sequences
		String expectedColor = "rgb(255, 0, 0)";

		// ChatGpt command : I need to retrieve the content and color css style
		// properties of a
		// ::before section of a label tag in html. What is the JavaScript code I need
		// to write for this
		// I would this in selenium java code
		Assert.assertEquals(registerPage.getFirstNameLabelContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getFirstNameLabelColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getLastNameLabelContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getLastNameLabelColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getEmailLabelContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getEmailLabelColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getTeliphoneLabelContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getTeliphoneLabelColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getPasswordLabelContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getPasswordLabelColor(driver), expectedColor);
		Assert.assertEquals(registerPage.getConfirmPasswordLabelContent(driver), expectedContent);
		Assert.assertEquals(registerPage.getConfirmPasswordLabelColor(driver), expectedColor);
		// Assert.assertEquals(registerPage.getPrivacyPolicyLabelContent(driver),
		// expectedContent);
		// Assert.assertEquals(registerPage.getPrivacyPolicyLableColor(driver),
		// expectedColor);
	}

	@Test(priority = 15)
	public void verifyRegisteringAccountWithOnlySpaces() {

		registerPage.enterFirstName(" ");
		registerPage.enterLastName(" ");
		registerPage.enterTelephoneNumber(" ");
		registerPage.enterPassword(" ");
		registerPage.enterConfirmPassword(" ");
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String firstNameWarning = "First Name must be between 1 and 32 characters!";
		String lastNameWarning = "Last Name must be between 1 and 32 characters!";
		String emailWarning = "E-Mail Address does not appear to be valid!";
		String telephoneWarning = "Telephone must be between 3 and 32 characters!";
		String passwordWarning = "Password must be between 4 and 20 characters!";

		// if(prop.getProperty("browserName").equals("chrome") ||
		// prop.getProperty("browserName").equals("edge"))
		// {
		Assert.assertEquals(registerPage.getFirstNameWarning(), firstNameWarning);
		Assert.assertEquals(registerPage.getLastNameWarning(), lastNameWarning);
		Assert.assertEquals(registerPage.getEmailIdWarning(), emailWarning);
		Assert.assertEquals(registerPage.getTeliphoneNumberWarning(), telephoneWarning);
		Assert.assertEquals(registerPage.getPasswordWarning(), passwordWarning);
		// } else if (prop.getProperty("browserName").equals("firefox"))
		// {
		// Assert.assertEquals(registerPage.getEmailValidationMessage(), "Please enter
		// an email address.");
		// }
	}

	@Test(priority = 16, dataProvider = "passwordSupplier")
	public void verifyRegisteringAccountAndCheckingPasswordComplexityStandards(String passwordText) {
	//public void verifyRegisteringAccountAndCheckingPasswordComplexityStandards(HashMap<String, String> hMap) {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		registerPage.enterPassword(passwordText);
		//registerPage.enterPassword(hMap.get("Passwords"));
		registerPage.enterConfirmPassword(passwordText);
		//registerPage.enterConfirmPassword(hMap.get("Passwords"));
		registerPage.clickOnContinueButton();

		String warningMessage = "Password entered is not matching the Complexity standards";

		boolean state = false;
		try {
			String actualWarningMessage = registerPage.getPasswordWarning();
			if (actualWarningMessage.equals(warningMessage)) {
				state = true;
			}
		} catch (NoSuchElementException e) {
			state = false;
		}
		Assert.assertTrue(state);
		Assert.assertFalse(registerPage.didWeNavigateToRegisterAccountPage());

	}

	@DataProvider(name = "passwordSupplier")
	public Object[][] supplyPasswords() {
		Object[][] data = { { "12345" }, { "abcdefghi" }, { "abcd1234" }, { "abcd123$" }, { "ABCD456#" } };
		return data;
		
	/*
	@DataProvider(name = "passwordSupplier")
	public Object[][] supplyPasswords() {
		myXLSReader = new MyXLSReader(System.getProperty("user.dir") + "\\src\\test\\resources\\TutorialsNinja.xlsx");
		Object[][] data = CommonUtils.getTestData(myXLSReader, "RegsiterTestSupplyPasswords", "data");
		return data;
	} 
		 */
	}

	@Test(priority = 17)
	public void verifyRegisteringAccountFieldsHeightWidthAligment() throws IOException {

		String browserName = prop.getProperty("browserName");

		String expectedHeight = "34px";
		String expectedWidth = "701.25px";

		String actualFirstNameFieldHeight = registerPage.getFirstNameFieldHeight();
		String expectedFirstNameFieldWidth = registerPage.getFirstNameFieldWidth();
		Assert.assertEquals(actualFirstNameFieldHeight, expectedHeight);
		Assert.assertEquals(expectedFirstNameFieldWidth, expectedWidth);

		registerPage.enterFirstName("");
		registerPage.clickOnContinueButton();

		String expectedWarning = "First Name must be between 1 and 32 characters!";
		Assert.assertEquals(registerPage.getFirstNameWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("a");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("ab");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("abcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("abcdefghijklmnopabcdefghijklmnop");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isFirstNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("abcdefghijklmnopabcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getFirstNameWarning(), expectedWarning);

		// ---------------------

		registerPage = new RegisterPage(driver);
		String actualLastNameFieldHeight = registerPage.getLastNameFieldHeight();
		String actualLastNameFieldWidth = registerPage.getLastNameFieldWidth();
		Assert.assertEquals(actualLastNameFieldHeight, expectedHeight);
		Assert.assertEquals(actualLastNameFieldWidth, expectedWidth);

		expectedWarning = "Last Name must be between 1 and 32 characters!";
		registerPage.clearLastNameField();
		registerPage.enterLastName("");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getLastNameWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("a");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("ab");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("abcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("abcdefghijklmnopabcdefghijklmnop");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isLastNameWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearLastNameField();
		registerPage.enterLastName("abcdefghijklmnopabcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getLastNameWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		String actualEmailFieldHeight = registerPage.getEmailFieldHeight();
		String actualEmailFieldWidth = registerPage.getEmailFieldWidth();
		Assert.assertEquals(actualEmailFieldHeight, expectedHeight);
		Assert.assertEquals(actualEmailFieldWidth, expectedWidth);

		registerPage = new RegisterPage(driver);
		registerPage.clearEmailField();
		registerPage.enterEmail("abcdefghijklmnopabcdefghijklmnopqabcdefghijklmnopabcdefghijklmno@gmail.com");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isEmailWarningDisplayed());

		// ----------------------------------------

		registerPage = new RegisterPage(driver);

		String actualTelephoneFieldHeight = registerPage.getTelephoneFieldHeight();
		String actualTelephoneFieldWidth = registerPage.getTelephoneFieldWidth();

		Assert.assertEquals(actualTelephoneFieldHeight, expectedHeight);
		Assert.assertEquals(actualTelephoneFieldWidth, expectedWidth);

		expectedWarning = "Telephone must be between 3 and 32 characters!";

		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTeliphoneNumberWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("a");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTeliphoneNumberWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("ab");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTeliphoneNumberWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abc");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.isTelephoneWarningDisplayed(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abcd");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.isTelephoneWarningDisplayed(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abcdefghijklmnop");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.isTelephoneWarningDisplayed(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abcdefghijklmnopabcdefghijklmnop");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.isTelephoneWarningDisplayed(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearTelephoneField();
		registerPage.enterTelephoneNumber("abcdefghijklmnopabcdefghijklmnopq");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTeliphoneNumberWarning(), expectedWarning);

		// -----------------------
		registerPage = new RegisterPage(driver);
		String actualPasswordFieldHeight = registerPage.getPasswordFieldHeight();
		String actualPasswordFieldWidth = registerPage.getPasswordFieldWidth();
		Assert.assertEquals(actualPasswordFieldHeight, expectedHeight);
		Assert.assertEquals(actualPasswordFieldWidth, expectedWidth);

		expectedWarning = "Password must be between 4 and 20 characters!";
		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("a");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("ab");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abc");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedWarning);

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcd");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcde");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghij");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghijabcdefghi");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghijabcdefghij");
		registerPage.clickOnContinueButton();
		Assert.assertFalse(registerPage.isPasswordWarningDisplayed());

		registerPage = new RegisterPage(driver);
		registerPage.clearPasswordField();
		registerPage.enterPassword("abcdefghijabcdefghijk");
		registerPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.isPasswordWarningDisplayedAndMatch(expectedWarning));

		String actualConfirmPasswordFieldHeight = registerPage.getPasswordConfirmFieldHeight();
		String actualConfirmPasswordFieldWidth = registerPage.getPasswordConfirmFieldWidth();

		Assert.assertEquals(actualConfirmPasswordFieldHeight, expectedHeight);
		Assert.assertEquals(actualConfirmPasswordFieldWidth, expectedWidth);
		navigateToRegisterPage(driver, prop.getProperty("registerPageURL"));
		driver.navigate().to("https://tutorialsninja.com/demo/index.php?route=account/register");

		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(srcScreenshot,
				new File(System.getProperty("user.dir") + "\\Screenshots\\registerPageActualAligment.png"));

		Assert.assertFalse(CommonUtils.compareTwoScreenshots(
				System.getProperty("user.dir") + "\\Screenshots\\registerPageActualAligment.png",
				System.getProperty("user.dir") + "\\Screenshots\\registerPageExpectedAligment.png"));
	}

	@Test(priority = 18)
	public void verifyLeadingAndTrailingSpacesWhileRegisteringAccount() {

		SoftAssert softAssert = new SoftAssert();
		String enteredFirstName = "        " + prop.getProperty("firstName") + "        ";
		registerPage.enterFirstName(enteredFirstName);
		String enteredLastName = "        " + prop.getProperty("lastName") + "        ";
		registerPage.enterLastName(enteredLastName);
		String enteredEmail = "        " + CommonUtils.generateNewEmail() + "        ";
		registerPage.enterEmail(enteredEmail);
		String enteredTelphone = "        " + prop.getProperty("telephoneNumber") + "        ";
		registerPage.enterTelephoneNumber(enteredTelphone);
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		if (prop.getProperty("browserName").equals("chrome") || prop.getProperty("browserName").equals("edge")) {
			accountPage = accountSuccessPage.clickOnContinueBUtton();
			editAccountInformationPage = accountPage.clickOnEditYourAccountInformationOption();
			softAssert.assertEquals(editAccountInformationPage.getFirstNameFieldValue(), enteredFirstName.trim());
			softAssert.assertEquals(editAccountInformationPage.getLastNameFieldValue(), enteredLastName.trim());
			softAssert.assertEquals(editAccountInformationPage.getEmailFieldValue(), enteredEmail.trim());
			softAssert.assertEquals(editAccountInformationPage.getTelephoneFieldValue(), enteredTelphone.trim());
			//softAssert.assertAll();
		} else if (prop.getProperty("browserName").equals("firefox")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(), "Please enter an email address.");
		}
	}

	@Test(priority = 19)
	public void verifyRegisteringAccountWithoutPrivacyPolicySelection() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		accountSuccessPage = registerPage.clickOnContinueButton();

		String expectedWarningMessage = "Warning: You must agree to the Privacy Policy!";
		Assert.assertEquals(registerPage.privacyPolicyWarning(), expectedWarningMessage);
	}

	@Test(priority = 20)
	public void verifyWorkingOfEveryLinkOnRegisterAccountPage() throws InterruptedException {

		
		headerOption = new HeaderOptions(registerPage.getDriver());
		contactUsPage = headerOption.clickOnPhoneIconOption();
		Assert.assertTrue(contactUsPage.didWeNavigateToContactUsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		headerOption = new HeaderOptions(registerPage.getDriver());
		loginPage = headerOption.clickOnWishListHeartOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		headerOption = new HeaderOptions(registerPage.getDriver());
		shoppingCartPage = headerOption.clickOnShoppingCartOption();
		Assert.assertTrue(shoppingCartPage.didWeNavigateToShoppingCartPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		headerOption = new HeaderOptions(registerPage.getDriver());
		shoppingCartPage = headerOption.clickOnCheckOutOption();
		Assert.assertTrue(shoppingCartPage.didWeNavigateToShoppingCartPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		headerOption = new HeaderOptions(registerPage.getDriver());
		headerOption.clickOnLogoOption();
		Assert.assertEquals(getPageURL(driver), prop.getProperty("landingPageURL"));
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		headerOption = new HeaderOptions(registerPage.getDriver());
		searchPage = headerOption.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		registerPage.clickOnRegisterBreadCrumb();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());

		registerPage = new RegisterPage(driver);
		headerOption = new HeaderOptions(registerPage.getDriver());
		loginPage = headerOption.clickOnAccountBreadCrumb();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		/*
		 * driver.findElement(By.
		 * xpath("//ul[@class='breadcrumb']//i[@class='fa fa-home']")).click();
		 * Assert.assertEquals(driver.getCurrentUrl(),
		 * "https://tutorialsninja.com/demo/index.php?route=common/home");
		 * driver.navigate().back();
		 */

		registerPage = new RegisterPage(driver);
		loginPage = registerPage.clickOnLoginPageLink();
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		registerPage.clickOnPrivacyPolicyOption();
		registerPage.waitAndCheckDisplayStatusOfClosePrivacyPolicyOption(driver, 10);
		registerPage.closePrivacyPolicyDialog();

		registerPage = new RegisterPage(driver);
		registerPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideLoginOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		registerPage = rightColumnOptions.clickOnRightSideRegisterOption();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		forgottonPasswordPage = rightColumnOptions.clicoOnRightSideForgotPasswordOption();
		Assert.assertTrue(forgottonPasswordPage.didWeNavigateToForgotPasswordPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideMyAccountOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideMyAddressBookOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideWishListOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideOrderHistoryOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideDownloadsOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideRecurringPaymentsOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideRewardPointsOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideReturnOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightSideTransactionsOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		rightColumnOptions = new RightColumnOptions(registerPage.getDriver());
		loginPage = rightColumnOptions.clickOnRightsideNewsletterOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		aboutUsPage = footerOptions.clickOnAboutUsFooterOption();
		Assert.assertTrue(aboutUsPage.didWeNavigateToAboutUsPageBreadCrumb());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		deliveryInfoPage = footerOptions.clickOnDeliveryInfoFooterOption();
		Assert.assertTrue(deliveryInfoPage.didWeNavigateToDeliveryInformationPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		privacyPolicyPage = footerOptions.clickOnPrivacyPolicyFooterOption();
		Assert.assertTrue(privacyPolicyPage.didWeNavigateToPrivacyPolicyPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		termAndConditionPage = footerOptions.clickOnTermAndConditionFooterOption();
		Assert.assertTrue(termAndConditionPage.didWeNavigateToTermAndConditionsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		contactUsPage = footerOptions.clickOnContactUsFooterOption();
		Assert.assertTrue(contactUsPage.didWeNavigateToContactUsPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		productreturnPage = footerOptions.clickOnReturnsFooterOption();
		Assert.assertTrue(productreturnPage.didWeNavigateToProductReturnPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		siteMapPage = footerOptions.clickOnSiteMapFooterOption();
		Assert.assertTrue(siteMapPage.didWeNavigateToSiteMapPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		brandPage = footerOptions.clickOnbrandFooterOption();
		Assert.assertTrue(brandPage.didWeNavigateToBrandPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		giftCertificatePage = footerOptions.clickOnGiftCertificateFooterOption();
		Assert.assertTrue(giftCertificatePage.didWeNavigateToGiftCertificatePage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		affiliateLoginPage = footerOptions.clickOnAffiliateFooterOption();
		Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("affiliateLoginPageURL"));
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		specialOffersPage = footerOptions.clickOnSpecialsFooterOption();
		Assert.assertTrue(specialOffersPage.didWeNavigateToSpecialOfferPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		loginPage = footerOptions.clickOnMyAccountFooterOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		loginPage = footerOptions.clickOnOrderHistoryFooterPage();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		loginPage = footerOptions.clickOnWishListFooterOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);

		registerPage = new RegisterPage(driver);
		footerOptions = new FooterOptions(registerPage.getDriver());
		loginPage = footerOptions.clickOnNewsletterFooterOption();
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		driver = navigateBack(driver);
	}

	@Test(priority = 21)
	public void verifyRegisteringAccountWithoutEnteringPasswordIntoPasswordConfirmField() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();

		String expectedWarningMessage = "Password confirmation does not match password!";
		Assert.assertEquals(registerPage.getPasswordConfirmWarning(), expectedWarningMessage);
	}

	@Test(priority = 22)
	public void verifyBreadcrumbURLHeadingTitleOfRegisterAccountPage() {
		Assert.assertTrue(registerPage.didWeNavigateToRegisterAccountPage());
		Assert.assertEquals(registerPage.getRegisterPageHeading(), prop.getProperty("registerPageHeading"));
		Assert.assertEquals(getPageURL(driver), prop.getProperty("registerPageURL"));
		Assert.assertEquals(getPageTitle(driver), prop.getProperty("registerPageTitle"));
	}

	@Test(priority = 23)
	public void verifyRegisteringAccountInDifferentTestEnvironments() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtils.generateNewEmail());
		registerPage.enterTelephoneNumber(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validConfirmPassword"));
		registerPage.selectYesNewSletterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());
		accountSuccessPage.clickOnContinueBUtton();
		Assert.assertEquals(driver.getTitle(), prop.getProperty("accountPageTitle"));
	}
}
