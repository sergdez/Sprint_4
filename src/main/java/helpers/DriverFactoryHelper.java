package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverHelper {
    public static WebDriver setupDriver(String driverType) {
        return driverType.equals("chrome")
                ? new ChromeDriver()
                : new FirefoxDriver();
    }
}
