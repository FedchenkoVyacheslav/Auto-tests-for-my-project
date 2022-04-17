package api.utils.services;

import api.pojos.CreateUserResponse;
import api.pojos.UserPojo;
import api.pojos.UserRequest;
import io.restassured.http.Cookies;

import static io.restassured.RestAssured.given;

public class UserService extends RestService{
    private CreateUserResponse user;

    @Override
    protected String getBasePath() {
        return "/users";
    }

    public UserService(Cookies cookies) {
        super(cookies);
    }

    public CreateUserResponse createUser(UserRequest rq) {
        user = given()
                .spec(REQ_SPEC)
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

    public UserPojo getUser(String User_id) {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", User_id)
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }
}
