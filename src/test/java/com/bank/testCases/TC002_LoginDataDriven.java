package com.bank.testCases;
import java.io.IOException;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.bank.pageObjects.LoginPage;
import com.bank.utilities.XLUtils;
public class TC002_LoginDataDriven extends BaseClass
{
	@Test(dataProvider="LoginData")
	public void loginDDT(String username,String password) throws InterruptedException
	{
		LoginPage lp=new LoginPage(driver);

		lp.setUserName(username);
		logger.info("Entered Username");
		
		lp.setPassword(password);
		logger.info("Entered Password");
		
		lp.clickSubmit();
		logger.info("Click Submit");
		
		Thread.sleep(3000);
		
		if(isAlertPresent()==true)
		{
			driver.switchTo().alert().accept();//close alert
			driver.switchTo().defaultContent();
			Assert.assertTrue(false);
			logger.warn("Login failed");
		}
		else
		{
			Assert.assertTrue(true);
			logger.info("Login passed");
			lp.clickLogout();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();//close logout alert
			driver.switchTo().defaultContent();		
		}	
	}
	
	
	public boolean isAlertPresent() //user defined method created to check alert is presetn or not
	{
		try
		{
		driver.switchTo().alert();
		return true;
		}
		catch(NoAlertPresentException e)
		{
			return false;
		}	
	}	
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException
	{
		logger.info("Inside Data Provider");
		String path=System.getProperty("user.dir")+"/src/test/java/com/bank/testData/LoginData.xlsx";
		
		int rownum=XLUtils.getRowCount(path, "Sheet1");
		int colcount=XLUtils.getCellCount(path,"Sheet1",1);
		
		String logindata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				logindata[i-1][j]=XLUtils.getCellData(path,"Sheet1", i,j);//1 0
				logger.info("Login Data Row -"+(i-1)+" Column -"+j+" Value -"+logindata[i-1][j]);
			}
				
		}
		return logindata;
	}
}
