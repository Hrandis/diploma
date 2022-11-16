package ru.netology.tests.UITests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

public class OwnerFieldTests {
    PurchaseAndLoanPage purchasePage;
    @BeforeEach
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase(); //нажать кнопку "купить"
    }
    @Test
    void shouldWarnAboutDigits(){
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
    void shouldWarnAboutCyrillic(){
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
    void shouldWarnAboutSpecialSymbols(){
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
    void shouldCutExtraChars(){
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getInvalidOwnerExtraLength().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getOwnerField().shouldHave(Condition.exactValue("еееееееееееееееееееееееееее"));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
    @Test
    void shouldWarnAboutEmptyField (){
        purchasePage.fillEmptyFieldsExceptOwner(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidCode().getCode());
        purchasePage.getOwnerField().shouldBe(Condition.empty);
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldBe(Condition.exist)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldClearWarningAfterFixingValue(){
        purchasePage.fillEmptyFields(
                DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getInvalidYearChars().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.fixOwner(DataHelper.getValidOwner().getOwner());
        purchasePage.getOwnerField().ancestor("span").sibling(0).shouldNot(Condition.exist);
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }
}
