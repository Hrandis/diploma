package ru.netology.tests.UITests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

//GUI Year field tests
public class YearFieldTests {
    PurchaseAndLoanPage purchasePage;

    @BeforeEach
    //open tour page and press purchase button
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase();
    }

    @Test
    void shouldWarnAboutWrongEarlierYear() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getInvalidEarlierYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getYearField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Истёк срок действия карты"));
        //checking there is no other warnings
        purchasePage.getCardNumberField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getYearField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getCodeField().ancestor("span").sibling(0).shouldNot(Condition.exist);
    }

    @Test
    void shouldWarnAboutWrongLaterYear() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getInvalidLaterYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getYearField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверно указан срок действия карты"));
    }

    @Test
        //checking field length limit
    void shouldCutExtraDigits() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidExtraDigitsYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field length limit sending short year
    void shouldWarnAboutShortYear() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getInvalidOneDigitYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверный формат"));
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fillEmptyFieldsExceptYear(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getMonthField().shouldBe(Condition.empty);
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //page should hide warning after fixing wrong value
        purchasePage.fixYear(DataHelper.getValidYear().getYear());
        purchasePage.getYearField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field doesn't accept chars
    void shouldNotEnterChars() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getInvalidYearChars().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getMonthField().shouldBe(Condition.empty);
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}
