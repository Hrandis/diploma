package ru.netology.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {


    @Value
    public static class CardNumber {
        private String cardNumber;
    }
    public static CardNumber getCard1Number() {
        return new CardNumber("4444 4444 4444 4441");
    }
    public static CardNumber getCard2Number() {
        return new CardNumber("4444 4444 4444 4442");
    }
    public static CardNumber getInvalidCardNotInList() {
        Faker faker = new Faker();
        String cardNumberNotInList;
        do{cardNumberNotInList = faker.finance().creditCard(CreditCardType.MASTERCARD);}
        while (cardNumberNotInList.equals("4444 4444 4444 4441") || cardNumberNotInList.equals("4444 4444 4444 4442"));
        return new CardNumber(cardNumberNotInList);//вернуть карту в верном формате, но не из списка
    }
    public static CardNumber getInvalidShortCard() {
        return new CardNumber(new Faker().finance().creditCard(CreditCardType.AMERICAN_EXPRESS));//AMERICAN_EXPRESS card has only 15 digits
    }
    public static CardNumber getLongCard() {
        Faker faker = new Faker();
        return new CardNumber("4444 4444 4444 4441" + faker.number().digit());//вернуть карту больше лимита цифр
    }
    public static CardNumber getInvalidCardChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new CardNumber(faker.letterify("?????????"));//вернуть буквы
    }

    @Value
    public static class Month {
        private String month;
    }

    public static Month getValidMonth() {
        return new Month(LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"))); //вернуть номер месяца в формате 00
    }
    public static Month getInvalidMultipleDigitsMonth() {
        Faker faker = new Faker();
        String invalidMonth;
        do{invalidMonth = faker.number().digits(2);}
        while (invalidMonth.equals("10") || invalidMonth.equals("11")|| invalidMonth.equals("12"));
        return new Month(invalidMonth); //вернуть невалидный номер месяца в формате 00
    }
    public static Month getInvalidOneDigitMonth() {
        Faker faker = new Faker();
        return new Month(faker.number().digit()); //вернуть номер месяца в формате 0
    }
    public static Month getExtraDigitsMonth() {
        Faker faker = new Faker();
        return new Month(LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"))+faker.number().digit()); //вернуть номер месяца в формате 000 и больше
    }
    public static Month getInvalidMonthChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new Month(faker.letterify("?????????")); //вернуть номер месяца буквами
    }

    @Value
    public static class Year {
        private String year;
    }
    public static Year getValidYear() {
        return new Year(LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"))); //вернуть номер года в формате 00 не раньше текущего
    }
    public static Year getInvalidEarlierYear() {
        return new Year(LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"))); //вернуть номер года в формате 00 раньше текущего
    }
    public static Year getInvalidLaterYear() {
        return new Year(LocalDate.now().plusYears(50).format(DateTimeFormatter.ofPattern("yy"))); //вернуть номер года в формате 00 сильно позже текущего
    }
    public static Year getInvalidOneDigitYear() {
        Faker faker = new Faker();
        return new Year(faker.number().digit()); //вернуть номер года в формате 0
    }
    public static Year getValidExtraDigitsYear() {
        Faker faker = new Faker();
        return new Year(LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"))+faker.number().digit()); //вернуть номер года в формате 000
    }
    public static Year getInvalidYearChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new Year(faker.letterify("?????????")); //вернуть буквы
    }

    @Value
    public static class Owner {
        private String owner;
    }
    public static Owner getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return new Owner(faker.name().name()); //вернуть имя владельца
    }
    public static Owner getInvalidOwnerDigits() {
        Faker faker = new Faker(new Locale("ru"));
        return new Owner(faker.number().digit()); //вернуть цифры
    }
    public static Owner getInvalidOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return new Owner(faker.name().name()); //вернуть русские буквы
    }
    public static Owner getInvalidOwnerSpecialSymbols() {
        return new Owner("%^&*"); //вернуть спецсимволы
    }
    public static Owner getInvalidOwnerExtraLength() {
        Faker faker = new Faker(new Locale("en"));
        return new Owner(faker.letterify("??????????????????????????????")); //вернуть слишком длинное имя, более 27 символов
    }

    @Value
    public static class Code {
        private String code;
    }

    public static Code getValidCode() {
        Faker faker = new Faker();
        return new Code(faker.number().digits(3)); //вернуть код в формате 000
    }
    public static Code getInvalidOneOrTwoDigitsCode() {
        Faker faker = new Faker();
        return new Code(faker.number().digits(2)); //вернуть код в формате 00 или 0
    }
    public static Code getMultipleDigitsCode() {
        Faker faker = new Faker();
        return new Code(faker.number().digits(4)); //вернуть код в формате 0000 и больше
    }
    public static Code getInvalidCodeChars() {
        Faker faker = new Faker(new Locale("ru"));
        return new Code(faker.letterify("???????")); //вернуть код буквами
    }
}
