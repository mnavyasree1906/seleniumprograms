package testngprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class HerokoAppLoginTest {

    static WebDriver driver = null;
    static WebDriverWait wait = null;

    @BeforeClass
    public void launchApplication() {
        // Launch the browser
        driver = new ChromeDriver();
        // Maximize the window
        driver.manage().window().maximize();
        // Add implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // Create object for WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        // Open the URL
        driver.get("http://the-internet.herokuapp.com/");
        // Validate the title
        wait.until(ExpectedConditions.titleIs("The Internet"));
        // Click on Form Authentication link
        driver.findElement(By.linkText("Form Authentication")).click();
    }

    @BeforeMethod
    public void setUp() {
        // Verify login page header text
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='content']/div/h2")));
        String loginPgHeaderTxt = driver.findElement(By.xpath("//div[@class='example']/h2")).getText();
        Assert.assertEquals(loginPgHeaderTxt, "Login Page", "Login page header text is not matching");
        // Validate the title of login page
        Assert.assertEquals(driver.getTitle(), "The Internet");
    }

    @Test
    public void validCredentialsTest() {
        doLogin("tomsmith", "SuperSecretPassword!");
        validateMessage("You logged into a secure area!");
        // Click on logout button
        driver.findElement(By.xpath("//a[@class='button secondary radius']")).click();
        validateMessage("You logged out of the secure area!");
    }

    @Test
    public void validUserNameInvalidPswdTest() {
        doLogin("tomsmith", "Test@124");
        validateMessage("Your password is invalid!");
    }

    @Test
    public void invalidUserNameValidPswd() {
        doLogin("Navya", "SuperSecretPassword!");
        validateMessage("Your username is invalid!");
    }

    @AfterMethod
    public void tearDown() {
        // Validate the title
        wait.until(ExpectedConditions.titleIs("The Internet"));
        // Delete all cookies
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }

    public static void doLogin(String userName, String pswd) {
        // Enter value in username editbox
        WebElement userName_Editbox = driver.findElement(By.cssSelector("#username"));
        userName_Editbox.clear();
        userName_Editbox.sendKeys(userName);
        // Enter value in password editbox
        WebElement pswd_Editbox = driver.findElement(By.id("password"));
        pswd_Editbox.clear();
        pswd_Editbox.sendKeys(pswd);
        // Click on login button
        driver.findElement(By.cssSelector("button[class='radius']")).click();
    }

    public static void validateMessage(String msg) {
        // Validate the success message
        WebElement validMsg = driver.findElement(By.cssSelector("div[id='flash']"));
        Assert.assertTrue(validMsg.getText().trim().contains(msg), "Success message is not displayed");
        // Check presence of Secure Area header
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='example']/h2")));
    }
}
