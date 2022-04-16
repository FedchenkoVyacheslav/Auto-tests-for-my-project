package api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
	private String password;
	private String surname;
	private String name;
	private String location;
	private String email;
	private int age;
}
