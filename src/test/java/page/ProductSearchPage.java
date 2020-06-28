package page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import qaFramework.PageObject.PageObject;

public class ProductSearchPage extends PageObject{

	public ProductSearchPage(AppiumDriver<WebElement> _driver) throws Exception {
		super(_driver);
		driver = _driver;
	}
	
	public static final String PRODUCT_INFO = "//android.view.View[2]";
	
	public String fetchProductInformation() throws Exception {
		waitForElementIsVisible(PRODUCT_INFO, "xpath");
		String description = getText(PRODUCT_INFO,"xpath");
		return description;
	}
	

}
