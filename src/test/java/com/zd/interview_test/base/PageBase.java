package com.zd.interview_test.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.zd.interview_test.driver.DriverBase;

public class PageBase {

	public void input(String text, String locator, String type) {
		inputWithoutCheck(text, locator, type);
		valueToBe(text, locator, type);
	}

	public void inputWithoutCheck(String text, String locator, String type) {
		scrollIntoView();
		WebElement e = getWebElement(locator, type);
		e.clear();
		e.sendKeys(text);
	}

	public void scrollIntoView() {
//		jsExecutor("arguments[0].scrollIntoView();");
	}

	public WebElement getWebElement(String locator, String type) {
		return DriverBase.dr.findElement(getBy(locator, type));
	}

	public void valueToBe(final String value, final String locator, final String type) {
		new WebDriverWait(DriverBase.dr, DriverBase.webDriverWait).until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return getAttribute("value", locator, type).equals(value);
			}
		});
	}

	public String getAttribute(String attribute, String locator, String type) {
		return getWebElement(locator, type).getAttribute(attribute);
	}
	
	public void click(String locator, String type) {
		scrollIntoView();
		getWebElement(locator,  type).click();
	}
	
	public boolean isElementPresent(int timeout,String locator, String type) {
		try {
			DriverBase.dr.manage().timeouts().implicitlyWait(timeout,TimeUnit.SECONDS);
			getWebElement(locator,type);
			DriverBase.dr.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			return true;
		} catch (NoSuchElementException e) {
			DriverBase.dr.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			return false;
		}
	}
	
	
	public  boolean isVisible(String locator, String type) {
		return getWebElement(locator,type).isDisplayed();
	}
	
	public void toBeVisible(String locator, String type) {
		new WebDriverWait(DriverBase.dr, DriverBase.webDriverWait).until(ExpectedConditions.visibilityOfElementLocated(getBy(locator,type)));
	}
	
	
	public void toBeInvisible(String locator, String type) {
		new WebDriverWait(DriverBase.dr, DriverBase.webDriverWait).until(ExpectedConditions.invisibilityOfElementLocated(getBy(locator,type)));
	}
	
	public  boolean isEnabled(String locator, String type) {
		return getWebElement(locator,type).isEnabled();
	}
	private By getBy(String locator, String type) {
		if (type == null) {
			return By.xpath(locator);
		} else {
			type = type.toLowerCase();
			if (type.equals("xpath")) {
				return By.xpath(locator);
			} else if (type.equals("id")) {
				return By.id(locator);
			} else if (type.equals("linktext") || type.equals("link")) {
				return By.linkText(locator);
			} else if (type.equals("partiallinktext")) {
				return By.partialLinkText(locator);
			} else if (type.equals("name")) {
				return By.name(locator);
			} else if (type.equals("tagname")) {
				return By.tagName(locator);
			} else if (type.equals("classname")) {
				return By.className(locator);
			} else if (type.equals("cssselector") || type.equals("css")) {
				return By.cssSelector(locator);
			} else {
				return By.xpath(locator);
			}
		}

	}
}
