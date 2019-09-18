package com.aSelenium.base;

/**
 * Setup1：Base内的封装
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * PageObject mode的
 * 
 * 第一步：封装driver对象
 * 
 * 作用：根据用户输入 选择不同的浏览器
 */
public class SelectDriver {
   /*
	*	public WebDriver driverName(String browser) {
	*		// equalsIgnoreCase 与另一个 String 进行比较，不考虑大小写。
	*		// 判断浏览器的类型是"firefox"或者"chrome"又或者是"IE"
	*		if (browser.equalsIgnoreCase("fireFox")) {
	*			System.setProperty("webdriver.firefox.marionette", "D:\\java\\geckodriver\\geckodriver.exe");
	*			return new FirefoxDriver();
	*		} else {
	*			System.setProperty("webdriver.chrome.driver", "D:\\java\\chromedriver-32\\chromedriver.exe");
	*			return new ChromeDriver();
	*		}
	*	}
	*/

	private static WebDriver driver = null;
	/**
	 * 选择浏览器类型的类
	 */
	public static WebDriver selectBrowserDriver(String browser) {
		switch (browser) {
		case "ie":
			System.setProperty("webdriver.ie.driver", ".\\source\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;
		case "ff":
		case "firefox":
		case "Firefox":
		case "FireFox":
			// FireFox版本小于48
//                System.setProperty("webdriver.firefox.marionette", ".\\Tools\\geckodriver.exe");
			// FireFox版本大于48，默认安装时可以试试，应该可以
//                System.setProperty("webdriver.gecko.driver", ".\\Tools\\geckodriver.exe");
			// 自定义安装要这么写，使用上面的会报错找不到Firefox浏览器
			System.setProperty("webdriver.firefox.bin", "E:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			driver = new FirefoxDriver();
			break;
		case "chrome":
		case "Chrome":
//			System.setProperty("webdriver.chrome.driver", ".\\Tools\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver",
					"E:/Program Files/Google/Chrome/Application/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		default:
			try {
				throw new Exception("浏览器错误!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return driver;
	}
}
