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

public class DeleteProfileCase {
    Faker faker = new Faker();
    static WebDriver driver;
    private final String URL = "https://fedchenkovyacheslav.github.io/";
    private String NAME = faker.name().firstName();
    private String SURNAME = faker.name().lastName();
    private String PASSWORD = faker.internet().password();
    private String LOCATION = faker.address().city();
    private String AGE = String.valueOf((int) (Math.random() * (100 - 18)) + 18);
    private String EMAIL = BasePage.getUserEmail(NAME, SURNAME, AGE);
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
    @DisplayName("Should delete user")
    public void deleteProfile(){
        myMainPage
                .clickOnRegister()
                .registerUser(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, AGE)
                .clickOnSignIn()
                .loginWithCredential(EMAIL, PASSWORD)
                .goToProfilePage()
                .clickOnDeleteAccountButton()
                .clickOnDeleteAccountModalButton()
                .checkUrlIsValid(URL)
                .clickOnSignIn()
                .typeEmail(EMAIL)
                .typePassword(PASSWORD)
                .checkErrorInLoginForm("This combination, mail and password were not found!");
    }

    @After
    public void quit(){
        driver.quit();
    }
}
