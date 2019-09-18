package com.cmail163.element;

import java.io.IOException;

import org.openqa.selenium.By;

import com.cmail163.util.ReadProperties;

public class ByElement {
	/**
	 * 封装by 静态方法 直接调用
	 * 
	 * @throws IOException
	 */
	public static By bystr(String PropertiesKey) {

		String PropertiesPath = "LoginElement.properties";
		// 创建ReadProperties对象
		ReadProperties properties = new ReadProperties(PropertiesPath);

		String value = properties.getValue(PropertiesKey);
		// 对value进行拆分
		String LocateMethon = value.split(">")[0];
		String LocateEle = value.split(">")[1];

		// System.out.println(LocateMethon+"========="+LocateEle);

		if (LocateMethon.equalsIgnoreCase("id")) {

			return By.id(LocateEle);
		} else if (LocateMethon.equalsIgnoreCase("name")) {

			return By.name(LocateEle);
		} else if (LocateMethon.equalsIgnoreCase("tagNmae")) {

			return By.tagName(LocateEle);
		} else if (LocateMethon.equalsIgnoreCase("linkText")) {

			return By.linkText(LocateEle);
		} else if (LocateMethon.equalsIgnoreCase("xpath")) {

			return By.xpath(LocateEle);
		} else if (LocateMethon.equalsIgnoreCase("cssSelector")) {

			return By.cssSelector(LocateEle);
		} else {

			return By.partialLinkText(LocateEle);
		}

	}
}
