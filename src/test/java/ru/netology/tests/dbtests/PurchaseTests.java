package ru.netology.tests.dbtests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.additional.SQLQueries;
import ru.netology.data.CardInfo;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

//Purchase requests tests
public class PurchaseTests {
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
        purchasePage.fill(new CardInfo(DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
    //system should deny purchase for Card 2
    void shouldDenyPurchaseForCard2() {
        purchasePage.fill(new CardInfo(DataHelper.getCard2Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    //system should deny purchase for any other card
    void shouldDenyPurchaseForCardNotFromList() {
        purchasePage.fill(new CardInfo(DataHelper.getInvalidCardNotInList(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        purchasePage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @AfterAll
    //asking MYSQL for requests data
    static void query() {
        SQLQueries queries = new SQLQueries();
        queries.RequestInfo();
    }
}
