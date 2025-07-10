package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.AccountRegistrationPage;
import PageObjects.HomePage;
import TestBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass{
	
	@Test(groups = {"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("***** Starting TC_001_AccountRegistrationTest *****");
		
		try 
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("***** Clicking on my Account Link *****");
		
		hp.clickRegister();
		logger.info("***** Clicking on the Register Link *****");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("***** Providing Customers details *****");
		
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");// randomly generated the email
		regpage.setTelephone(randomeNumber());
		
		String password=randomAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		Thread.sleep(3000);
		
		logger.info("***** Clicking on the Continue button to complete Customer Registration... *****");
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("***** Validating expected Message... *****");
		String confmsg=regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!")) 
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed...");
			logger.debug("Debug Logs...");
			Assert.assertTrue(false);
		}
				
	    System.out.println(confmsg + " " + "Successfully" + "..." + "Thanks for being patient");
	    
	   }
		catch(Exception e)
		{
			Assert.fail();
		}
	
	logger.info("Done with the Execution of TC_001_AccountRegistrationTest...");
    }
	
	
}




