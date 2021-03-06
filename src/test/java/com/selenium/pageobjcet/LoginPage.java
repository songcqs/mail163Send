package com.selenium.pageobjcet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by 米阳 on 14/10/2017.
 * 
 * 对象库层:首先我们新建个叫LoginPage的类，编写登录页面所有可能需要操作的元素定位方式和操作
 * 这里我们只把邮件输入框和密码输入框的操作进行封装，当然如果为了便于阅读也可以把所有的元素操作进行封装取名。
 */
public class LoginPage {

	// 定位 ifrmae
	public static By loginFrame = By.id("x-URS-iframe");
	// 定位 邮箱地址输入框
	public static By emailField = By.name("email");
	// 定位 密码输入框
	public static By pwdFiled = By.name("password");
	// 定位 登录按钮
	public static By loginBtn = By.id("dologin");

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 往邮箱文本框输入邮箱
	 * 
	 * @param email 邮箱地址
	 */
	public void sendKeysEmail(String email) {
		driver.findElement(emailField).clear();
		driver.findElement(emailField).sendKeys(email);
	}

	/**
	 * 往密码文本框输入密码
	 * 
	 * @param pwd 密码
	 */
	public void sendKeysPWD(String pwd) {
		driver.findElement(pwdFiled).clear();
		driver.findElement(pwdFiled).sendKeys(pwd);
	}

}