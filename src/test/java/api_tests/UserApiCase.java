package api_tests;

import Pages.BasePage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class UserApiCase {
    private String EMAIL = BasePage.getRandomLogin();
    private final String BASE_URL = "https://academy.directlinedev.com/api";

    @Test
    @DisplayName("Should create new user")
    public void shouldCreateUserCase() {
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
    @DisplayName("Shouldn't create new user with invalid email")
    public void shouldNotCreateUserWithInvalidEmailCase() {
        String requestBody = "{\n" +
                "  \"email\": \"11111\",\n" +
                "  \"location\": \"New York\",\n" +
                "  \"surname\": \"Anderson\",\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"password\": \"12345678\",\n" +
                "  \"age\": 21 \n}";

        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then()
                .extract().response();

        Assertions.assertEquals(422, response.statusCode());
        Assertions.assertFalse(response.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Не верный формат почты!", response.jsonPath().getString("errors.email"));
    }

    @Test
    @DisplayName("Shouldn't create new user with already exist email")
    public void shouldNotCreateUserWithExistEmailCase() {
        String requestBodyCreate = String.format("{\n" +
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
                .body(requestBodyCreate)
                .when().post()
                .then()
                .extract().response();

        String newUserEmail = responseCreate.jsonPath().getString("data.email");

        String requestBody = String.format("{\n" +
                "  \"email\": \"%s\",\n" +
                "  \"location\": \"New York\",\n" +
                "  \"surname\": \"Anderson\",\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"password\": \"12345678\",\n" +
                "  \"age\": 21 \n}", newUserEmail);

        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then()
                .extract().response();

        Assertions.assertEquals(422, response.statusCode());
        Assertions.assertFalse(response.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Данный email уже занят!", response.jsonPath().getString("errors.email"));
    }

    @Test
    @DisplayName("Should get user")
    public void shouldGetUserCase() {
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
    @DisplayName("Should login user")
    public void shouldLoginUserCase() {
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
    @DisplayName("Should update user data")
    public void shouldUpdateUserCase() {
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

            Response responseUpdate = given()
                    .baseUri(BASE_URL)
                    .basePath("/users")
                    .contentType(ContentType.JSON)
                    .header("x-access-token", accessToken)
                    .body(requestBodyUpdate)
                    .when().put()
                    .then()
                    .extract().response();

            Assertions.assertEquals(200, responseUpdate.statusCode());
            Assertions.assertEquals(246, responseUpdate.jsonPath().getInt("data.id"));
            Assertions.assertEquals("g1@gmail.com", responseUpdate.jsonPath().getString("data.email"));
            Assertions.assertEquals("Los Angeles", responseUpdate.jsonPath().getString("data.location"));
            Assertions.assertEquals("Smith", responseUpdate.jsonPath().getString("data.surname"));
            Assertions.assertEquals("Will", responseUpdate.jsonPath().getString("data.name"));
            Assertions.assertEquals("12345678", responseUpdate.jsonPath().getString("data.password"));
            Assertions.assertEquals(30, responseUpdate.jsonPath().getInt("data.age"));
        }
    }

    @Test
    @DisplayName("Shouldn't update not authorised user data")
    public void shouldNotUpdateNotAuthorisedUserCase() {
        String request = "{\n" +
                "  \"email\": \"g1@gmail.com\",\n" +
                "  \"location\": \"Los Angeles\",\n" +
                "  \"surname\": \"Smith\",\n" +
                "  \"name\": \"Will\",\n" +
                "  \"newPassword\": \"12345678\",\n" +
                "  \"oldPassword\": \"12345678\",\n" +
                "  \"age\": 30,\n" +
                "  \"avatar\": \"\" \n}";
        {

            Response response = given()
                    .baseUri(BASE_URL)
                    .basePath("/users")
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when().put()
                    .then()
                    .extract().response();

            Assertions.assertEquals(401, response.statusCode());
            Assertions.assertFalse(response.jsonPath().getBoolean("success"));
            Assertions.assertEquals("Вы не авторизированны!5", response.jsonPath().getString("_message"));
        }
    }

    @Test
    @DisplayName("Should delete user")
    public void shouldDeleteUserCase() {
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

        Response responseDelete = given()
                .baseUri(BASE_URL)
                .basePath("/users/{id}")
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .header("x-access-token", accessToken)
                .delete()
                .then()
                .extract().response();

        Assertions.assertEquals(200, responseDelete.statusCode());
        Assertions.assertTrue(responseDelete.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Ок!", responseDelete.jsonPath().getString("data"));
    }

    @Test
    @DisplayName("Shouldn't delete not authorised user")
    public void shouldNotDeleteNotAuthorisedUserCase() {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/users/{id}")
                .pathParam("id", 1)
                .contentType(ContentType.JSON)
                .delete()
                .then()
                .extract().response();

        Assertions.assertEquals(401, response.statusCode());
        Assertions.assertFalse(response.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Вы не авторизированны!5", response.jsonPath().getString("_message"));
    }
}
