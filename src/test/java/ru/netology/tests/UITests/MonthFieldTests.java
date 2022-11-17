package ru.netology.tests.UITests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getInvalidMultipleDigitsMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверно указан срок действия карты"));
        //checking there is no other warnings
        purchasePage.getCardNumberField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getYearField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getCodeField().ancestor("span").sibling(0).shouldNot(Condition.exist);
    }

    @Test
        //checking field length limit
    void shouldCutExtraDigits() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getExtraDigitsMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field length limit sending short month
    void shouldWarnAboutShortMonth() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getInvalidOneDigitMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверный формат"));
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fillEmptyFieldsExceptMonth(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getMonthField().shouldBe(Condition.empty);
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //page should hide warning after fixing wrong value
        purchasePage.fixMonth(DataHelper.getValidMonth().getMonth());
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field doesn't accept chars
    void shouldNotEnterChars() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getInvalidMonthChars().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getMonthField().shouldBe(Condition.empty);
        purchasePage.getMonthField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

}
