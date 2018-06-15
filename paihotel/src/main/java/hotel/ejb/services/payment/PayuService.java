package hotel.ejb.services.payment;

import java.io.IOException;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import hotel.dao.ClientDAO;
import hotel.domain.Client;
import hotel.domain.User;
import hotel.ejb.services.SessionManagerService;
import hotel.ejb.services.SessionObject;
import hotel.ejb.services.payment.dto.PayuBuyer;
import hotel.ejb.services.payment.dto.PayuOrderResponse;
import hotel.ejb.services.payment.dto.PayuProduct;
import hotel.ejb.services.payment.dto.PayuRequest;
import hotel.ejb.services.payment.dto.PayuResponse;
import hotel.ejb.services.payment.dto.PayuSettings;

/**
 * @author Artur Ambrolewicz
 */
@Stateless
@Named
@LocalBean
public class PayuService {
	
	private CloseableHttpClient restClient = HttpClients.createDefault();
	private Gson gsonParser = new Gson();
	
	@EJB
	private SessionManagerService sessionManagerService;
	
	@EJB
	private ClientDAO clientDAO;
	
	private String selfUrl = "http://localhost:8080/paihotel";
	
	public PayuOrderResponse getPaymentRedirect(User user, String controlParam) throws JsonSyntaxException, ParseException, IOException {
		String url = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize?grant_type=client_credentials&client_id=332246&client_secret=f29943d8404568aaedfbd424c5922775";
		String urlToOrder = "https://secure.snd.payu.com/api/v2_1/orders";
		
		HttpPost request = new HttpPost(url);
		request.addHeader("Access-Control-Allow-Origin", "*");
		request.addHeader("charset", "UTF-8");
		 HttpResponse response = (HttpResponse) restClient.execute(request);
		 
		 PayuResponse payuResponse = gsonParser.fromJson(EntityUtils.toString(response.getEntity()), PayuResponse.class);
		 
		 
		HttpPost orderRequest = new HttpPost(urlToOrder); 
		
		orderRequest.addHeader("Authorization", String.format("Bearer %s", payuResponse.getAccess_token()));
		orderRequest.addHeader("Content-Type", "application/json");	

		PayuRequest bodyObject = buildPayuRequest(user, controlParam);
		StringEntity entity = new StringEntity(gsonParser.toJson(bodyObject), "UTF-8");
		orderRequest.setEntity(entity);
		
		HttpResponse response2 = (HttpResponse) restClient.execute(orderRequest);
		
		 PayuOrderResponse payuOrderResponse = gsonParser.fromJson(EntityUtils.toString(response2.getEntity()), PayuOrderResponse.class);
		return payuOrderResponse;
	}
	
//	public PayuOrderResponse getPaymentRedirect(User user, String controlParam) {
//		String url = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize?grant_type=client_credentials&client_id=332246&client_secret=f29943d8404568aaedfbd424c5922775";
//		String urlToOrder = "https://secure.snd.payu.com/api/v2_1/orders";
//		
//		RestTemplate restTemplate = new RestTemplate();
//
//		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
////		HttpHeaders headers = new HttpHeaders();
////		headers.add("Access-Control-Allow-Origin", "*");
////		headers.add("Content-Type", "application/json");
////
////		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
////				body, headers);
//
//		PayuResponse payuResponse = restTemplate.postForObject(url, null, PayuResponse.class);
//		System.out.println(payuResponse);
//
//		
//		HttpHeaders payuOrderHeaders = new HttpHeaders();
//		payuOrderHeaders.add("Authorization", String.format("Bearer %s", payuResponse.getAccess_token()));
//		payuOrderHeaders.add("Content-Type", "application/json");
//		HttpEntity<PayuRequest> payuOrderRequestEntity = new HttpEntity<>(buildPayuRequest(user, controlParam), payuOrderHeaders);
//		
//		return restTemplate.postForObject(urlToOrder, payuOrderRequestEntity, PayuOrderResponse.class);
//	}
	
	private PayuRequest buildPayuRequest(User user, String controlParam) {
		//Client client = clientDAO.findByUser(user);
		
		Client client = (Client) sessionManagerService.getFromSession(SessionObject.BOOKING_CLIENT);
		
		return PayuRequest.builder()
				.continueUrl(selfUrl + "/view/booking/make-booking.xhtml") //?" + SessionObject.CONTROL_BOOKING_PARAM + "=" + controlParam)
				.customerIp("127.0.0.1")
				.merchantPosId("332246")
				.description("Room booking")
				.currencyCode("PLN")
				.totalAmount("500")
				.buyer(PayuBuyer.builder()
						.emial(client.getEmail())
						.phone(client.getPhone())
						.firstName(client.getName())
						.lastName(client.getSurname())
						.laguage("pl")
						.build())
				.settings(PayuSettings.builder().invoiceDisabled(true).build())
				.products(Arrays.asList(PayuProduct.builder()
						.name("Producer account activation")
						.unitPrice("500")
						.quantity("1")
						.virtual(true)
						.build()))
				.build();
	}
	
}