package team_5_pack;

import org.testng.annotations.Test; 
import io.github.bonigarcia.wdm.WebDriverManager; 
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass; 
import java.time.Duration; 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TC009_TestNG3_pageobjects {
	WebDriver driver;
  
	@Test(dataProvider="logindata")
	public void f(String username, String password) {
		System.out.println("This is the test");
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		Login_Page objlogin = new Login_Page(driver);  // FIXED: Changed Loginpage to Login_Page
		
		if(objlogin.usernameisdisplayed()) {
			objlogin.enterusername(username);
			System.out.println("Get placeholder:" + objlogin.unamegetattributevalue());
		} else {
			System.out.println("username is not displayed");
		}
		
		objlogin.enterpassword(password);
		objlogin.clickonbutton();
		
		if(objlogin.dashisdisplayed()) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("This is After Method");
		driver.quit();
	}

	@DataProvider
	public Object[][] logindata() {
		return new Object[][] {
			new Object[] { "Admin", "admin123" },
		};
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("This is Before Class");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("This is After Class");
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("This is Before Test");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("This is After Test");
	}

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("This is Before Suite");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("This is After Suite");
	}
}