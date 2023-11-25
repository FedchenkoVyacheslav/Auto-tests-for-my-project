package api.utils;

import api.pojos.UserRequest;
import com.github.javafaker.Faker;
import ui.Pages.BasePage;

public class UserGenerator {
    public static Faker faker = new Faker();
    private static final String NAME = faker.name().firstName();
    private static final String SURNAME = faker.name().lastName();
    private static final String PASSWORD = faker.internet().password();
    private static final String AGE = String.valueOf((int) (Math.random() * (100 - 18)) + 18);
    private static final String LOCATION = faker.address().city();
    private static final String EMAIL = BasePage.getUserEmail(NAME, SURNAME, AGE);

    public static UserRequest getSimpleUser() {
        return UserRequest.builder()
                .email(EMAIL)
                .location(LOCATION)
                .surname(SURNAME)
                .name(NAME)
                .password(PASSWORD)
                .age(Integer.parseInt(AGE))
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
                .location(LOCATION)
                .surname(SURNAME)
                .name(NAME)
                .password(PASSWORD)
                .age(Integer.parseInt(AGE))
                .build();
    }
}
