package api.utils.services;

import api.pojos.TagPojo;
import io.restassured.http.Cookies;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TagService extends RestService{
    @Override
    protected String getBasePath() {
        return "/tags";
    }

    public TagService(Cookies cookies) {
        super(cookies);
    }

    public List<TagPojo> getTags(){
        return given().spec(REQ_SPEC)
                .get()
                .jsonPath().getList("data", TagPojo.class);
    }
}
