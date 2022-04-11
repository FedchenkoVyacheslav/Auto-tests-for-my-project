package api_tests;

import Pages.BasePage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class RestTest {
    private String EMAIL = BasePage.getRandomLogin();
    private final String BASE_URL = "https://academy.directlinedev.com/api";

    @Test
    public void createUser() {
        String requestBody = String.format("{\n" +
                "  \"email\": \"%s\",\n" +
                "  \"location\": \"New York\",\n" +
                "  \"surname\": \"Anderson\",\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"password\": \"12345678\",\n" +
                "  \"age\": 21 \n}", EMAIL);

        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(EMAIL, response.jsonPath().getString("data.email"));
        Assertions.assertEquals("New York", response.jsonPath().getString("data.location"));
        Assertions.assertEquals("Anderson", response.jsonPath().getString("data.surname"));
        Assertions.assertEquals("Tom", response.jsonPath().getString("data.name"));
        Assertions.assertEquals("12345678", response.jsonPath().getString("data.password"));
        Assertions.assertEquals(21, response.jsonPath().getInt("data.age"));
    }

    @Test
    public void getUser() {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/users/{id}")
                .pathParam("id", "246")
                .contentType(ContentType.JSON)
                .when().get()
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(246, response.jsonPath().getInt("data.id"));
        Assertions.assertEquals("t1@gmail.com", response.jsonPath().getString("data.email"));
        Assertions.assertEquals("Test", response.jsonPath().getString("data.location"));
        Assertions.assertEquals("Anderson", response.jsonPath().getString("data.surname"));
        Assertions.assertEquals("Tom", response.jsonPath().getString("data.name"));
        Assertions.assertEquals("87654321", response.jsonPath().getString("data.password"));
        Assertions.assertEquals(30, response.jsonPath().getInt("data.age"));
    }

    @Test
    public void loginUser() {
        String requestBody = "{\n" +
                "  \"email\": \"t1@gmail.com\",\n" +
                "  \"password\": \"87654321\"}";

        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/users/login")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(246, response.jsonPath().getInt("data.userId"));
        Assertions.assertNotNull(response.jsonPath().getString("data.token"));
    }
}
