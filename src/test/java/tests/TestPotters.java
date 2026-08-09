package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import enums.Account;
import heplers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.BankingPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.webdriver;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

public class TestPotters extends TestBase {
    BankingPage bankingPage = new BankingPage();

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;

        // Считываем переменную, переданную через -Dselenide.remote=...
        String remoteUrl = System.getProperty("selenide.remote");

        // Если переменная передана (например, в Jenkins или GitHub Actions) — подключаем Selenoid
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            Configuration.remote = remoteUrl;

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

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

    @Tag("demoqa")
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

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("LastScreenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

//        Selenide.closeWebDriver();
    }
}
