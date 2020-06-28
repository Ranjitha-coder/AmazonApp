package qaFramework.PageObject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;


public class PageObject{

	public static AppiumDriver<WebElement> driver;
	public static final int WAIT_TIME_LONGDURATION = 100;
	public static final int WAIT_TIME_SHORTDURATION = 100;
	public static final int WAIT_TIME = 100;

	public PageObject(AppiumDriver<WebElement> _driver) throws Exception {
		driver = _driver;
	}

	public enum type {
		xpath, css, id, name, tagName, linktext,className;
	}

	@SuppressWarnings("incomplete-switch")
	public static By getWebDriverBy(String elementName, String locatorStrategy)
			throws Exception {
		By webdriverby = null;

		switch (type.valueOf(locatorStrategy)) {
		case css:
			webdriverby = By.cssSelector(elementName);
			break;
		case name: 
			webdriverby = By.name(elementName);
			break;
		case tagName:
			webdriverby = By.tagName(elementName);
			break;
		case linktext:
			webdriverby = By.linkText(elementName);
			break;
		case className:
			webdriverby = By.className(elementName);
			break;
		case id:
			webdriverby = By.id(elementName);
			break;
		
		}
		
		return webdriverby;
	}

	@SuppressWarnings("incomplete-switch")
	public static WebElement getElementBy(String elementName,String locatorStrategy) {
		WebElement element = null;
		switch(type.valueOf(locatorStrategy)) {
		case xpath:
			element = driver.findElementByXPath(elementName);
			break;
	}	
		return element;
	}
	
	public WebElement getElement(String elementName, String locatorStrategy)throws Exception{
		WebElement element;
		if(locatorStrategy=="xpath") {
		element = getElementBy(elementName,locatorStrategy);
		}
		else {
		element = driver.findElement(getWebDriverBy(elementName, locatorStrategy));
		}
		return element;
	}

	public List<WebElement> getElementLists(String elementName, String locatorStrategy) throws Exception {
		List<WebElement> elements;
		if(locatorStrategy == "xpath") {
			elements = driver.findElementsByXPath(elementName);
		}
		else {
		elements = driver.findElements(getWebDriverBy(elementName, locatorStrategy));
		}
		return elements;
	}
	/************** Selenium Functions*********************/

	public void sendKeys(String elementName, String locatorStrategy, String keys) throws Exception {
		waitForElementIsVisible(elementName, locatorStrategy);
		getElement(elementName, locatorStrategy).click();
		getElement(elementName, locatorStrategy).sendKeys(keys);
	}

	public String getText(String elementName, String locatorStrategy) throws Exception {
		String text = getElement(elementName, locatorStrategy).getText();
		return text;
	}
	public String getAttributeValue(String elementName, String locatorStrategy) throws Exception {
		String text = getElement(elementName, locatorStrategy).getAttribute("value");
		return text;
	}

	/************** Browser Navigations*********************/

	public void navigateForward() throws Exception{
		driver.navigate().forward();
	}
	public void navigateBack() throws Exception{	
		driver.navigate().back();
	}
	public void browserRefresh() throws Exception{
		driver.navigate().refresh();
	}

	/************** Scroll Functions*********************/

	//This function scroll the page till the element is found
	public void scrollToElement(String elementName, String locatorStrategy) throws Exception{
		TouchActions action = new TouchActions(driver);
		action.scroll(getElement(elementName,locatorStrategy), 10, 100);
		action.perform();
	}

	/************** Click Functions  *********************/

	public void click(String elementName, String locatorStrategy) throws Exception {
		waitForElementIsVisible(elementName, locatorStrategy);
		getElement(elementName, locatorStrategy).click();
	}

	/**************Wait Functions *********************/
	//wait for text present 

	public boolean waitForTextPresent(String elementName, String locatorStrategy,String text) throws Exception{
		WebElement element = getElement( elementName, locatorStrategy);
		boolean condition = true;
		try{
			WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
			wait.until(ExpectedConditions.textToBePresentInElement(element, text));
		}
		catch (Exception ex){
			condition = false;
			throw ex;
		}
		return condition;
	}

	//wait for text present in Value
	public boolean waitForTextPresentByValue(String elementName, String locatorStrategy,String text) throws Exception{
		WebElement element = getElement( elementName, locatorStrategy);
		boolean condition = true;
		try{
			WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
			wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
		}
		catch (Exception ex){
			condition = false;
			throw ex;
		}
		return condition;
	}
	//Expectation for checking an element is visible and enabled
	public void waitForElementToBeClickable(String elementName, String locatorStrategy)throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		wait.until(ExpectedConditions.elementToBeClickable(getElement(elementName,locatorStrategy)));
	}

	//Expectation for checking an alert pop-up is present
	public void waitForAlert()throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	//An expectation for checking WebElement with given locator has attribute which contains specific value
	public WebElement waitForAttributeValue(String elementName, String locatorStrategy,String attribute, String value)throws Exception{
		WebElement element = getElement(elementName,locatorStrategy);
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		wait.until(ExpectedConditions.attributeContains(element,attribute,value));
		return element;
	}

	//An expectation for checking if the given element is selected.
	public WebElement waitForElementIsSelected(String elementName, String locatorStrategy)throws Exception{
		WebElement element = getElement(elementName,locatorStrategy);
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		wait.until(ExpectedConditions.elementToBeSelected(element));
		return element;
	}

	//An expectation for checking that an element, known to be present on the DOM of a page, is visible
	public WebElement waitForElementIsVisible(String elementName, String locatorStrategy)throws Exception{
		WebElement element = getElement(elementName,locatorStrategy);
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOf(element) );
		return element;
	}

	//An expectation for checking whether the given frame is available to switch to.
	public WebElement waitForFrameToSwitch(String elementName, String locatorStrategy)throws Exception{
		WebElement element = getElement(elementName,locatorStrategy);
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( element));
		return element;
	}

	//An expectation for checking the element to be invisible
	public WebElement waitForInvisibleOfElement(String elementName, String locatorStrategy)throws Exception{
		WebElement element = getElement(elementName,locatorStrategy);
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		wait.until(ExpectedConditions.invisibilityOf(element));
		return element;
	}

	//An expectation for checking the page load
	public static void waitForPageLoad() {
		try {
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver webdriver) {
					return ((JavascriptExecutor) webdriver).executeScript("return document.readyState")
							.equals("complete");
				}
			};
			WebDriverWait wait = new WebDriverWait(driver,WAIT_TIME_LONGDURATION );
			wait.until(pageLoadCondition);
		} catch (WebDriverException we) {
			System.out.println(we.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void waitForTextToBePresent(String elementName, String locatorStrategy, String strText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
			if(locatorStrategy=="id"||locatorStrategy=="xpath") {
				wait.until(ExpectedConditions.textToBePresentInElement(getElementBy(elementName,locatorStrategy), strText));
			}
			else {
				wait.until(ExpectedConditions.textToBePresentInElementLocated(getWebDriverBy(elementName,locatorStrategy), strText));
			}
		} catch (WebDriverException we) {
			System.out.println(we.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Checks is element present returns boolean value
	public boolean isElementPresent(String elementName, String locatorStrategy) {
		try {
			List<WebElement> elements;
				elements = getElementLists(elementName,locatorStrategy);
				if (elements.size()!= 0) {
					return true;

				} else {
					return false;
				}

		} catch (WebDriverException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForAllElementVisible(String elementName, String locatorStrategy) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_LONGDURATION);
			if(locatorStrategy=="id"||locatorStrategy=="xpath") {
				wait.until(ExpectedConditions.visibilityOfAllElements(getElementBy(elementName,locatorStrategy)));
			}
			else {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getWebDriverBy(elementName,locatorStrategy)));
			}
		} catch (WebDriverException we) {
			System.out.println(we.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**************Verification Functions *********************/
	
	public void verifyTitle(String strValue,String errorMessage) throws Exception {
		String titleName = driver.getTitle();
		assertEquals(strValue, titleName, errorMessage);
	}

	public  void verifyText(String elementName, String locatorStrategy, String strValue, String errorMessage) throws Exception {
		assertEquals(strValue, getElement(elementName,locatorStrategy).getText().trim(),errorMessage);
	}

	public  void verifyTextIsPresent(String elementName, String locatorStrategy, String errorMessage) throws Exception {
		assertTrue(isElementPresent(elementName,locatorStrategy),errorMessage);
	}

	public  void verifyTextIsNotPresent(int count,String elementName, String locatorStrategy, String errorMessage) throws Exception {
		assertFalse(isElementPresent(count,elementName,locatorStrategy),errorMessage);
	}

	public void verifyInputValue(String elementName, String locatorStrategy, String strValue,String errorMessage) throws Exception {
		assertEquals(getAttributeValue(elementName,locatorStrategy).trim(),strValue,errorMessage);
	}
	
	public  void verifyTextIsNotDisplayed(String elementName, String locatorStrategy, String errorMessage) throws Exception {
		assertFalse(getElement(elementName,locatorStrategy).isEnabled());
	}
	
	public  void verifyElementIsNotPresent(String elementName, String locatorStrategy, String errorMessage) throws Exception {
		assertFalse(getElement(elementName,locatorStrategy).isDisplayed());
	}
	
	public boolean isElementPresent(int count,String elementName,String locatorStrategy) throws Exception {
		driver.manage().timeouts().implicitlyWait(count, TimeUnit.SECONDS);
		boolean present = true;
		try {
			if (driver.findElements(getWebDriverBy(elementName,locatorStrategy)).size() == 0) {
				present = false;
			}
		}
		catch (Exception e) {
			return false;
		}
		return present;
	}
	
	public void verifyTextAreEqual(String actual,String expected,String errorMessage) throws Exception {
		assertEquals(actual,expected);
	}
	
	public  boolean verifySelectedOption(String elementName, String locatorStrategy)throws Exception {
		try {
			assertTrue(getElement(elementName,locatorStrategy).isSelected());
		} catch (StaleElementReferenceException se) {
			waitForPageLoad();
			assertTrue(getElement(elementName,locatorStrategy).isSelected());
		}
		return false;
	}
	
	public void rotateScreen() throws Exception {
		driver.rotate(new DeviceRotation(10, 10, 10));
	}
	
	public void setScreenresolution() throws Exception {
		driver.manage().window().setSize(new Dimension(10, 10));
	}
}
