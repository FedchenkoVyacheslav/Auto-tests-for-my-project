package Pages;

import Elements.ValidationMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

import java.util.List;

public abstract class BasePage {
    private final WebDriver driver;

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

    public BasePage typeEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public BasePage typePassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public BasePage loginWithCredential(String email, String password) {
        this.typeEmail(email);
        this.typePassword(password);
        logInButton.click();
        pause(4000);
        return this;
    }

    public BasePage goToProfilePage() {
        profileButton.click();
        pause(2000);
        return this;
    }

    public BasePage checkValidMessageOnLogin() {
        List<String> messages = ValidationMessage.getValidMessages(driver, "form-sing-in");
        for (String message:messages) {
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
