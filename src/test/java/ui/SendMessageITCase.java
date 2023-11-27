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

public class SendMessageITCase {
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
    @MethodSource("ui.testData#validMessageData")
    @DisplayName("Should send new message")
    public void sendMessage(String name, String message, String email, String phone, String text, boolean consent) {
        myMainPage
                .clickOnSendMessage()
                .sendMessage(name, message, email, phone, text, consent)
                .checkValidMessagesInForm("form-message");
    }

    @ParameterizedTest
    @MethodSource("ui.testData#messageValidationTestData")
    @DisplayName("Should check validation errors in send message popup")
    public void checkValidationErrors(String name, String message, String email, String phone, String text, String errorMessage, boolean formFlag, boolean consent) {
        myMainPage
                .clickOnSendMessage()
                .sendMessage(name, message, email, phone, text, consent)
                .checkInputErrorMessage(errorMessage, formFlag, consent)
                .checkConsentError("form-message", consent);
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }
}
