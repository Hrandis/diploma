package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;
//tour page object
public class MainPage {
    public static ElementsCollection buttons = $$("button");

    //press purchase button method
    public PurchaseAndLoanPage directPurchase() {
        buttons.first().click();
        return new PurchaseAndLoanPage();
    }

    //press loan button method
    public PurchaseAndLoanPage loan() {
        buttons.last().click();
        return new PurchaseAndLoanPage();
    }
}
