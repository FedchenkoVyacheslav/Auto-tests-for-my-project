package api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor @AllArgsConstructor
public class BlogPojo{
	private String date;
	private int commentsCount;
	private int id;
	private String text;
	private String title;
	private int views;
}
