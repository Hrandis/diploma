package ru.netology.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//for generating test data
public class DataHelper {

    @Value
    // class for Card Number field values
    public static class CardNumber {
        private String cardNumber;
    }

    //get approved card from list
    public static CardNumber getCard1Number() {
        return new CardNumber("4444 4444 4444 4441");
    }

    //get declined card from list
    public static CardNumber getCard2Number() {
        return new CardNumber("4444 4444 4444 4442");
    }

    //get right format card not from the list
    public static CardNumber getInvalidCardNotInList() {
        Faker faker = new Faker();
        String cardNumberNotInList;
        do {
            cardNumberNotInList = faker.finance().creditCard(CreditCardType.MASTERCARD);
        }
        while (cardNumberNotInList.equals("4444 4444 4444 4441") || cardNumberNotInList.equals("4444 4444 4444 4442"));
        return new CardNumber(cardNumberNotInList);
    }

    //get shorter card number, AMERICAN_EXPRESS card has only 15 digits
    public static CardNumber getInvalidShortCard() {
        return new CardNumber(new Faker().finance().creditCard(CreditCardType.AMERICAN_EXPRESS));
    }

    //get longer card
    public static CardNumber getLongCard() {
        Faker faker = new Faker();
        return new CardNumber("4444 4444 4444 4441" + faker.number().digit());//longer card
    }

    //get chars for card number
    public static CardNumber getInvalidCardChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new CardNumber(faker.letterify("?????????"));//chars
    }

    @Value
    // class for Month field values
    public static class Month {
        private String month;
    }

    //get valid month, 00 format
    public static Month getValidMonth() {
        return new Month(LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM")));
    }

    //get invalid month, 00 format, ex. 80
    public static Month getInvalidMultipleDigitsMonth() {
        Faker faker = new Faker();
        String invalidMonth;
        do {
            invalidMonth = faker.number().digits(2);
        }
        while (invalidMonth.equals("10") || invalidMonth.equals("11") || invalidMonth.equals("12"));
        return new Month(invalidMonth);
    }

    //get invalid month, 0 format, ex. 8
    public static Month getInvalidOneDigitMonth() {
        Faker faker = new Faker();
        return new Month(faker.number().digit()); //вернуть номер месяца в формате 0
    }

    //get valid month with extra digit, 000 format, ex. 128
    public static Month getExtraDigitsMonth() {
        Faker faker = new Faker();
        return new Month(LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM")) + faker.number().digit());
    }

    //get chars for month
    public static Month getInvalidMonthChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new Month(faker.letterify("?????????")); //вернуть номер месяца буквами
    }

    @Value
    // class for Year field values
    public static class Year {
        private String year;
    }

    //get valid later year, 00 format, ex. 23
    public static Year getValidYear() {
        return new Year(LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")));
    }

    //get invalid earlier year, 00 format, ex. 20
    public static Year getInvalidEarlierYear() {
        return new Year(LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")));
    }

    //get invalid much later year, 00 format, ex. 80
    public static Year getInvalidLaterYear() {
        return new Year(LocalDate.now().plusYears(50).format(DateTimeFormatter.ofPattern("yy")));
    }

    //get invalid year, 0 format, ex. 8
    public static Year getInvalidOneDigitYear() {
        Faker faker = new Faker();
        return new Year(faker.number().digit());
    }

    //get valid year with extra digit, 000 format, ex. 248
    public static Year getValidExtraDigitsYear() {
        Faker faker = new Faker();
        return new Year(LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")) + faker.number().digit());
    }

    //get chars for year
    public static Year getInvalidYearChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new Year(faker.letterify("?????????")); //вернуть буквы
    }

    @Value
    // class for Owner field values
    public static class Owner {
        private String owner;
    }

    //get valid owner, latin
    public static Owner getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return new Owner(faker.name().name());
    }

    //get digits for owner
    public static Owner getInvalidOwnerDigits() {
        Faker faker = new Faker(new Locale("ru"));
        return new Owner(faker.number().digit());
    }

    //get cyrillic for owner
    public static Owner getInvalidOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return new Owner(faker.name().name());
    }

    //get special symbols for owner
    public static Owner getInvalidOwnerSpecialSymbols() {
        return new Owner("%^&*");
    }

    //get valid but extra length owner; no more than 27 symbols are allowed by standards
    public static Owner getOwner27Chars() {
        Faker faker = new Faker(new Locale("en"));
        return new Owner(faker.letterify("???????????????????????????"));
    }

    @Value
    // class for Code field values
    public static class Code {
        private String code;
    }

    //get valid code, 000 format, ex. 253
    public static Code getValidCode() {
        Faker faker = new Faker();
        return new Code(faker.number().digits(3));
    }

    //get invalid code, 00 format, ex. 25
    public static Code getInvalidTwoDigitsCode() {
        Faker faker = new Faker();
        return new Code(faker.number().digits(2));
    }

    //get invalid code, 0000 format, ex. 2556
    public static Code getMultipleDigitsCode() {
        Faker faker = new Faker();
        return new Code(faker.number().digits(4));
    }

    //get chars for code
    public static Code getInvalidCodeChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new Code(faker.letterify("???????")); //вернуть код буквами
    }
}
