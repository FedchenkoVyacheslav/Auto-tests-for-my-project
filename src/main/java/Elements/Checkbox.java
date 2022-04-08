package Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkbox {
    public static void checkCheckBox(WebDriver driver, String formName){
        if(!driver.findElement(By.xpath("//form[@name='" + formName + "']//label[contains(@class, 'form__checkbox-label')]")).isSelected())
            driver.findElement(By.xpath("//form[@name='" + formName + "']//label[contains(@class, 'form__checkbox-label')]")).click();
    }
}
