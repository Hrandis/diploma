package ru.netology.tests.functional.postgreSQL;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.additional.Queries;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

//Purchase requests tests
public class PostgreSQLPurchaseTests {
    PurchaseAndLoanPage purchasePage;

    @BeforeEach
    //open tour page and press purchase button
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        purchasePage = mainPage.directPurchase();
    }

    @Test
    //system should approve purchase for Card 1
    void shouldApprovePurchaseWithCard1() {
        purchasePage.fillEmptyFields(DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
    //system should deny purchase for Card 2
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
    //system should deny purchase for any other card
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
    //asking PostgreSQL for requests data
    static void query() {
        Queries queries = new Queries();
        queries.postgreSQLPurchaseInfo();
    }
}
