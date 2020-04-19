package com.zd.interview_test.page;

import java.io.File;

import org.openqa.selenium.WebDriver;

import com.zd.interview_test.base.PageBase;
import com.zd.interview_test.driver.DriverBase;

public class ImagePage extends PageBase {
	public static String url = "http://image.baidu.com";
	public static String search_input = "kw";
	public static String search_button = "span.s_search";
	public static String search_text = "test";
	public static String image_path = "";
	public static String imageDataPath = System.getProperty("user.dir") + File.separatorChar + "resources"
			+ File.separatorChar + "pages" + File.separatorChar + "ImagePage.properties";
	static {
		DriverBase.initProperties(imageDataPath);
		String resultValue = System.getProperty("VISIT_RESULT");

		//// div[@id='imgid']/div[1]/ul[1]/li[3]
		image_path="//li[@class='imgitem']["+resultValue+"]/div";
	}

	public void loadImagePage() {
		DriverBase.dr.get(url);
	}

	public void searchImage() {
		super.inputWithoutCheck(search_text, search_input, "id");
		super.click(search_button, "cssselector");

	}

	public void findoutImage() {
		System.out.print("------------" + image_path);
		click(image_path, "xpath");
	}

	public void screenshot() {
		String handle = DriverBase.dr.getWindowHandle();
		 for (String handles:DriverBase.dr.getWindowHandles()) {
			 if (handles.equals(handle))
				 continue;
			 DriverBase.dr.switchTo().window(handles);
		 }
		DriverBase.screenShot(this.getClass().getSimpleName());
	}

}
