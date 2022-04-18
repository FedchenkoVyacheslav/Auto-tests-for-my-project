package api.pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {
	private String body;
	private String to;
}
