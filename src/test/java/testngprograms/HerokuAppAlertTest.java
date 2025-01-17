package testngprograms;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class HerokuAppAlertTest {

    static WebDriver driver = null;
    static WebDriverWait wait = null;

    @BeforeClass
    public static void launchApplication() {
        // Launch Chrome browser
        driver = new ChromeDriver();
        // Navigate to the URL
        driver.get("http://the-internet.herokuapp.com/");
        // Maximize the browser window
        driver.manage().window().maximize();
        // Add implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        // Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        // Validate the title
        Assert.assertEquals(driver.getTitle(), "The Internet");
        // Validate the URL
        Assert.assertTrue(driver.getCurrentUrl().contains("the-internet.herokuapp.com"));
        // Click on JS Alert link
        driver.findElement(By.linkText("JavaScript Alerts")).click();
        // Verify next page URL
        wait.until(ExpectedConditions.urlContains("/javascript_alerts"));
    }

    @Test
    public void simpleAlertTest() {
        // Click on JS Alert
        driver.findElement(By.cssSelector("button[onclick='jsAlert()']")).click();
        // Switch to the Alert
        Alert alt = driver.switchTo().alert();
        System.out.println("Simple Alert text is: " + alt.getText());
        // Click on OK button
        alt.accept();
        // Validate the success message
        validateMsg("You successfully clicked an alert");
    }

    @Test
    public void confirmAlertTest() {
        // Click on Confirm Alert
        driver.findElement(By.cssSelector("button[onclick='jsConfirm()']")).click();
        // Switch to the Alert
        Alert alt = driver.switchTo().alert();
        System.out.println("Confirm Alert text is: " + alt.getText());
        // Click on OK button
        alt.accept();
        // Validate the success message
        validateMsg("You clicked: Ok");

        // Click on Confirm Alert again
        driver.findElement(By.cssSelector("button[onclick='jsConfirm()']")).click();
        // Switch to the Alert
        alt = driver.switchTo().alert();
        System.out.println("Confirm Alert text is: " + alt.getText());
        // Click on Cancel button
        alt.dismiss();
        // Validate the failure message
        validateMsg("You clicked: Cancel");
    }

    @Test
    public void promptAlertTest() {
        // Click on Prompt Alert
        driver.findElement(By.cssSelector("button[onclick='jsPrompt()']")).click();
        // Switch to the Alert
        Alert alt = driver.switchTo().alert();
        System.out.println("Prompt Alert text is: " + alt.getText());
        // Enter text in the edit box
        alt.sendKeys("Harshini");
        // Click on OK button
        alt.accept();
        // Validate the success message
        validateMsg("You entered: Harshini");

        // Click on Prompt Alert again
        driver.findElement(By.cssSelector("button[onclick='jsPrompt()']")).click();
        // Switch to the Alert
        alt = driver.switchTo().alert();
        System.out.println("Prompt Alert text is: " + alt.getText());
        // Enter text in the edit box
        alt.sendKeys("Kiran");
        // Click on Cancel button
        alt.dismiss();
        // Validate the failure message
        validateMsg("You entered: null");
    }

    @AfterMethod
    public void tearDown() {
        // Delete all cookies
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public static void closeBrowser() {
        // Close the browser
        driver.quit();
    }

    public static void validateMsg(String msg) {
        String altMsg = driver.findElement(By.cssSelector("#result")).getText();
        Assert.assertTrue(altMsg.contains(msg), "Message validation failed.");
    }
}
