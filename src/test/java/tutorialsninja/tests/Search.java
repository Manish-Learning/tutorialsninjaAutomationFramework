package tutorialsninja.tests;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.HeaderOptions;
import pages.LandingPage;
import tutorialsninja.base.Base;
import utils.CommonUtils;

public class Search extends Base {

	public WebDriver driver;
	Properties prop;

	@Parameters({"browser"})
	@BeforeMethod
	public void setup(String browserName) throws MalformedURLException, URISyntaxException {
		driver = openBrowserAndApplication(browserName);
		prop = CommonUtils.loadProperties();
		landingPage = new LandingPage(driver);
	}

	@AfterMethod
	public void tearDown() {
		closeBrowser(driver);
	}

	@Test(priority = 1)
	public void verifySearchWithValidProduct() {

		landingPage.enterProductNameIntoIntoSearchBoxFiled(prop.getProperty("existingProduct"));
		searchPage = landingPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchPage());
		Assert.assertTrue(searchPage.isExistingProductDisplayedInSearchResults());

	}

	@Test(priority = 2)
	public void verifySearchWithNonExistingProduct() {

		landingPage.enterProductNameIntoIntoSearchBoxFiled(prop.getProperty("nonExistingProduct"));
		searchPage = landingPage.clickOnSearchButton();
		String expectedMessage = "There is no product that matches the search criteria.";
		Assert.assertEquals(searchPage.getNoProductMessage(), expectedMessage);

	}

	@Test(priority = 3)
	public void verifySearchWithoutEnteringAnyProduct() {

		searchPage = landingPage.clickOnSearchButton();
		String expectedMessage = "There is no product that matches the search criteria.";
		Assert.assertEquals(searchPage.getNoProductMessage(), expectedMessage);

	}

	@Test(priority = 4)
	public void verifySearchAfterLogin() {

		loginPage = landingPage.navigateToLoginPage();
		accountPage = loginPage.loginToApplication(prop.getProperty("existingEmail"),
				prop.getProperty("validPassword"));
		headerOptions = new HeaderOptions(accountPage.getDriver());
		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProduct"));
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.isExistingProductDisplayedInSearchResults());

	}
	@Test(priority = 5)
	public void verifySearchResultingInMultipleProducts() {

		landingPage.enterProductNameIntoIntoSearchBoxFiled(prop.getProperty("searchTermResultMultipleProducts"));
		searchPage = landingPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.getNumberOfProductsDisplayedInSearchResults() > 1);

	}
	
	@Test(priority = 6)
	public void verifySearchFieldsPlaceholders() {

		headerOptions = new HeaderOptions(driver);
		String exptedSearchBoxPlaceHolderText = "Search";
		Assert.assertEquals(headerOptions.getPlaceHolderTextOfSearchBoxField(), exptedSearchBoxPlaceHolderText);
		searchPage = headerOptions.clickOnSearchButton();
		String exptdSearchCriteriaFieldPlaceHolderText = "Keywords";
		Assert.assertEquals(searchPage.getPlaceHolderTextOfSearchCriteriaField(),
				exptdSearchCriteriaFieldPlaceHolderText);

	}
	
	@Test(priority = 7)
	public void verifySearchingForProductUsingSearchCriteriaField() {

		headerOptions = new HeaderOptions(driver);
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterIntoSearchCriteriaField(prop.getProperty("existingProduct"));
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isExistingProductDisplayedInSearchResults());

	}
	@Test(priority = 8)
	public void verifySearchingForProductUsingSomeTextInProductDescription() {

		headerOptions = new HeaderOptions(driver);
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterIntoSearchCriteriaField(prop.getProperty("termInProductDescription"));
		searchPage.selectSearchInProductDescriptionCheckboxBoxField();
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductHavingDescriptionTextDisplayedInSearchResults());

	}
	
	@Test(priority = 9)
	public void verifySearchBySelectingSubCategory() {

		headerOptions = new HeaderOptions(driver);
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterIntoSearchCriteriaField(prop.getProperty("exitingProuductInSubCategory"));
		searchPage.selectOptionFromCategoryDropdownFieldUsingIndex(3);
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductInCategoryDisplayedInSearchResults());

	}
	
	@Test(priority = 10)
	public void verifySearchByUsingParentCategoryAndSearchInSubCategoriesOption() {

		headerOptions = new HeaderOptions(driver);
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterIntoSearchCriteriaField(prop.getProperty("exitingProuductInSubCategory"));
		searchPage.selectOptionFromCategoryDropdownFieldUsingIndex(1);
		searchPage.clickOnSearchButton();
		String expectedMessage = "There is no product that matches the search criteria.";
		Assert.assertEquals(searchPage.getNoProductMessage(), expectedMessage);
		searchPage.selectSearchInSubCategoriesCheckboxField();
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductInCategoryDisplayedInSearchResults());

	} 
	
	
}
