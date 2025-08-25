package team_5_pack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TC_Auto_Sel_002 {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();

            // 1. Open Register Page
            driver.get("https://tutorialsninja.com/demo/index.php?route=account/register");

            // 2. Wait for and verify Register Account heading
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Register Account']"))
            );

            if (heading.getText().equals("Register Account")) {
                System.out.println(" Verified: Matched the title - Register Account");
            } else {
                System.out.println(" Not Matched: Found - " + heading.getText());
            }

            // 3. Click Continue without filling form (to trigger validations)
            driver.findElement(By.cssSelector("input[type='submit'][value='Continue']")).click();

         // 4. Validate First Name
            WebElement firstNameErr = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#input-firstname + .text-danger")));
            if (firstNameErr.getText().contains("First Name must be between 1 and 32 characters")) {
                System.out.println(" First Name length validation working");
            } else {
                System.out.println(" Validation failed: " + firstNameErr.getText());
            }

            // 5. Validate Last Name
            WebElement lastNameErr = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#input-lastname + .text-danger")));
            if (lastNameErr.getText().contains("Last Name must be between 1 and 32 characters")) {
                System.out.println(" Last Name length validation working");
            } else {
                System.out.println(" Validation failed: " + lastNameErr.getText());
            }

            // 6. Validate Telephone
            WebElement telephoneErr = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#input-telephone + .text-danger")));
            if (telephoneErr.getText().contains("Telephone must be between 3 and 32 characters")) {
                System.out.println(" Telephone length validation working");
            } else {
                System.out.println(" Validation failed: " + telephoneErr.getText());
            }

            // 7. Enter Password and Confirm (4-20 chars)
            driver.findElement(By.id("input-password")).sendKeys("Test@1234");
            driver.findElement(By.id("input-confirm")).sendKeys("Test@1234");
            
            driver.findElement(By.id("input-firstname")).sendKeys("John");
            driver.findElement(By.id("input-lastname")).sendKeys("Doe");
            driver.findElement(By.id("input-email")).sendKeys("john" + System.currentTimeMillis() + "@test.com"); 
            driver.findElement(By.id("input-telephone")).sendKeys("9876543210");
            driver.findElement(By.id("input-password")).sendKeys("Test@1234");
            driver.findElement(By.id("input-confirm")).sendKeys("Test@1234");

            // 8. Newsletter -> Yes
            driver.findElement(By.cssSelector("input[name='newsletter'][value='1']")).click();

            // 9. Agree Privacy Policy and Submit
            driver.findElement(By.name("agree")).click();
            driver.findElement(By.cssSelector("input[type='submit'][value='Continue']")).click();

            // 10. Verify success message
            WebElement success = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content h1"))
            );
            if ("Your Account Has Been Created!".equals(success.getText().trim())) {
                System.out.println(" Verified: Your Account Has Been Created!");
            } else {
                System.out.println(" Registration failed - found: " + success.getText().trim());
            }

            // 11. Click Continue on success page
         // Wait for 'Continue' button after successful account creation
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));

            // First wait until visible
            WebElement continueBtn = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.btn.btn-primary")));

            // Then wait until clickable and click
            wait1.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
            System.out.println("Clicked on 'Continue' after account creation.");

            // 12. Click 'View your order history' under My Orders
            wait1.until(ExpectedConditions.elementToBeClickable(By.linkText("View your order history"))).click();
            System.out.println(" Opened: View your order history");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}

