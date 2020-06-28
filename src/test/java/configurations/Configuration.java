package configurations;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobilePlatform;
import qaFramework.UserDefinedFunctions.Date_Time_settings;

public class Configuration {

	public static AppiumDriver<WebElement> driver;

	public String platformName = "Android";
	String applicationPath;
	protected String Result,startTime,date;
	public static Date startDate;
	
	Date_Time_settings dts = new Date_Time_settings();
	
	@BeforeMethod
	public void setResultToFalse() throws Exception {
		Result = "FAIL";
		startTime = dts.timeNow("HH:mm:ss");
		date = dts.getCurrentDate("yyyy-MM-dd");
		startDate = new Date();
	}	

	@BeforeClass
	public void setUp() throws Exception {

		if (MobilePlatform.ANDROID == platformName) {

			File appDir = new File(System.getProperty("user.dir"));
			File app    = new File(appDir,"/src/test/resources/APKFiles/Amazon_shopping.apk");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("device","Android");

			//mandatory capabilities
			capabilities.setCapability("deviceName","mi");
			capabilities.setCapability("platformName","Android");
			capabilities.setCapability("platformVersion", "9");
			capabilities.setCapability("udid", "49f1099");
			capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
			capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
			capabilities.setCapability("newCommandTimeout", "2000000");
			capabilities.setCapability("autoGrantPermissions", true);
			//other caps
			capabilities.setCapability("app", app.getAbsolutePath());
			driver =  new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
		}

	}

	@AfterMethod
	//Takes screenshot of the failed Test case
	public void tearDown(ITestResult result) throws Exception {		
		if(ITestResult.FAILURE == result.getStatus()) {
			TakesScreenshot ts = (TakesScreenshot)driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String FailedTC_Name = result.getName();
			FailedTC_Name = FailedTC_Name + "_"+ dts.getCurrentDate("d_M_yyyy_HH_mm");
			FileUtils.copyFile(source, new File("./src/test/resources/Screenshots/"+ FailedTC_Name +".png"));
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	

}
