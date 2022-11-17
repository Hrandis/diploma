package ru.netology.tests.UITests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getInvalidOwnerDigits().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Введите имя владельца латиницей")); //the exact phrase is unknown
        //checking there is no other warnings
        purchasePage.getCardNumberField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getYearField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getCodeField().ancestor("span").sibling(0).shouldNot(Condition.exist);
    }

    @Test
        //checking field doesn't accept cyrillic
    void shouldWarnAboutCyrillic() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getInvalidOwnerCyrillic().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Введите имя владельца латиницей"));
    }

    @Test
        //checking field doesn't accept special symbols
    void shouldWarnAboutSpecialSymbols() {
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getInvalidOwnerSpecialSymbols().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Введите имя владельца латиницей"));
    }

    @Test
        //checking field length limit; no more than 27 symbols are allowed by standards
    void shouldCutExtraChars() {
        String owner = DataHelper.getOwner27Chars().getOwner();
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                owner + "extra",
                DataHelper.getValidCode().getCode());
        purchasePage.getOwnerField().shouldHave(Condition.exactValue(owner));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //checking warning for empty field case
    void shouldWarnAboutEmptyField() {
        purchasePage.fillEmptyFieldsExceptOwner(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidCode().getCode());
        purchasePage.getOwnerField().shouldBe(Condition.empty);
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        //page should hide warning after fixing wrong value
        purchasePage.fixOwner(DataHelper.getValidOwner().getOwner());
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
