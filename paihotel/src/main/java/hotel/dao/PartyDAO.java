package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Party;

@Named
@Stateless
@LocalBean
public class PartyDAO extends CrudDAO<Long, Party> {
	
	
}
