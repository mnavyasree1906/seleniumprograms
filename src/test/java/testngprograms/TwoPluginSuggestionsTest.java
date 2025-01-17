package testngprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TwoPluginSuggestionsTest {

    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void beforeMethod() {
        driver = new ChromeDriver();
        Reporter.log("Open the URL", true);
        driver.get("https://twoplugs.com/newsearchserviceneed?q=toronto&type=s");
        Reporter.log("Maximize the window", true);
        driver.manage().window().maximize();
        Reporter.log("Add implicit wait", true);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        Reporter.log("Add explicit wait", true);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Reporter.log("Verify the home page title", true);
        Assert.assertEquals(driver.getTitle(), "Google", "Home page title is not matching");
    }

    @Test(priority = 0)
    public void twoPluginSuggestionTest() {
        Reporter.log("@Test twoPluginSuggestionTest", true);
        Reporter.log("Wait for page URL", true);
        wait.until(ExpectedConditions.urlContains("/newsearchserviceneed"));
        Reporter.log("Wait for visibility of twoPlugs icon", true);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='navbar-brand']")));
        Reporter.log("Identify the search editbox", true);
        WebElement search_Editbox = driver.findElement(By.cssSelector("#autocomplete"));
        Reporter.log("Input the value in search editbox", true);
        String keyword = "Toronto";
        search_Editbox.sendKeys(keyword);
        String expectedString = "Toronto NSW, Australia";
        for (int i = 0; i < 4; i++) {
            search_Editbox.sendKeys(Keys.DOWN);
            String value = search_Editbox.getAttribute("value");
            if (value.equals(expectedString)) {
                search_Editbox.sendKeys(Keys.ENTER);
                break;
            }
        }
        Reporter.log("Selected: " + search_Editbox.getAttribute("value"), true);
    }
}
