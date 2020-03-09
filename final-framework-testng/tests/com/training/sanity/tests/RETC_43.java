package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class RETC_43 {

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
		//RETC_043
		//Scenario:TO verify whether application allows admin to view added property details in Home screen
		
		driver.findElement(By.xpath("//a[contains(text(),' Log In / Register')]")).click();;
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		//screenShot.captureScreenShot();
		//Thread.sleep(2000);
		
		//1. Click on Properties tab and 2. 2. Click on Add New link
		Actions action=new Actions(driver);
		WebElement Properties=driver.findElement(By.xpath("//div[contains(text(),'Properties')]"));
	//	WebElement ADDNEW=driver.findElement(By.xpath("//a[@href='post-new.php?post_type=property']"));
		action.moveToElement(Properties).moveToElement(driver.findElement(By.xpath("//a[@href='post-new.php?post_type=property']"))).click().build().perform();
		screenShot.captureScreenShot();
		
		//3. Enter valid credentials in Enter Title Here textbox		
		driver.findElement(By.xpath("//input[@id='title']")).sendKeys("SIVAKUMAR");
				
		
		//Reading data from external File(Data Driven method)
	/*	String Testdatasheet;
		FileInputStream fis = new FileInputStream("C:\\Users\\SIVAKUMARVISWABARATH\\Documents\\Testdata\\Input Data.xlsx");			
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowCount=7, colCount=2;
		for(int i=0;i<=rowCount;i++)
		{ for(int i=0;i<=colCount;i++){ } 
		}
	*/			
		//4. Enter valid credentials in textbox
		//driver.findElement(By.xpath("//a[contains(text(),' Log In / Register')]")).click();;
		driver.findElement(By.xpath("//textarea[@id='content']")).sendKeys("VISWABARATHI");
				
		//5. Click on Publish button
		/*driver.findElement(By.xpath("//input[@id='publish']")).click();
		driver.findElement(By.xpath("//input[@id='publish']")).click();*/
		
		WebElement element = driver.findElement(By.xpath("//input[@id='publish' and @class='button button-primary button-large']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		screenShot.captureScreenShot();
		
		//6. Click on ViewPost link
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='View post']")).click();
		screenShot.captureScreenShot();
						
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		//Thread.sleep(1000);
		driver.quit();
	}
}


