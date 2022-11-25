package ru.netology.tests.UITests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
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
        purchasePage.fill(new CardInfo(DataHelper.getInvalidShortCard(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
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
        purchasePage.fill(new CardInfo(DataHelper.getLongCard(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field can fix format and enter spaces
    void shouldEnterSpaces() {
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number().trim(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fill(new CardInfo(null,
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
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
        purchasePage.fill(new CardInfo(DataHelper.getInvalidValueChars(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
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
        purchasePage.fill(new CardInfo(DataHelper.getInvalidShortCard(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getCardNumberFieldWarn().shouldBe(Condition.exist);
        //fixing value
        purchasePage.fix(DataHelper.getCard1Number(), purchasePage.getCardNumberField());
        purchasePage.getCardNumberFieldWarn().shouldNotBe(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
