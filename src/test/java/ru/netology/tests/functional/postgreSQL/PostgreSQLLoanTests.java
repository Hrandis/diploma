package ru.netology.tests.functional.postgreSQL;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import ru.netology.Queries;
import ru.netology.data.DataHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchaseAndLoanPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

public class PostgreSQLLoanTests {
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
        loanPage.fillEmptyFields(DataHelper.getCard1Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        loanPage.getNotificationOk().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Операция одобрена Банком."));
    }

    @Test
    //system should deny loan for Card 2
    void shouldDenyLoanForCard2() {
        loanPage.fillEmptyFields(DataHelper.getCard2Number().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        loanPage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    //system should deny loan for any other card
    void shouldDenyLoanForCardNotFromList() {
        loanPage.fillEmptyFields(DataHelper.getInvalidCardNotInList().getCardNumber(),
                DataHelper.getValidMonth().getMonth(),
                DataHelper.getValidYear().getYear(),
                DataHelper.getValidOwner().getOwner(),
                DataHelper.getValidCode().getCode());
        loanPage.getNotificationError().shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));
    }

    @AfterAll
    //asking PostgreSQL for requests data
    static void query() {
        Queries queries = new Queries();
        queries.postgreSQLLoanInfo();
    }
}
