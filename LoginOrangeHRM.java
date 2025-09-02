package com.orangehrm.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class LoginOrangeHRM {
    
    WebDriver driver;
    WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }
    
    @Test(priority = 1)
    public void testValidLogin() {
        // Enter valid credentials
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        username.sendKeys("Admin");
        
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin123");
        
        // Click login
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        
        // Verify successful login
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[text()='Dashboard']")));
        Assert.assertTrue(dashboard.isDisplayed(), "Login was not successful");
    }
    
    @Test(priority = 2)
    public void testInvalidLogin() {
        // Enter invalid credentials
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        username.sendKeys("InvalidUser");
        
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("WrongPassword");
        
        // Click login
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        	
        // Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(@class, 'oxd-alert-content-text')]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}