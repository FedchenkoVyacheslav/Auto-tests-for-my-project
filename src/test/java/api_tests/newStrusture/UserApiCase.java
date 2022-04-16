package api_tests.newStrusture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pojos.UserPojo;
import selenium.Pages.BasePage;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.*;


public class UserApiCase {
    private String EMAIL = BasePage.getRandomLogin();
    private final String BASE_URL = "https://academy.directlinedev.com/api";

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Should get user")
    public void shouldGetUserCase() {
        JsonNode userObject = given()
                .baseUri(BASE_URL)
                .basePath("/users/{id}")
                .pathParam("id", "246")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then().statusCode(200)
                .extract().jsonPath().getObject("data", JsonNode.class);

        UserPojo user = mapper.convertValue(userObject, UserPojo.class);

        assertThat(user).extracting(UserPojo::getId).isEqualTo(246);
        assertThat(user).extracting(UserPojo::getEmail).isEqualTo("g1@gmail.com");
        assertThat(user).extracting(UserPojo::getLocation).isEqualTo("Los Angeles");
        assertThat(user).extracting(UserPojo::getSurname).isEqualTo("Smith");
        assertThat(user).extracting(UserPojo::getName).isEqualTo("Will");
        assertThat(user).extracting(UserPojo::getPassword).isEqualTo("12345678");
        assertThat(user).extracting(UserPojo::getAge).isEqualTo(30);
    }
}