package team_5_pack;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
 
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
 
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class TC_Screenshot {
	WebDriver driver;
	@Test
	public void screenshottest() throws InterruptedException, IOException
	{
		String projectpath=System.getProperty("user.dir");
		ExtentReports extent=new ExtentReports();
		ExtentSparkReporter spark=new ExtentSparkReporter(projectpath+"\\aug29threport.html");
		extent.attachReporter(spark);
		ExtentTest test=extent.createTest("Verify the login");
		
		WebDriverManager.chromedriver().setup();
		  driver=new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		  driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		  Thread.sleep(3000);
		  
		  if((driver.getTitle()).equals("orangehrm"))
				  {
			  
			  System.out.println("title is matched");
			  
			  test.pass("title is matched");
			  
				  }
		  
		  else
		  {
			  File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			  String screenpath=projectpath+"\\screenshots\\s1.png";
		      File dest=new File(screenpath);
			  FileUtils.copyFile(src, dest);
			  test.fail("title is matched").addScreenCaptureFromPath(screenpath);
		  }
		  
		  
		  driver.findElement(By.name("username")).sendKeys("Admin");
		  driver.findElement(By.name("password")).sendKeys("admin123");
		  driver.findElement(By.xpath("//button[@type='submit']")).click();
	   
		  extent.flush();
		
	}
 
}