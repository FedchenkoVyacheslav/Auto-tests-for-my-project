package api_tests;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class RestTest {


    @Test
    public void getUser() {
        given()
                .baseUri("https://academy.directlinedev.com/api")
                .basePath("/users/{id}")
                .pathParam("id", "273")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().response().prettyPrint();
    }
}
