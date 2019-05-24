package com.mail.base;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 基于selenium进行二次开发的Web自动化测试常用方法封装的类, 包括浏览器操作, 元素操作, cookie操作,
 * 特殊情况弹框的处理以及优化测试的等待处理等方法
 * 
 * @author Andrew
 */
public class WebAutoTest {
	private static WebDriver driver = null;
	@SuppressWarnings("unused")
	private static WebDriverWait wait = null;
	private static long timeOutInSeconds = 10;
	private static Select select = null;
	private static Alert alert = null;

	public WebAutoTest(long timeOutInSeconds) {
		WebAutoTest.timeOutInSeconds = timeOutInSeconds;
	}

	public WebAutoTest() {
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		WebAutoTest.driver = driver;
	}

	/**
	 * 指定浏览器打开URL + 隱式等待
	 * 
	 * @param url
	 * @param browser
	 */
	public static void openBrowser(String url, String browser) {
//		setDriver(initBrowser(browser));
		setDriver(SelectBrowser.selectBrowser(browser));
		getDriver().manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);
		// driver.get(url);
		getDriver().navigate().to(url);
	}

	/**
	 * 关闭当前浏览器
	 */
	public static void closeCurrentBrowser() {
		getDriver().close();
	}

	/**
	 * 关闭所有selenium驱动打开的浏览器
	 */
	public static void closeAllBrowser() {
		getDriver().quit();
	}

	/**
	 * 最大化浏览器
	 */
	public static void maxBrowser() {
		getDriver().manage().window().maximize();
	}

	/**
	 * 自定义设置浏览器尺寸
	 * 
	 * @param width
	 * @param heigth
	 */
	public static void setBrowserSize(int width, int heigth) {
		getDriver().manage().window().setSize(new Dimension(width, heigth));
	}

	/**
	 * 获取当前浏览器页面的URL的字符串
	 * 
	 * @return 85
	 */
	public static String getURL() {
		return getDriver().getCurrentUrl();
	}

	/**
	 * 获取当前浏览器页面的标题
	 * 
	 * @return 93
	 */
	public static String getTitle() {
		return getDriver().getTitle();
	}

	/**
	 * 在浏览器的历史中向后到上一个页面, 即点击浏览器返回
	 */
	public static void returnToPreviousPage() {
		getDriver().navigate().back();
	}

	/**
	 * 在浏览器的历史中向前到下一个页面, 如果我们在最新的页面上看, 什么也不做, 即点击浏览器下一页
	 */
	public static void forwardToNextPage() {
		getDriver().navigate().forward();
	}

	/**
	 * 刷新页面
	 */
	public static void refreshPage() {
		getDriver().navigate().refresh();
	}

	/**
	 * WebDriver切换到当前页面
	 */
	public static void switchToCurrentPage() {
		String handle = getDriver().getWindowHandle();
		for (String tempHandle : getDriver().getWindowHandles()) {
			if (tempHandle.equals(handle)) {
				driver.close();
			} else {
				getDriver().switchTo().window(tempHandle);
			}
		}
	}

	/**
	 * 输入文本字符串
	 * 
	 * @param locator
	 * @param text
	 */
	public static void inputText(By locator, String text) {
		getDriver().findElement(locator).sendKeys(text);
	}

	/**
	 * 点击元素
	 * 
	 * @param locator 定位器
	 */
	public static void clickElement(By locator) {
		getDriver().findElement(locator).click();
	}

	/**
	 * 获取该元素的可见（没有被CSS隐藏）内文, 包括子元素, 没有任何前导或尾随空白
	 * 
	 * @param locator
	 * @return 此元素的内部文本
	 */
	public static String getElementText(By locator) {
		return getDriver().findElement(locator).getText();
	}

	/**
	 * 下拉选择框根据选项文本值选择, 即当text="Bar", 那么这一项将会被选择: &lt;option
	 * value="foo"&gt;Bar&lt;/option&gt;
	 * 
	 * @param locator
	 * @param text
	 * @see org.openqa.selenium.support.ui.Select.selectByVisibleText(String text)
	 */
	public static void selectByText(By locator, String text) {
		select = new Select(getDriver().findElement(locator));
		select.selectByVisibleText(text);
	}

	/**
	 * 下拉选择框根据索引值选择, 这是通过检查一个元素的“index”属性来完成的, 而不仅仅是通过计数
	 * 
	 * @param locator
	 * @param index
	 * @see org.openqa.selenium.support.ui.Select.selectByIndex(int index)
	 */
	public static void selectByIndex(By locator, int index) {
		select = new Select(getDriver().findElement(locator));
		select.selectByIndex(index);
	}

	/**
	 * 下列选择框根据元素属性值(value)选择, 即value = "foo" , 那么这一项将会被选择: &lt;option
	 * value="foo"&gt;Bar&lt;/option&gt;
	 * 
	 * @param locator
	 * @param value
	 * @see org.openqa.selenium.support.ui.Select.selectByValue(String value)
	 */
	public static void selectByValue(By locator, String value) {
		select = new Select(getDriver().findElement(locator));
		select.selectByValue(value);
	}

	/**
	 * 下拉选择框全选操作
	 * 
	 */
	public static void selectAll(By locator) {
		select = new Select(getDriver().findElement(locator));
		select.getOptions();
	}

	/**
	 * 清空文本输入框
	 * 
	 * @param locator
	 * @see org.openqa.selenium.WebElement.clear()
	 */
	public static void clearText(By locator) {
		getDriver().findElement(locator).clear();
	}

	/**
	 * 提交表单
	 * 
	 * @param locator
	 * @see org.openqa.selenium.WebElement.submit()
	 */
	public static void submitForm(By locator) {
		getDriver().findElement(locator).submit();
	}

	/**
	 * 上传文件
	 * 
	 * @param locator
	 * @param filePath
	 */
	public static void uploadFile(By locator, String filePath) {
		// 元素属性是否为“input”
		getDriver().findElement(locator).sendKeys(filePath);
	}

	/**
	 * 上传文件(单个文件)，需要点击弹出上传照片的窗口才行
	 * 
	 * @@通过 AutoIT 实现
	 * 
	 * @parambrower 使用的浏览器名称
	 * @paramfile 需要上传的文件及文件名
	 */
//	public static void handleUpload(String browser, File file) {
//		String filePath = file.getAbsolutePath();
	public static void handleUpload(String browser, String filePath, String executeFile) {
//		String executeFile = "E:\\Javaworkspace\\mail163Send\\source\\uplod.exe"; // 定义了upload.exe文件的路径
		String cmd = "\"" + executeFile + "\"" + " " + "\"" + browser + "\"" + " " + "\"" + filePath + "\"";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件(多个文件)，需要点击弹出上传照片的窗口才行
	 * 
	 * @@通过 AutoIT 实现
	 * 
	 * @parambrower 使用的浏览器名称
	 * @paramfile 需要上传的文件及文件名
	 */
	public static void handleUploads(By locator, String browser, String[] filePaths, String executeFile) {
		for (String filePath : filePaths) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WebAutoTest.clickElement(locator);// 点击上传按钮
			String cmd = "\"" + executeFile + "\"" + " " + "\"" + browser + "\"" + " " + "\"" + filePath + "\"";
			try {
				/*
				 * Runtime.getRuntime()返回当前应用程序的Runtime对象，该对象的exec()
				 * 方法指示Java虚拟机创建一个子进程执行指定的可执行程序, 并返回与该子进程对应的Process对象实例
				 */
				Process p = Runtime.getRuntime().exec(cmd);
				/*
				 * 方法将导致当前的线程等待，如果必要的话，直到由该Process对象表示的进程已经终止。 此方法将立即返回，如果子进程已经终止。 如果子进程尚未终止，
				 * 则调用线程将被阻塞，直到子进程退出。
				 */
				p.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 切换frame
	 * 
	 * @param locator
	 * @return 这个驱动程序切换到给定的frame
	 * @see org.openqa.selenium.WebDriver.TargetLocator.frame(WebElement
	 *      frameElement)
	 */
	public static void switchToFrame(By locator) {
		getDriver().switchTo().frame(getDriver().findElement(locator));
	}

	/**
	 * 切换回父frame
	 * 
	 * @return 这个驱动程序聚焦在顶部窗口/第一个frame上
	 * @see org.openqa.selenium.WebDriver.TargetLocator.defaultContent()
	 */
	public static void switchToParentFrame() {
		getDriver().switchTo().defaultContent();
	}

	/**
	 * 关闭或取消弹出对话框
	 */
	public static void dismissAlert() {
		alert = getDriver().switchTo().alert();
		alert.dismiss();
	}

	/**
	 * 弹出对话框点击确定
	 */
	public static void acceptAlert() {
		alert = getDriver().switchTo().alert();
		alert.accept();
	}

	/**
	 * 获取弹出对话框内容
	 * 
	 * @return259
	 */
	public static String getAlertText() {
		alert = getDriver().switchTo().alert();
		return alert.getText();
	}

	/**
	 * 弹出对话框输入文本字符串
	 * 
	 * @param text
	 */
	public static void inputTextToAlert(String text) {
		alert = getDriver().switchTo().alert();
		alert.sendKeys(text);
	}

	/**
	 * 根据cookie名称删除cookie
	 * 
	 * @param name cookie的name值
	 * @see org.openqa.selenium.WebDriver.Options.deleteCookieNamed(String name)
	 */
	public static void deleteCookie(String name) {
		getDriver().manage().deleteCookieNamed(name);
	}

	/**
	 * 删除当前域的所有Cookie
	 * 
	 * @see org.openqa.selenium.WebDriver.Options.deleteAllCookies()
	 */
	public static void deleteAllCookies() {
		getDriver().manage().deleteAllCookies();
	}

	/**
	 * 根据名称获取指定cookie
	 * 
	 * @param name cookie名称
	 * @return Map&lt;String, String>, 如果没有cookie则返回空, 返回的Map的key值如下:
	 *         <ul>
	 *         <li><tt>name</tt> <tt>cookie名称</tt>
	 *         <li><tt>value</tt> <tt>cookie值</tt>
	 *         <li><tt>path</tt> <tt>cookie路径</tt>
	 *         <li><tt>domain</tt> <tt>cookie域</tt>
	 *         <li><tt>expiry</tt> <tt>cookie有效期</tt>
	 *         </ul>
	 * @see org.openqa.selenium.WebDriver.Options.getCookieNamed(String name)
	 */
	public static Map<String, String> getCookieByName(String name) {
		Cookie cookie = getDriver().manage().getCookieNamed(name);
		if (cookie != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", cookie.getName());
			map.put("value", cookie.getValue());
			map.put("path", cookie.getPath());
			map.put("domain", cookie.getDomain());
			map.put("expiry", cookie.getExpiry().toString());
			return map;
		}
		return null;
	}

	/**
	 * 获取当前域所有的cookies
	 * 
	 * @return Set&lt;Cookie> 当前的cookies集合
	 * @see org.openqa.selenium.WebDriver.Options.getCookies()
	 */
	public static Set<Cookie> getAllCookies() {
		return getDriver().manage().getCookies();
	}

	/**
	 * 用给定的name和value创建默认路径的Cookie并添加, 永久有效
	 * 
	 * @param name
	 * @param value
	 * @see org.openqa.selenium.WebDriver.Options.addCookie(Cookie cookie)
	 * @see org.openqa.selenium.Cookie.Cookie(String name, String value)
	 */
	public static void addCookie(String name, String value) {
		getDriver().manage().addCookie(new Cookie(name, value));
	}

	/**
	 * 用给定的name和value创建指定路径的Cookie并添加, 永久有效
	 * 
	 * @param name  cookie名称
	 * @param value cookie值
	 * @param path  cookie路径
	 */
	public static void addCookie(String name, String value, String path) {
		getDriver().manage().addCookie(new Cookie(name, value, path));
	}

	/**
	 * “显式等待 ” 指定时间内等待直到页面包含文本字符串
	 * 
	 * @param text    期望出现的文本
	 * @param seconds 超时时间
	 * @return Boolean 检查给定文本是否存在于指定元素中, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement(WebElement
	 *      element, String text)
	 */
	public static Boolean waitUntilPageContainText(String text, long seconds) {
		try {
			return new WebDriverWait(getDriver(), seconds).until(
					ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.tagName("body")), text));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * “显式等待 ” 默认时间等待直到页面包含文本字符串
	 * 
	 * @param text 期望出现的文本
	 * @return Boolean 检查给定文本是否存在于指定元素中, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement(WebElement
	 *      element, String text)
	 */
	public static Boolean waitUntilPageContainText(String text) {
		try {
			return new WebDriverWait(getDriver(), timeOutInSeconds).until(
					ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.tagName("body")), text));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * “显式等待 ” 指定时间内等待直到元素存在于页面的DOM上并可见, 可见性意味着该元素不仅被显示, 而且具有大于0的高度和宽度
	 * 
	 * @param locator 元素定位器
	 * @param seconds 超时时间
	 * @return Boolean 检查给定元素的定位器是否出现, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By
	 *      locator)
	 */
	public static Boolean waitUntilElementVisible(By locator, int seconds) {
		try {
			new WebDriverWait(getDriver(), seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * “显式等待 ” 默认时间内等待直到元素存在于页面的DOM上并可见, 可见性意味着该元素不仅被显示, 而且具有大于0的高度和宽度
	 * 
	 * @param locator 元素定位器
	 * @return Boolean 检查给定元素的定位器是否出现, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By
	 *      locator)
	 */
	public static Boolean waitUntilElementVisible(By locator) {
		try {
			new WebDriverWait(getDriver(), timeOutInSeconds)
					.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 悬停 到更多按钮实现
	public static void roverElement(By locator) {
		WebElement link = getDriver().findElement(locator);
		Actions actions = new Actions(getDriver());
		actions.moveToElement(link).perform();
	}

	// 查找元素
	public static WebElement findElement(By locator) {
		return getDriver().findElement(locator);
	}

	// 判断当前页面中是否存在某个期望查找的元素
//	public static boolean isElementExsit(WebDriver driver, By locator) {
	public static boolean isElementExsit(By locator) {
		boolean flag = false;
		try {
//			WebElement element = driver.findElement(locator);
			WebElement element = getDriver().findElement(locator);
			flag = null != element;
		} catch (NoSuchElementException e) {
			System.out.println("Element:" + locator.toString() + " is not exsit!");
		}
		return flag;
	}

	/**
	 * 将滚动条滚到适合的位置(垂直滚动)
	 * 
	 * @param height
	 */

	public static void setScroll(int width, int height) {
		try {
//			String setscroll = "document.documentElement.scrollTop=" + height;
			String setscroll = "window.scrollBy(" + width + "," + height + ")";
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript(setscroll);
		} catch (Exception e) {
			System.out.println("Fail to set the scroll.");
		}
	}

	/**
	 * 初始化浏览器
	 * 
	 * @param browser 参数值为ie, ff, chrome
	 * @return WebDriver
	 */
/*	private static WebDriver initBrowser(String browser) {
		switch (browser) {
		case "ie":
			System.setProperty("webdriver.ie.driver", ".\\source\\IEDriverServer.exe");
			setDriver(new InternetExplorerDriver());
			break;
		case "ff":
		case "firefox":
		case "Firefox":
		case "FireFox":
			// FireFox版本小于48
//		                System.setProperty("webdriver.firefox.marionette", ".\\Tools\\geckodriver.exe");
			// FireFox版本大于48，默认安装时可以试试，应该可以
//		                System.setProperty("webdriver.gecko.driver", ".\\Tools\\geckodriver.exe");
			// 自定义安装要这么写，使用上面的会报错找不到Firefox浏览器
			System.setProperty("webdriver.firefox.bin", "E:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			setDriver(new FirefoxDriver());
			break;
		case "chrome":
		case "Chrome":
			System.setProperty("webdriver.chrome.driver", ".\\Tools\\chromedriver.exe");
			setDriver(new ChromeDriver());
			break;
		default:
			try {
				throw new Exception("浏览器错误!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getDriver();
	}
*/
}
