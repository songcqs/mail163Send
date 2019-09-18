package com.selenium.pageobjcet;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by 米阳 on 14/10/2017.
 * 
 * 业务层:最后我们来看下业务层的封装，也就是我们真正Case的编写
 */
public class LoginTest {
	WebDriver driver;

	@BeforeClass
	public void openChrome() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void testLogin2() {
		driver.get("http://mail.163.com/");
		LoginMail loginMail = new LoginMail(driver);
		loginMail.login("meyoungtester", "meyoung123");
	}

	@AfterClass
	public void closedChrome() {
		driver.quit();
	}

}
