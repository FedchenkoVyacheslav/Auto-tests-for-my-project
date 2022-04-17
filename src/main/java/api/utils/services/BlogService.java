package api.utils.services;

import api.pojos.BlogPojo;
import io.restassured.http.Cookies;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BlogService extends RestService{
    public BlogService(Cookies cookies) {
        super(cookies);
    }

    @Override
    protected String getBasePath() {
        return "/blogs";
    }

    public List<BlogPojo> getBlogs(){
        return given().spec(REQ_SPEC)
                .get()
                .jsonPath().getList("data", BlogPojo.class);
    }
}
