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
    String URL = "https://fedchenkovyacheslav.github.io/";
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
    public void main(){
        myMainPage
                .clickOnSignIn()
                .loginWithCredential("t1@gmail.com", "12345678")
                .pause(4000)
                .goToProfilePage();
        assertEquals(driver.getCurrentUrl(),"https://fedchenkovyacheslav.github.io/pages/profile/index.html");
    }

    @After
    public void quit(){
        driver.quit();
    }
}