package com.once.test;

import static org.testng.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.lib.LibLogin;
import com.test.lib.MyRetry;

public class DeleteMail {
	WebDriver driver;

	@BeforeMethod
	public void setUp() throws Exception {
		System.out.println("start test");
		// 加载浏览器
		System.setProperty("webdriver.chrome.driver", "E:/Program Files/Google/Chrome/Application/chromedriver.exe");
		this.driver = new ChromeDriver();
		// 浏览器最大化
//		driver.manage().window().maximize();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		System.out.println("end test");
		Thread.sleep(2000);
		// 退出浏览器
		this.driver.quit();
	}

	/**
	 * 测试邮件删除
	 * 
	 * @throws InterruptedException
	 */
	@Test(retryAnalyzer = MyRetry.class) // 失败重跑
	public void deleteMail() throws InterruptedException {
		System.out.println("delete test");
		String username = "chengqingsong01";
		String password = "cqs0108152535";
		String sendedElement = "//span[text()='已发送']";

		// 先登录
		LibLogin libLoogin = new LibLogin(this.driver);
		libLoogin.login(username, password);
//		Actions action = new Actions(this.driver);
//		action.moveToElement(driver.findElement(By.xpath("//span[text()='已发送']"))).perform();

		Thread.sleep(1000);
		// 点击已发送
		this.driver.findElement(By.xpath(sendedElement)).click();
		// 选择要删除的邮件
		Thread.sleep(1000);
		List<WebElement> list = new ArrayList<WebElement>();
		list = this.driver.findElements(By.className("nui-chk-symbol"));
		list.get(1).click();
		// 获取被选择的邮件主题名
		list = this.driver.findElements(By.className("da0"));
		String content = list.get(0).getText();
		System.out.println("content:" + content);

		Thread.sleep(1000);
//		System.out.println(list.get(1).isSelected());
		// 点击删除
		this.driver.findElement(By.xpath("//span[text()='删 除']")).click();

		driver.navigate().refresh();

		boolean bool;
		try {
			driver.findElement(By.xpath("//span[text()='" + content + "']"));
			bool = true;
			System.out.println(content + "：存在");
		} catch (Exception e) {
			bool = false;
			System.out.println(content + "：不存在了");
		}
		assertFalse(bool);
	}

}
