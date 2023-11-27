package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ui.Actions.PrepareDriver;
import ui.Actions.Screenshot;
import ui.Pages.MainPage;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static ui.testData.*;

public class EditProfileITCase {
    static WebDriver driver;
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
    @MethodSource("ui.testData#validPasswordData")
    @DisplayName("Should change password")
    public void editPassword(String email, String name, String surname, String password, String newPassword, String location, String age) {
        myMainPage
                .clickOnRegister()
                .registerUser(email, name, surname, password, password, location, age, true)
                .clickOnSignIn()
                .loginWithCredential(email, password)
                .goToProfilePage()
                .clickOnChangePasswordButton()
                .changePassword(password, newPassword, newPassword)
                .checkValidMessagesInForm("form-password-edit")
                .clickOnSignOut()
                .clickOnSignIn()
                .loginWithCredential(email, newPassword)
                .goToProfilePage()
                .checkUrlIsValid(URL + "pages/profile/");
    }

    @ParameterizedTest
    @MethodSource("ui.testData#validUserData")
    @DisplayName("Should update user data")
    public void editData(String email, String name, String surname, String password, String location, String age,
                         String newEmail, String newName, String newSurname, String newLocation, String newAge) {
        myMainPage
                .clickOnRegister()
                .registerUser(email, name, surname, password, password, location, age, true)
                .clickOnSignIn()
                .loginWithCredential(email, password)
                .goToProfilePage()
                .clickOnChangeDataButton()
                .changeData(newEmail, newName, newSurname, newLocation, newAge, PATH)
                .checkValidMessagesInForm("form-editing-data")
                .clickOnSignOut()
                .clickOnSignIn()
                .loginWithCredential(newEmail, password)
                .goToProfilePage()
                .confirmSavedValues(newName, newSurname, newEmail, newLocation, newAge);

        Screenshot.takeScreenshot();
    }

    @ParameterizedTest
    @MethodSource("ui.testData#passwordEditValidationTestData")
    @DisplayName("Should check validation errors in edit password popup")
    public void checkValidationErrorsOnEditPassword(String password, String newPassword, String newPasswordRepeat, String errorMessage) {
        myMainPage
                .clickOnSignIn()
                .loginWithCredential(L_EMAIL, L_PASSWORD)
                .goToProfilePage()
                .clickOnChangePasswordButton()
                .changePassword(password, newPassword, newPasswordRepeat)
                .checkInputErrorInChangePasswordForm(errorMessage);
    }

    @ParameterizedTest
    @MethodSource("ui.testData#userEditValidationTestData")
    @DisplayName("Should check validation errors in edit data popup")
    public void checkValidationErrorsOnEditData(String newEmail, String newName, String newSurname, String newLocation, String newAge, String errorMessage) {
        myMainPage
                .clickOnSignIn()
                .loginWithCredential(L_EMAIL, L_PASSWORD)
                .goToProfilePage()
                .clickOnChangeDataButton()
                .changeData(newEmail, newName, newSurname, newLocation, newAge)
                .checkInputErrorInChangeDataForm(errorMessage);
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }
}
