package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.*;

public class ProfilePage extends BasePage{
    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[contains(@class, 'profile__name')]")
    private WebElement profileName;
    @FindBy(xpath = "//span[contains(@class, 'profile__surname')]")
    private WebElement profileSurname;
    @FindBy(xpath = "//span[contains(@class, 'profile__email')]")
    private WebElement profileEmail;
    @FindBy(xpath = "//span[contains(@class, 'profile__location')]")
    private WebElement profileLocation;
    @FindBy(xpath = "//span[contains(@class, 'profile__age')]")
    private WebElement profileAge;
    @FindBy(xpath = "//div[@class='profile__list-content']//button[text()='Change password']")
    private WebElement changePasswordButton;
    @FindBy(xpath = "//button[text()='Change other data']")
    private WebElement changeDataButton;
    @FindBy(xpath = "//div[@class='profile__list-content']//button[text()='Delete account']")
    private WebElement deleteAccountButton;
    @FindBy(xpath = "//form[@name='form-password-edit']//button[text()='Change password']")
    private WebElement changePasswordButtonModal;
    @FindBy(xpath = "//div[contains(@class, 'form_modal-profile-delete')]//button[text()='Delete account']")
    private WebElement deleteAccountButtonModal;
    @FindBy(xpath = "//input[@id='form-old-password-edit']")
    private WebElement oldPasswordInput;
    @FindBy(xpath = "//input[@id='form-new-password-edit']")
    private WebElement newPasswordInput;
    @FindBy(xpath = "//input[@id='form-repeat-password-edit']")
    private WebElement newRepeatPasswordInput;

    public ProfilePage confirmSavedValues(String name, String surname, String email, String location, String age) {
        assertEquals(profileName.getText(), name);
        assertEquals(profileSurname.getText(), surname);
        assertEquals(profileEmail.getText(), email);
        assertEquals(profileLocation.getText(), location);
        assertEquals(profileAge.getText(), age);
        return this;
    }

    public ProfilePage clickOnChangePasswordButton() {
        changePasswordButton.click();
        return this;
    }

    public ProfilePage clickOnChangeDataButton() {
        changeDataButton.click();
        return this;
    }

    public ProfilePage clickOnDeleteAccountButton() {
        deleteAccountButton.click();
        return this;
    }

    public ProfilePage clickOnChangePasswordModalButton() {
        changePasswordButtonModal.click();
        return this;
    }

    public ProfilePage clickOnDeleteAccountModalButton() {
        deleteAccountButtonModal.click();
        pause(2000);
        return this;
    }

    public ProfilePage typeOldPassword(String oldPassword) {
        oldPasswordInput.sendKeys(oldPassword);
        return this;
    }

    public ProfilePage typeNewPassword(String newPassword) {
        newPasswordInput.sendKeys(newPassword);
        return this;
    }

    public ProfilePage typeNewPasswordRepeat(String newPasswordRepeat) {
        newRepeatPasswordInput.sendKeys(newPasswordRepeat);
        return this;
    }

    public ProfilePage changePassword(String oldPassword, String newPassword, String newPasswordRepeat) {
        this.typeOldPassword(oldPassword);
        this.typeNewPassword(newPassword);
        this.typeNewPasswordRepeat(newPasswordRepeat);
        clickOnChangePasswordModalButton();
        pause(2000);
        return this;
    }
}
