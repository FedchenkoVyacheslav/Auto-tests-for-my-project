package ui_tests;

import com.github.javafaker.Faker;
import selenium.Actions.PrepareDriver;
import selenium.Pages.BasePage;
import selenium.Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class SendMessageCase {
    Faker faker = new Faker();
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private final String NAME = faker.name().firstName();
    private final String SURNAME = faker.name().lastName();
    private final String AGE = String.valueOf((int) (Math.random() * (100 - 18)) + 18);
    private final String MESSAGE = faker.howIMetYourMother().catchPhrase();
    private final String EMAIL = BasePage.getUserEmail(NAME, SURNAME, AGE);
    private final String PHONE = "+1 234 567-89-00";
    private final String TEXT = faker.howIMetYourMother().quote();
    private final String SEND_MESSAGE = "form-message";
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
    @DisplayName("Should send new message")
    public void sendMessage() {
        myMainPage
                .clickOnSendMessage()
                .sendMessage(NAME, MESSAGE, EMAIL, PHONE, TEXT)
                .checkValidMessagesInForm(SEND_MESSAGE);
    }

    @Test
    @DisplayName("Should check validation errors in send message popup")
    public void checkValidationErrors() {
        myMainPage
                .clickOnSendMessage()

                .checkInputErrorInMessageForm("This field is required")
                .typeName("1")
                .checkInputErrorInMessageForm("This name is not valid")
                .typeName("a")
                .checkInputErrorInMessageForm("Your name is too short or too long")
                .typeName("qwertyuiopasdfghjklzx")
                .checkInputErrorInMessageForm("Your name is too short or too long")
                .typeName(NAME)

                .checkInputErrorInMessageForm("This field is required")
                .typeMessage("1")
                .checkInputErrorInMessageForm("This message is not valid")
                .typeMessage("a")
                .checkInputErrorInMessageForm("Your message is too short or too long")
                .typeMessage("qwertyuiopasdfghjklzx")
                .checkInputErrorInMessageForm("Your message is too short or too long")
                .typeMessage(MESSAGE)

                .checkInputErrorInMessageForm("This field is required")
                .typeMessageEmail("1")
                .checkInputErrorInMessageForm("Please enter a valid email address (your entry is not in the format \"somebody@example.com\")")
                .typeMessageEmail(EMAIL)

                .checkInputErrorInMessageForm("This field is required")
                .typePhone("1")
                .checkInputErrorInMessageForm("Please enter a valid phone number")
                .typePhone(PHONE)

                .checkTextAreaErrorInMessageForm("This field is required")
                .typeTextMessage("1")
                .checkTextAreaErrorInMessageForm("Your message is too short")
                .typeTextMessage(TEXT)

                .clickOnConfirmSendMessage()
                .checkConsentError(SEND_MESSAGE);
    }

    @After
    public void quit() {
        driver.quit();
    }
}
