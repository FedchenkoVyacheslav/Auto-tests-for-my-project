package selenium.Pages;

import selenium.Elements.Checkbox;
import selenium.Elements.ValidationMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BasePage {
    protected final WebDriver driver;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    public static String getBirthYear(String age) {
        int year = Integer.parseInt(sdf.format(new Date())) - Integer.parseInt(age);
        return String.valueOf(year);
    }

    public static String getUserEmail(String name, String surname, String age) {
        return String.format("%s.%s.%s@gmail.com", name, surname, getBirthYear(age)).toLowerCase();
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
    @FindBy(xpath = "//nav//a[text()='My blog']")
    private WebElement blogButton;

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

    public ProfilePage goToProfilePage() {
        profileButton.click();
        pause(2000);
        return new ProfilePage(driver);
    }

    public BlogPage goToBlogPage() {
        blogButton.click();
        pause(2000);
        return new BlogPage(driver);
    }

    public BasePage checkConsentMessage(String formName) {
        Checkbox.checkCheckBox(driver, formName);
        return this;
    }

    public BasePage checkValidMessagesInForm(String formClassName) {
        List<String> messages = ValidationMessage.getValidMessages(driver, formClassName);
        for (String message : messages) {
            assertEquals("All right", message);
        }
        return this;
    }

    public BasePage checkUrlIsValid(String url) {
        assertEquals(driver.getCurrentUrl(), url + "index.html");
        return this;
    }

    public BasePage clearInvalidInput(String formName, String element) {
        switch (element) {
            case "input":
                driver.findElement(By.xpath("//form[@name='" + formName + "']//div[@class='invalid-feedback']/../input")).clear();
                return this;
            case "textarea":
                driver.findElement(By.xpath("//form[@name='" + formName + "']//div[@class='invalid-feedback']/../textarea")).clear();
                return this;
            default:
                throw new IllegalStateException("Unexpected value: " + element);
        }
    }

    public BasePage checkInvalidMessage(String formClass, String innerText) {
        String message = ValidationMessage.getIvnalidMessage(driver, formClass);
        assertEquals(innerText, message);
        return this;
    }

    public BasePage checkConsentError(String formClass) {
        WebElement invalidCheckbox = driver.findElement(By.xpath("//form[@name='" + formClass + "']//span[contains(@class, 'form__checkbox-indicator_bad')]"));
        assertNotNull(invalidCheckbox);
        return this;
    }

    public BasePage checkErrorInLoginForm(String message) {
        clickOnLogIn();
        checkInvalidMessage("form-sing-in", message);
        clearInvalidInput("form-sing-in", "input");
        return this;
    }

    public BasePage checkErrorInRegistrationForm(String message) {
        clickOnSignUp();
        checkInvalidMessage("form-register", message);
        clearInvalidInput("form-register", "input");
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
