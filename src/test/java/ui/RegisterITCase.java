package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ui.Actions.PrepareDriver;
import ui.Pages.MainPage;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static ui.testData.URL;

public class RegisterITCase {
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
    @MethodSource("ui.testData#validRegisterData")
    @DisplayName("Should register new user")
    public void registerNewUser(String email, String name, String surname, String password, String location, String age, boolean consent) {
        myMainPage
                .clickOnRegister()
                .registerUser(email, name, surname, password, password, location, age, consent)
                .clickOnSignIn()
                .checkValidMessagesInForm("form-register")
                .loginWithCredential(email, password)
                .goToProfilePage()
                .confirmSavedValues(name, surname, email, location, age)
                .checkUrlIsValid(URL + "pages/profile/")
                .clickOnSignOut()
                .checkUrlIsValid(URL);
    }

    @ParameterizedTest
    @MethodSource("ui.testData#registerValidationTestData")
    @DisplayName("Should check validation errors in registration popup")
    public void checkValidationErrorsOnRegister(String email, String name, String surname, String password, String passwordRep, String location, String age, boolean consent, String errorMessage) {
        myMainPage
                .clickOnRegister()
                .registerUser(email, name, surname, password, passwordRep, location, age, consent)
                .checkErrorInRegistrationForm(errorMessage, consent)
                .checkConsentError("form-register", consent);
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }
}
