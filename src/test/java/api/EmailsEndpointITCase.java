package api;

import api.pojos.MessageRequest;
import api.utils.MessageGenerator;
import api.utils.RestWrapper;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class EmailsEndpointITCase {
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

    @Test
    @DisplayName("Shouldn't send message to the same email twice")
    public void shouldNotSendMessageToSameEmailCase() {
        MessageRequest rq = MessageGenerator.sendNewMessage();
        api.message.sendMessage(rq);
        Response secondRs = api.message.sendMessage(rq);

        Assertions.assertEquals(200, secondRs.statusCode());
        Assertions.assertFalse(secondRs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Данна почта уже подписана на рассылку!", secondRs.jsonPath().getString("errors.to"));
    }
}
