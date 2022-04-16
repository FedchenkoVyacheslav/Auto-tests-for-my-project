package selenium.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.logging.Level;

public class PrepareDriver {
    public static WebDriver driverInit(String browser) {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);

        WebDriver driver;
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
                desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
                driver = new ChromeDriver(desiredCapabilities);
                return driver;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:/firefoxdriver/geckodriver.exe");
                desiredCapabilities = DesiredCapabilities.firefox();
                desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
                driver = new FirefoxDriver(desiredCapabilities);
                return driver;
            default:
                throw new IllegalStateException("Unexpected value: " + browser);
        }
    }
}
