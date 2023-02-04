package tests;

import com.github.javafaker.Faker;
import model.OrderModel;
import helpers.OrderButtonEnum;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import pageobject.ConfirmationPopupPageObject;
import pageobject.OrderPageObject;
import pageobject.SuccessOrderPopupPageObject;

import java.util.Locale;

import static helpers.Constants.BASE_URL;
import static helpers.DriverFactoryHelper.setupDriver;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class OrderSectionTest {
    private WebDriver driver;

    @Test
    @Parameters({"chrome", "firefox"})
    public void checkIsOrderButtonInHeaderSectionOpensOrderForm(String driverType) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        OrderPageObject orderPageObject = new OrderPageObject(driver);
        orderPageObject.waitUntilHomePageWillBeLoaded();

        orderPageObject.clickToCookieButton();

        orderPageObject.clickToOrderButtonInHeaderSection();

        orderPageObject.waiteUntilOrderFormWillDisplayed();

        assertTrue("Ожидалолсь, что форма оформления была отображена",orderPageObject.checkIsOrderFormOpen());
    }

    @Test
    @Parameters({"chrome", "firefox"})
    public void checkIsOrderButtonInHowItWorksSectionOpensOrderForm(String driverType) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        OrderPageObject orderPageObject = new OrderPageObject(driver);

        orderPageObject.waitUntilHomePageWillBeLoaded();

        orderPageObject.clickToCookieButton();

        orderPageObject.scrollToHowItWorksOrderButton();

        orderPageObject.clickToOrderButtonInHowItWorksSection();

        orderPageObject.waiteUntilOrderFormWillDisplayed();

        assertTrue("Ожидалолсь, что форма оформления была отображена",orderPageObject.checkIsOrderFormOpen());
    }

    @Test
    @Parameters({
            "chrome, headerOrderButton",
            "chrome, rentSectionOrderButton",
            "firefox, headerOrderButton",
            "firefox, rentSectionOrderButton"
    })
    public void fillTheFirstPartFormWithPositiveResult(String driverType, String buttonType) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        OrderPageObject orderPageObject = new OrderPageObject(driver);
        orderPageObject.waitUntilHomePageWillBeLoaded();

        orderPageObject.fillTheFirstPartOrderForm(generatePositiveDataOrder(), OrderButtonEnum.valueOf(buttonType));

        orderPageObject.waiteUntilRentFormWillDisplayed();

        assertTrue("Ожидалось, что будет отображена страница 'Про ренту'",
                orderPageObject.checkIsRentStepDisplayed());
    }

    @Test
    @Parameters({
            "chrome, headerOrderButton",
            "chrome, rentSectionOrderButton",
            "firefox, headerOrderButton",
            "firefox, rentSectionOrderButton"
    })
    public void fillTheFirstPartFormWithNegativeResult(String driverType, String buttonType) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        OrderPageObject orderPageObject = new OrderPageObject(driver);
        orderPageObject.waitUntilHomePageWillBeLoaded();

        orderPageObject.fillTheFirstPartOrderForm(generateNegativeDataOrder(),  OrderButtonEnum.valueOf(buttonType));

        assertTrue("Ожидалось, что будет отображена страница 'Для кого самокат'",
                orderPageObject.checkIsOrderFormOpen());
    }

    @Test
    @Parameters({
            "chrome, headerOrderButton",
            "chrome, rentSectionOrderButton",
            "firefox, headerOrderButton",
            "firefox, rentSectionOrderButton"
    })
    public void fillTheSecondPartFormWithPositiveResult(String driverType, String buttonType) {
        OrderModel orderModel = generatePositiveDataOrder();

        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        OrderPageObject orderPageObject = new OrderPageObject(driver);
        orderPageObject.waitUntilHomePageWillBeLoaded();

        orderPageObject.fillTheFirstPartOrderForm(orderModel, OrderButtonEnum.valueOf(buttonType));

        orderPageObject.fillTheSecondPartOrderForm(orderModel);

        orderPageObject.clickToOrderButtonOnRentSection();

        ConfirmationPopupPageObject confirmationPopupPageObject = new ConfirmationPopupPageObject(driver);

        confirmationPopupPageObject.waiteUntilOrderFormWillDisplayed();

        assertTrue("Ожидалось, что пупап подтверждения заказа отображен",
                confirmationPopupPageObject.isPopUpDisplayed());
    }

    @Test
    @Parameters({
            "chrome, headerOrderButton",
            "chrome, rentSectionOrderButton",
            "firefox, headerOrderButton",
            "firefox, rentSectionOrderButton"
    })
    public void fillTheSecondPartFormWithNegativeResult(String driverType, String buttonType) {
        OrderModel orderModel = generateNegativeDataOrderForSecondFormPart();

        driver = setupDriver(driverType);
        driver.get(BASE_URL);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        orderPageObject.waitUntilHomePageWillBeLoaded();

        orderPageObject.fillTheFirstPartOrderForm(orderModel, OrderButtonEnum.valueOf(buttonType));

        orderPageObject.fillTheSecondPartOrderForm(orderModel);

        orderPageObject.clickToOrderButtonOnRentSection();

        assertTrue("Ожидалось, что будет отображена страница 'Про Ренту'",
                orderPageObject.checkIsRentStepDisplayed());
    }

    @Test
    @Parameters({
            "chrome, headerOrderButton",
            "firefox, headerOrderButton",
            "chrome, rentSectionOrderButton",
            "firefox, rentSectionOrderButton"
    })
    public void checkPositiveScooterOrder(String driverType, String buttonType) {
        OrderModel orderModel = generatePositiveDataOrder();

        driver = setupDriver(driverType);
        driver.get(BASE_URL);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        orderPageObject.waitUntilHomePageWillBeLoaded();

        orderPageObject.fillTheFirstPartOrderForm(orderModel, OrderButtonEnum.valueOf(buttonType));

        orderPageObject.fillTheSecondPartOrderForm(orderModel);

        orderPageObject.clickToOrderButtonOnRentSection();

        ConfirmationPopupPageObject confirmationPopupPageObject = new ConfirmationPopupPageObject(driver);

        confirmationPopupPageObject.waiteUntilOrderFormWillDisplayed();

        confirmationPopupPageObject.clickYesButton();

        SuccessOrderPopupPageObject successOrderPopupPageObject = new SuccessOrderPopupPageObject(driver);

        successOrderPopupPageObject.waiteUntilSuccessOrderPopupWillDisplayed();

        assertTrue("Ожидалось, что пупап с сообщением об успешном заказе будет отображен",
                successOrderPopupPageObject.checkIsSuccessOrderPopupWillBeDisplayed());

        String orderNumber = successOrderPopupPageObject.getOrderNumber();
        assertFalse("Ожидалось, что в об успешном заказе будет указан код", orderNumber.isEmpty());
    }

    @After
    public void teardown() {
        driver.quit();
    }

    private OrderModel generatePositiveDataOrder() {
        Faker fake = new Faker(new Locale("ru"));
        return OrderModel
                .builder()
                .firstName(fake.name().firstName())
                .lastName(fake.name().lastName())
                .address(fake.address().streetAddress())
                .phone("+79995554433")
                .metroStation("5")
                .deliveryDate("15")
                .rentPeriod("четверо суток")
                .commentForCourier("Какой-то комментарий")
                .color("gray")
                .orderMonth("Next Month")
                .build();
    }
    private OrderModel generateNegativeDataOrder() {
        Faker fake = new Faker(new Locale("ru"));
        return OrderModel
                .builder()
                .firstName(fake.lorem().characters(2))
                .address("")
                .metroStation("")
                .lastName(fake.lorem().characters(150))
                .phone(fake.phoneNumber().subscriberNumber(70))
                .build();
    }

    private OrderModel generateNegativeDataOrderForSecondFormPart() {
        Faker fake = new Faker(new Locale("ru"));
        return OrderModel
                .builder()
                .firstName(fake.name().firstName())
                .lastName(fake.name().lastName())
                .address(fake.address().streetAddress())
                .phone("+79995554433")
                .metroStation("5")
                .deliveryDate("15")
                .rentPeriod("")
                .orderMonth("Previous Month")
                .build();
    }
}
