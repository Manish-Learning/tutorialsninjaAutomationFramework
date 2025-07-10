package tutorialsninja.base;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.Parameters;

import pages.AboutUsPage;
import pages.AccountLogoutPage;
import pages.AccountPage;
import pages.AccountSuccessPage;
import pages.AffiliateLoginPage;
import pages.BrandPage;
import pages.ChangePasswordPage;
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
import utils.CommonUtils;
import utils.MyXLSReader;

public class Base {

	WebDriver driver;
	Properties prop;
	DesiredCapabilities caps;
	public LandingPage landingPage;
	public RegisterPage registerPage;
	public AccountSuccessPage accountSuccessPage;
	public AccountPage accountPage;
	public NewsSletterPage newsSletterPage;
	public LoginPage loginPage;
	public EditAccountInformationPage editAccountInformationPage;
	public ContactUsPage contactUsPage;
	public ShoppingCartPage shoppingCartPage;
	public CheckOutPage checkoutPage;
	public SearchPage searchPage;
	public ForgottonPasswordPage forgottonPasswordPage;
	public AboutUsPage aboutUsPage;
	public DeliveryInformationPage deliveryInfoPage;
	public PrivacyPolicyPage privacyPolicyPage;
	public TermsAndConditionsPage termAndConditionPage;
	public ProductReturnsPage productreturnPage;
	public SiteMapPage siteMapPage;
	public BrandPage brandPage;
	public PurchaseGiftCertificatePage giftCertificatePage;
	public AffiliateLoginPage affiliateLoginPage;
	public SpecialOffersPage specialOffersPage;
	public HeaderOptions headerOption;
	public RightColumnOptions rightColumnOptions;
	public FooterOptions footerOptions;
	public AccountLogoutPage accountLogoutPage;
	public ChangePasswordPage changePasswordPage;
	public HeaderOptions headerOptions;
	public DeliveryInformationPage deliveryInformationPage;
	public TermsAndConditionsPage termsAndConditionsPage;
	public ProductReturnsPage productReturnsPage;
	public PurchaseGiftCertificatePage purchaseGiftCertificatePage;
	public MyXLSReader myXLSReader;

	@Parameters({"browser"})
	public WebDriver openBrowserAndApplication(String browserName) throws MalformedURLException, URISyntaxException {

		prop = CommonUtils.loadProperties();

		 String executionEnv = prop.getProperty("execution_env");
		 //Use Below line when you want to read the browserName fromt he properties file.
		   // String browserName = prop.getProperty("browserName");

		    if (executionEnv.equalsIgnoreCase("remote")) {
		        DesiredCapabilities caps = new DesiredCapabilities();
		        caps.setBrowserName(browserName.toLowerCase());

		        if (browserName.equalsIgnoreCase("Chrome")) {
		            ChromeOptions options = new ChromeOptions();
		            options.merge(caps);
		            driver = new RemoteWebDriver(new URL(prop.getProperty("gridServerURL")), options);
		        } else if (browserName.equalsIgnoreCase("Firefox")) {
		            FirefoxOptions options = new FirefoxOptions();
		            options.merge(caps);
		            driver = new RemoteWebDriver(new URL(prop.getProperty("gridServerURL")), options);
		        } else if (browserName.equalsIgnoreCase("IE")) {
		            InternetExplorerOptions options = new InternetExplorerOptions();
		            options.merge(caps);
		            driver = new RemoteWebDriver(new URL(prop.getProperty("gridServerURL")), options);
		        } else if (browserName.equalsIgnoreCase("Edge")) {
		            EdgeOptions options = new EdgeOptions();
		            options.merge(caps);
		            driver = new RemoteWebDriver(new URL(prop.getProperty("gridServerURL")), options);
		        } else if (browserName.equalsIgnoreCase("Safari")) {
		            SafariOptions options = new SafariOptions();
		            options.merge(caps);
		            driver = new RemoteWebDriver(new URL(prop.getProperty("gridServerURL")), options);
		        } else {
		            throw new IllegalArgumentException("Unsupported browser for remote execution: " + browserName);
		        }

		        driver.manage().window().maximize();
		        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		        driver.get(prop.getProperty("appURL"));
		        System.out.println("This is remote URL event : " + driver.getCurrentUrl());
		        return driver;
		    }

		    // Local execution
		    else if (executionEnv.equalsIgnoreCase("local")) {
		        if (browserName.equalsIgnoreCase("Chrome")) {
		            driver = new ChromeDriver();
		        } else if (browserName.equalsIgnoreCase("Firefox")) {
		            driver = new FirefoxDriver();
		        } else if (browserName.equalsIgnoreCase("IE")) {
		            driver = new InternetExplorerDriver();
		        } else if (browserName.equalsIgnoreCase("edge")) {
		            driver = new EdgeDriver();
		        } else if (browserName.equalsIgnoreCase("Safari")) {
		            driver = new SafariDriver();
		        } else {
		            throw new IllegalArgumentException("Unsupported browser for local execution: " + browserName);
		        }

		        driver.manage().window().maximize();
		        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		        driver.get(prop.getProperty("appURL"));
		        System.out.println("This is Local URL event : " + driver.getCurrentUrl());
		        return driver;
		    }

		    throw new IllegalArgumentException("Invalid execution environment: " + executionEnv);
		}
	
	public WebDriver openBrowserToAutomateEmailFlow() {
		String browserName = "chrome";

		if (browserName.equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			driver = new InternetExplorerDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("Safari")) {
			driver = new SafariDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		return driver;
	}

	public void navigateToRegisterPage(WebDriver driver, String URL) {
		driver.navigate().to("URL");
	}

	public WebDriver navigateBack(WebDriver driver) {
		driver.navigate().back();
		return driver;
	}

	public WebDriver pressKeyMultipleTimes(WebDriver driver, Keys keyName, int count) {
		Actions actions = new Actions(driver);

		for (int i = 1; i <= count; i++) {
			actions.sendKeys(keyName).perform();
		}
		return driver;
	}

	public WebDriver enterDetailsIntoRegistrationAccountPageFields(WebDriver driver) {
		prop = CommonUtils.loadProperties();
		Actions actions = new Actions(driver);
		actions.sendKeys(prop.getProperty("firstName")).pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB)
				.pause(Duration.ofSeconds(1)).sendKeys(prop.getProperty("lastName")).sendKeys(Keys.TAB)
				.pause(Duration.ofSeconds(1)).sendKeys(CommonUtils.generateNewEmail()).pause(Duration.ofSeconds(1))
				.sendKeys(Keys.TAB).pause(Duration.ofSeconds(1)).sendKeys(prop.getProperty("telephoneNumber"))
				.pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB).pause(Duration.ofSeconds(1))
				.sendKeys(prop.getProperty("validPassword")).pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB)
				.pause(Duration.ofSeconds(1)).sendKeys(prop.getProperty("validConfirmPassword"))
				.pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB).pause(Duration.ofSeconds(1)).sendKeys(Keys.LEFT)
				.pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB).pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB)
				.pause(Duration.ofSeconds(1)).sendKeys(Keys.SPACE).pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB)
				.pause(Duration.ofSeconds(3)).sendKeys(Keys.ENTER).build().perform();
		return driver;
	}

	public WebDriver enterDetailsIntoLoginPageFields(WebDriver driver) {

		prop = CommonUtils.loadProperties();
		Actions actions = new Actions(driver);
		actions.sendKeys(prop.getProperty("existingEmail")).pause(Duration.ofSeconds(1)).sendKeys(Keys.TAB)
				.pause(Duration.ofSeconds(1)).sendKeys(prop.getProperty("validPassword")).pause(Duration.ofSeconds(1))
				.sendKeys(Keys.TAB).sendKeys(Keys.TAB).pause(Duration.ofSeconds(1)).sendKeys(Keys.ENTER)
				.pause(Duration.ofSeconds(2)).build().perform();

		return driver;
	}

	public String getHTMLCodeOfThePage() {
		return driver.getPageSource();
	}

	public String getPageURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public void closeBrowser(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}

	public WebDriver refreshPage(WebDriver driver) {
		driver.navigate().refresh();
		return driver;
	}
}
