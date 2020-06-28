package page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import qaFramework.PageObject.PageObject;

public class LoginPage extends PageObject {

	public LoginPage(AppiumDriver<WebElement> _driver) throws Exception {
		super(_driver);
		driver = _driver;
	}

	public static final String LOGIN_CHECKBOX = "in.amazon.mShop.android.shopping:id/login_accordion_header",
			EMAIL      = "in.amazon.mShop.android.shopping:id/ap_email_login",
			CONTINUE   = "android.widget.Button",
			LOGIN_PAGE = "//android.view.View[@text()='Welcome']",
			PASSWORD   = "in.amazon.mShop.android.shopping:id/ap_password",
			LOGIN      = "in.amazon.mShop.android.shopping:id/signInSubmit";

	public LoginPage verifyLoginEnabled() throws Exception {
		waitForElementIsVisible(LOGIN_CHECKBOX, "id");
		verifySelectedOption(LOGIN_CHECKBOX,"id");
		return this;
	}

	public LoginPage provideContactOrEmail(String email) throws Exception {
		sendKeys(EMAIL,"id",email);
		verifyInputValue(EMAIL,"id",email,email+"not displayed");
		return this;
	}
	
	public LoginPage clickContinue() throws Exception {
		click(CONTINUE,"className");
		return this;
	}

	public LoginPage verifyLoginPageIsOpened() throws Exception {
		waitForElementIsVisible(LOGIN_PAGE, "xpath");
		verifyTextIsPresent(LOGIN_PAGE,"xpath","Login page is not displayed");
		return this;
	}

	public LoginPage providePassword(String password) throws Exception {
		sendKeys(PASSWORD,"id",password);
		verifyInputValue(PASSWORD,"id",password,password+"no displayed");
		return this;
	}
	
	public LoginPage login() throws Exception {
		click(LOGIN,"id");
		return this;
	}
	
	public LoginPage loginToApplication(String email,String password) throws Exception {
		this.verifyLoginEnabled()
		.provideContactOrEmail(email)
		.clickContinue()
		.providePassword(password)
		.login();
		return this;
	}

}
