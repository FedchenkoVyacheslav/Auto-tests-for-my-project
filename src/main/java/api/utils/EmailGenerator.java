package api.utils;

import api.pojos.EmailRequest;
import selenium.Pages.BasePage;

public class EmailGenerator {
    private static String EMAIL = BasePage.getRandomLogin();

    public static EmailRequest sendNewMessage() {
        return EmailRequest.builder()
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
