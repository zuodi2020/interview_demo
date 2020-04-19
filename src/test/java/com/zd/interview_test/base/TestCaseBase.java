package com.zd.interview_test.base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.zd.interview_test.driver.DriverBase;



public class TestCaseBase extends DriverBase{
	@BeforeTest
	public void beforeTestBase() {
		launch();
	}

	@AfterTest(alwaysRun=true)
	public void afterTest() {
		if (getDriver() != null) {
			getDriver().quit();
			setDriver(null);
		}
	}
}
