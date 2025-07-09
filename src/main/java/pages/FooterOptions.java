package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class FooterOptions extends RootPage{

	public FooterOptions(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(linkText="About Us")
	private WebElement aboutUsFooterOption;
	
	@FindBy(linkText="Delivery Information")
	private WebElement deliveryInfoFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Privacy Policy']")
	private WebElement privacyPolicyFooterOption;
	
	@FindBy(linkText="Terms & Conditions")
	private WebElement termAndConditionsFooterOption;
	
	@FindBy(linkText="Contact Us")
	private WebElement contactUsFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Returns']")
	private WebElement returnsFooterOption;
	
	@FindBy(linkText="Site Map")
	private WebElement siteMapFooterOption;
	
	@FindBy(linkText="Brands")
	private WebElement brandFooterOption;
	
	@FindBy(linkText="Gift Certificates")
	private WebElement giftCertificateFooterOption;
	
	@FindBy(linkText = "Affiliate")
	private WebElement affiliateFooterOption;
	
	@FindBy(linkText="Specials")
	private WebElement specialsFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='My Account']")
	private WebElement myAccountFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Order History']")
	private WebElement orderHistoryFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Wish List']")
	private WebElement wishListFooterOption;
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[text()='Newsletter']")
	private WebElement newsletterFooterOption;
	
	public LoginPage clickOnNewsletterFooterOption()
	{
		newsletterFooterOption.click();
		return new LoginPage(driver);
	}
	public LoginPage clickOnWishListFooterOption()
	{
		wishListFooterOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnOrderHistoryFooterPage()
	{
		orderHistoryFooterOption.click();
		return new LoginPage(driver);
	}
	public LoginPage clickOnMyAccountFooterOption()
	{
		myAccountFooterOption.click();
		return new LoginPage(driver);
	}
	public SpecialOffersPage clickOnSpecialsFooterOption()
	{
		specialsFooterOption.click();
		return new SpecialOffersPage(driver);
	}
	public AffiliateLoginPage clickOnAffiliateFooterOption()
	{
		affiliateFooterOption.click();
		return new AffiliateLoginPage(driver);
	}
	
	public PurchaseGiftCertificatePage clickOnGiftCertificateFooterOption()
	{
		giftCertificateFooterOption.click();
		return new PurchaseGiftCertificatePage(driver);
	}
	public BrandPage clickOnbrandFooterOption()
	{
		brandFooterOption.click();
		return new BrandPage(driver);
	}
	public SiteMapPage clickOnSiteMapFooterOption()
	{
		siteMapFooterOption.click();
		return new SiteMapPage(driver);
	}
	public ProductReturnsPage clickOnReturnsFooterOption()
	{
		returnsFooterOption.click();
		return new ProductReturnsPage(driver);
	}
	
	
	public ContactUsPage clickOnContactUsFooterOption()
	{
		contactUsFooterOption.click();
		return new ContactUsPage(driver);
	}
	
	public TermsAndConditionsPage clickOnTermAndConditionFooterOption()
	{
		termAndConditionsFooterOption.click();
		return new TermsAndConditionsPage(driver);
		
	}
	public PrivacyPolicyPage clickOnPrivacyPolicyFooterOption()
	{
		privacyPolicyFooterOption.click();
		return new PrivacyPolicyPage(driver);
	}
	public DeliveryInformationPage clickOnDeliveryInfoFooterOption()
	{
		deliveryInfoFooterOption.click();
		return new DeliveryInformationPage(driver);
	}
	public AboutUsPage clickOnAboutUsFooterOption()
	{
		aboutUsFooterOption.click();
		return new AboutUsPage(driver);
	}
}
