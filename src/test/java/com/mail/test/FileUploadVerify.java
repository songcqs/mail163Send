package com.mail.test;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mail.base.WebAutoTest;

public class FileUploadVerify {
	@BeforeMethod
	public void beforeMethod() throws InterruptedException {
		// 打开浏览器，访问163邮箱登录页
		WebAutoTest.openBrowser("https://mail.163.com/", "chrome");
	}

	@Test
	public void uplod() throws InterruptedException, AWTException {
		// 跳转frame
		WebAutoTest.switchToFrame(By.cssSelector("iframe[id^=x-URS-iframe]"));
		// 输入用户名
		WebAutoTest.inputText(By.cssSelector("input[class*=dlemail]"), "songcqs");
		// 输入密码
		WebAutoTest.inputText(By.cssSelector(".j-inputtext.dlpwd"), "cqs0108152535");
		// 点击登录
		WebAutoTest.clickElement(By.id("dologin"));
		// 点击写信
		WebAutoTest.clickElement(By.xpath("//span[text()='写 信']"));
		// 输入接收方
		WebAutoTest.inputText(By.className("nui-editableAddr-ipt"), "1357863545@qq.com");
		// 输入主题
		WebAutoTest.inputText(By.cssSelector("div[aria-label$='邮件主题']>input"), "上传测试");
		// AutoIT生成的辅助文件路径
		String executeFile = "E:\\Javaworkspace\\mail163Send\\source\\uplod.exe";

		// =====================方法一：单个文件上传=========================//
		// 点击上传文件按钮
//		WebAutoTest.clickElement(By.xpath("//div[@class='by0'][1]"));
		// 调用文件上传方法上传文件(通过AutoIT实现)
//		WebAutoTest.handleUpload("chrome", "C:\\Users\\Administrator\\Desktop\\测试用例.xlsx", executeFile);

		// =====================方法二：多个文件上传=========================//
		// 所有需要上传的文件集合
		String[] list = { "C:\\Users\\Administrator\\Desktop\\pom.xml", "C:\\Users\\Administrator\\Desktop\\测试用例.xlsx",
				"C:\\Users\\Administrator\\Desktop\\PageObject.jpg" };
		// 调用批量上传方法
		WebAutoTest.handleUploads(By.xpath("//div[@class='by0'][1]"), "chrome", list, executeFile);
		Thread.sleep(1000);

		// =====================方法三：单个文件上传=========================//
		/*
		 * // 指定图片的路径，这里我放桌面上 StringSelection sel = new
		 * StringSelection("C:\\Users\\Administrator\\Desktop\\测试用例.xlsx");
		 * 
		 * // 把图片文件路径复制到剪贴板
		 * Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
		 * System.out.println("selection" + sel);
		 * 
		 * // 点击本地上传图片 WebAutoTest.clickElement(By.xpath("//div[@class='by0'][1]"));
		 * 
		 * // 新建一个Robot类的对象 Robot robot = new Robot();
		 * 
		 * Thread.sleep(1000);
		 * 
		 * // 按下回车 robot.keyPress(KeyEvent.VK_ENTER);
		 * 
		 * // 释放回车 robot.keyRelease(KeyEvent.VK_ENTER);
		 * 
		 * // 按下 CTRL+V robot.keyPress(KeyEvent.VK_CONTROL);
		 * robot.keyPress(KeyEvent.VK_V);
		 * 
		 * // 释放 CTRL+V robot.keyRelease(KeyEvent.VK_CONTROL);
		 * robot.keyRelease(KeyEvent.VK_V); Thread.sleep(1000);
		 * 
		 * // 点击回车 Enter robot.keyPress(KeyEvent.VK_ENTER);
		 * robot.keyRelease(KeyEvent.VK_ENTER);
		 */
		/**
		 * 校验文件是否上传成功
		 * 
		 * @@步骤：获取所有上传文件元素 - 遍历元素取得上传文件名并加入list - assert判断list中是否含有上传的文件名
		 */
		WebDriver webDriver = WebAutoTest.getDriver();
		List<WebElement> webElement = webDriver.findElements(By.className("o0"));
		List<String> listText = new ArrayList<>();
		for (WebElement element : webElement) {
			System.out.println(element.getText());
			listText.add(element.getText());
		}
		System.out.println(listText);
		assertTrue(listText.contains("测试用例.xlsx"));

		// 点击图片图标
		WebAutoTest.clickElement(By.className("ico-editor-image"));
		// 点击“浏览”按钮
		WebAutoTest.clickElement(By.xpath("//span[text()='浏览']"));
		// 调用文件上传方法上传图片(通过AutoIT实现)
		WebAutoTest.handleUpload("chrome", "G:\\图片\\动态-搞笑\\开花瞬间-荷花.gif", executeFile);
		// 跳转frame
		WebAutoTest.switchToFrame(By.className("APP-editor-iframe"));
		// 输入内容
		WebAutoTest.inputText(By.cssSelector("body[class=nui-scroll]"), "这是一个文件上传测试邮件！");
		// 返回默认表单
		WebAutoTest.switchToParentFrame();
		// 点击发送
		WebAutoTest.clickElement(By.xpath("//span[text()='发送']"));
	}

	@AfterMethod
	public void afterMethod() {
		// 关闭浏览器
//		WebAutoTest.closeAllBrowser();
	}

}
