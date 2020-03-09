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

public class RETC_020 {

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
		//RETC_020
		
		//Scenario: To Verify whether application allows admin to add new tag
		driver.findElement(By.xpath("//a[contains(text(),' Log In / Register')]")).click();;
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		//screenShot.captureScreenShot();
		//Thread.sleep(2000);
		
		//1. Click on Posts link and 2. Click on Tags link
		Actions action=new Actions(driver);
		WebElement posts=driver.findElement(By.xpath("//div[contains(text(),'Posts')]"));
		WebElement tags=driver.findElement(By.xpath("//a[@href='edit-tags.php?taxonomy=post_tag']"));
		action.moveToElement(posts).moveToElement(tags).click().build().perform();
		screenShot.captureScreenShot();
		
		//3. Enter Valid Credentials in Name textbox		
		driver.findElement(By.xpath("//input[@id='tag-name']")).sendKeys("SIVAKUMAR");
				
		
		//Reading data from external File(Data Driven method)
		//String Testdatasheet;
		//FileInputStream fis = new FileInputStream(Testdatasheet, “C:\\Users\\SIVAKUMARVISWABARATH\\Documents\\Testdataxls.”);			
		//XSSFWorkbook wb = new XSSFWorkbook(fis);
		//XSSFSheet sheet = wb.getSheetAt(0);
		//for(int i=0;i<=rowCount;i++)
		//{ for(int i=0;i<=colCount;i++){ } 
		//}
				
		//4. Enter Valid Credentials in Slug textbox
		//driver.findElement(By.xpath("//a[contains(text(),' Log In / Register')]")).click();;
		driver.findElement(By.xpath("//input[@id='tag-slug']")).sendKeys("VISWABARATHI");
		
		//5. Enter Valid Credentials in Description textbox
		driver.findElement(By.xpath("//textarea[@id='tag-description']")).sendKeys("Technology begins Here");
		screenShot.captureScreenShot();
		//6. Click on Add New Tag button
		driver.findElement(By.xpath("//input[@id='submit']")).click();
		screenShot.captureScreenShot();
						
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		//Thread.sleep(1000);
		driver.quit();
	}
}
