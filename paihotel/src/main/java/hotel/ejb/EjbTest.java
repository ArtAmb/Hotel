package hotel.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;


@Named
@Stateless
@LocalBean
public class EjbTest {

	private String test = "TESTTTTTTTTTTTT";
	
	public String getTest() {
		return test;
	}
    public EjbTest() {
    	
    }
    
    public String doSth() {
    	return "EJB: I'm working too !";
    }

}
