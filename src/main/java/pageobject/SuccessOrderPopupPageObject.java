package pageobject;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static helpers.Constants.WAITING_TIMEOUT;


public class SuccessOrderPopupPageObject extends PageObjectBase {

    @FindBy(xpath = ".//div[text() = 'Заказ оформлен']")
    private WebElement successOrderPopupTitle;


    @FindBy(xpath = ".//div[@class='Order_Text__2broi']")
    private WebElement successOrderPopupMessage;


    @FindBy(xpath = ".//button[text() = 'Посмотреть статус']")
    private WebElement checkStatusButton;

    public SuccessOrderPopupPageObject(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void waiteUntilSuccessOrderPopupWillDisplayed() {
        new WebDriverWait(driver, WAITING_TIMEOUT).until(driver -> (
                successOrderPopupTitle.isDisplayed()));
    }


    public boolean checkIsSuccessOrderPopupWillBeDisplayed() {
        return successOrderPopupTitle.isDisplayed();
    }


    public String getOrderNumber() {
        try {
            return StringUtils.substringBetween(successOrderPopupMessage.getText(), ": ", ".");
        } catch (Exception e) {
            return "";
        }
    }


    public void clickToCheckOrderStatusButton() {
        checkStatusButton.click();
    }
}