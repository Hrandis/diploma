package ru.netology.tests.functional.mySQL;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.Queries;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.open;

public class MySQLPurchaseTests {
    PurchaseAndLoanPage purchasePage;

    @BeforeEach
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase(); //нажать кнопку "купить"
        //TODO сделать подключение к mySQL
    }

    @Test
    void shouldApprovePurchaseWithCard1() {
        purchasePage.fillEmptyFields(DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
    void shouldDenyPurchaseForCard2() {
        purchasePage.fillEmptyFields(DataHelper.getCard2Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    void shouldDenyPurchaseForCardNotFromList() {
        purchasePage.fillEmptyFields(DataHelper.getInvalidCardNotInList().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @AfterAll
    static void query(){
        Queries queries = new Queries();
        queries.mySQLLoanInfo();
    }
}
