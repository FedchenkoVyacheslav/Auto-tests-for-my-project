package Pages;

import Elements.Checkbox;
import Elements.ValidationMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;

    public static String getRandomLogin() {
        String generatedString = RandomStringUtils.randomAlphabetic(7);
        return generatedString + "@gmail.com";
    }

    public BasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//input[@id='form-email-sing-in']")
    private WebElement emailInput;
    @FindBy(xpath = "//input[@id='form-password-sing-in']")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[contains(@class, 'button_modal-sing-in')]")
    private WebElement logInButton;
    @FindBy(xpath = "//a[contains(@class, 'header__nav-item header__profile') and text()='My profile']")
    private WebElement profileButton;
    @FindBy(xpath = "//button[text()='Sign in']")
    private WebElement signInButton;
    @FindBy(xpath = "//button[text()='Sign out']")
    private WebElement signOutButton;
    @FindBy(xpath = "//button[text()='Register']")
    private WebElement registerButton;
    @FindBy(xpath = "//input[@id='form-email-register']")
    private WebElement emailRegisterInput;
    @FindBy(xpath = "//input[@id='form-name-register']")
    private WebElement nameRegisterInput;
    @FindBy(xpath = "//input[@id='form-surname-register']")
    private WebElement surnameRegisterInput;
    @FindBy(xpath = "//input[@id='form-password-register']")
    private WebElement passwordRegisterInput;
    @FindBy(xpath = "//input[@id='form-password-repeat-register']")
    private WebElement passwordRepeatRegisterInput;
    @FindBy(xpath = "//input[@id='form-location-register']")
    private WebElement locationRegisterInput;
    @FindBy(xpath = "//input[@id='form-age-register']")
    private WebElement ageRegisterInput;
    @FindBy(xpath = "//button[text()='Sign up']")
    private WebElement signUpButton;

    public BasePage clickOnSignIn() {
        signInButton.click();
        return this;
    }

    public BasePage clickOnSignOut() {
        signOutButton.click();
        pause(1000);
        return this;
    }

    public BasePage clickOnLogIn() {
        logInButton.click();
        return this;
    }

    public BasePage clickOnRegister() {
        registerButton.click();
        return this;
    }

    public BasePage clickOnSignUp() {
        signUpButton.click();
        return this;
    }

    public BasePage typeEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public BasePage typePassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public BasePage typeRegisterEmail(String email) {
        emailRegisterInput.sendKeys(email);
        return this;
    }

    public BasePage typeRegisterName(String name) {
        nameRegisterInput.sendKeys(name);
        return this;
    }

    public BasePage typeRegisterSurname(String surname) {
        surnameRegisterInput.sendKeys(surname);
        return this;
    }

    public BasePage typeRegisterPassword(String password) {
        passwordRegisterInput.sendKeys(password);
        return this;
    }

    public BasePage typeRegisterRepeatPassword(String password) {
        passwordRepeatRegisterInput.sendKeys(password);
        return this;
    }

    public BasePage typeRegisterLocation(String location) {
        locationRegisterInput.sendKeys(location);
        return this;
    }

    public BasePage typeRegisterAge(String age) {
        ageRegisterInput.sendKeys(age);
        return this;
    }

    public BasePage loginWithCredential(String email, String password) {
        this.typeEmail(email);
        this.typePassword(password);
        clickOnLogIn();
        pause(2000);
        return this;
    }

    public BasePage registerUser(String email, String name, String surname, String password, String passwordRep, String location, String age) {
        this.typeRegisterEmail(email);
        this.typeRegisterName(name);
        this.typeRegisterSurname(surname);
        this.typeRegisterPassword(password);
        this.typeRegisterRepeatPassword(passwordRep);
        this.typeRegisterLocation(location);
        this.typeRegisterAge(age);
        this.checkConsentMessage("form-register");
        clickOnSignUp();
        pause(2000);
        return this;
    }

    public BasePage goToProfilePage() {
        profileButton.click();
        pause(2000);
        return this;
    }

    public BasePage checkConsentMessage(String formName) {
        Checkbox.checkCheckBox(driver, formName);
        return this;
    }

    public BasePage checkValidMessagesInForm(String formClassName) {
        List<String> messages = ValidationMessage.getValidMessages(driver, formClassName);
        assertTrue(messages.size() > 0);
        for (String message : messages) {
            assertEquals("All right", message);
        }
        return this;
    }

    public BasePage checkInvalidMessageOnLogin(String innerText) {
        String message = ValidationMessage.getIvnalidMessage(driver, "form-sing-in");
        assertEquals(innerText, message);
        return this;
    }

    public BasePage checkUrlIsValid(String url) {
        assertEquals(driver.getCurrentUrl(), url + "index.html");
        return this;
    }

    public BasePage pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
}
