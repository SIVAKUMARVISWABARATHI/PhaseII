package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.training.generics.ScreenShot;
import com.training.pom.LoginPOM;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class RETC_019 {

	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private static Properties properties;
	private ScreenShot screenShot;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeMethod
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver); 
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver); 
		// open the browser 
		driver.get(baseUrl);
	}
	
	
	@Test
	public void validLoginTest() throws InterruptedException {
		
		//Scenario:To Verify whether application allows admin to delete category from the categories page
		driver.findElement(By.xpath("//a[contains(text(),' Log In / Register')]")).click();;
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		//screenShot.captureScreenShot();
		//Thread.sleep(2000);
		
		Actions action=new Actions(driver);
		WebElement posts=driver.findElement(By.xpath("//div[contains(text(),'Posts')]"));
		WebElement categories=driver.findElement(By.xpath("//a[@href='edit-tags.php?taxonomy=category']"));
		
		//1. Click on Posts link and 2. Click on Tags link
		action.moveToElement(posts).moveToElement(categories).click().build().perform();
		screenShot.captureScreenShot();
		
		// 3. Click on the checkbox of the category to be deleted
		driver.findElement(By.xpath("(//input[@name='delete_tags[]'])[1]")).click();
		screenShot.captureScreenShot();
		String name_to_be_deleted=driver.findElement(By.xpath("(//td[@class='name column-name has-row-actions column-primary'])[1]")).getText();
		
		//4. Click on Bulk Action list box and 5. Select Delete in Bulk Action links
		Select dropdown=new Select(driver.findElement(By.xpath("//select[@id='bulk-action-selector-top']")));
		dropdown.selectByVisibleText("Delete");
		screenShot.captureScreenShot();
		
		//6. Click on Apply button
		driver.findElement(By.xpath("//input[@id='doaction']")).click();
		screenShot.captureScreenShot();
		
		// Verification of delete Message 
		String actualMessage=driver.findElement(By.xpath("//div[@id='message']/p")).getText();
		Assert.assertEquals(actualMessage, "Categories deleted.");
		String name_after_deletion=driver.findElement(By.xpath("(//td[@class='name column-name has-row-actions column-primary'])[1]")).getText();
		boolean condn=name_to_be_deleted.equalsIgnoreCase(name_after_deletion);
		Assert.assertFalse(condn);
	
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		//Thread.sleep(1000);
		driver.quit();
	}
}
