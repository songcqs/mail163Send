package com.other.test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*
 * 验证文件下载是否成功非常重要。大多数情况下，我们只关注单击“下载”按钮。但同时，确认文件下载成功且没有任何错误或其他文件正在下载也是非常重要的。
 * 在大多数情况下，我们在单击下载按钮/链接后知道要下载哪个文件。现在，当我们知道文件名时，我们可以在指定的下载文件夹位置使用Java来验证“文件存在”。
 * 即使在某些情况下，文件名也不唯一。文件名可以动态生成。在这种情况下，我们还可以检查文件扩展名是否存在。
 * 我们将看到所有上述情况，并验证正在下载的文件。我们将使用Java文件IO和CalthService API来查看示例
 */
public class FileDownloadVerify {

	private WebDriver driver;

	// 可以在Firefox浏览器地址栏中输入about:config来查看属性
	// 设置下载文件放置路径，注意如果是windows环境一定要用\\,用/不行
	private static String downloadPath = "D:\\seleniumdownloads";
	private String URL = "http://spreadsheetpage.com/index.php/file/C35/P10/";

	@BeforeClass
	public void testSetup() throws Exception {
		driver = new FirefoxDriver(firefoxProfile());
		driver.manage().window().maximize();
	}

	@Test
	public void example_VerifyDownloadWithFileName() {
		driver.get(URL);
		driver.findElement(By.linkText("mailmerge.xls")).click();
		Assert.assertTrue(isFileDownloaded(downloadPath, "mailmerge.xls"), "Failed to download Expected document");
	}

	@Test
	public void example_VerifyDownloadWithFileExtension() {
		driver.get(URL);
		driver.findElement(By.linkText("mailmerge.xls")).click();
		Assert.assertTrue(isFileDownloaded_Ext(downloadPath, ".xls"),
				"Failed to download document which has extension .xls");
	}

	@Test
	public void example_VerifyExpectedFileName() {
		driver.get(URL);
		driver.findElement(By.linkText("mailmerge.xls")).click();
		File getLatestFile = getLatestFilefromDir(downloadPath);
		String fileName = getLatestFile.getName();
		Assert.assertTrue(fileName.equals("mailmerge.xls"),
				"Downloaded file name is not matching with expected file name");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	/*
	 * 在这里，我们定义了Firefox浏览器的首选项，并将其传递给WebDriver。我们可以根据需要设置不同的首选项。在本教程中，有许多其他的首选项，
	 * 在使用firefox首选项下载文件时使用。
	 */
	public static FirefoxProfile firefoxProfile() throws Exception {
		// 配置响应下载参数
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("browser.download.folderList", 2);// 2为保存在指定路径，0代表默认路径
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);// 是否显示开始
		firefoxProfile.setPreference("browser.download.dir", downloadPath);// 下载路径
		// 禁止弹出保存框，value是文件格式，如zip文件
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");

		return firefoxProfile;
	}

	/*
	 * 下面的方法获取下载目录和文件名，它将检查目录中提到的文件名，如果文档在文件夹中可用，则返回“true”，否则返回“false”。当我们确定文件名时，
	 * 我们可以使用这个方法进行验证。
	 */
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				return flag = true;
		}

		return flag;
	}

	/*
	 * 下面的方法采用两个参数，首先采用文件夹路径和文件扩展名/mime类型。如果指定文件夹中有具有特定扩展名的文件，则返回true。
	 */
	/* Check the file from a specific directory with extension 检查具有扩展名的特定目录中的文件 */
	private boolean isFileDownloaded_Ext(String dirPath, String ext) {
		boolean flag = false;
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			flag = false;
		}

		for (int i = 1; i < files.length; i++) {
			if (files[i].getName().contains(ext)) {
				flag = true;
			}
		}
		return flag;
	}

	/*
	 * 下面的方法用于根据在指定文件夹中执行的最后一个操作获取文档名。这是通过使用JavaLASTimeFiDeD来完成的，它返回一个表示文件上次修改时间的长值。
	 */
	/* Get the latest file from a specific directory 从特定目录获取最新文件 */
	private File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	/*
	 * 当我们点击下载时，根据文件大小和网络，我们需要等待特定的下载操作完成。否则，由于文件未下载，我们可能会遇到问题。
	 * 我们还可以使用“Java监视服务API”来监视目录中的更改。注意：这与Java 7 版本兼容。 下面是使用Watch Service
	 * API的示例程序。在这里，我们将只使用“entry \ u create”事件。
	 */
	public static String getDownloadedDocumentName(String downloadDir, String fileExtension)
    {   
        String downloadedFileName = null;
        boolean valid = true;
        boolean found = false;
   
        //default timeout in seconds
        long timeOut = 20;
        try
        {                   
            Path downloadFolderPath = Paths.get(downloadDir);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            downloadFolderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            long startTime = System.currentTimeMillis();
            do
            {
                WatchKey watchKey;
                watchKey = watchService.poll(timeOut,TimeUnit.SECONDS);
                long currentTime = (System.currentTimeMillis()-startTime)/1000;
                if(currentTime>timeOut)
                {
                    System.out.println("Download operation timed out.. Expected file was not downloaded");
                    return downloadedFileName;
                }
               
				for (WatchEvent event : watchKey.pollEvents())
                {
                     Kind kind = event.kind();
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE))
                    {
                        String fileName = event.context().toString();
                        System.out.println("New File Created:" + fileName);
                        if(fileName.endsWith(fileExtension))
                        {
                            downloadedFileName = fileName;
                            System.out.println("Downloaded file found with extension " + fileExtension + ". File name is " +
 
fileName);
                            Thread.sleep(500);
                            found = true;
                            break;
                        }
                    }
                }
                if(found)
                {
                    return downloadedFileName;
                }
                else
                {
                    currentTime = (System.currentTimeMillis()-startTime)/1000;
                    if(currentTime>timeOut)
                    {
                        System.out.println("Failed to download expected file");
                        return downloadedFileName;
                    }
                    valid = watchKey.reset();
                }
            } while (valid);
        }
       
        catch (InterruptedException e)
        {
            System.out.println("Interrupted error - " + e.getMessage());
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            System.out.println("Download operation timed out.. Expected file was not downloaded");
        }
        catch (Exception e)
        {
            System.out.println("Error occured - " + e.getMessage());
            e.printStackTrace();
        }
        return downloadedFileName;
    }
}