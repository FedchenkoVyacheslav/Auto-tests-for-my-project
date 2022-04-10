import Actions.PrepareDriver;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

public class LoginInCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private final String EMAIL = "t1@gmail.com";
    private final String PASSWORD = "12345678";
    private final String SIGN_IN = "form-sing-in";
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
    public void loginLogoutToProfile(){
        myMainPage
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .checkValidMessagesInForm(SIGN_IN)
                .goToProfilePage()
                .checkUrlIsValid(URL + "pages/profile/")
                .clickOnSignOut()
                .checkUrlIsValid(URL);
    }

    @Test
    public void checkValidationErrorsOnLogin(){
        myMainPage
                .clickOnSignIn()
                .checkErrorInLoginForm("This field is required")
                .typeEmail("1")
                .checkErrorInLoginForm("Please enter a valid email address (your entry is not in the format \"somebody@example.com\")")
                .typeEmail("test@mail.com")
                .typePassword("1")
                .checkErrorInLoginForm("This combination, mail and password were not found!");
    }

    @After
    public void quit(){
        driver.quit();
    }
}