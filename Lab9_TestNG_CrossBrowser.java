package team_5_pack;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;

public class Lab9_TestNG_CrossBrowser {

    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Create driver depending on parameter. Sets sensible timeouts and
     * unhandled-prompt behaviour to avoid modal blocks that hang tests.
     */
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        Reporter.log("Starting browser: " + browser, true);

        browser = browser == null ? "chrome" : browser.trim().toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                co.setAcceptInsecureCerts(true);
                co.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                driver = new ChromeDriver(co);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions eo = new EdgeOptions();
                eo.setAcceptInsecureCerts(true);
                eo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                driver = new EdgeDriver(eo);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fo = new FirefoxOptions();
                fo.setAcceptInsecureCerts(true);
                fo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                driver = new FirefoxDriver(fo);
                break;

            case "ie":
            case "internetexplorer":
                // IE only works on Windows and has many environment requirements
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions ieo = new InternetExplorerOptions()
                        .ignoreZoomSettings()
                        .introduceFlakinessByIgnoringSecurityDomains()
                        .requireWindowFocus()
                        .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                driver = new InternetExplorerDriver(ieo);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // timeouts & window
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); // minimal implicit
        try { driver.manage().window().maximize(); } catch (Exception e) { /* ignore if not supported */ }

        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            Reporter.log("Quitting browser", true);
            try { driver.quit(); } catch (Exception ignored) {}
        }
    }

    /**
     * Lab3 flow: Desktops -> Mac(1) (handles Mac or Mac(1)) -> sort -> iMac -> add to cart -> assert alert
     */
    @Test(priority = 1)
    public void lab3Flow() {
        try {
            driver.get("https://tutorialsninja.com/demo/index.php?");
            Reporter.log("Opened homepage", true);

            // Open Desktops menu (move then click to reveal submenu reliably)
            WebElement desktops = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Desktops")));
            desktops.click();

            // Click the Mac category link robustly (handles "Mac" or "Mac(1)")
            By macLocator = By.xpath("//a[contains(normalize-space(.), 'Mac') and not(contains(., 'iMac'))]");
            WebElement macLink = wait.until(ExpectedConditions.elementToBeClickable(macLocator));
            macLink.click();

            // heading contains Mac
            String heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content h2"))).getText().trim();
            Assert.assertTrue(heading.toLowerCase().contains("mac"), "Expected page heading to contain 'Mac' but was: " + heading);

            // Select sort: Name (A - Z)
            Select sortSel = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-sort"))));
            sortSel.selectByVisibleText("Name (A - Z)");
            Assert.assertTrue(sortSel.getFirstSelectedOption().getText().contains("Name"), "Sort selection failed");

            // Click iMac product and Add to Cart
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("iMac"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart"))).click();

            // Assert an alert (success or warning) is shown after add to cart
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert")));
            Assert.assertTrue(alert.isDisplayed(), "Expected an alert after Add to Cart.");

            Reporter.log("Lab3 flow completed OK", true);
        } catch (TimeoutException te) {
            Reporter.log("Timeout in lab3Flow: " + te.getMessage(), true);
            Assert.fail("Timeout while executing lab3Flow: " + te.getMessage());
        } catch (Exception e) {
            Reporter.log("Exception in lab3Flow: " + e.getMessage(), true);
            Assert.fail("Exception in lab3Flow: " + e.getMessage());
        }
    }

    /**
     * Lab4 flow: includes lab3 steps + search Mobile -> clear -> Monitors with description
     */
    @Test(priority = 2)
    public void lab4Flow() {
        try {
            driver.get("https://tutorialsninja.com/demo/index.php?");
            Reporter.log("Opened homepage for lab4", true);

            // Desktops -> Mac (same robust locator)
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Desktops"))).click();
            By macLocator = By.xpath("//a[contains(normalize-space(.), 'Mac') and not(contains(., 'iMac'))]");
            wait.until(ExpectedConditions.elementToBeClickable(macLocator)).click();

            // verify heading contains Mac
            String heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content h2"))).getText().trim();
            Assert.assertTrue(heading.toLowerCase().contains("mac"), "Expected page heading to contain 'Mac' but was: " + heading);

            // Sort + Add iMac to cart
            Select sortSel = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-sort"))));
            sortSel.selectByVisibleText("Name (A - Z)");
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("iMac"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert")));

            // HEADER search: Mobile
            WebElement headerSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
            headerSearch.clear();
            headerSearch.sendKeys("Mobile");
            driver.findElement(By.cssSelector("#search button")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content")));

            // Clear criteria -> Monitors + description checkbox -> Search
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-search")));
            searchInput.clear();
            searchInput.sendKeys("Monitors");

            WebElement descChk = driver.findElement(By.id("description"));
            if (!descChk.isSelected()) {
                descChk.click();
            }
            driver.findElement(By.id("button-search")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content")));

            Reporter.log("Lab4 flow completed OK", true);
        } catch (TimeoutException te) {
            Reporter.log("Timeout in lab4Flow: " + te.getMessage(), true);
            Assert.fail("Timeout while executing lab4Flow: " + te.getMessage());
        } catch (Exception e) {
            Reporter.log("Exception in lab4Flow: " + e.getMessage(), true);
            Assert.fail("Exception in lab4Flow: " + e.getMessage());
        }
    }
}
