package api_tests;

import api.pojos.UserPojo;
import api.pojos.CreateUserResponse;
import api.pojos.UserRequest;
import api.utils.RestWrapper;
import api.utils.UserGenerator;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class UserApiCase {
    private static RestWrapper api;

    @BeforeClass
    public static void prepareClient() {
        api = RestWrapper.loginAsUser("g1@gmail.com", "12345678");
    }

    @Test
    @DisplayName("Should create new user")
    public void shouldCreateUserCase() {
        UserRequest rq = UserGenerator.getSimpleUser();
        CreateUserResponse rs = api.user.createUser(rq);

        assertThat(rs).extracting(CreateUserResponse::getEmail).isEqualTo(rq.getEmail());
        assertThat(rs).extracting(CreateUserResponse::getLocation).isEqualTo(rq.getLocation());
        assertThat(rs).extracting(CreateUserResponse::getSurname).isEqualTo(rq.getSurname());
        assertThat(rs).extracting(CreateUserResponse::getName).isEqualTo(rq.getName());
        assertThat(rs).extracting(CreateUserResponse::getPassword).isEqualTo(rq.getPassword());
        assertThat(rs).extracting(CreateUserResponse::getAge).isEqualTo(rq.getAge());
    }

    @Test
    @DisplayName("Shouldn't create new user with invalid email")
    public void shouldNotCreateUserWithInvalidEmailCase() {
        UserRequest rq = UserGenerator.getUserWithEmail("1111111");
        Response rs = api.user.getResponse(rq);

        Assertions.assertEquals(422, rs.statusCode());
        Assertions.assertFalse(rs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Не верный формат почты!", rs.jsonPath().getString("errors.email"));
    }

    @Test
    @DisplayName("Shouldn't create new user with already exist email")
    public void shouldNotCreateUserWithExistEmailCase() {
        UserRequest rq = UserGenerator.getSimpleUser();
        CreateUserResponse rs = api.user.createUser(rq);

        String newUserEmail = rs.getEmail();

        UserRequest new_rq = UserGenerator.getUserWithEmail(newUserEmail);
        Response new_rs = api.user.getResponse(new_rq);

        Assertions.assertEquals(422, new_rs.statusCode());
        Assertions.assertFalse(new_rs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Данный email уже занят!", new_rs.jsonPath().getString("errors.email"));
    }

    @Test
    @DisplayName("Should get user")
    public void getUserCase() {
        UserRequest rq = UserGenerator.getSimpleUser();
        CreateUserResponse rs = api.user.createUser(rq);

        assertThat(api.user.getUser(rs.getId())).extracting(UserPojo::getEmail).isEqualTo(rq.getEmail());
        assertThat(api.user.getUser(rs.getId())).extracting(UserPojo::getLocation).isEqualTo("New York");
        assertThat(api.user.getUser(rs.getId())).extracting(UserPojo::getSurname).isEqualTo("Anderson");
        assertThat(api.user.getUser(rs.getId())).extracting(UserPojo::getName).isEqualTo("Tom");
        assertThat(api.user.getUser(rs.getId())).extracting(UserPojo::getPassword).isEqualTo("12345678");
        assertThat(api.user.getUser(rs.getId())).extracting(UserPojo::getAge).isEqualTo(21);
    }

    @Test
    @DisplayName("Should login user")
    public void shouldLoginUserCase() {
        UserRequest rq = UserGenerator.getSimpleUser();
        CreateUserResponse rs = api.user.createUser(rq);
        Response loginResp = api.user.loginResponse(rq.getEmail(), rq.getPassword());

        Assertions.assertEquals(200, loginResp.statusCode());
        Assertions.assertEquals(api.user.getUser(rs.getId()).getId(), loginResp.jsonPath().getInt("data.userId"));
        Assertions.assertNotNull(loginResp.jsonPath().getString("data.token"));
    }

    @Test
    @DisplayName("Shouldn't login user with invalid email")
    public void shouldLoginUserWithInvalidEmailCase() {
        Response rs = api.user.loginResponse("11111", "12345678");

        Assertions.assertEquals(422, rs.statusCode());
        Assertions.assertFalse(rs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Не верный формат почты!", rs.jsonPath().getString("errors.email"));
    }

    @Test
    @DisplayName("Shouldn't login user with wrong combination of email and password")
    public void shouldLoginUserWithWrongCombinationCase() {
        Response rs = api.user.loginResponse("g1@gmail.com", "1111111");

        Assertions.assertEquals(400, rs.statusCode());
        Assertions.assertFalse(rs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Данная комбинация, почта и пароль не найдена!", rs.jsonPath().getString("_message"));
    }

    @Test
    @DisplayName("Should update user data")
    public void shouldUpdateUserCase() {
        Response rs = api.user.loginResponse("g1@gmail.com", "12345678");
        String accessToken = rs.jsonPath().getString("data.token");

        UserRequest rq = UserGenerator.getUpdatedUser();
        UserPojo updatedUser = api.user.updateUser(rq, accessToken);

        assertThat(updatedUser).extracting(UserPojo::getEmail).isEqualTo(rq.getEmail());
        assertThat(updatedUser).extracting(UserPojo::getLocation).isEqualTo(rq.getLocation());
        assertThat(updatedUser).extracting(UserPojo::getSurname).isEqualTo(rq.getSurname());
        assertThat(updatedUser).extracting(UserPojo::getName).isEqualTo(rq.getName());
        assertThat(updatedUser).extracting(UserPojo::getPassword).isEqualTo(rq.getPassword());
        assertThat(updatedUser).extracting(UserPojo::getAge).isEqualTo(rq.getAge());
    }

    @Test
    @DisplayName("Shouldn't update not authorised user data")
    public void shouldNotUpdateNotAuthorisedUserCase() {
        UserRequest rq = UserGenerator.getUpdatedUser();
        Response rs = api.user.updateResponse(rq, "");

        Assertions.assertEquals(401, rs.statusCode());
        Assertions.assertFalse(rs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Вы не авторизированны!5", rs.jsonPath().getString("_message"));
    }

    @Test
    @DisplayName("Should delete user")
    public void shouldDeleteUserCase() {
        UserRequest rq = UserGenerator.getSimpleUser();
        Response rsCreate = api.user.getResponse(rq);

        String email = rsCreate.jsonPath().getString("data.email");
        Response rsLogin = api.user.loginResponse(email, "12345678");

        String accessToken = rsLogin.jsonPath().getString("data.token");
        int id = rsLogin.jsonPath().getInt("data.userId");

        Response rs = api.user.deleteResponse(id, accessToken);

        Assertions.assertEquals(200, rs.statusCode());
        Assertions.assertTrue(rs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Ок!", rs.jsonPath().getString("data"));
    }

    @Test
    @DisplayName("Shouldn't delete not authorised user")
    public void shouldNotDeleteNotAuthorisedUserCase() {
        Response rs = api.user.deleteResponse(1, "");

        Assertions.assertEquals(401, rs.statusCode());
        Assertions.assertFalse(rs.jsonPath().getBoolean("success"));
        Assertions.assertEquals("Вы не авторизированны!5", rs.jsonPath().getString("_message"));
    }
}