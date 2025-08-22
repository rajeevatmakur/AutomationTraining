package team_5_pack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_Auto_Sel_001 {

	public static void main(String[] args) 
	{
		WebDriverManager.chromedriver().setup();

		WebDriver driver=new ChromeDriver();

		driver.get("https://www.google.com/");
		 
		WebElement search=driver.findElement(By.id("APjFqb"));
		search.sendKeys("Google Pixel 10 Fold");
		search.submit();
	}

}
