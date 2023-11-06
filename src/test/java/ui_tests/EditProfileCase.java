package ui_tests;

import com.github.javafaker.Faker;
import selenium.Actions.PrepareDriver;
import selenium.Actions.Screenshot;
import selenium.Pages.BasePage;
import selenium.Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class EditProfileCase {
    Faker faker = new Faker();
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private final String NAME = faker.name().firstName();
    private final String SURNAME = faker.name().lastName();
    private final String PASSWORD = faker.internet().password();
    private final String LOCATION = faker.address().city();
    private final String AGE = String.valueOf((int) (Math.random() * (100 - 18)) + 18);
    private final String EMAIL = BasePage.getUserEmail(NAME, SURNAME, AGE);
    private final String NEW_NAME = "Keanu";
    private final String NEW_SURNAME = "Reeves";
    private final String NEW_AGE = String.valueOf(BasePage.getCurrentYear() - 1964);
    private final String NEW_EMAIL = EMAIL;
    private final String NEW_PASSWORD = "12345678";
    private final String NEW_LOCATION = "Toronto";
    private final String PATH = "src/test/resources/testData/neo_matrix.jpg";
    private final String PASSWORD_EDIT = "form-password-edit";
    private final String DATA_EDIT = "form-editing-data";
    MainPage myMainPage;

    @Before
    public void setup() {
        driver = PrepareDriver.driverInit("chrome");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(URL);
        myMainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Should change password")
    public void editPassword() {
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnChangePasswordButton()
                .changePassword(PASSWORD, NEW_PASSWORD, NEW_PASSWORD)
                .checkValidMessagesInForm(PASSWORD_EDIT)
                .clickOnSignOut()
                .clickOnSignIn()
                .loginWithCredential(EMAIL, NEW_PASSWORD)
                .goToProfilePage()
                .checkUrlIsValid(URL + "pages/profile/");
    }

    @Test
    @DisplayName("Should update user data")
    public void editData() {
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnChangeDataButton()
                .changeData(NEW_EMAIL, NEW_NAME, NEW_SURNAME, NEW_LOCATION, NEW_AGE, PATH)
                .checkValidMessagesInForm(DATA_EDIT)
                .clickOnSignOut()
                .clickOnSignIn()
                .loginWithCredential(NEW_EMAIL, PASSWORD)
                .goToProfilePage()
                .confirmSavedValues(NEW_NAME, NEW_SURNAME, NEW_EMAIL, NEW_LOCATION, NEW_AGE);

        Screenshot.takeScreenshot();
    }

    @Test
    @DisplayName("Should check validation errors in edit password popup")
    public void checkValidationErrorsOnEditPassword() {
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

    @Test
    @DisplayName("Should check validation errors in edit data popup")
    public void checkValidationErrorsOnEditData() {
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnChangeDataButton()

                .checkInputErrorInChangeDataForm("This field is required")
                .typeNewEmail("1")
                .checkInputErrorInChangeDataForm("Please enter a valid email address (your entry is not in the format \"somebody@example.com\")")
                .typeNewEmail(NEW_EMAIL)

                .checkInputErrorInChangeDataForm("This field is required")
                .typeNewName("1")
                .checkInputErrorInChangeDataForm("This name is not valid")
                .typeNewName("a")
                .checkInputErrorInChangeDataForm("Your name is too short or too long")
                .typeNewName("qwertyuiopasdfghjklzx")
                .checkInputErrorInChangeDataForm("Your name is too short or too long")
                .typeNewName(NEW_NAME)

                .checkInputErrorInChangeDataForm("This field is required")
                .typeNewSurname("1")
                .checkInputErrorInChangeDataForm("This surname is not valid")
                .typeNewSurname("a")
                .checkInputErrorInChangeDataForm("Your surname is too short or too long")
                .typeNewSurname("qwertyuiopasdfghjklzx")
                .checkInputErrorInChangeDataForm("Your surname is too short or too long")
                .typeNewSurname(NEW_SURNAME)

                .checkInputErrorInChangeDataForm("This field is required")
                .typeNewLocation("1")
                .checkInputErrorInChangeDataForm("This location is not valid")
                .typeNewLocation("a")
                .checkInputErrorInChangeDataForm("Location name is too short or too long")
                .typeNewLocation("united states of america state california")
                .checkInputErrorInChangeDataForm("Location name is too short or too long")
                .typeNewLocation(NEW_LOCATION)

                .checkInputErrorInChangeDataForm("This field is required")
                .typeNewAge("-1")
                .checkInputErrorInChangeDataForm("This age is not valid")
                .typeNewAge("101")
                .checkInputErrorInChangeDataForm("This age is not valid")
                .typeNewAge(NEW_AGE)

                .checkInputErrorInChangeDataForm("You forgot to choose a photo");
    }

    @After
    public void quit() {
        driver.quit();
    }
}
