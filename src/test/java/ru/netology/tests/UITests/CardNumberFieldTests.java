package ru.netology.tests.UITests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

//GUI Card Number field tests
public class CardNumberFieldTests {
    PurchaseAndLoanPage purchasePage;

    @BeforeEach
    //open tour page and press purchase button
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase();
    }

    @Test
        //checking field length limit sending short card number
    void shouldWarnAboutShortCardNumber() {
        purchasePage.fillEmptyFields(
                DataHelper.getInvalidShortCard().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getCardNumberFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверный формат"));
        //checking there is no other warnings
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field length limit sending long card number
    void shouldCutExtraDigits() {
        purchasePage.fillEmptyFields(
                DataHelper.getLongCard().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field can fix format and enter spaces
    void shouldEnterSpaces() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber().trim(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fillEmptyFieldsExceptCardNumber(
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getCardNumberField().shouldBe(Condition.empty);
        purchasePage.getCardNumberFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //checking there is no other warnings
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field doesn't accept chars
    void shouldNotEnterChars() {
        purchasePage.fillEmptyFields(
                DataHelper.getInvalidCardChars().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getCardNumberField().shouldBe(Condition.empty);
        purchasePage.getCardNumberFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //checking there is no other warnings
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //page should hide warning after fixing wrong value
    void shouldClearWarningAfterFixingValue() {
        purchasePage.fillEmptyFields(
                DataHelper.getInvalidShortCard().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getCardNumberFieldWarn().shouldBe(Condition.exist);
        //fixing value
        purchasePage.fixCardNumber(DataHelper.getCard1Number().getCardNumber());
        purchasePage.getCardNumberFieldWarn().shouldBe(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
