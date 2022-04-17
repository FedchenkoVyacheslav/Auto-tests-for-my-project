package api.utils;

import api.pojos.UserLogin;
import api.utils.services.BlogService;
import api.utils.services.UserService;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestWrapper {
    private static final String BASE_URL = "https://academy.directlinedev.com/api";
    private static RequestSpecification REQ_SPEC;
    private Cookies cookies;

    public UserService user;
    public BlogService blogs;

    public RestWrapper(Cookies cookies){
        this.cookies = cookies;
        user = new UserService(cookies);
        blogs = new BlogService(cookies);
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
}
