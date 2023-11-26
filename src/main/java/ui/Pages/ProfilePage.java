package ui.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

import static org.junit.Assert.*;

public class ProfilePage extends BasePage {
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
    @FindBy(xpath = "//input[@id='form-email-editing-data']")
    private WebElement newEmailInput;
    @FindBy(xpath = "//input[@id='form-name-editing-data']")
    private WebElement newNameInput;
    @FindBy(xpath = "//input[@id='form-surname-editing-data']")
    private WebElement newSurnameInput;
    @FindBy(xpath = "//input[@id='form-location-editing-data']")
    private WebElement newLocationInput;
    @FindBy(xpath = "//input[@id='form-age-editing-data']")
    private WebElement newAgeInput;
    @FindBy(xpath = "//input[@id='form-profile-picture-editing-data']")
    private WebElement avatarInput;
    @FindBy(xpath = "//button[text()='Submit changes']")
    private WebElement submitDataButton;

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

    public ProfilePage clickOnSubmitDataButton() {
        submitDataButton.click();
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

    public ProfilePage typeNewEmail(String email) {
        newEmailInput.sendKeys(email);
        return this;
    }

    public ProfilePage typeNewName(String name) {
        newNameInput.sendKeys(name);
        return this;
    }

    public ProfilePage typeNewSurname(String surmane) {
        newSurnameInput.sendKeys(surmane);
        return this;
    }

    public ProfilePage typeNewLocation(String location) {
        newLocationInput.sendKeys(location);
        return this;
    }

    public ProfilePage typeNewAge(String age) {
        newAgeInput.sendKeys(age);
        return this;
    }

    public ProfilePage addAvatar(String pathToFile) {
        File file = new File(pathToFile);
        avatarInput.sendKeys(file.getAbsolutePath());
        return this;
    }

    public ProfilePage changeData(String email, String name, String surname, String location, String age, String path) {
        this.typeNewEmail(email);
        this.typeNewName(name);
        this.typeNewSurname(surname);
        this.typeNewLocation(location);
        this.typeNewAge(age);
        this.addAvatar(path);
        clickOnSubmitDataButton();
        pause(3000);
        return this;
    }

    public ProfilePage changeData(String email, String name, String surname, String location, String age) {
        this.typeNewEmail(email);
        this.typeNewName(name);
        this.typeNewSurname(surname);
        this.typeNewLocation(location);
        this.typeNewAge(age);
        clickOnSubmitDataButton();
        pause(3000);
        return this;
    }

    public ProfilePage checkInputErrorInChangePasswordForm(String message) {
        clickOnChangePasswordModalButton();
        checkInvalidMessage("form-password-edit", message);
        clearInvalidInput("form-password-edit", "input");
        return this;
    }

    public ProfilePage checkInputErrorInChangeDataForm(String message) {
        clickOnSubmitDataButton();
        checkInvalidMessage("form-editing-data", message);
        clearInvalidInput("form-editing-data", "input");
        return this;
    }
}
