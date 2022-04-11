package api_tests;

import Pages.BasePage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class UserApiCase {
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
        Assertions.assertEquals("g1@gmail.com", response.jsonPath().getString("data.email"));
        Assertions.assertEquals("Los Angeles", response.jsonPath().getString("data.location"));
        Assertions.assertEquals("Smith", response.jsonPath().getString("data.surname"));
        Assertions.assertEquals("Will", response.jsonPath().getString("data.name"));
        Assertions.assertEquals("12345678", response.jsonPath().getString("data.password"));
        Assertions.assertEquals(30, response.jsonPath().getInt("data.age"));
    }

    @Test
    public void loginUser() {
        String requestBody = "{\n" +
                "  \"email\": \"g1@gmail.com\",\n" +
                "  \"password\": \"12345678\"}";

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

    @Test
    public void updateUser() {
        String requestBodyLogin = "{\n" +
                "  \"email\": \"g1@gmail.com\",\n" +
                "  \"password\": \"12345678\"}";

        Response responseLogin = given()
                .baseUri(BASE_URL)
                .basePath("/users/login")
                .contentType(ContentType.JSON)
                .body(requestBodyLogin)
                .when().post()
                .then()
                .extract().response();

        String accessToken = responseLogin.jsonPath().getString("data.token");

        String requestBodyUpdate = "{\n" +
                "  \"email\": \"g1@gmail.com\",\n" +
                "  \"location\": \"Los Angeles\",\n" +
                "  \"surname\": \"Smith\",\n" +
                "  \"name\": \"Will\",\n" +
                "  \"newPassword\": \"12345678\",\n" +
                "  \"oldPassword\": \"12345678\",\n" +
                "  \"age\": 30,\n" +
                "  \"avatar\": \"\" \n}";

        {

            given()
                    .baseUri(BASE_URL)
                    .basePath("/users")
                    .contentType(ContentType.JSON)
                    .header("x-access-token", accessToken)
                    .body(requestBodyUpdate)
                    .when().put()
                    .then().statusCode(200)
                    .extract().response().prettyPrint();
        }
    }

    @Test
    public void deleteUser() {
        String requestBody = String.format("{\n" +
                "  \"email\": \"%s\",\n" +
                "  \"location\": \"New York\",\n" +
                "  \"surname\": \"Anderson\",\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"password\": \"12345678\",\n" +
                "  \"age\": 21 \n}", EMAIL);

        Response responseCreate = given()
                .baseUri(BASE_URL)
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then()
                .extract().response();

        String newUserEmail = responseCreate.jsonPath().getString("data.email");

        String requestBodyLogin = String.format("{\n" +
                "  \"email\": \"%s\",\n" +
                "  \"password\": \"12345678\"}", newUserEmail);

        Response responseLogin = given()
                .baseUri(BASE_URL)
                .basePath("/users/login")
                .contentType(ContentType.JSON)
                .body(requestBodyLogin)
                .when().post()
                .then()
                .extract().response();

        String accessToken = responseLogin.jsonPath().getString("data.token");
        String id = responseLogin.jsonPath().getString("data.userId");

        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/users/{id}")
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .header("x-access-token", accessToken)
                .delete()
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }
}
