package ui;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ui.Actions.PrepareDriver;
import ui.Pages.MainPage;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class LoginITCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    MainPage myMainPage;

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("", "1", "This field is required"),
                Arguments.of("1", "1", "Please enter a valid email address (your entry is not in the format \"somebody@example.com\")"),
                Arguments.of("test@mail.com", "", "This field is required"),
                Arguments.of("test@mail.com", "1", "This combination, mail and password were not found!")
        );
    }

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
    @MethodSource("testData")
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