package page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import qaFramework.PageObject.PageObject;

public class SignInPage extends PageObject {

	public SignInPage(AppiumDriver<WebElement> _driver) throws Exception {
		super(_driver);
		driver = _driver;
	}
	
	public static final String SIGN_IN = "in.amazon.mShop.android.shopping:id/sign_in_button";
	
	public SignInPage navigateToSignIn() throws Exception {
		click(SIGN_IN,"id");
		return this;
	}

}
