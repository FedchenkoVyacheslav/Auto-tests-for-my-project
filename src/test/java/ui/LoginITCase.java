package ui;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ui.Actions.PrepareDriver;
import ui.Pages.MainPage;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class LoginITCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
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
    @CsvSource({"g1@gmail.com, 12345678"})
    @DisplayName("Should login user")
    public void loginLogoutToProfile(String email, String password) {
        myMainPage
                .clickOnSignIn()
                .loginWithCredential(email, password)
                .checkValidMessagesInForm("form-sing-in")
                .goToProfilePage()
                .checkUrlIsValid(URL + "pages/profile/")
                .clickOnSignOut()
                .checkUrlIsValid(URL);
    }

    @ParameterizedTest
    @MethodSource("testData#loginValidationTestData")
    @DisplayName("Should check validation errors in login popup")
    public void checkValidationErrorsOnLogin(String email, String password, String validationError) {
        myMainPage
                .clickOnSignIn()
                .loginWithCredential(email, password)
                .checkErrorInLoginForm(validationError);
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }
}