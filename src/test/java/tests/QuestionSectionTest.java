package tests;

import helpers.Constants;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import pageobject.QuestionPageObject;

import static helpers.Constants.*;
import static helpers.DriverFactoryHelper.*;
import static org.junit.Assert.*;
@RunWith(JUnitParamsRunner.class)
public class QuestionSectionTest {
    private WebDriver driver;

    @Test
    @Parameters(method = "checkQuestionTitleSource")
    public void checkQuestionsSectionsTitle (String driverType, String expectedTitle) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        QuestionPageObject questionPageObject = new QuestionPageObject(driver);

        questionPageObject.waitUntilHomePageWillBeLoaded();

        questionPageObject.scrollToQuestionSection();

        String actualTitle = questionPageObject.getQuestionTitleText();

        assertEquals(String.format("Ожидаемое название секции  - \"%s\"," +
                        " фактическое название секции - \"%s\"", expectedTitle, actualTitle),
                expectedTitle, actualTitle);
    }

    @Test
    @Parameters(method = "checkQuestionsCountSource")
    public void checkIfQuestionPanelsOpen (String driverType, int expectedQuestionsCount) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        QuestionPageObject questionPageObject = new QuestionPageObject(driver);

        questionPageObject.waitUntilHomePageWillBeLoaded();

        questionPageObject.scrollToQuestionSection();

        int actualQuestionsCount = questionPageObject.getQuestionsCount();

        assertEquals(String.format("Ожидаемое количество вопросов - %d," +
                        " фактическое количество вопросов - %d", expectedQuestionsCount, actualQuestionsCount),
                expectedQuestionsCount, actualQuestionsCount);
    }

    @Test
    @Parameters(method = "getQuestionsAndAnswersTexts")
    public void checkQuestionAndAnswersTests (int index, String driverType, String expectedQuestion, String expectedAnswer ) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        QuestionPageObject questionPageObject = new QuestionPageObject(driver);

        questionPageObject.waitUntilHomePageWillBeLoaded();

        questionPageObject.clickToCookieButton();

        questionPageObject.scrollToQuestionSection();

        String actualQuestion = questionPageObject.getQuestionTextByIndex(index);

        questionPageObject.clickToAccordionHeading(index);

        questionPageObject.waitForAnswerDisplayed(index);

        String actualAnswer = questionPageObject.getQuestionAnswerByIndex(index);

        assertEquals(String.format("Ожидаемый вопрос  - \"%s\"," +
                        " фактический вопрос - \"%s\"", expectedQuestion , actualQuestion),
                expectedQuestion, actualQuestion);

        assertEquals(String.format("Ожидаемый ответ  - \"%s\"," +
                        " фактический ответ - \"%s\"", expectedAnswer , actualAnswer),
                expectedAnswer, actualAnswer);
    }

    @Test
    @Parameters(method="checkOpeningAndClosingAccordionPanelsSource")
    public void checkOpeningAndClosingQuestionAccordionPanelsByClickingToNextQuestion(int index, String driverType) {
        driver = setupDriver(driverType);
        driver.get(BASE_URL);

        QuestionPageObject questionPageObject = new QuestionPageObject(driver);

        questionPageObject.waitUntilHomePageWillBeLoaded();

        questionPageObject.clickToCookieButton();

        questionPageObject.scrollToQuestionSection();

        questionPageObject.clickToAccordionHeading(index);

        questionPageObject.waitForAnswerDisplayed(index);

        if (index == Constants.QUESTIONS_COUNT - 1) {
            questionPageObject.clickToAccordionHeading(0);
            questionPageObject.waitForAnswerDisplayed(0);

            assertTrue(String.format("Ожидалось, что элемент аккордеона под номером %d будет открыт", 0),
                    questionPageObject.checkIsPanelOpen(0));
            assertTrue(String.format( "Ожидалось, что элемент аккордеона под номером %d будет закрыт", index),
                    questionPageObject.checkIsPanelClosed(index));
            driver.quit();
        } else {
            questionPageObject.clickToAccordionHeading(index + 1);
            questionPageObject.waitForAnswerDisplayed(index + 1);
            assertTrue(String.format("Ожидалось, что элемент аккордеона под номером %d будет развернут", index + 1),
                    questionPageObject.checkIsPanelOpen(index + 1));
            assertTrue(String.format( "Ожидалось, что элемент аккордеона под номером %d будет закрыт", index), questionPageObject.checkIsPanelClosed(index));
        }
    }

    @After
    public void teardown() {driver.quit();}

    //region Question And Answers Factory
    private static Object[] checkQuestionsCountSource() {
        return new Object[] {
                new Object[] {"chrome", 8},
                new Object[] {"firefox", 8}
        };
    }
    private static Object[] checkQuestionTitleSource() {
        return new Object[] {
                new Object[]  {"chrome", "Вопросы о важном"},
                new Object[] {"firefox", "Вопросы о важном"},
        };
    }
    private static Object[] checkOpeningAndClosingAccordionPanelsSource() {
        return new Object[] {
                new Object[] {0, "chrome"},
                new Object[] {1, "chrome"},
                new Object[] {2, "chrome"},
                new Object[] {3, "chrome"},
                new Object[] {4, "chrome"},
                new Object[] {5, "chrome"},
                new Object[] {6, "chrome"},
                new Object[] {7, "chrome"},
                new Object[] {0, "firefox"},
                new Object[] {1, "firefox"},
                new Object[] {2, "firefox"},
                new Object[] {3, "firefox"},
                new Object[] {4, "firefox"},
                new Object[] {5, "firefox"},
                new Object[] {6, "firefox"},
                new Object[] {7, "firefox"},
        };
    }
    private static Object[] getQuestionsAndAnswersTexts() {
        return new Object[]{
                new Object[]{0, "chrome", "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. " +
                        "Оплата курьеру — наличными или картой."},
                new Object[]{1, "chrome", "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. " +
                                "Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},

                new Object[]{2, "chrome", "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. " +
                        "Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента," +
                        " когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, " +
                        "суточная аренда закончится 9 мая в 20:30."},
                new Object[]{3, "chrome", "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                new Object[]{4, "chrome", "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! " +
                        "Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},

                new Object[]{5, "chrome", "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — " +
                                "даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                new Object[]{6, "chrome", "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, " +
                                "объяснительной записки тоже не попросим. Все же свои."},
                new Object[]{7, "chrome", "Я живу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."},

                new Object[]{0, "firefox", "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. " +
                        "Оплата курьеру — наличными или картой."},
                new Object[]{1, "firefox", "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат." +
                                " Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},

                new Object[]{2, "firefox", "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. " +
                        "Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента," +
                        " когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, " +
                        "суточная аренда закончится 9 мая в 20:30."},
                new Object[]{3, "firefox", "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                new Object[]{4, "firefox", "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! " +
                        "Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},

                new Object[]{5, "firefox", "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — " +
                                "даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                new Object[]{6, "firefox", "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, " +
                                "объяснительной записки тоже не попросим. Все же свои."},
                new Object[]{7, "firefox", "Я живу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }
    //endregion
}