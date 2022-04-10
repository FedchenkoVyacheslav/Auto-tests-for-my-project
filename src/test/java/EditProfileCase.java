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
    private final String PATH = "src/test/resources/testData/neo_matrix.jpg";
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

    @Test
    public void editData(){
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnChangeDataButton()
                .changeData(NEW_EMAIL, NEW_NAME, NEW_SURNAME, NEW_LOCATION, NEW_AGE, PATH)
                .clickOnSignOut()
                .clickOnSignIn()
                .loginWithCredential(NEW_EMAIL, PASSWORD)
                .goToProfilePage()
                .confirmSavedValues(NEW_NAME, NEW_SURNAME, NEW_EMAIL, NEW_LOCATION, NEW_AGE);
    }

    @Test
    public void checkValidationErrorsOnEditPassword(){
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnChangePasswordButton()

                .checkInputErrorInChangePasswordForm("This field is required")
                .typeOldPassword(PASSWORD)

                .checkInputErrorInChangePasswordForm("This field is required")
                .typeNewPassword("1")
                .checkInputErrorInChangePasswordForm("Password must be between 3 and 20 characters")
                .typeNewPassword("1234567890asdfghjklzx")
                .checkInputErrorInChangePasswordForm("Password must be between 3 and 20 characters")
                .typeNewPassword(PASSWORD)
                .checkInputErrorInChangePasswordForm("The new password cannot be the same as the old")
                .typeNewPassword(NEW_PASSWORD)

                .checkInputErrorInChangePasswordForm("This field is required")
                .typeNewPasswordRepeat("1")
                .checkInputErrorInChangePasswordForm("New password mismatch")
                .typeNewPasswordRepeat(NEW_PASSWORD);
    }

    @After
    public void quit(){
        driver.quit();
    }
}
