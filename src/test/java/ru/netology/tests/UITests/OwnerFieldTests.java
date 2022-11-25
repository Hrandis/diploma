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

//GUI Owner field tests
public class OwnerFieldTests {
    PurchaseAndLoanPage purchasePage;

    @BeforeEach
    //open tour page and press purchase button
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase();
    }

    @Test
        //checking field doesn't accept digits
    void shouldWarnAboutDigits() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getValueDigits(10),
                DataHelper.getValueDigits(3)));
        purchasePage.getOwnerFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Введите имя владельца латиницей")); //the exact phrase is unknown
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field doesn't accept cyrillic
    void shouldWarnAboutCyrillic() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("ru"),
                DataHelper.getValueDigits(3)));
        purchasePage.getOwnerFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Введите имя владельца латиницей"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field doesn't accept special symbols
    void shouldWarnAboutSpecialSymbols() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getInvalidOwnerSpecialSymbols(),
                DataHelper.getValueDigits(3)));
        purchasePage.getOwnerFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Введите имя владельца латиницей"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //checking field length limit; no more than 27 symbols are allowed by standards
    void shouldCutExtraChars() {
        String owner = DataHelper.getOwner27Chars();
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                owner + "extra",
                DataHelper.getValueDigits(3)));
        purchasePage.getOwnerField().shouldHave(Condition.exactValue(owner));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                null,
                DataHelper.getValueDigits(3)));
        purchasePage.getOwnerField().shouldBe(Condition.empty);
        purchasePage.getOwnerFieldWarn().shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //checking there is no other warnings
        purchasePage.getCardNumberFieldWarn().shouldNot(Condition.exist);
        purchasePage.getYearFieldWarn().shouldNot(Condition.exist);
        purchasePage.getMonthFieldWarn().shouldNot(Condition.exist);
        purchasePage.getCodeFieldWarn().shouldNot(Condition.exist);
    }

    @Test
        //page should hide warning after fixing wrong value
    void shouldClearWarningAfterFixingValue() {
        purchasePage.fill(new CardInfo(
                DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                null,
                DataHelper.getValueDigits(3)));
        purchasePage.getOwnerFieldWarn().shouldBe(Condition.exist);
        //fixing value
        purchasePage.fix(DataHelper.getOwner("en"), purchasePage.getOwnerField());
        purchasePage.getOwnerFieldWarn().shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
