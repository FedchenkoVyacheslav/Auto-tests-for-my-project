package api.utils;

import api.pojos.MessageRequest;
import selenium.Pages.BasePage;

public class MessageGenerator {
    private static String EMAIL = BasePage.getRandomLogin("Bob", "Smith");

    public static MessageRequest sendNewMessage() {
        return MessageRequest.builder()
                .body(String.format("{\"name\":\"Bob\"," +
                                "\"message\":\"new message\"," +
                                "\"email\":\"%s\"," +
                                "\"phone\":\"89250663456\"," +
                                "\"acceptbutton\":\"yes\"," +
                                "\"messagetext\":\"hello world!\"}",
                                EMAIL))
                .to(EMAIL)
                .build();
    }
}
