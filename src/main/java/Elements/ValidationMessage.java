package Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ValidationMessage {
    public static List<String> getValidMessages(WebDriver driver, String formClassName) {
        List<String> textMessages = new ArrayList<String>();
        List<WebElement> messages = driver.findElements(By.xpath("//form[@name='" + formClassName + "']//div[@class='valid-feedback']"));
        for (WebElement message : messages) {
            String textMessage = message.getAttribute("innerHTML");
            textMessages.add(textMessage);
        }
        return textMessages;
    }

    public static String getIvnalidMessage(WebDriver driver, String formClassName) {
        WebElement message = driver.findElement(By.xpath("//form[@name='" + formClassName + "']//div[@class='invalid-feedback']"));
        String textMessage = message.getAttribute("innerHTML");
        return textMessage;
    }
}
