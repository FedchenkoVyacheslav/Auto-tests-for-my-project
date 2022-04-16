package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserPojo {
    private int id;
    private String photoUrl;
    private String email;
    private int age;
    private String location;
    private String surname;
    private String name;
    private String password;
    private String token;
}
