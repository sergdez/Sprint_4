package PageElements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import static helpers.Const.WAITING_TIMEOUT;

public class ConfirmationPopupPageObject extends PageObjectBase {
    public PopupPage(WebDriver driver) {

        super(driver);
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = ".//div[@class='Order_Buttons__1xGrp']/button[text()='Да']")
    private WebElement yesButton;


    @FindBy(xpath = ".//div[@class='Order_Buttons__1xGrp']/button[text()='Нет']")
    private WebElement noButton;


    @FindBy(xpath = ".//div[@class='Order_Modal__YZ-d3']/" +
            "div[@class='Order_ModalHeader__3FDaJ' and text()='Хотите оформить заказ?']")
    private WebElement confirmationPopupTitle;


    public void waiteUntilOrderFormWillDisplayed() {
        new WebDriverWait(driver, WAITING_TIMEOUT).until(driver -> (
                confirmationPopupTitle.isDisplayed()));
    }


    public boolean isPopUpDisplayed() {
        return confirmationPopupTitle.isDisplayed();
    }


    public void clickYesButton() {
        yesButton.click();
    }


    public void clickNoButton() {
        noButton.click();
    }

}