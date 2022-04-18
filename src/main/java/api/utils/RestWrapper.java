package api.utils;

import api.pojos.UserLogin;
import api.utils.services.EmailService;
import api.utils.services.PostService;
import api.utils.services.TagService;
import api.utils.services.UserService;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;

import static io.restassured.RestAssured.given;

public class RestWrapper {
    private static final String BASE_URL = "https://academy.directlinedev.com/api";
    private Cookies cookies;

    public UserService user;
    public PostService posts;
    public TagService tags;
    public EmailService email;

    public RestWrapper(Cookies cookies){
        this.cookies = cookies;
        user = new UserService(cookies);
        posts = new PostService(cookies);
        tags = new TagService(cookies);
        email = new EmailService(cookies);
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
