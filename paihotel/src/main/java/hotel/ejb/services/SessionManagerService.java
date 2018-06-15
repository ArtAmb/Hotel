package hotel.ejb.services;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * @author Artur Ambrolewicz
 */
@Stateless
public class SessionManagerService {
	
	
	public HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	

	public void saveInSession(SessionObject sessionObject, Object object) {
		//if(!(object instanceof sessionObject.getObjectClass() ))
		//	throw new IllegalStateException(String.format("%s musi byc typu %s", sessionObject.getName(), sessionObject.getClass().toString()));
		saveInSession(sessionObject.getName(), object);
	}
	
	public void saveInSession(String name, Object object) {
		HttpSession session = getSession();
		session.setAttribute(name, object);
	}

	public Object getFromSession(SessionObject sessionObject) {
		return getFromSession(sessionObject.getName());
	}
	
	public Object getFromSession(String name) {
		HttpSession session = getSession();
		return session.getAttribute(name);
	}
	
	public void removeFromSession(String name) {
		HttpSession session = getSession();
		session.removeAttribute(name);
	}
	
	public void deleteSession() {
		HttpSession session = getSession();
		session.invalidate();
	}
	
}
