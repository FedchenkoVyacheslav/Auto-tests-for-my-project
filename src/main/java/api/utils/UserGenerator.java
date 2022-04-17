package api.utils;

import api.pojos.UserRequest;
import selenium.Pages.BasePage;

public class UserGenerator {
    private static String EMAIL = BasePage.getRandomLogin();

    public static UserRequest getSimpleUser() {
        return UserRequest.builder()
                .email(EMAIL)
                .location("New York")
                .surname("Anderson")
                .name("Tom")
                .password("12345678")
                .age(21)
                .build();
    }
}
