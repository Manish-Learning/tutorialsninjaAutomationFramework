package pages;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import pages.root.RootPage;

public class SearchPage extends RootPage {

	public SearchPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Search']")
	private WebElement searchPageBreadCrumb;

	@FindBy(linkText = "HP LP3065")
	private WebElement existingProduct;

	@FindBy(linkText = "iMac")
	private WebElement iMacProduct;

	@FindBy(xpath = "//input[@id='button-search']/following-sibling::p")
	private WebElement noProductMessage;

	@FindBy(xpath = "//div[@class='product-thumb']")
	private List<WebElement> numberOfProducts;

	@FindBy(id = "input-search")
	private WebElement searchCriteriaField;

	@FindBy(id = "button-search")
	private WebElement searchButton;

	@FindBy(id = "description")
	private WebElement searchInProductDescriptionCheckboxBoxField;

	@FindBy(name = "category_id")
	private WebElement categoryDropdownField;

	@FindBy(name = "sub_category")
	private WebElement searchInSubCategoriesCheckboxField;
	
	public void selectSearchInSubCategoriesCheckboxField()
	{
		searchInSubCategoriesCheckboxField.click();
	}

	@FindBy(id = "list-view")
	private WebElement listViewOption;

	@FindBy(xpath = "//span[text()='Add to Cart']")
	private WebElement addToCartOption;

	@FindBy(xpath = "//button[@*='Add to Wish List']")
	private WebElement addToWishlistOption;

	@FindBy(xpath = "//button[@*='Compare this Product']")
	private WebElement compareThisProduct;

	@FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
	private WebElement successMessage;

	@FindBy(xpath = "//div[@class='product-thumb']//img")
	private WebElement productImage;

	@FindBy(id = "grid-view")
	private WebElement gridOption;

	@FindBy(id = "compare-total")
	private WebElement productCompareLink;

	@FindBy(id = "input-sort")
	private WebElement sortByDropdownField;

	@FindBy(xpath = "(//div[@class='product-thumb']//h4//a)[1]")
	private WebElement firstProductInSearchResults;

	@FindBy(xpath = "(//div[@class='product-thumb']//h4//a)[2]")
	private WebElement secondProductInSearchResults;

	@FindBy(xpath = "(//div[@class='product-thumb']//h4//a)[3]")
	private WebElement thirdProductInSearchResults;

	@FindBy(xpath = "(//div[@class='product-thumb']//h4//a)[4]")
	private WebElement fourthProductInSearchResults;

	public boolean didWeNavigateToSearchPage() {
		return isElementDisplayed(searchPageBreadCrumb);
	}

	public boolean isExistingProductDisplayedInSearchResults() {
		return isElementDisplayed(existingProduct);
	}
	
	public boolean isProductHavingDescriptionTextDisplayedInSearchResults() {
		return isElementDisplayed(iMacProduct);
	}
	
	public boolean isProductInCategoryDisplayedInSearchResults() {
		return isElementDisplayed(iMacProduct);
	}

	public String getNoProductMessage() {
		return getTextOfElement(noProductMessage);
	}

	public int getNumberOfProductsDisplayedInSearchResults() {
		return getElementCount(numberOfProducts);
	}

	public String getPlaceHolderTextOfSearchCriteriaField() {
		return getDomAttributeOfElement(searchCriteriaField, "placeholder");
	}

	public void enterIntoSearchCriteriaField(String productName) {
		searchCriteriaField.sendKeys(productName);
	}

	public void clickOnSearchButton() {
		searchButton.click();
	}

	public void selectSearchInProductDescriptionCheckboxBoxField() {
		searchInProductDescriptionCheckboxBoxField.click();
	}
	
	public void selectOptionFromCategoryDropdownFieldUsingIndex(int indexNumber) {
		Select select = new Select(categoryDropdownField);
		select.selectByIndex(indexNumber);
	}
}
