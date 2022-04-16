package api_tests.newStrusture;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import api.pojos.CreateUserRequest;
import api.pojos.CreateUserResponse;
import api.pojos.UserPojo;
import selenium.Pages.BasePage;

import static api.steps.UserSteps.getUser;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.*;


public class UserApiCase {
    private String EMAIL = BasePage.getRandomLogin();
    private final String USER_ID = "246";
    private final String BASE_URL = "https://academy.directlinedev.com/api";

    private final RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    @Test
    @DisplayName("Should get user")
    public void getUserCase() {
        UserPojo user = getUser(USER_ID);

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
    public void createUserCase() {
        CreateUserRequest rq = new CreateUserRequest();
        rq.setEmail(EMAIL);
        rq.setLocation("New York");
        rq.setSurname("Anderson");
        rq.setName("Tom");
        rq.setPassword("12345678");
        rq.setAge(21);

        CreateUserResponse rs = given()
                .spec(REQ_SPEC)
                .basePath("/users")
                .body(rq)
                .when().post()
                .then().statusCode(200)
                .extract().jsonPath().getObject("data", CreateUserResponse.class);

        assertThat(rs).extracting(CreateUserResponse::getEmail).isEqualTo(rq.getEmail());
        assertThat(rs).extracting(CreateUserResponse::getLocation).isEqualTo(rq.getLocation());
        assertThat(rs).extracting(CreateUserResponse::getSurname).isEqualTo(rq.getSurname());
        assertThat(rs).extracting(CreateUserResponse::getName).isEqualTo(rq.getName());
        assertThat(rs).extracting(CreateUserResponse::getPassword).isEqualTo(rq.getPassword());
        assertThat(rs).extracting(CreateUserResponse::getAge).isEqualTo(rq.getAge());
    }
}