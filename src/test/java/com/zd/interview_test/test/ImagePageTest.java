package com.zd.interview_test.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.zd.interview_test.base.TestCaseBase;
import com.zd.interview_test.page.ImagePage;

public class ImagePageTest extends TestCaseBase {

	public static ImagePage imagePage = null;

	@BeforeClass
	public void beforeBaseClass() {
		imagePage = new ImagePage();
	}

	@Test
	public void imageSearch_Test() throws InterruptedException {
		imagePage.loadImagePage();
		imagePage.searchImage();
		
		imagePage.findoutImage();
		Thread.sleep(10000);
		imagePage.screenshot();
		Assert.assertTrue(dr.getTitle().contains(imagePage.search_text));

	}

}
