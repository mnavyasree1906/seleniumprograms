package testngprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GoogleSuggestionsTest {

    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void beforeMethod() {
        driver = new ChromeDriver();
        Reporter.log("Open the URL", true);
        driver.get("https://www.google.com/");
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
    public void googleSuggestionTest() {
        Reporter.log("Identify search editbox", true);
        WebElement searchEditbox = driver.findElement(By.cssSelector("textarea[title='Search']"));

        searchEditbox.clear(); // Clear the existing text in the search editbox

        Reporter.log("Input the value in search editbox", true);
        String keyword = "selenium"; // Define the keyword to search for
        searchEditbox.sendKeys(keyword); // Enter the keyword in the search editbox

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']")));
        Reporter.log("Fetch all suggestions into a list", true);
        List<WebElement> suggestionsList = driver.findElements(By.xpath("//ul[@role='listbox']/li"));

        Reporter.log("No. of suggestions: " + suggestionsList.size(), true);

        boolean isAvailable = false; // Flag to check if the keyword is present in suggestions

        for (WebElement suggestion : suggestionsList) {
            String suggestionText = suggestion.getText(); // Get the text of the suggestion
            Reporter.log("Suggestion text is: " + suggestionText, true);

            if (suggestionText.contains(keyword)) {
                isAvailable = true;
                break;
            }
        }

        Assert.assertTrue(isAvailable, "Keyword not found in suggestions");
    }

    @Test(priority = 1)
    public void googleSuggestionClickTest() {
        Reporter.log("Identify search editbox", true);
        WebElement searchEditbox = driver.findElement(By.cssSelector("textarea[title='Search']"));

        searchEditbox.clear(); // Clear the existing text in the search editbox

        Reporter.log("Input the value in search editbox", true);
        String expectedKeyword = "ramesh qa automation"; // Define the keyword to search for
        searchEditbox.sendKeys(expectedKeyword); // Enter the keyword in the search editbox

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']"))); // Wait until the suggestions list is visible

        Reporter.log("Fetch all suggestions into a list", true);
        List<WebElement> suggestionsList = driver.findElements(By.xpath("//ul[@role='listbox']/li"));

        Reporter.log("No. of suggestions: " + suggestionsList.size(), true);

        boolean isClicked = false; // Flag to check if a suggestion was clicked

        for (WebElement suggestion : suggestionsList) {
            String suggestionText = suggestion.getText(); // Get the text of the suggestion
            Reporter.log("Suggestion text is: " + suggestionText, true);

            if (suggestionText.equalsIgnoreCase(expectedKeyword)) {
                Reporter.log("Clicked on: " + suggestionText, true);
                suggestion.click(); // Click on the matching suggestion
                isClicked = true;
                break; // Exit the loop as the suggestion is found
            }
        }

        Assert.assertTrue(isClicked, "Expected keyword not found in suggestions");

        Reporter.log("Verify next page title", true);
        wait.until(ExpectedConditions.titleContains(expectedKeyword));
        Assert.assertTrue(driver.getTitle().contains(expectedKeyword), "Title is not matching");
    }

    @AfterMethod
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
            Reporter.log("Browser closed", true);
        }
    }
}
