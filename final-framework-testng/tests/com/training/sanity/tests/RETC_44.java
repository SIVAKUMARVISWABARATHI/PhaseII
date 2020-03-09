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

public class RETC_44 {

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
		//RETC_044
		//Scenario:To verify whether application allows admin to create property details based on the Feature created
		
		driver.findElement(By.xpath("//a[contains(text(),' Log In / Register')]")).click();;
		loginPOM.sendUserName("admin");
		loginPOM.sendPassword("admin@123");
		loginPOM.clickLoginBtn();
		//screenShot.captureScreenShot();
		//Thread.sleep(2000);
		
		//1.Click on Properties link and 2. Click on Features link
		Actions action=new Actions(driver);
		WebElement Properties=driver.findElement(By.xpath("//div[contains(text(),'Properties')]"));
		action.moveToElement(Properties).moveToElement(driver.findElement(By.xpath("//a[@href='edit-tags.php?taxonomy=property_feature&post_type=property']"))).click().build().perform();
		screenShot.captureScreenShot();
		
		//3. Enter Valid Credentials in Name textbox	
		driver.findElement(By.xpath("//input[@id='tag-name']")).sendKeys("New Launches");
				
		
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
		//4.Enter Valid Credentials in Slug textbox
		driver.findElement(By.xpath("//input[@id='tag-slug']")).sendKeys("launch");
				
		//5.Enter Valid Credentials in Description textbox
		driver.findElement(By.xpath("//textarea[@id='tag-description']")).sendKeys("New Launches of villas, apartments, flats");
		
		//6.Select the value from the Parent feature Drop down
		Select dropdown=new Select(driver.findElement(By.xpath("//select[@id='parent']")));
		dropdown.selectByIndex(1);
		//dropdown.selectByVisibleText("Delete");
					
		//7. Click on Add New Feature button
		driver.findElement(By.xpath("//input[@id='submit']")).click();
		
		//7. Click on Add new link of Properties section--Given testcase(seems to be wrong sequence)
		//action.moveToElement(Properties).moveToElement(driver.findElement(By.xpath("//a[@href='post-new.php?post_type=property']"))).click().build().perform();
		
		
		//8. Enter valid credentials in Enter Title Here textbox-Given testcase(seems to be wrong sequence)
		//driver.findElement(By.xpath("//input[@id='title']")).sendKeys("SIVAKUMAR");
		
		//9. Enter valid credentials in textbox---Given testcase(seems to be wrong sequence)
		//driver.findElement(By.xpath("//textarea[@id='content']")).sendKeys("VISWABARATHI");
		
		//8. Click on checkbox beside added Feature of Features section
		//driver.findElement(By.xpath("(//input[@name='delete_tags[]'])[1]")).click();
		//driver.findElement(By.xpath("(//td[@data-colname='Slug'] and ")).click();
		//driver.findElement(By.xpath("(//input[@id='cb-select-930' and @class='name column-name has-row-actions column-primary'])"));
		
		 WebElement  ele1=driver.findElement(By.xpath("(//input[@id='cb-select-930' and @class='name column-name has-row-actions column-primary'])"));
		 ele1.click();				
		screenShot.captureScreenShot();
						
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		//Thread.sleep(1000);
		driver.quit();
	}
}


