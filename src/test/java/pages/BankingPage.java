package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class BankingPage {
    private final SelenideElement
            userSelect = $("#userSelect"),
            loginBtn = $(byTagAndText("button", "Login")),
            customerTitle = $(".fontBig"),
            accountDescription = $("div.center"),
            accountSelect = $(byName("accountSelect")),
            depositButton = $(byTagAndText("button", "Deposit")),
            depositInput = $("input[ng-model='amount']"),
            applyDepositButton = $("[name=myForm] button"),
            message = $(byText("Deposit Successful"));

    public BankingPage openCustomerPage() {
        open("/#/customer");
        return this;
    }

    public BankingPage selectCustomer(String accountName) {
        userSelect.selectOption(accountName);
        return this;
    }

    public BankingPage login() {
        loginBtn.click();
        return this;
    }

    public BankingPage depositButtonClick() {
        depositButton.click();
        return this;
    }

    public BankingPage checkCustomer(String accountName) {
        customerTitle.shouldHave(text(accountName));
        return this;
    }

    public BankingPage checkCurrency(String currency) {
        accountDescription.shouldHave(text(currency));
        return this;
    }

    public BankingPage checkBalance(int balance) {
        accountDescription.shouldHave(text("Balance : " + balance));
        return this;
    }

    public BankingPage checkAccountNumber(String accountNumber) {
        accountDescription.shouldHave(text("Account Number : " + accountNumber));
        return this;
    }

    public BankingPage accountSelect(String accountNumber) {
        accountSelect.selectOption(accountNumber);
        return this;
    }

    public BankingPage setDeposit(String amount) {
        depositInput.setValue(amount);
        return this;
    }

    public BankingPage clickApplyDepositButton() {
        applyDepositButton.click();
        return this;
    }

    public BankingPage checkSuccessDeposit() {
        message.shouldHave(text("Deposit Successful"));
        return this;
    }
}
