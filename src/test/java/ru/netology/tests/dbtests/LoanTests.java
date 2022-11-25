package ru.netology.tests.dbtests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import ru.netology.additional.SQLQueries;
import ru.netology.data.CardInfo;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

//Loan requests tests
public class LoanTests {
    PurchaseAndLoanPage loanPage;

    @BeforeEach
    //open tour page and press loan button
    public void preps() {
        open("http://localhost:8080");
        MainPage mainPage = new MainPage();
        loanPage = mainPage.loan();
    }

    @Test
        //system should approve loan for Card 1
    void shouldApproveLoanWithCard1() {
        loanPage.fill(new CardInfo(DataHelper.getCard1Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        loanPage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
        //system should deny loan for Card 2
    void shouldDenyLoanForCard2() {
        loanPage.fill(new CardInfo(DataHelper.getCard2Number(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        loanPage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
        //system should deny loan for any other card
    void shouldDenyLoanForCardNotFromList() {
        loanPage.fill(new CardInfo(DataHelper.getInvalidCardNotInList(),
                DataHelper.getMonth(0),
                DataHelper.getYear(1),
                DataHelper.getOwner("en"),
                DataHelper.getValueDigits(3)));
        loanPage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @AfterAll
    //asking MYSQL for requests data
    static void query() {
        SQLQueries queries = new SQLQueries();
        queries.RequestInfo();
    }
}
