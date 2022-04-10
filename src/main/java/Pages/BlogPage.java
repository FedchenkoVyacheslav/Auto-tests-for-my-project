package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

public class BlogPage extends BasePage {
    public BlogPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[text()='Apply']")
    private WebElement resetButton;
    @FindBy(xpath = "//button[text()='Search']")
    private WebElement searchButton;
    @FindBy(xpath = "//p[@class='blog__message']")
    private WebElement searchMessageError;
    @FindBy(xpath = "//fieldset[contains(@class, 'filter__how-show')]//span[text()='show 5']/..")
    private WebElement show5Blogs;
    @FindBy(xpath = "//fieldset[contains(@class, 'filter__how-show')]//span[text()='show 10']/..")
    private WebElement show10Blogs;

    public BlogPage showNumberOfBlogs(String number) {
        switch (number) {
            case "show 5":
                show5Blogs.click();
                return this;
            case "show 10":
                show10Blogs.click();
                return this;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
    }

    public BlogPage checkNumberOfBlogsOnPage(int number) {
        List<WebElement> blogs = driver.findElements(By.xpath("//li[@class='blog__item']"));
        assertEquals(number, blogs.size());
        return this;
    }

    public BlogPage checkTags(int tagNumbers[]) {
        for (int tagNumber : tagNumbers) {
            driver.findElement(By.xpath("//div[@class='filter__tag-box']//input[@aria-label='tag " + tagNumber + "']/..")).click();
        }
        return this;
    }

    public BlogPage checkTag(int tagNumber) {
        driver.findElement(By.xpath("//div[@class='filter__tag-box']//input[@aria-label='tag " + tagNumber + "']/..")).click();
        return this;
    }

    public BlogPage checkBlogTag(int tagNumber) {
        List<WebElement> blogTags = driver.findElements(By.xpath("//li[contains(@class, 'blog__tag')]"));
        for (WebElement blogTag : blogTags) {
            assertEquals(tagNumber, parseInt(blogTag.getAttribute("ariaLabel").replaceAll("\\D+","")));
        }
        return this;
    }

    public BlogPage clickOnReset() {
        resetButton.click();
        return this;
    }

    public BlogPage clickOnSearch() {
        searchButton.click();
        return this;
    }

    public BlogPage checkEmptyResultOnSearch() {
        assertEquals(searchMessageError.getText(), "Nothing found");
        return this;
    }
}
//div[@class='filter__tag-box']//input[@aria-label='tag 1']/..