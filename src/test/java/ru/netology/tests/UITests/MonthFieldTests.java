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

//GUI Month field tests
public class MonthFieldTests {
    PurchaseAndLoanPage purchasePage;

    @BeforeEach
    //open tour page and press purchase button
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase();
    }

    @Test
        //should accept only month from 01 to 12
    void shouldWarnAboutWrongMonth() {
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number(),
                DataHelper.getMonth(50),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getMonthFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверно указан срок действия карты"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field length limit
    void shouldCutExtraDigits() {
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number(),
                DataHelper.getMonth(0) + DataHelper.getValueDigits(1),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field length limit sending short month
    void shouldWarnAboutShortMonth() {;
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number(),
                DataHelper.getValueDigits(1),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getMonthFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверный формат"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number(),
                null,
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getMonthField().shouldBe(Condition.empty);
        purchasePage.getMonthFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field doesn't accept chars
    void shouldNotEnterChars() {
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number(),
                DataHelper.getInvalidValueChars(),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getMonthField().shouldBe(Condition.empty);
        purchasePage.getMonthFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //page should hide warning after fixing wrong value
    void shouldClearWarningAfterFixingValue() {
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number(),
                null,
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getMonthFieldWarn().shouldBe(Condition.exist);
        //fixing value
        purchasePage.fix(DataHelper.getMonth(0), purchasePage.getMonthField());
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

}
