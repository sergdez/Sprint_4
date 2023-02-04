package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import static helpers.Constants.WAITING_TIMEOUT;

public class QuestionPageObject extends PageObjectBase {
    private static final String ACCORDION_HEADING_ID_PART = "accordion__heading-";
    private static final String ACCORDION_PANEL_ID_PART = "accordion__panel-";

    // Заголовок секции вопросов о важном
    private By questionTitle =
            By.xpath(".//div[@class='Home_FourPart__1uthg']/div[@class='Home_SubHeader__zwi_E']");

    // Аккордион секции о важном
    private By questionAccordionComponent = By.className("accordion");

    // Локатор получения элементов аккордиона
    private By accordionItems = By.xpath(".//div[@data-accordion-component='AccordionItem']");


    private By getAccordionHeadingByIndex(int index) {
        return By.id(ACCORDION_HEADING_ID_PART + index);
    }


    private By getAccordionPanelByIndex(int index) {
        return By.id(ACCORDION_PANEL_ID_PART + index);
    }


    public QuestionPageObject(WebDriver driver) {
        super(driver);
    }


    public int getQuestionsCount() {
        return driver.findElements(accordionItems).size();
    }


    public String getQuestionTitleText() {
        return driver.findElement(questionTitle).getText();
    }


    public String getQuestionTextByIndex(int index) {
        return driver.findElement(getAccordionHeadingByIndex(index)).getText();
    }


    public String getQuestionAnswerByIndex(int index) {
        return driver.findElement(getAccordionPanelByIndex(index)).getText();
    }


    public void clickToAccordionHeading(int index) {
        driver.findElement(getAccordionHeadingByIndex(index)).click();
    }


    public  void waitUntilHomePageWillBeLoaded() {
        new WebDriverWait(driver, WAITING_TIMEOUT).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }


    public void waitForAnswerDisplayed(int index) {
        new WebDriverWait(driver, WAITING_TIMEOUT).until(driver -> (
                driver.findElement(getAccordionPanelByIndex(index)).isDisplayed()));
    }


    public boolean checkIsPanelClosed(int index) {
        return !driver.findElement(getAccordionPanelByIndex(index)).isDisplayed();
    }


    public boolean checkIsPanelOpen(int index) {
        return driver.findElement(getAccordionPanelByIndex(index)).isDisplayed();
    }


    public void scrollToQuestionSection() {
        WebElement element = driver.findElement(questionAccordionComponent);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }
}