package ui;

import ui.Pages.BasePage;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static api.utils.UserGenerator.faker;

public class testData {
    public static final String URL = "https://fedchenkovyacheslav.github.io/";
    public static final String NAME = faker.name().firstName();
    public static final String SURNAME = faker.name().lastName();
    public static final String PASSWORD = faker.internet().password();
    public static final String LOCATION = faker.address().city();
    public static final String AGE = String.valueOf((int) (Math.random() * (100 - 18)) + 18);
    public static final String EMAIL = BasePage.getUserEmail(NAME, SURNAME, AGE);
    public static final String MESSAGE = faker.howIMetYourMother().catchPhrase();
    public static final String PHONE = "+1 234 567-89-00";
    public static final String TEXT = faker.howIMetYourMother().quote();
    public static final int[] ALL_TAGS = {1, 2, 3, 4, 5, 6, 7, 8};

    public static Stream<Arguments> registerValidationTestData() {
        return Stream.of(
                Arguments.of("", "", "", "", "", "", "", "This field is required"),
                Arguments.of("1", "", "", "", "", "", "", "Please enter a valid email address (your entry is not in the format \"somebody@example.com\")"),
                Arguments.of(EMAIL, "", "", "", "", "", "", "This field is required"),
                Arguments.of(EMAIL, "1", "", "", "", "", "", "This name is not valid"),
                Arguments.of(EMAIL, "a", "", "", "", "", "", "Your name is too short or too long"),
                Arguments.of(EMAIL, "qwertyuiopasdfghjklzx", "", "", "", "", "", "Your name is too short or too long"),
                Arguments.of(EMAIL, NAME, "", "", "", "", "", "This field is required"),
                Arguments.of(EMAIL, NAME, "1", "", "", "", "", "This surname is not valid"),
                Arguments.of(EMAIL, NAME, "a", "", "", "", "", "Your surname is too short or too long"),
                Arguments.of(EMAIL, NAME, "qwertyuiopasdfghjklzx", "", "", "", "", "Your surname is too short or too long"),
                Arguments.of(EMAIL, NAME, SURNAME, "", "", "", "", "This field is required"),
                Arguments.of(EMAIL, NAME, SURNAME, "1", "", "", "", "Password must be between 3 and 20 characters"),
                Arguments.of(EMAIL, NAME, SURNAME, "1234567890asdfghjklzx", "", "", "", "Password must be between 3 and 20 characters"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, "", "", "", "This field is required"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, "1", "", "", "Password mismatch"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, "", "", "This field is required"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, "1", "", "This location is not valid"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, "a", "", "Location name is too short or too long"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, "united states of america state california", "", "Location name is too short or too long"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, "17", "This age is not valid"),
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, PASSWORD, LOCATION, "101", "This age is not valid")
        );
    }

    public static Stream<Arguments> validRegisterData() {
        return Stream.of(
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, LOCATION, AGE, "This combination, mail and password were not found!")
        );
    }

    public static Stream<Arguments> validMessageData() {
        return Stream.of(
                Arguments.of(NAME, MESSAGE, EMAIL, PHONE, TEXT)
        );
    }

    public static Stream<Arguments> messageValidationTestData() {
        return Stream.of(
                Arguments.of("1", "", "", "", "", "This name is not valid", true),
                Arguments.of("a", "", "", "", "", "Your name is too short or too long", true),
                Arguments.of("qwertyuiopasdfghjklzx", "", "", "", "", "Your name is too short or too long", true),
                Arguments.of(NAME, "", "", "", "", "This field is required", true),
                Arguments.of(NAME, "1", "", "", "", "This message is not valid", true),
                Arguments.of(NAME, "a", "", "", "", "Your message is too short or too long", true),
                Arguments.of(NAME, "qwertyuiopasdfghjklzx", "", "", "", "Your message is too short or too long", true),
                Arguments.of(NAME, MESSAGE, "", "", "", "This field is required", true),
                Arguments.of(NAME, MESSAGE, "1", "", "", "Please enter a valid email address (your entry is not in the format \"somebody@example.com\")", true),
                Arguments.of(NAME, MESSAGE, EMAIL, "", "", "This field is required", true),
                Arguments.of(NAME, MESSAGE, EMAIL, "1", "", "Please enter a valid phone number", true),
                Arguments.of(NAME, MESSAGE, EMAIL, PHONE, "", "This field is required", false),
                Arguments.of(NAME, MESSAGE, EMAIL, PHONE, "1", "Your message is too short", false)
        );
    }

    public static Stream<Arguments> loginValidationTestData() {
        return Stream.of(
                Arguments.of("", "1", "This field is required"),
                Arguments.of("1", "1", "Please enter a valid email address (your entry is not in the format \"somebody@example.com\")"),
                Arguments.of("test@mail.com", "", "This field is required"),
                Arguments.of("test@mail.com", "1", "This combination, mail and password were not found!")
        );
    }
}
