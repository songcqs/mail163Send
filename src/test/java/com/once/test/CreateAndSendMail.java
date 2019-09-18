package com.once.test;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.test.lib.LibLogin;
import com.test.lib.MyRetry;

public class CreateAndSendMail {
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
	 * 测试创建与发送邮件
	 * 
	 * @throws InterruptedException
	 */
	@Test(retryAnalyzer = MyRetry.class) // 失败重跑
	public void createMail() throws InterruptedException {
		System.out.println("createandmail test");
		String username = "a1955031205130210b";
		String password = "1955031205130210";

		String address = "1357863545@qq.com";// 要输入的收件人邮箱
		String title = "Test title " + System.currentTimeMillis();// 要输入的主题内容
		String content = "Test content " + System.currentTimeMillis();// 要输入的内容

		String writeMailElement = "//span[text()='写 信']";// 写信按钮的xpath
		String addressElement = "nui-editableAddr-ipt";// 收件人输入框的className
		String titleElement = "div[id^='_mail_input_2']>input.nui-ipt-input";// 主题输入框的cssPath
		String frameElement = "APP-editor-iframe";// 表单元素的className
		String contentElement = "nui-scroll";// 输入文本内容的className
		String sendElement = "//footer[@class='jp0']/descendant::span[text()='发送']";// 发送按钮的xpath
		String sendSaveElement = "//span[text()='保存并发送']";// 保存并发送xpath
		String sendSuccess = "//h1[text()='发送成功']";

		// 先登录
		LibLogin libLoogin = new LibLogin(this.driver);
		libLoogin.login(username, password);
		// 点击写信
		this.driver.findElement(By.xpath(writeMailElement)).click();
		Thread.sleep(2000);

		// 输入收件人邮箱账号
		this.driver.findElement(By.className(addressElement)).sendKeys(address);
//		new WebDriverWait(this.driver, 10)
//				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(titleElement)));
		// 输入主题
		Thread.sleep(2000);
		this.driver.findElement(By.cssSelector(titleElement)).sendKeys(title);

		Thread.sleep(1000);
		// 跳转frame
		this.driver.switchTo().frame(this.driver.findElement(By.className(frameElement)));
		// 输入内容
		this.driver.findElement(By.className(contentElement)).sendKeys(content);
		Thread.sleep(1000);
		// 回到默认表单
		this.driver.switchTo().defaultContent();
		// 点击发送
		this.driver.findElement(By.xpath(sendElement)).click();
		// 点击保存并发送
//		this.driver.findElement(By.xpath(sendsaveElement)).click();

		/*
		 * WebElement w = this.driver.findElement(By.className("jp0"));
		 * System.out.println(w.getText() + "----");
		 * w.findElement(By.cssSelector("div[id*='_mail_button']")).click();
		 */

		Thread.sleep(2000);
		// 判断URL是否跳转(跳转URL含有)
		assertTrue(this.driver.getCurrentUrl().contains("jsp"));

		By successBy = By.xpath(sendSuccess);
		WebElement successElement = this.driver.findElement(successBy);
		new WebDriverWait(this.driver, 10).until(ExpectedConditions.presenceOfElementLocated(successBy));
		String successText = successElement.getText();
		System.out.println("successText:" + successText);
		assertTrue(successText.contains("发送成功"));

		/*
		 * // 打开新标签 ((JavascriptExecutor)
		 * driver).executeScript("window.open('http://www.baidu.com', '_blank')");
		 * 
		 * String handle = driver.getWindowHandle(); for (String tempHandle :
		 * driver.getWindowHandles()) { if (tempHandle.equals(handle)) { continue; }
		 * else { driver.switchTo().window(tempHandle); } }
		 */
		// 判断页面是否发送成功(到已发送页面查看)
//		this.driver.get(
//				"https://mail.163.com/js6/main.jsp?sid=bANtwNZhdccJoobQxjhhBKVBoXwBRngz&df=mail163_letter#module=mbox.ListModule");
////		this.driver.navigate().back();

//		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, "t");
//		driver.findElement(By.linkText("urlLink")).sendKeys(selectLinkOpeninNewTab);

//		Actions actionOpenLinkInNewTab = new Actions(driver);
//		actionOpenLinkInNewTab.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).perform();

//		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");

//		WebElement ele = this.driver.findElement(By.cssSelector(".span da0"));
//		System.out.println(ele.getText() + ".............");
//		assertTrue(ele.getText().contains(title));

//		下面的代码将打开新标签中的链接。
//		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
//		driver.findElement(By.linkText("urlLink")).sendKeys(selectLinkOpeninNewTab);

//		下面的代码将打开空的新标签。
//		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,"t");
//		driver.findElement(By.linkText("urlLink")).sendKeys(selectLinkOpeninNewTab);
	}

}
