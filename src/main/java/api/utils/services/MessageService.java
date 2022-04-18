package api.utils.services;

import api.pojos.MessageRequest;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MessageService extends PostService {
    @Override
    protected String getBasePath() {
        return "/emails";
    }

    public MessageService(Cookies cookies) {
        super(cookies);
    }

    public Response sendMessage(MessageRequest rq) {
        return given()
                .spec(REQ_SPEC)
                .body(rq)
                .when().post()
                .then()
                .extract().response();
    }
}
