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

//GUI Code field tests
public class CodeFieldTests {
    PurchaseAndLoanPage purchasePage;

    @BeforeEach
    //open tour page and press purchase button
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase();
    }

    @Test
        //checking field length limit sending short code
    void shouldWarnAboutShortCode() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(2)));
        purchasePage.getCodeFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверный формат"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field length limit
    void shouldCutExtraDigits() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(4)));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field doesn't accept chars
    void shouldNotEnterChars() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getInvalidValueChars()));
        purchasePage.getCodeFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                null));
        purchasePage.getCodeField().shouldBe(Condition.empty);
        purchasePage.getCodeFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //page should hide warning after fixing wrong value
    void shouldClearWarningAfterFixingValue() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                null));
        purchasePage.getCodeFieldWarn().shouldBe(Condition.exist);
        //page should hide warning after fixing wrong value
        purchasePage.fix(DataHelper.getValueDigits(3), purchasePage.getCodeField());
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
