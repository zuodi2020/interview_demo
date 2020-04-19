package com.zd.interview_test.driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class DriverBase {
	public static WebDriver dr = null;

	public static int implicitlyWait = 30;
	public static int webDriverWait = 30;
	Logger log = Logger.getLogger("test");
	public static final String pageFilePath = "resources/pages";
	public static final String SCREENSHOTPATH = "/test-output/screenshot";
	public static final String PFILEPATH = File.separatorChar + "resources" + File.separatorChar + "config"
			+ File.separatorChar + "config.properties";
	public static Map<String, String> mapproperties = new HashMap<String, String>();
	private static Properties config = new Properties();
	static {

		String configPath = System.getProperty("user.dir") + PFILEPATH;
		initProperties(configPath);
	}

	public static void initProperties(String configPath) {
		File file = new File(configPath);
		InputStreamReader fn = null;
		if (file.exists()) {
			try {
				fn = new InputStreamReader(new FileInputStream(configPath), "UTF-8");
				config.load(fn);
				if (!config.isEmpty()) {
					Set<Object> keys = config.keySet();
					for (Object key : keys) {
						mapproperties.put(key.toString(), config.getProperty(key.toString()));
						if (!System.getProperties().containsKey(key.toString())
								&& !config.getProperty(key.toString()).isEmpty()) {
							System.setProperty(key.toString(), config.getProperty(key.toString()));
						}
					}
					keys.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public WebDriver getDriver() {
		return dr;
	}

	public void setDriver(WebDriver wd) {
		dr = wd;
	}

	
	public void launch() {
		String browser = System.getProperty("WebDriver.Browser");
		String browserLocation = System.getProperty("WebDriver.Browser.Location");
		// launch browser
		if (browser.equalsIgnoreCase("Firefox")) {
			log.info("firefox browser");

			if (StringUtils.isNotBlank(browserLocation)) {
				System.setProperty("webdriver.firefox.bin", browserLocation);
			}
			dr = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			if (StringUtils.isNotBlank(browserLocation)) {
				System.setProperty("webdriver.chrome.driver", browserLocation);
			}
			log.info("chrome browser");
			dr = new ChromeDriver();
		} else {
			Assert.fail("new driver error");
		}

		implicitlyWait = Integer.parseInt(System.getProperty("WebDriver.ImplicitlyWait").trim());
		webDriverWait = Integer.parseInt(System.getProperty("WebDriver.WebDriverWait").trim());
		dr.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
		dr.manage().window().maximize();
	}

	public static void screenShot(String name) {
		String path = System.getProperty("user.dir") + SCREENSHOTPATH;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		File screenShotFile = ((TakesScreenshot) dr).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShotFile, new File(path + "/" + name + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * take screenShot
	 * 
	 */
	public void screenShot() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		screenShot(df.format(new Date()).toString());
	}

}
