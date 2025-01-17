package testngprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.time.Duration;

public class HerokuAppFramesTest {

    static WebDriver driver = null;
    static WebDriverWait wait = null;

    @BeforeClass
    @Parameters("browser")
    public void launchApplication(String browser) {
        // launch the empty browser
        if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        }
        // open the URL
        driver.get("http://the-internet.herokuapp.com/");
        // maximize the window
        driver.manage().window().maximize();
        // add implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        // add explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // validate the title
        Assert.assertEquals(driver.getTitle(), "The Internet");
        // click on Frames link
        driver.findElement(By.linkText("Frames")).click();
        // verify the next page URL
        wait.until(ExpectedConditions.urlContains("/frames"));
        // verify the Frames header
        String framesHeader = driver.findElement(By.cssSelector("div[class='example'] h3")).getText();
        Assert.assertEquals(framesHeader, "Frames");
    }

    @Test
    public void framesTest() {
        // click on Nested Frames link
        driver.findElement(By.partialLinkText("Nested")).click();
        // verify the next page URL
        wait.until(ExpectedConditions.urlContains("/nested_frames"));
        // fetch number of frames in a web page
        List<WebElement> framesList = driver.findElements(By.tagName("frame"));
        System.out.println("No. of frames in a webpage: " + framesList.size());
        // switch to top-frame
        driver.switchTo().frame("frame-top");
        // fetch number of child elements in top frame
        List<WebElement> childElementList = driver.findElements(By.tagName("frame"));
        System.out.println("No. of child elements in top frame: " + childElementList.size());
        // switch to middle frame
        driver.switchTo().frame(1);
        // fetch the text of middle frame
        String middleFrameText = driver.findElement(By.cssSelector("#content")).getText();
        // verify the text
        Assert.assertEquals(middleFrameText, "MIDDLE");
        // switch to parent frame (top frame)
        driver.switchTo().defaultContent();
        driver.switchTo().frame("frame-top").switchTo().frame(2);
        // switch to RIGHT frame
        // driver.switchTo().frame(2); //frame-right
        // switch to parent frame (top frame)
        driver.switchTo().defaultContent();
        // switch to bottom frame
        driver.switchTo().frame("frame-bottom");
        // fetch bottom frame text
        String bottomFrameText = driver.findElement(By.tagName("body")).getText();
        System.out.println("Bottom frame text is: " + bottomFrameText);
        // switch to parent frame (top frame)
        driver.switchTo().defaultContent();
        // navigate to the previous page
        driver.navigate().back();
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }
}
