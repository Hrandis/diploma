package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    public static ElementsCollection buttons = $$("button");

    public PurchaseAndLoanPage directPurchase() {
        buttons.first().click(); //нажать кнопку покупки
        return new PurchaseAndLoanPage();
    }
    public PurchaseAndLoanPage loan() {
        buttons.last().click(); //нажать кнопку кредита
        return new PurchaseAndLoanPage();
    }
}
