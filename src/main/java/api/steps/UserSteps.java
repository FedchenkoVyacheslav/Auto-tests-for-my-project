package api.steps;

import api.pojos.CreateUserRequest;
import api.pojos.CreateUserResponse;
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

    private CreateUserResponse user;
    public CreateUserResponse createUser(CreateUserRequest rq) {
        user = given()
                .spec(REQ_SPEC)
                .basePath("/users")
                .body(rq)
                .when().post()
                .jsonPath().getObject("data", CreateUserResponse.class);
        return user;
    }

    public UserPojo getLastUser(){
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", user.getId())
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }

    public static UserPojo getUser(String User_id) {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", User_id)
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }
}
