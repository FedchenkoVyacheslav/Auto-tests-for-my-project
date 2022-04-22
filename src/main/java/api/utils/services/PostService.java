package api.utils.services;

import api.pojos.PostPojo;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

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

    public Response getPostTags(List<String> tags) {
        return given().spec(REQ_SPEC)
                .param("tags", tags.toString())
                .when().get()
                .then().extract().response();
    }

    public List<PostPojo> getPostsLimit(int limit){
        return given().spec(REQ_SPEC)
                .param("limit", limit)
                .get()
                .jsonPath().getList("data", PostPojo.class);
    }

    public List<PostPojo> getPostsByFilter(String filter, int min, int max){
        return given().spec(REQ_SPEC)
                .param("filter", String.format("{\"%s\": {\"$between\": [%s, %s]}}", filter, min, max))
                .get()
                .jsonPath().getList("data", PostPojo.class);
    }

    public List<PostPojo> getPostsByTitlePartialMatch(String text){
        return given().spec(REQ_SPEC)
                .param("filter", String.format("{\"title\":\"%s\"}", text))
                .get()
                .jsonPath().getList("data", PostPojo.class);
    }
}
