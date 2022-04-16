package api_tests.newStrusture;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pojos.CreateUserRequest;
import pojos.CreateUserResponse;
import pojos.UserPojo;
import selenium.Pages.BasePage;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.*;


public class UserApiCase {
    private String EMAIL = BasePage.getRandomLogin();
    private final String BASE_URL = "https://academy.directlinedev.com/api";

    @Test
    @DisplayName("Should get user")
    public void getUser() {
        UserPojo user = given()
                .baseUri(BASE_URL)
                .basePath("/users/{id}")
                .pathParam("id", "246")
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then().statusCode(200)
                .extract().jsonPath().getObject("data", UserPojo.class);

        assertThat(user).extracting(UserPojo::getId).isEqualTo(246);
        assertThat(user).extracting(UserPojo::getEmail).isEqualTo("g1@gmail.com");
        assertThat(user).extracting(UserPojo::getLocation).isEqualTo("Los Angeles");
        assertThat(user).extracting(UserPojo::getSurname).isEqualTo("Smith");
        assertThat(user).extracting(UserPojo::getName).isEqualTo("Will");
        assertThat(user).extracting(UserPojo::getPassword).isEqualTo("12345678");
        assertThat(user).extracting(UserPojo::getAge).isEqualTo(30);
    }

    @Test
    @DisplayName("Should create new user")
    public void createUser() {
        CreateUserRequest rq = new CreateUserRequest();
        rq.setEmail(EMAIL);
        rq.setLocation("New York");
        rq.setSurname("Anderson");
        rq.setName("Tom");
        rq.setPassword("12345678");
        rq.setAge(21);

        CreateUserResponse rs = given()
                .baseUri(BASE_URL)
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(rq)
                .when().post()
                .then().statusCode(200)
                .extract().jsonPath().getObject("data", CreateUserResponse.class);

        assertThat(rs).extracting(CreateUserResponse::getEmail).isEqualTo(EMAIL);
        assertThat(rs).extracting(CreateUserResponse::getLocation).isEqualTo("New York");
        assertThat(rs).extracting(CreateUserResponse::getSurname).isEqualTo("Anderson");
        assertThat(rs).extracting(CreateUserResponse::getName).isEqualTo("Tom");
        assertThat(rs).extracting(CreateUserResponse::getPassword).isEqualTo("12345678");
        assertThat(rs).extracting(CreateUserResponse::getAge).isEqualTo(21);
    }
}