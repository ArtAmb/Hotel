package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.MyTest;

@Named
@Stateless
@LocalBean
public class MyTestDAO extends CrudDAO<Long, MyTest>{

}
