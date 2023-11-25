import ui.Pages.BasePage;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static api.utils.UserGenerator.faker;

public class testData {
    public static final String NAME = faker.name().firstName();
    public static final String SURNAME = faker.name().lastName();
    public static final String PASSWORD = faker.internet().password();
    public static final String LOCATION = faker.address().city();
    public static final String AGE = String.valueOf((int) (Math.random() * (100 - 18)) + 18);
    public static final String EMAIL = BasePage.getUserEmail(NAME, SURNAME, AGE);

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
                Arguments.of(EMAIL, NAME, SURNAME, PASSWORD, LOCATION, AGE)
        );
    }

    private static Stream<Arguments> loginValidationTestData() {
        return Stream.of(
                Arguments.of("", "1", "This field is required"),
                Arguments.of("1", "1", "Please enter a valid email address (your entry is not in the format \"somebody@example.com\")"),
                Arguments.of("test@mail.com", "", "This field is required"),
                Arguments.of("test@mail.com", "1", "This combination, mail and password were not found!")
        );
    }
}
