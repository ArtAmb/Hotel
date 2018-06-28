package paihotel;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import hotel.dao.ClientDAO;
import hotel.ejb.controller.CreateNewClientAcountController;

public class ExampleTest {

	private CreateNewClientAcountController controller = new CreateNewClientAcountController();
	
	
	@Test
	public void tmp() {
		ClientDAO mockClientDAO = mock(ClientDAO.class);
		when(mockClientDAO.save(any())).thenReturn(null);
		
		controller.setClientDAO(mockClientDAO);
		controller.saveNewClient();
		verify(mockClientDAO, times(1)).save(any());
	}
	
}
