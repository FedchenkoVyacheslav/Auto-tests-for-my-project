package api.utils;

import api.pojos.MessageRequest;
import com.github.javafaker.Faker;
import selenium.Pages.BasePage;

public class MessageGenerator {
    static Faker faker = new Faker();
    private static String NAME = faker.name().firstName();
    private static String SURNAME = faker.name().lastName();
    private static String AGE = String.valueOf((int) (Math.random() * (100 - 18)) + 18);
    private static String EMAIL = BasePage.getUserEmail(NAME, SURNAME, AGE);

    public static MessageRequest sendNewMessage() {
        return MessageRequest.builder()
                .body(String.format("{\"name\":\"%s\"," +
                                "\"message\":\"new message\"," +
                                "\"email\":\"%s\"," +
                                "\"phone\":\"89250663456\"," +
                                "\"acceptbutton\":\"yes\"," +
                                "\"messagetext\":\"hello world!\"}",
                        NAME, EMAIL))
                .to(EMAIL)
                .build();
    }
}
