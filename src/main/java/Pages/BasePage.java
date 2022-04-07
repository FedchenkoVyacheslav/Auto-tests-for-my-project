package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    private final WebDriver driver;

    public BasePage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//input[@id='form-email-sing-in']")
    private WebElement emailInput;
    @FindBy(xpath = "//input[@id='form-password-sing-in']")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[contains(@class, 'button_modal-sing-in')]")
    private WebElement confirmButton;
    @FindBy(xpath = "//a[contains(@class, 'header__nav-item header__profile') and text()='My profile']")
    private WebElement profileButton;

    public BasePage clickOnSignIn() {
        driver.findElement(By.xpath("//button[text()='Sign in']")).click();
        return this;
    }

    public void typeEmail (String email) {
        emailInput.sendKeys(email);
    }

    public void typePassword (String password) {
        passwordInput.sendKeys(password);
    }

    public BasePage loginWithCredential (String email, String password) {
        this.typeEmail(email);
        this.typePassword(password);
        confirmButton.click();
        return this;
    }

    public void goToProfilePage () {
        profileButton.click();
        new ProfilePage(driver);
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
