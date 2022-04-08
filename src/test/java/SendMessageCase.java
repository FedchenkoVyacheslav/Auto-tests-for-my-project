import Actions.PrepareDriver;
import Pages.BasePage;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class SendMessageCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private final String NAME = "Tom";
    private final String MESSAGE = "Greetings";
    private String EMAIL = BasePage.getRandomLogin();
    private final String PHONE = "+1 234 567-89-00";
    private final String TEXT = "Nice to meet you!";
    private final String SEND_MESSAGE = "form-message";
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
    public void sendMessage(){
        myMainPage
                .clickOnSendMessage()
                .sendMessage(NAME, MESSAGE, EMAIL, PHONE, TEXT)
                .checkValidMessagesInForm(SEND_MESSAGE);
    }

    @Test
    public void checkValidationErrors(){
        myMainPage
                .clickOnSendMessage()
                .checkInvalidInput("This field is required")
                .typeName("1")
                .checkInvalidInput("This name is not valid")
                .typeName("a")
                .checkInvalidInput("Your name is too short or too long")
                .typeName("qwertyuiopasdfghjklzx")
                .checkInvalidInput("Your name is too short or too long")
                .typeName(NAME)
                .checkInvalidInput("This field is required")
                .typeMessage("1")
                .checkInvalidInput("This message is not valid")
                .typeMessage("a")
                .checkInvalidInput("Your message is too short or too long")
                .typeMessage("qwertyuiopasdfghjklzx")
                .checkInvalidInput("Your message is too short or too long")
                .typeMessage(MESSAGE)
                .checkInvalidInput("This field is required")
                .typeMessageEmail("1")
                .checkInvalidInput("Please enter a valid email address (your entry is not in the format \"somebody@example.com\")")
                .typeMessageEmail(EMAIL)
                .checkInvalidInput("This field is required")
                .typePhone("1")
                .checkInvalidInput("Please enter a valid phone number")
                .typePhone(PHONE)
                .checkInvalidTextArea("This field is required")
                .typeTextMessage("1")
                .checkInvalidTextArea("Your message is too short")
                .typeTextMessage(TEXT)
                .clickOnConfirmSendMessage()
                .checkConsentError();
    }

    @After
    public void quit(){
        driver.quit();
    }
}
