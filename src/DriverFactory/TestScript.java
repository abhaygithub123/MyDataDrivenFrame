package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import CommonFunctions.AddEmpPage;
import CommonFunctions.AddUserPage;
import CommonFunctions.LogOutPage;
import CommonFunctions.LoginPage;

public class TestScript {
WebDriver driver;
@BeforeMethod
public void AdminLogin()throws Throwable
{
	System.setProperty("webdriver.chrome.driver","./CommonDrivers/chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("http://orangehrm.qedgetech.com/");
	LoginPage login = PageFactory.initElements(driver, LoginPage.class);
	login.verifyLogin("Admin", "Qedge123!@#");
}
@Test(priority=1)
public void validateuser()throws Throwable
{
	AddUserPage user = PageFactory.initElements(driver,AddUserPage.class);
	String results = user.verifyAddUsers("Admin","ANIKET JAIN","Testing123#$%","Testing123#$%","Akhilesh98");
	Reporter.log(results,true);
}
@Test(priority=0)
public void validateAddEmp() throws Throwable
{
	AddEmpPage emp = PageFactory.initElements(driver,AddEmpPage.class);
	String results = emp.verifyAddEmp("Akhi","Ranga","Testing");
	Reporter.log(results,true);	
}
@AfterTest
public void tearDown()throws Throwable
{
	LogOutPage logout = PageFactory.initElements(driver,LogOutPage.class);
	logout.verifyLogout();
	driver.close();
}

}
