package api_tests.newStrusture;

import api.steps.UserSteps;
import api.utils.UserGenerator;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import api.pojos.CreateUserRequest;
import api.pojos.CreateUserResponse;
import api.pojos.UserPojo;

import static api.steps.UserSteps.getUser;
import static org.assertj.core.api.AssertionsForClassTypes.*;


public class UserApiCase {
    private final String USER_ID = "246";

    @Test
    @DisplayName("Should get user")
    public void getUserCase() {
        UserPojo user = getUser(USER_ID);

        assertThat(user).extracting(UserPojo::getId).isEqualTo(246);
        assertThat(user).extracting(UserPojo::getEmail).isEqualTo("g1@gmail.com");
        assertThat(user).extracting(UserPojo::getLocation).isEqualTo("Los Angeles");
        assertThat(user).extracting(UserPojo::getSurname).isEqualTo("Smith");
        assertThat(user).extracting(UserPojo::getName).isEqualTo("Will");
        assertThat(user).extracting(UserPojo::getPassword).isEqualTo("12345678");
        assertThat(user).extracting(UserPojo::getAge).isEqualTo(30);
    }

    @Test
    @DisplayName("Should create new user")
    public void createUserCase() {
        CreateUserRequest rq = UserGenerator.getSimpleUser();

        UserSteps userApi = new UserSteps();
        CreateUserResponse rs = userApi.createUser(rq);

        assertThat(rs).extracting(CreateUserResponse::getEmail).isEqualTo(rq.getEmail());
        assertThat(rs).extracting(CreateUserResponse::getLocation).isEqualTo(rq.getLocation());
        assertThat(rs).extracting(CreateUserResponse::getSurname).isEqualTo(rq.getSurname());
        assertThat(rs).extracting(CreateUserResponse::getName).isEqualTo(rq.getName());
        assertThat(rs).extracting(CreateUserResponse::getPassword).isEqualTo(rq.getPassword());
        assertThat(rs).extracting(CreateUserResponse::getAge).isEqualTo(rq.getAge());
    }
}