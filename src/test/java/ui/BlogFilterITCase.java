package ui;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ui.Actions.PrepareDriver;
import ui.Pages.MainPage;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static ui.testData.URL;

public class BlogFilterITCase {
    static WebDriver driver;
    private final int[] ALL_TAGS = {1, 2, 3, 4, 5, 6, 7, 8};
    MainPage myMainPage;

    @BeforeEach
    public void setup() {
        driver = PrepareDriver.driverInit("chrome");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(URL);
        myMainPage = new MainPage(driver);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    @DisplayName("Should group posts by selected tags")
    public void searchGroupByTags(int tag) {
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTag(tag)
                .clickOnSearch()
                .checkTagInBlog(tag);
    }

    @ParameterizedTest
    @ValueSource(strings = {"100-500", "500-1000", "1000-2000"})
    @DisplayName("Should group posts by number of views")
    public void searchGroupByViews(String views) {
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .showNumberOfViews(views)
                .clickOnSearch()
                .checkNumberOfViewsInBlogs(views);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "0-1", "1-50"})
    @DisplayName("Should group posts by number of comments")
    public void searchGroupByComments(String comments) {
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .checkCommentsCount(comments)
                .clickOnSearch()
                .checkNumberOfCommentsInBlogs(comments);
    }

    @ParameterizedTest
    @ValueSource(strings = {"how", "about", "what"})
    @DisplayName("Should get posts by partial match with search query")
    public void searchGroupByPartialWordsMatch(String searchQuery) {
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .typeSearchQuery(searchQuery)
                .clickOnSearch()
                .checkPartialSearchMatch(searchQuery);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 5})
    @DisplayName("Should change number of posts displayed on page")
    public void showNumberOfResultsPerPage(int number) {
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .showNumberOfBlogs(number)
                .clickOnSearch()
                .checkNumberOfBlogsOnPage(number);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test"})
    @DisplayName("Should clear search filter")
    public void checkResetSearchFilter(String searchQuery) {
        myMainPage
                .goToBlogPage()
                .typeSearchQuery(searchQuery)
                .clickOnReset()
                .clickOnSearch()
                .checkEmptyResultOnSearch();
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }
}
