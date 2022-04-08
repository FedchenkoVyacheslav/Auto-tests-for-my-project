import Actions.PrepareDriver;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class LoginInCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private final String EMAIL = "t1@gmail.com";
    private final String PASSWORD = "12345678";
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
                .checkValidMessageOnLogin()
                .goToProfilePage()
                .checkUrlIsValid(URL + "pages/profile/")
                .clickOnSignOut()
                .checkUrlIsValid(URL);
    }

    @Test
    public void checkValidationErrorsOnLogin(){

    }

    @After
    public void quit(){
        driver.quit();
    }
}