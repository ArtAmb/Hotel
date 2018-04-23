package hotel.dao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Page {
	private Integer first;
	private Integer max;
	
}