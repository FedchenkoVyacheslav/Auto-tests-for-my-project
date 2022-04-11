package ui_tests;

import Actions.PrepareDriver;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class FilterCase {
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
    public void searchGroupByTags(){
        myMainPage
                .goToBlogPage()
                .clickOnReset()
                .checkTag(7)
                .clickOnSearch()
                .checkTagInBlog(7);
    }

    @Test
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
