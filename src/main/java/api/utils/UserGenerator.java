package api.utils;

import api.pojos.CreateUserRequest;
import selenium.Pages.BasePage;

public class UserGenerator {
    private static String EMAIL = BasePage.getRandomLogin();

    public static CreateUserRequest getSimpleUser() {
        return CreateUserRequest.builder()
                .email(EMAIL)
                .location("New York")
                .surname("Anderson")
                .name("Tom")
                .password("12345678")
                .age(21)
                .build();
    }
}
