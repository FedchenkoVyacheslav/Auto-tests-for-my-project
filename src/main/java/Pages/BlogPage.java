package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.*;

public class BlogPage extends BasePage{
    public BlogPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[text()='Apply']")
    private WebElement resetButton;
    @FindBy(xpath = "//button[text()='Search']")
    private WebElement searchButton;
    @FindBy(xpath = "//p[@class='blog__message']")
    private WebElement searchMessageError;

    public BlogPage checkTags() {
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
