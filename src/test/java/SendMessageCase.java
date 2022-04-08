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

    @After
    public void quit(){
        driver.quit();
    }
}
