package Pages;

import Elements.Checkbox;
import Elements.ValidationMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    public MainPage checkConsentMessage() {
        Checkbox.checkCheckBox(driver, "form-message");
        return this;
    }

    public MainPage sendMessage(String name, String message, String email, String phone, String textMessage) {
        this.typeName(name);
        this.typeMessage(message);
        this.typeMessageEmail(email);
        this.typePhone(phone);
        this.typeTextMessage(textMessage);
        this.checkConsentMessage();
        clickOnConfirmSendMessage();
        return this;
    }

    public MainPage checkInvalidMessage(String innerText) {
        String message = ValidationMessage.getIvnalidMessage(driver, "form-message");
        assertEquals(innerText, message);
        return this;
    }

    public MainPage clearInvalidInput() {
        driver.findElement(By.xpath("//form[@name='form-message']//div[@class='invalid-feedback']/../input")).clear();
        return this;
    }

    public MainPage clearInvalidTextArea() {
        driver.findElement(By.xpath("//form[@name='form-message']//div[@class='invalid-feedback']/../textarea")).clear();
        return this;
    }

    public MainPage checkConsentError() {
        WebElement invalidCheckbox = driver.findElement(By.xpath("//form[@name='form-message']//span[contains(@class, 'form__checkbox-indicator_bad')]"));
        assertNotNull(invalidCheckbox);
        return this;
    }

    public MainPage checkInvalidInput(String message) {
        clickOnConfirmSendMessage();
        checkInvalidMessage(message);
        clearInvalidInput();
        return this;
    }

    public MainPage checkInvalidTextArea(String message) {
        clickOnConfirmSendMessage();
        checkInvalidMessage(message);
        clearInvalidTextArea();
        return this;
    }
}
