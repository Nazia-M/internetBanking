package com.bank.testCases;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import com.bank.utilities.ReadConfig;
public class BaseClass 
{
	ReadConfig readconfig = new ReadConfig();
	public String baseURL=readconfig.getApplicationURL();
	public String username=readconfig.getUsername();
	public String password=readconfig.getPassword();
	
  	public static WebDriver driver;
  	
  	public static Logger logger;

  	@Parameters({"browser"})
  	@BeforeClass
  	public void setUp(String browser) throws InterruptedException
  	{	
  		logger=Logger.getLogger("ebanking");
  		PropertyConfigurator.configure("Log4j.properties");
  		
  		logger.info("Inside Before Class -> Launch browser");
  		if(browser.equals("chrome"))
  		{
  			System.setProperty("webdriver.chrome.driver",readconfig.getChromePath());  		
  	  		driver=new ChromeDriver(); 		
  	  		logger.info("Opened Chrome Instance");
  		}
  		if(browser.equals("firefox"))
  		{
  			System.setProperty("webdriver.gecko.driver",readconfig.getFirefoxPath());  		
  	  		driver=new FirefoxDriver(); 			
  		}
  		if(browser.equals("ie"))
  		{
  			System.setProperty("webdriver.ie.driver",readconfig.geIEPath());  		
  	  		driver=new InternetExplorerDriver(); 			
  		}
  		
  		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get(baseURL);
		logger.info("Opened Url "+baseURL);
		Thread.sleep(3000);
  			
  		driver.manage().window().maximize();
  		logger.info("Maximize Brwoser");
		
  	}

  	@AfterClass
  	public void tearDown()
  	{
  		logger.info("Inside After Class -> Quit browser");
  		driver.quit();
  	}
}
