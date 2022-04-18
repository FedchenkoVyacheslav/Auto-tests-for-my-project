package api.utils.services;

import api.pojos.CreateUserResponse;
import api.pojos.UserLogin;
import api.pojos.UserPojo;
import api.pojos.UserRequest;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService extends RestService {
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

    public UserPojo updateUser(UserRequest rq, String accessToken) {
        return given()
                .spec(REQ_SPEC)
                .header("x-access-token", accessToken)
                .body(rq)
                .when().put()
                .jsonPath().getObject("data", UserPojo.class);
    }

    public UserPojo getLastUser() {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", user.getId())
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }

    public UserPojo getUser(int User_id) {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", User_id)
                .get()
                .jsonPath().getObject("data", UserPojo.class);
    }

    public Response loginResponse(String login, String password) {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/login")
                .body(new UserLogin(login, password))
                .when().post()
                .then()
                .extract().response();
    }

    public Response getResponse(UserRequest rq) {
        return given()
                .spec(REQ_SPEC)
                .body(rq)
                .when().post()
                .then()
                .extract().response();
    }

    public Response updateResponse(UserRequest rq, String accessToken) {
        return given()
                .spec(REQ_SPEC)
                .header("x-access-token", accessToken)
                .body(rq)
                .when().put()
                .then()
                .extract().response();
    }

    public Response deleteResponse(int User_id, String accessToken) {
        return given()
                .spec(REQ_SPEC)
                .basePath("/users/{id}")
                .pathParam("id", User_id)
                .header("x-access-token", accessToken)
                .delete()
                .then()
                .extract().response();
    }
}
