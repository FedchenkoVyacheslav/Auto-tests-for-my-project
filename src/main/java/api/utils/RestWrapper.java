package api.utils;

import api.pojos.CreateUserRequest;
import api.pojos.CreateUserResponse;
import api.pojos.UserLogin;
import api.pojos.UserPojo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestWrapper {
    private static final String BASE_URL = "https://academy.directlinedev.com/api";
    private static RequestSpecification REQ_SPEC;
    private Cookies cookies;

    public RestWrapper(Cookies cookies){
        this.cookies = cookies;

        REQ_SPEC = new RequestSpecBuilder()
                .addCookies(cookies)
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RestWrapper loginAsUser(String login, String password){
        Cookies cookies = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .basePath("/users/login")
                .body(new UserLogin(login, password))
                .when().post()
                .getDetailedCookies();

        return new RestWrapper(cookies);
    }

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

    public UserPojo getUser(){
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", user.getId())
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }

    public UserPojo getUser(String User_id) {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", User_id)
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }
}
