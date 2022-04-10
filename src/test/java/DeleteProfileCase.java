import Actions.PrepareDriver;
import Pages.BasePage;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class DeleteProfileCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private String EMAIL = BasePage.getRandomLogin();
    private final String NAME = "Tom";
    private final String SURNAME = "Anderson";
    private final String PASSWORD = "12345678";
    private final String LOCATION = "New York City";
    private final String AGE = "35";
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
    public void deleteProfile(){
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnDeleteAccountButton()
                .clickOnDeleteAccountModalButton()
                .checkUrlIsValid(URL)
                .clickOnSignIn()
                .typeEmail(EMAIL)
                .typePassword(PASSWORD)
                .checkErrorInLoginForm("This combination, mail and password were not found!");
    }

    @After
    public void quit(){
        driver.quit();
    }
}
