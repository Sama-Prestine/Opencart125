package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.MyAccountPage;
import TestBase.BaseClass;

public class TC_002_LoginTest extends BaseClass
{

	@Test(groups = {"Sanity", "Master"})
	public void verify_login()
	{
		logger.info("**** Starting TC_002_LoginTest  ****");
		logger.debug("capturing application debug logs....");
		try
		{
		//Home page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("clicked on myaccount link on the home page..");
		hp.clickLogin(); //Login link under MyAccount
		logger.info("clicked on login link under myaccount..");
		
		//Login page
		LoginPage lp=new LoginPage(driver);
		logger.info("Entering valid email and password..");
		lp.setEmail(P.getProperty("email"));
		lp.setPassword(P.getProperty("password2"));
		lp.clickLogin(); //Login button
		logger.info("clicked on ligin button..");
		
		//My Account Page
		MyAccountPage macc= new MyAccountPage(driver);
				
		boolean targetPage=macc.isMyAccountPageExists();
		
		Assert.assertEquals(targetPage, true,"Login failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("**** Finished TC_002_LoginTest  ****");
	}
}
