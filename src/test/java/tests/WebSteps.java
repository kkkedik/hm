package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.BankingPage;

public class WebSteps {
    BankingPage bankingPage;

    public WebSteps(BankingPage bankingPage) {
        this.bankingPage = bankingPage;
    }

    @Step("Открываем страницу с выбором пользователя")
    public void openMainPage() {
        bankingPage.openCustomerPage();
    }

    @Step("Выбираем пользователя")
    public void selectCustomer(String searchQuery) {
        bankingPage.selectCustomer(searchQuery);
    }

    @Step("Проверяем пользователя")
    public void checkCustomer(String searchQuery) {
        bankingPage.checkCustomer(searchQuery);
    }

    @Step("Входим")
    public void login() {
        bankingPage.login();
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "pnd")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
