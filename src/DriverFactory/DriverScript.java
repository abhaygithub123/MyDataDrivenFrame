package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Constant.AppUtil;
import Utilities.ExcelFileUtil;

public class DriverScript extends AppUtil {
ExtentReports report;
ExtentTest test;
String inputpath = "E:\\mrng9oclockBatch\\DDT_Framework\\TestInput\\LoginData.xlsx";
String outputpath = "E:\\mrng9oclockBatch\\DDT_Framework\\TestOutput\\DDTResults.xlsx";

@Test
public void validateLogin()throws Throwable
{
   //define path for generating html report
	report = new ExtentReports("./ExtentReports/DDT.html");
   // create reference object to call xl methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
   // count no rows in login sheet
	int rc = xl.rowCount("Login");
	
	// count no cells in login sheet
	int cc = xl.cellCount("Login");
	Reporter.log("No of rows are::"+rc+"  "+"No of cells::"+cc,true);
	for(int i=1;i<=rc;i++)
	{
		
		test = report.startTest("Login Test");
		driver.get(config.getProperty("Url"));
		driver.manage().window().maximize();
	  // read username and password cell data
		String username = xl.getCellData("Login",i,0);
		String password = xl.getCellData("Login",i,1);
	//  call login method
		FunctionLibrary.verifyLogin(username, password);
		String expected = "dashboard";
		String actual = driver.getCurrentUrl();
		if(actual.contains(expected))
		{
		  // write as login success in result cell
			xl.setCellData("Login",i,2,"Login success", outputpath);
		 // write as pass in status cell
			xl.setCellData("login",i,3,"Pass",outputpath);
			test.log(LogStatus.PASS,"Login Success::"+expected+"    "+"    "+actual);
			Reporter.log("login success::"+expected+"    "+actual,true);
		}
		else
		{
			// take screen shot
			File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen,new File("./Screenshot/iteration/"+i+"Loginpage.png"));
			// capture error message
			String errormessage = driver.findElement(By.cssSelector(config.getProperty("Objerrormessage"))).getText();
			xl.setCellData("Login",i,2,errormessage,outputpath);
			xl.setCellData("Login",i,3,"Fail",outputpath);
			test.log(LogStatus.FAIL,errormessage+"   "+expected+"    "+actual);
			Reporter.log("login fail::"+expected+"    "+actual,true);
			
		}
		report.endTest(test);
		report.flush();
		
		
	}
	
}


}
