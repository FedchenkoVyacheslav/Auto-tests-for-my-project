package api_tests;

import Pages.BasePage;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class RestTest {
    private String EMAIL = BasePage.getRandomLogin();

    @Test
    public void createUser() {
        String requestBody = String.format("{\n" +
                "  \"email\": \"%s\",\n" +
                "  \"location\": \"New York\",\n" +
                "  \"surname\": \"Anderson\",\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"password\": \"12345678\",\n" +
                "  \"age\": 21 \n}", EMAIL);

        given()
                .baseUri("https://academy.directlinedev.com/api")
                .basePath("/users")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then().statusCode(200)
                .extract().response().prettyPrint();
    }

    @Test
    public void getUser() {
        given()
                .baseUri("https://academy.directlinedev.com/api")
                .basePath("/users/{id}")
                .pathParam("id", "273")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .body("data.email", equalTo("email1@email.com"))
                .extract().response().prettyPrint();
    }
}
