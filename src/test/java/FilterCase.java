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
    public void tagsSearch(){
        myMainPage
                .goToBlogPage();
    }

    @Test
    public void viewsSearch(){

    }

    @Test
    public void commentsSearch(){

    }

    @Test
    public void partialTextSearch(){

    }

    @Test
    public void showByCheck(){

    }

    @Test
    public void resetSearch(){
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
