package TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;   //log4j


public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties P;
	
	@BeforeClass(groups = {"Regression","Master","Sanity"})
	@Parameters({"OS", "Browser"})
	public void setup(String OS, String Br) throws IOException
	{
		//loading properties file
		 FileReader file=new FileReader(".//src//test//resources//Config.properties");
		 P=new Properties();
		 P.load(file);
		 
		logger = LogManager.getLogger(this.getClass());
		
		
		if(P.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//Operating System
			if(OS.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
		    }
		    else if (OS.equalsIgnoreCase("linux"))
		    {
		    	capabilities.setPlatform(Platform.LINUX);
			}
			else if (OS.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching OS");
				return;
			}
			
			//browser
			switch(Br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
				
		if(P.getProperty("execution_env").equalsIgnoreCase("local"))
		{

		//launching browser based on condition
				switch(Br.toUpperCase())
				{
				case "CHROME": driver=new ChromeDriver(); break;
				case "EDGE": driver=new EdgeDriver(); break;
				case "FIREFOX": driver=new FirefoxDriver(); break;
				
				default: System.out.println("Invalid matching browser!!!..");
							return;
				}
		}
				
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(P.getProperty("appURL2"));
		driver.manage().window().maximize();
	}
	   
		@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.close();
	}
	

	public String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomeNumber()
	{
		String generatedString=RandomStringUtils.randomNumeric(9);
		return generatedString;
	}
	
	public String randomAlphaNumeric()
	{
		String str=RandomStringUtils.randomAlphabetic(3);
		String num=RandomStringUtils.randomNumeric(3);
		
		return (str+"@"+num);
	}
		
		public String captureScreen(String tname) throws IOException {

			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
			String destination = System.getProperty("user.dir") + "\\Screenshots\\" + tname + "_" + timeStamp + ".png";

			try {
				FileUtils.copyFile(source, new File(destination));
			} catch (Exception e) {
				e.getMessage();
			}
			return destination;

		}
		
	}