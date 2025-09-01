package stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class StepDefinitions {
    
    WebDriver driver;
    WebDriverWait wait;
    
    @Given("Open the Browser")
    public void open_the_browser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @And("Navigate to the url")
    public void navigate_to_the_url() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }
    
    @Then("Close the browser")
    public void close_the_browser() {
       driver.quit();
    }

    @Then("Enter the valid username and password")
    public void enter_the_valid_username_and_password() {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys("Admin");
        
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("admin123");
    }

    @And("Click on Login")
    public void click_on_login() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        loginButton.click();
    }

    @Then("Verify login success")
    public void verify_login_success() {
        // Wait for dashboard to load and verify
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h6[text()='Dashboard']")));
        Assert.assertTrue(dashboard.isDisplayed(), "Login was not successful");
        System.out.println("Login verified successfully");
    }

    @Then("Enter the invalid username and password")
    public void enter_the_invalid_username_and_password() {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys("Admin12121");
        
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("admin123rewerwe");
    }

    @Then("Verify login unsuccess")
    public void verify_login_unsuccess() {
        // Wait for error message to appear and verify
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//p[contains(@class, 'oxd-alert-content-text')]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for invalid login");
        System.out.println("Invalid login verified successfully");
    }
}