package api.utils.services;

import api.pojos.EmailRequest;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class EmailService extends PostService {
    @Override
    protected String getBasePath() {
        return "/emails";
    }

    public EmailService(Cookies cookies) {
        super(cookies);
    }

    public Response sendMessage(EmailRequest rq) {
        return given()
                .spec(REQ_SPEC)
                .body(rq)
                .when().post()
                .then()
                .extract().response();
    }
}
