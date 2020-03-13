package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class RETC_74 {

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
		//RETC_074
		
		//Scenario: To Verify whether application allows admin to add new tag
		driver.findElement(By.xpath("//a[contains(text(),' Log In / Register')]")).click();;
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		//screenShot.captureScreenShot();
		//Thread.sleep(2000);
		
		//1. Click on Users link and 2. Click on All Users link
		Actions action=new Actions(driver);
		WebElement Users=driver.findElement(By.xpath("//*[@id='menu-users']/a/div[3]"));
		WebElement AllUSers=driver.findElement(By.xpath("//*[@id='menu-users']/ul/li[2]/a"));
		action.moveToElement(Users).moveToElement(AllUSers).click().build().perform();
		screenShot.captureScreenShot();
		
		//3. Click on the checkbox beside the user	
		driver.findElement(By.xpath("//*[@id='user_566']")).click();
		
		//4.Click on Change role to list box and 5.Select valid credentials in change role to list box
		Select Changerole=new Select(driver.findElement(By.xpath("//*[@id='new_role']")));
		Changerole.selectByVisibleText("Shop manager");
		screenShot.captureScreenShot();
		
		//6. Click on Change button
		
		driver.findElement(By.xpath("//*[@id=\'changeit\']")).click();
		
		//Verification of "changed roles" Message
		String actualMessage=driver.findElement(By.xpath("//*[@id='message']/p")).getText();
		Assert.assertEquals(actualMessage, "Changed roles.");
		
					
			}
	
	@AfterMethod
	public void tearDown() throws Exception {
		//Thread.sleep(1000);
		driver.quit();
	}
}
