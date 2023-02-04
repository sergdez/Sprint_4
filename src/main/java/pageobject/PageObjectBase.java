package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageObjectBase {
    protected final WebDriver driver;
    protected PageObjectBase(WebDriver driver) {
        this.driver = driver;
    }


    private By cookieButton = By.id("rcc-confirm-button");


    public void clickToCookieButton() {
        driver.findElement(cookieButton).click();
    }
}