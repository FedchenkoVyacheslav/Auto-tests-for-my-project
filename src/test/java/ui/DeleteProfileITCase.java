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

public class DeleteProfileITCase {
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
    @DisplayName("Should delete user")
    public void deleteProfile(String email, String name, String surname, String password, String location, String age) {
        myMainPage
                .clickOnRegister()
                .registerUser(email, name, surname, password, password, location, age)
                .clickOnSignIn()
                .loginWithCredential(email, password)
                .goToProfilePage()
                .clickOnDeleteAccountButton()
                .clickOnDeleteAccountModalButton()
                .checkUrlIsValid(URL)
                .clickOnSignIn()
                .typeEmail(email)
                .typePassword(password)
                .clickOnLogIn()
                .checkErrorInLoginForm("This combination, mail and password were not found!");
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }
}
