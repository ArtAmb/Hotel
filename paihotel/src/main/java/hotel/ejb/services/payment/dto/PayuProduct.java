package hotel.ejb.services.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayuProduct {
	private String name;
	private String unitPrice;
	private String quantity;
	private Boolean virtual;
}
