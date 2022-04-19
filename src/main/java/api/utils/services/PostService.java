package api.utils.services;

import api.pojos.PostPojo;
import io.restassured.http.Cookies;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PostService extends RestService{
    @Override
    protected String getBasePath() {
        return "/posts";
    }

    public PostService(Cookies cookies) {
        super(cookies);
    }

    public List<PostPojo> getPosts(){
        return given().spec(REQ_SPEC)
                .get()
                .jsonPath().getList("data", PostPojo.class);
    }
}