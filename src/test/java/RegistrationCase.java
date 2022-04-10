import Actions.PrepareDriver;
import Pages.BasePage;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class RegistrationCase {
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private String EMAIL = BasePage.getRandomLogin();
    private final String NAME = "Tom";
    private final String SURNAME = "Anderson";
    private final String PASSWORD = "12345678";
    private final String LOCATION = "New York City";
    private final String AGE = "35";
    private final String REGISTER = "form-register";
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
    public void registerNewUser(){
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .checkValidMessagesInForm(REGISTER)
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .confirmSavedValues(NAME, SURNAME, EMAIL, LOCATION, AGE)
                .checkUrlIsValid(URL + "pages/profile/")
                .clickOnSignOut()
                .checkUrlIsValid(URL);
    }

    @Test
    public void checkValidationErrorsOnRegister(){
        myMainPage
                .clickOnRegister()
                .checkErrorInRegistrationForm("This field is required")
                .typeRegisterEmail("1")
                .checkErrorInRegistrationForm("Please enter a valid email address (your entry is not in the format \"somebody@example.com\")")
                .typeRegisterEmail(EMAIL)

                .checkErrorInRegistrationForm("This field is required")
                .typeRegisterName("1")
                .checkErrorInRegistrationForm("This name is not valid")
                .typeRegisterName("a")
                .checkErrorInRegistrationForm("Your name is too short or too long")
                .typeRegisterName("qwertyuiopasdfghjklzx")
                .checkErrorInRegistrationForm("Your name is too short or too long")
                .typeRegisterName(NAME)

                .checkErrorInRegistrationForm("This field is required")
                .typeRegisterSurname("1")
                .checkErrorInRegistrationForm("This surname is not valid")
                .typeRegisterSurname("a")
                .checkErrorInRegistrationForm("Your surname is too short or too long")
                .typeRegisterSurname("qwertyuiopasdfghjklzx")
                .checkErrorInRegistrationForm("Your surname is too short or too long")
                .typeRegisterSurname(SURNAME)

                .checkErrorInRegistrationForm("This field is required")
                .typeRegisterPassword("1")
                .checkErrorInRegistrationForm("Password must be between 3 and 20 characters")
                .typeRegisterPassword("1234567890asdfghjklzx")
                .checkErrorInRegistrationForm("Password must be between 3 and 20 characters")
                .typeRegisterPassword(PASSWORD)

                .checkErrorInRegistrationForm("This field is required")
                .typeRegisterRepeatPassword("1")
                .checkErrorInRegistrationForm("Password mismatch")
                .typeRegisterRepeatPassword(PASSWORD)

                .checkErrorInRegistrationForm("This field is required")
                .typeRegisterLocation("1")
                .checkErrorInRegistrationForm("This location is not valid")
                .typeRegisterLocation("a")
                .checkErrorInRegistrationForm("Location name is too short or too long")
                .typeRegisterLocation("united states of america state california")
                .checkErrorInRegistrationForm("Location name is too short or too long")
                .typeRegisterLocation(LOCATION)

                .typeRegisterAge("17")
                .checkErrorInRegistrationForm("This age is not valid")
                .typeRegisterAge("101")
                .checkErrorInRegistrationForm("This age is not valid")
                .typeRegisterAge(AGE)

                .clickOnSignUp()
                .checkConsentError(REGISTER);
    }

    @After
    public void quit(){
        driver.quit();
    }
}
