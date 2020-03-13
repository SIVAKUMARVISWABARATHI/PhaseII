package com.training.sanity.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

import jxl.Workbook;

public class RETC_73 {

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
	public void validLoginTest() throws InterruptedException, IOException {
		// RETC_073
		// To Verify whether application allows multiple user to send the query in
		// Contact Form Page & data should get displayed in excel sheet

 
		// 1.Click on New Launch tab and click on Click on Drop Us a Line link in Got Any Question? Section
		
		  Actions action=new Actions(driver);
		  driver.findElement(By.xpath("//*[@id='menu-item-354']/a")).click();
		  driver.navigate().to("http://realty-real-estatem1.upskills.in/region/new-launch-in-bangalore/?realteo_order=&keyword_search=Sivas%2C+Sivas+Merkez%2FSivas%2C+Turkey&_property_type=apartments&tax-region=adfada");
		  driver.findElement(By.xpath("//*[@id=\"text-4\"]/div/div/p[2]/a")).click();
		  				 
		ArrayList<String> values=new ArrayList<>();
		FileInputStream fs = new FileInputStream("C:\\Users\\SIVAKUMARVISWABARATH\\Desktop\\JAVA\\Data sheet.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		Sheet sh = wb.getSheet("Testdata");
		for (int row = 1; row < 2; row++) {
			for (int column = 0; column < 4; column++) {
				Row Rows = sh.getRow(row);
				Cell cell = Rows.getCell(column);
				String cellvalue = cell.getStringCellValue();
				System.out.println(cellvalue);
				values.add(cellvalue);
			}
		}
		System.out.println("Inside array list ");
		
		for(String value:values) {
			
			System.out.println(value);
		}
		
		  //3.Enter valid credentials in Your Name textbox 
		  driver.findElement(By.xpath( "//*[@id=\"wpcf7-f119-p134-o1\"]/form/div[2]/div[1]/div/span/input")).sendKeys(values.get(0));
		  
		  //4.Enter valid credentials in Your Email textbox
		 driver.findElement(By.xpath("//*[@id=\"wpcf7-f119-p134-o1\"]/form/div[2]/div[2]/div/span/input")).sendKeys(values.get(1));
		 
		  //5. Enter valid credentials in Subject textbox
		 driver.findElement(By.xpath("//*[@id=\"wpcf7-f119-p134-o1\"]/form/div[2]/div[3]/div/span/input")).sendKeys(values.get(2));
		 
		 //6.Enter valid credentials in Your Message textbox
		 driver.findElement(By.xpath("//*[@id=\"wpcf7-f119-p134-o1\"]/form/div[3]/span/textarea")).sendKeys(values.get(3));
		 //7.Click on Send button
		 driver.findElement(By.xpath("//*[@id=\"wpcf7-f119-p134-o1\"]/form/p/input")).click();
		
		Thread.sleep(5000);
		 }

	@AfterMethod
	public void tearDown() throws Exception {
		// Thread.sleep(1000);
		driver.quit();
	}
}
