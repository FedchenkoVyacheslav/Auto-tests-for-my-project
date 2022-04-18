package api_tests;

import api.pojos.MessageRequest;
import api.utils.MessageGenerator;
import api.utils.RestWrapper;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class EmailApiCase {
    private static RestWrapper api;

    @BeforeClass
    public static void prepareClient() {
        api = RestWrapper.loginAsUser("g1@gmail.com", "12345678");
    }

    @Test
    @DisplayName("Should send new message to email")
    public void shouldSendMessageToEmailCase() {
        MessageRequest rq = MessageGenerator.sendNewMessage();
        Response rs = api.message.sendMessage(rq);

        Assertions.assertEquals(200, rs.statusCode());
        Assertions.assertTrue(rs.jsonPath().getBoolean("success"));
    }
}
