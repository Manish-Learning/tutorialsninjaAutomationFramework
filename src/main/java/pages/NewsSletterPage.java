package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class NewsSletterPage extends RootPage {

	public NewsSletterPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Newsletter']")
	private WebElement newsSletterBreadCrumb;

	@FindBy(xpath = "//input[@name='newsletter'][@value='1']")
	private WebElement yesNewSletterOption;

	@FindBy(xpath = "//input[@name='newsletter'][@value='0']")
	private WebElement noNewSletterOption;

	public boolean didWeNavigteToNewsletterPage() {
		return isElementDisplayed(newsSletterBreadCrumb);
	}

	public boolean isYesNewsletterOptionSelected() {
		return isElementSelected(yesNewSletterOption);
	}

	public boolean isNoNewsletterOptionSelected() {
		return isElementSelected(noNewSletterOption);
	}
}
