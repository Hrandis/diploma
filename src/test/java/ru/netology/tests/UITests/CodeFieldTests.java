package ru.netology.tests.UITests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getInvalidTwoDigitsCode().getCode());
        purchasePage.getCodeField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Неверный формат"));
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
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getMultipleDigitsCode().getCode());
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking field doesn't accept chars
    void shouldNotEnterChars() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getInvalidCodeChars().getCode());
        purchasePage.getCodeField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fillEmptyFieldsExceptCode(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner());
        purchasePage.getCodeField().shouldBe(Condition.empty);
        purchasePage.getCodeField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //page should hide warning after fixing wrong value
        purchasePage.fixCode(DataHelper.getValidCode().getCode());
        purchasePage.getCodeField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
