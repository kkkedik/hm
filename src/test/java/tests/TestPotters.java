package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import enums.Account;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.BankingPage;

import static com.codeborne.selenide.Selenide.webdriver;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

public class TestPotters extends TestBase {
    BankingPage bankingPage = new BankingPage();

    @ParameterizedTest
    @DisplayName("Успешная авторизация пользователя в системе")
    @ValueSource(strings = {
            "Hermoine Granger",
            "Harry Potter",
            "Ron Weasly",
            "Albus Dumbledore",
            "Neville Longbottom"
    })
    void customerSuccessLoginTest(String searchQuery) {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открываем страницу с выбором пользователя", () -> {
            bankingPage.openCustomerPage();
            addAttachment("Sourse", webdriver().driver().source());
        });
        step("Выбираем пользователя", () -> {
            bankingPage.selectCustomer(searchQuery);
        });
        step("Входим", () -> {
            bankingPage.login();
        });

        step("Проверяем пользователя", () -> {
            bankingPage.checkCustomer(searchQuery);
        });
    }

    @ParameterizedTest
    @DisplayName("Успешная авторизация пользователя в системе используя класс с методами")
    @ValueSource(strings = {
            "Hermoine Granger",
            "Harry Potter",
            "Ron Weasly",
            "Albus Dumbledore",
            "Neville Longbottom"
    })
    void testAnnotatedStep(String searchQuery) {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps(bankingPage);

        steps.openMainPage();
        steps.selectCustomer(searchQuery);
        steps.takeScreenshot();
        steps.login();
        steps.checkCustomer(searchQuery);

    }

    @ParameterizedTest
    @DisplayName("Успешная авторизация пользователя в системе")
    @EnumSource(Account.class)
    void customerSuccessLoginTest1(Account account) {
        SelenideLogger.addListener("allure", new AllureSelenide());
        bankingPage.openCustomerPage()
                .selectCustomer(account.getCustomerName())
                .login()
                .accountSelect(account.getAccountNumber())
                .checkCustomer(account.getCustomerName())
                .checkCurrency(account.getCurrency())
                .checkAccountNumber(account.getAccountNumber())
                .checkBalance(account.getCurrentAmount())
                .depositButtonClick()
                .setDeposit(account.getIncreaseAmount())
                .clickApplyDepositButton()
                .checkSuccessDeposit()
                .checkBalance(account.getFinalAmount());
    }
}
