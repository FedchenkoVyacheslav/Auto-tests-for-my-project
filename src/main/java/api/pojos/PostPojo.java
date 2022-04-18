package api.pojos;

import api.utils.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostPojo {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSZ")
	@JsonDeserialize(using = DateDeserializer.class)
	private LocalDateTime date;
	private int commentsCount;
	private int id;
	private String text;
	private String title;
	private int views;
}
