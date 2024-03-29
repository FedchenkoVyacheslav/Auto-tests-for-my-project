package ui.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[text()='Send message']")
    private WebElement sendMessageButton;
    @FindBy(xpath = "//input[@id='form-name-send-message']")
    private WebElement nameInput;
    @FindBy(xpath = "//input[@id='form-message-send-message']")
    private WebElement messageInput;
    @FindBy(xpath = "//input[@id='form-email-send-message']")
    private WebElement emailMessageInput;
    @FindBy(xpath = "//input[@id='form-phone-send-message']")
    private WebElement phoneInput;
    @FindBy(xpath = "//textarea[@id='form-discuss-send-message']")
    private WebElement textMessageInput;
    @FindBy(xpath = "//form[@name='form-message']//input[contains(@class, 'form__checkbox')]")
    private WebElement confirmCheckbox;
    @FindBy(xpath = "//button[text()='Send']")
    private WebElement confirmMessageButton;

    public MainPage clickOnSendMessage() {
        sendMessageButton.click();
        return this;
    }

    public MainPage clickOnConfirmSendMessage() {
        confirmMessageButton.click();
        return this;
    }

    public MainPage typeName(String name) {
        nameInput.sendKeys(name);
        return this;
    }

    public MainPage typeMessage(String message) {
        messageInput.sendKeys(message);
        return this;
    }

    public MainPage typeMessageEmail(String email) {
        emailMessageInput.sendKeys(email);
        return this;
    }

    public MainPage typePhone(String phone) {
        phoneInput.sendKeys(phone);
        return this;
    }

    public MainPage typeTextMessage(String textMessage) {
        textMessageInput.sendKeys(textMessage);
        return this;
    }

    public MainPage sendMessage(String name, String message, String email, String phone, String textMessage, boolean consent) {
        this.typeName(name);
        this.typeMessage(message);
        this.typeMessageEmail(email);
        this.typePhone(phone);
        this.typeTextMessage(textMessage);
        if(consent) this.checkConsentMessage("form-message");
        clickOnConfirmSendMessage();
        return this;
    }

    public MainPage checkInputErrorMessage(String message, boolean inputFlag, boolean consentCheck){
        if (consentCheck) {
            if (inputFlag) checkInputErrorInMessageForm(message);
            else checkTextAreaErrorInMessageForm(message);
        }
        return this;
    }

    public MainPage checkInputErrorInMessageForm(String message) {
        clickOnConfirmSendMessage();
        checkInvalidMessage("form-message", message);
        clearInvalidInput("form-message", "input");
        return this;
    }

    public MainPage checkTextAreaErrorInMessageForm(String message) {
        clickOnConfirmSendMessage();
        checkInvalidMessage("form-message", message);
        clearInvalidInput("form-message", "textarea");
        return this;
    }
}
