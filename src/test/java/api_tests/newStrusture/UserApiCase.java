package api_tests.newStrusture;

import api.utils.RestWrapper;
import api.utils.UserGenerator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import api.pojos.UserRequest;
import api.pojos.CreateUserResponse;
import api.pojos.UserPojo;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class UserApiCase {
    private static RestWrapper api;

    @BeforeClass
    public static void prepareClient() {
        api = RestWrapper.loginAsUser("g1@gmail.com", "12345678");
    }

    @Test
    @DisplayName("Should get user")
    public void getUserCase() {
        assertThat(api.user.getUser("246")).extracting(UserPojo::getId).isEqualTo(246);
        assertThat(api.user.getUser("246")).extracting(UserPojo::getEmail).isEqualTo("g1@gmail.com");
        assertThat(api.user.getUser("246")).extracting(UserPojo::getLocation).isEqualTo("Los Angeles");
        assertThat(api.user.getUser("246")).extracting(UserPojo::getSurname).isEqualTo("Smith");
        assertThat(api.user.getUser("246")).extracting(UserPojo::getName).isEqualTo("Will");
        assertThat(api.user.getUser("246")).extracting(UserPojo::getPassword).isEqualTo("12345678");
        assertThat(api.user.getUser("246")).extracting(UserPojo::getAge).isEqualTo(30);
    }

    @Test
    @DisplayName("Should create new user")
    public void createUserCase() {
        UserRequest rq = UserGenerator.getSimpleUser();
        CreateUserResponse rs = api.user.createUser(rq);

        assertThat(rs).extracting(CreateUserResponse::getEmail).isEqualTo(rq.getEmail());
        assertThat(rs).extracting(CreateUserResponse::getLocation).isEqualTo(rq.getLocation());
        assertThat(rs).extracting(CreateUserResponse::getSurname).isEqualTo(rq.getSurname());
        assertThat(rs).extracting(CreateUserResponse::getName).isEqualTo(rq.getName());
        assertThat(rs).extracting(CreateUserResponse::getPassword).isEqualTo(rq.getPassword());
        assertThat(rs).extracting(CreateUserResponse::getAge).isEqualTo(rq.getAge());
    }
}