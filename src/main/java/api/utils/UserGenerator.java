package api.utils;

import api.pojos.UserRequest;
import selenium.Pages.BasePage;

public class UserGenerator {
    private static String EMAIL = BasePage.getRandomLogin("Tom", "Anderson");

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

    public static UserRequest getUpdatedUser() {
        return UserRequest.builder()
                .email("g1@gmail.com")
                .location("Los Angeles")
                .surname("Smith")
                .name("Will")
                .password("12345678")
                .age(30)
                .build();
    }

    public static UserRequest getUserWithEmail(String email) {
        return UserRequest.builder()
                .email(email)
                .location("New York")
                .surname("Anderson")
                .name("Tom")
                .password("12345678")
                .age(21)
                .build();
    }
}
