package ru.netology.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//for generating test data
public class DataHelper {

    //get approved card from list
    public static String getCard1Number() {
        return (")4444 4444 4444 4441");
    }

    //get declined card from list
    public static String getCard2Number() {
        return ("4444 4444 4444 4442");
    }

    //get right format card not from the list
    public static String getInvalidCardNotInList() {
        Faker faker = new Faker();
        String cardNumberNotInList;
        do {
            cardNumberNotInList = faker.finance().creditCard(CreditCardType.MASTERCARD);
        }
        while (cardNumberNotInList.equals("4444 4444 4444 4441") || cardNumberNotInList.equals("4444 4444 4444 4442"));
        return (cardNumberNotInList);
    }

    //get shorter card number, AMERICAN_EXPRESS card has only 15 digits
    public static String getInvalidShortCard() {
        return new Faker().finance().creditCard(CreditCardType.AMERICAN_EXPRESS);
    }

    //get longer card
    public static String getLongCard() {
        Faker faker = new Faker();
        return ("4444 4444 4444 4441" + faker.number().digit());//longer card
    }

    //get valid month then shift = 0, invalid month then shift > 12
    public static String getMonth(int shift) {
        int month = LocalDate.now().getMonthValue();
        if ((month + shift) > 12) {
            return String.valueOf(month + shift);
        } else {
            return (LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
        }
    }

    //get year
    public static String getYear(int shift) {
        return (LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy")));
    }

    //get owner, "en" for latin, "ru" for cyrillic
    public static String getOwner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return (faker.name().name());
    }

    //get special symbols for owner
    public static String getInvalidOwnerSpecialSymbols() {
        return ("%^&*");
    }

    //get valid but extra length owner; no more than 27 symbols are allowed by standards
    public static String getOwner27Chars() {
        Faker faker = new Faker(new Locale("en"));
        return (faker.letterify("???????????????????????????"));
    }

    //get digit value
    public static String getValueDigits(int length) {
        Faker faker = new Faker();
        return (faker.number().digits(length));
    }

    //get chars for digit fields
    public static String getInvalidValueChars() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.letterify("???????");
    }
}
