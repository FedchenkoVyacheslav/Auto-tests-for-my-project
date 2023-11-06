package ui_tests;

import selenium.Actions.PrepareDriver;
import selenium.Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BlogFilterITCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private int ALL_TAGS[] = { 1, 2, 3, 4, 5, 6, 7, 8 };
    MainPage myMainPage;

    @Before
    public void setup(){
        driver = PrepareDriver.driverInit("chrome");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(URL);
        myMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Should group posts by selected tags")
    public void searchGroupByTags(){
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTag(7)
                .clickOnSearch()
                .checkTagInBlog(7);
    }

    @Test
    @DisplayName("Should group posts by number of views")
    public void searchGroupByViews(){
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .showNumberOfViews("100-500")
                .clickOnSearch()
                .checkNumberOfViewsInBlogs("100-500")
                .showNumberOfViews("500-1000")
                .clickOnSearch()
                .checkNumberOfViewsInBlogs("500-1000")
                .showNumberOfViews("1000-2000")
                .clickOnSearch()
                .checkNumberOfViewsInBlogs("1000-2000");
    }

    @Test
    @DisplayName("Should group posts by number of comments")
    public void searchGroupByComments(){
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .checkCommentsCount("0")
                .clickOnSearch()
                .checkNumberOfCommentsInBlogs("0")
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .checkCommentsCount("0-1")
                .clickOnSearch()
                .checkNumberOfCommentsInBlogs("0-1")
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .checkCommentsCount("1-50")
                .clickOnSearch()
                .checkNumberOfCommentsInBlogs("1-50");
    }

    @Test
    @DisplayName("Should get posts by partial match with search query")
    public void searchGroupByPartialWordsMatch(){
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .typeSearchQuery("and")
                .clickOnSearch()
                .checkPartialSearchMatch("and");
    }

    @Test
    @DisplayName("Should change number of posts displayed on page")
    public void showNumberOfResultsPerPage(){
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTags(ALL_TAGS)
                .showNumberOfBlogs("show 10")
                .clickOnSearch()
                .checkNumberOfBlogsOnPage(10)
                .showNumberOfBlogs("show 5")
                .clickOnSearch()
                .checkNumberOfBlogsOnPage(5);
    }

    @Test
    @DisplayName("Should clear search filter")
    public void checkResetSearchFilter(){
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .clickOnSearch()
                .checkEmptyResultOnSearch();
    }

    @After
    public void quit(){
        driver.quit();
    }
}
