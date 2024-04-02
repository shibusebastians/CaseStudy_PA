package test_package;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;  
public class TestClass {

	public String baseUrl = "https://www.saucedemo.com/";  
	String driverPath = "D://automation//chromedriver-win64//chromedriver.exe";  
	public WebDriver driver ;   
	
	 @BeforeSuite  
	 public void before_suite()  
	 {  
	     System.out.println("First method"); 
	 	// set the system property for Chrome driver      
	 	System.setProperty("webdriver.chrome.driver", driverPath);  
	 	// Create driver object for CHROME browser  
	 	driver = new ChromeDriver();  
	 	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);  
	 	driver.manage().window().maximize();  
	 	driver.get(baseUrl);  
	 	// get the current URL of the page  
	 	String URL= driver.getCurrentUrl();  
	 	System.out.print(URL); 
	 }  
	
	
	@Test   
	@Parameters({"username","password"})
	public void verifyLogin(String username, String password) throws InterruptedException, IOException {      
	driver.findElement(By.xpath("//input[@name='user-name']")).sendKeys(username);
	driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
	Thread.sleep(3000);
	driver.findElement(By.xpath("//input[@id='login-button']")).click();
	Thread.sleep(3000);
	//get the title of the page  
	String actual_title = "Swag Labs";
	String expected_title = driver.getTitle();    
	Assert.assertEquals(actual_title,expected_title);
	// System.out.println("title:"+ title);  
	takescreenshot();
	}     

	@Test(dependsOnMethods = {"verifyLogin"})
	public void addVerifyItemInCart()
	{
		String actual_itemName = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText();
		System.out.println("Item:" +actual_itemName);
		driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();		
		driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();		
		String expected_itemName = driver.findElement(By.xpath("//div[@class='inventory_item_name']")).getText();
		Assert.assertEquals(actual_itemName,expected_itemName);
	}
	
	
	void takescreenshot() throws IOException
	{
		//Use TakesScreenshot method to capture screenshot
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		//Saving the screenshot in desired location
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		//Path to the location to save screenshot
		FileUtils.copyFile(source, new File("./SeleniumScreenshots/Screen.png"));
	}
	
	@BeforeTest  
	public void beforeTest() {    
	System.out.println("before test");  
	}    
	
	@AfterTest  
	public void afterTest() {  
	driver.quit();  
	System.out.println("after test");  
	}    
}
