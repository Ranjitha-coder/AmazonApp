package page;

import java.util.Random;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import qaFramework.PageObject.PageObject;

public class HomePage extends PageObject {

	public HomePage(AppiumDriver<WebElement> _driver) throws Exception {
		super(_driver);
		driver = _driver;
	}

	public static final String HOME = "in.amazon.mShop.android.shopping:id/action_bar_home_logo",
			SEARCH         = "in.amazon.mShop.android.shopping:id/rs_search_src_text",
			PRODUCTS       = "//android.view.View[",
			SEARCH_RESULTS = "//android.view.View[1]",
			PRODUCT        = "//android.view.View[",
			NEXT           = "//android.view.View[20]";

	public HomePage verifyHomePageIsDisplayed() throws Exception {
		waitForElementIsVisible(HOME, "id");
		verifyTextIsPresent(HOME,"id","Login page is not displayed");
		return this;
	}

	public HomePage provideProductName(String product) throws Exception {
		sendKeys(SEARCH,"id",product);
		verifyInputValue(SEARCH,"id",product,product+"not searched");
		return this;
	}

	public HomePage selectProductRandomly() throws Exception {
		int number = this.generalteRandomNumber();
		boolean value = isElementPresent(3,PRODUCT + number + SharedPage.CLOSING_BRACKETS,"xpath");
		while(value==false) {
			click(NEXT,"xpath");
			value = isElementPresent(3,PRODUCT + number + SharedPage.CLOSING_BRACKETS,"xpath");
		}
		this.scrollToElement(PRODUCT + number + SharedPage.CLOSING_BRACKETS, "xpath");
		click(PRODUCT + number + SharedPage.CLOSING_BRACKETS,"xpath");
		return this;
	}

	public HomePage selectProduct(String product) throws Exception {
		this.verifyHomePageIsDisplayed()
		.provideProductName(product)
		.selectProductRandomly();
		return this;
	}

	public int generalteRandomNumber() throws Exception {
		String results = getText(SEARCH_RESULTS,"xpath");
		int result = Integer.parseInt(results);
		Random rand = new Random();
		int number = rand.nextInt(result - 8 + 1) + result;
		return number;
	}

}
