package api.steps;

import api.pojos.UserPojo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserSteps {
    private static final RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("https://academy.directlinedev.com/api")
                    .setContentType(ContentType.JSON)
                    .build();

    public static UserPojo getUser(String User_id) {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", User_id)
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }
}
