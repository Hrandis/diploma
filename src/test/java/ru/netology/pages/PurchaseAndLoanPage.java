package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

@Value
public class PurchaseAndLoanPage {

    private final SelenideElement cardNumberField = $("[placeholder=\"0000 0000 0000 0000\"]");
    private final SelenideElement monthField = $("[placeholder=\"08\"]");
    private final SelenideElement yearField = $("[placeholder=\"22\"]");
    private final SelenideElement ownerField = $("[placeholder=\"999\"]").ancestor("span", 3).preceding(0).find(By.cssSelector("input"));
    private final SelenideElement codeField = $("[placeholder=\"999\"]");
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement notificationOk = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");

    public void fillEmptyFields (String cardNumber, String month, String year, String owner, String code){
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }

    public void fillEmptyFieldsExceptCardNumber (String month, String year, String owner, String code){
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }
    public void fillEmptyFieldsExceptMonth (String cardNumber, String year, String owner, String code){
        cardNumberField.setValue(cardNumber);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }
    public void fillEmptyFieldsExceptYear (String cardNumber, String month, String owner, String code){
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        ownerField.setValue(owner);
        codeField.setValue(code);
        continueButton.click();
    }
    public void fillEmptyFieldsExceptOwner (String cardNumber, String month, String year, String code){
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        codeField.setValue(code);
        continueButton.click();
    }
    public void fillEmptyFieldsExceptCode (String cardNumber, String month, String year, String owner){
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        continueButton.click();
    }

    public void fixCardNumber(String cardNumber){
        cardNumberField.setValue(cardNumber);
        continueButton.click();
    }
    public void fixMonth(String month){
        monthField.doubleClick().sendKeys("BackSpace");
        monthField.setValue(month);
        continueButton.click();
    }
    public void fixYear(String year){
        yearField.doubleClick().sendKeys("BackSpace");
        yearField.setValue(year);
        continueButton.click();
    }
    public void fixOwner(String owner){
        ownerField.doubleClick().sendKeys("BackSpace");
        ownerField.setValue(owner);
        continueButton.click();
    }
    public void fixCode(String code){
        codeField.doubleClick().sendKeys("BackSpace");
        codeField.setValue(code);
        continueButton.click();
    }
}
