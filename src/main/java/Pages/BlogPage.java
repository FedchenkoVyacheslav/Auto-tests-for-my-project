package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
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
    private WebElement radioButton5Blogs;
    @FindBy(xpath = "//fieldset[contains(@class, 'filter__how-show')]//span[text()='show 10']/..")
    private WebElement radioButton10Blogs;
    @FindBy(xpath = "//fieldset[contains(@class, 'filter__views')]//span[text()='100-500']/..")
    private WebElement radioButton100_500views;
    @FindBy(xpath = "//fieldset[contains(@class, 'filter__views')]//span[text()='500-1000']/..")
    private WebElement radioButton500_1000views;
    @FindBy(xpath = "//fieldset[contains(@class, 'filter__views')]//span[text()='1000-2000']/..")
    private WebElement radioButton1000_2000views;

    public BlogPage showNumberOfBlogs(String number) {
        switch (number) {
            case "show 5":
                radioButton5Blogs.click();
                return this;
            case "show 10":
                radioButton10Blogs.click();
                return this;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
    }

    public BlogPage showNumberOfViews(String number) {
        switch (number) {
            case "100-500":
                radioButton100_500views.click();
                return this;
            case "500-1000":
                radioButton500_1000views.click();
                return this;
            case "1000-2000":
                radioButton1000_2000views.click();
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

    public BlogPage checkNumberOfViewsInBlogs(String number) {
        List<Integer> numbersOfViews = new ArrayList<Integer>();
        List<WebElement> blogViews = driver.findElements(By.xpath("//li[@class='blog__item']//span[contains(@class, 'blog__views')]"));
        for (WebElement blogView : blogViews) {
            int numberOfViews = parseInt(blogView.getText().replaceAll("\\D+",""));
            numbersOfViews.add(numberOfViews);
        }

        switch (number) {
            case "100-500":
                for(Integer views : numbersOfViews) {
                    assertTrue(100 < views);
                    assertTrue(500 > views);
                }
                return this;
            case "500-1000":
                for(Integer views : numbersOfViews) {
                    assertTrue(500 < views);
                    assertTrue(1000 > views);
                }
                return this;
            case "1000-2000":
                for(Integer views : numbersOfViews) {
                    assertTrue(1000 < views);
                    assertTrue(2000 > views);
                }
                return this;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
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
