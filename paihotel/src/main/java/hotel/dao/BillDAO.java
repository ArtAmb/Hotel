package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Bill;

@Named
@Stateless
@LocalBean
public class BillDAO extends CrudDAO<Long, Bill> {

}
