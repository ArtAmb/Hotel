package hotel.ejb.services.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayuOrderResponse {
	private PayuStatus status;
	private String redirectUri;
	private String orderId;
	private String extOrderId;
}