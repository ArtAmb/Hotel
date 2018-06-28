package hotel.ejb.services.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDTO {
	private String to;
	private String subject;
	private String content;
	
	public String toUrlParams() {
		return String.format("to=%s&subject=%s&content=%s", to, subject, content);
	}
}
