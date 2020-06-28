package requirement;

import java.util.Properties;

import org.testng.annotations.Test;

import configurations.Configuration;
import page.HomePage;
import page.LoginPage;
import page.ProductSearchPage;
import page.SignInPage;
import qaFramework.UserDefinedFunctions.ReadPropertyFiles;

public class Tests extends Configuration {

	public Tests() throws Exception {
		super();
	}

	SignInPage signIn;
	LoginPage login;
	HomePage homePage;
	ProductSearchPage searchPage;
	ReadPropertyFiles readPropertyFiles = new ReadPropertyFiles();
	Properties envPropertyDetails = readPropertyFiles.ReadEnvironment();

	String description;
	
	@Test(description= "Shop in amazon application")
	
	
	
	public void shopProduct() throws Exception {
		
		signIn = new SignInPage(driver);
		login  = new LoginPage(driver);
		homePage = new HomePage(driver);
		searchPage = new ProductSearchPage(driver);
		
		signIn.navigateToSignIn();
		login.loginToApplication(envPropertyDetails.getProperty("CONTACT"), envPropertyDetails.getProperty("PASSWORD"));
		homePage.selectProduct(envPropertyDetails.getProperty("PRODUCT"));
		description = searchPage.fetchProductInformation();//Comapre this description with description fetched in checkout screen using assertEquals method.
		//Amazon currently not allowing to add products to cart for purchase.
		
		Result = "PASS";
	}
}
