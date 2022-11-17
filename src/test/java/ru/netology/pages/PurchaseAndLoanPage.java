package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

@Value
//one class for purchase and loan cases because they are the same
public class PurchaseAndLoanPage {
    SelenideElement cardNumberField = $("[placeholder=\"0000 0000 0000 0000\"]");
    SelenideElement monthField = $("[placeholder=\"08\"]");
    SelenideElement yearField = $("[placeholder=\"22\"]");
    SelenideElement ownerField = $("[placeholder=\"999\"]").ancestor("span", 3).preceding(0).find(By.cssSelector("input"));
    SelenideElement codeField = $("[placeholder=\"999\"]");
    SelenideElement continueButton = $(byText("Продолжить"));
    SelenideElement notificationOk = $(".notification_status_ok");
    SelenideElement notificationError = $(".notification_status_error");

    //fill all empty fields
    public void fillEmptyFields(String cardNumber, String month, String year, String owner, String code) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }

    //fill all empty fields except Card Number field
    public void fillEmptyFieldsExceptCardNumber(String month, String year, String owner, String code) {
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }

    //fill all empty fields except Month field
    public void fillEmptyFieldsExceptMonth(String cardNumber, String year, String owner, String code) {
        cardNumberField.setValue(cardNumber);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }

    //fill all empty fields except Year field
    public void fillEmptyFieldsExceptYear(String cardNumber, String month, String owner, String code) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }

    //fill all empty fields except Owner field
    public void fillEmptyFieldsExceptOwner(String cardNumber, String month, String year, String code) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        codeField.setValue(code);
        continueButton.click();
    }

    //fill all empty fields except Code field
    public void fillEmptyFieldsExceptCode(String cardNumber, String month, String year, String owner) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        continueButton.click();
    }

    //fill only Card Number field and press the Continue button
    public void fixCardNumber(String cardNumber) {
        cardNumberField.sendKeys(Keys.CONTROL, "A");
        cardNumberField.sendKeys(Keys.BACK_SPACE);
        cardNumberField.setValue(cardNumber);
        continueButton.click();
    }

    //clean and fill only Month field and press the Continue button
    public void fixMonth(String month) {
        monthField.doubleClick().sendKeys("BackSpace");
        monthField.setValue(month);
        continueButton.click();
    }

    //clean and fill only Year field and press the Continue button
    public void fixYear(String year) {
        yearField.doubleClick().sendKeys("BackSpace");
        yearField.setValue(year);
        continueButton.click();
    }

    //clean and fill only Year field and press the Continue button
    public void fixOwner(String owner) {
        ownerField.sendKeys(Keys.CONTROL, "A");
        ownerField.sendKeys("BackSpace");
        ownerField.setValue(owner);
        continueButton.click();
    }

    //clean and fill only Code field and press the Continue button
    public void fixCode(String code) {
        codeField.doubleClick().sendKeys("BackSpace");
        codeField.setValue(code);
        continueButton.click();
    }
}
