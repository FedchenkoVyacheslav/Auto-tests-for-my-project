import Actions.PrepareDriver;
import Pages.BasePage;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class EditProfileCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private String EMAIL = BasePage.getRandomLogin();
    private final String NAME = "Tom";
    private final String SURNAME = "Anderson";
    private final String PASSWORD = "12345678";
    private final String LOCATION = "New York City";
    private final String AGE = "35";
    private String NEW_EMAIL = BasePage.getRandomLogin();
    private final String NEW_NAME = "Neo";
    private final String NEW_SURNAME = "The One";
    private final String NEW_PASSWORD = "87654321";
    private final String NEW_LOCATION = "Zion";
    private final String NEW_AGE = "36";
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
    public void editPassword(){
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnChangePasswordButton()
                .changePassword(PASSWORD, NEW_PASSWORD, NEW_PASSWORD)
                .clickOnSignOut()
                .clickOnSignIn()
                .loginWithCredential(EMAIL, NEW_PASSWORD)
                .goToProfilePage()
                .checkUrlIsValid(URL + "pages/profile/");
    }

    @After
    public void quit(){
        driver.quit();
    }
}