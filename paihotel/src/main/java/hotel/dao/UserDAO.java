package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.User;

@Named
@Stateless
@LocalBean
public class UserDAO extends CrudDAO<Long, User>{

}
