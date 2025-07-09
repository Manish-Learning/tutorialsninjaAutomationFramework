package pages;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class EditAccountInformationPage extends RootPage {

	public EditAccountInformationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "input-firstname")
	private WebElement firstNameField;

	@FindBy(id = "input-lastname")
	private WebElement lastNameField;

	@FindBy(id = "input-email")
	private WebElement emailField;

	@FindBy(id = "input-telephone")
	private WebElement telephoneField;

	public String getFirstNameFieldValue() {
		return getDomAttributeOfElement(firstNameField, "value");
	}

	public String getLastNameFieldValue() {
		return getDomAttributeOfElement(lastNameField, "value");
	}

	public String getEmailFieldValue() {
		return getDomAttributeOfElement(emailField, "value");
	}

	public String getTelephoneFieldValue() {
		return getDomAttributeOfElement(telephoneField, "value");
	}
}
