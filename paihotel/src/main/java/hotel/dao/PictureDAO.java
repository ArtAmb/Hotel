package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Picture;

@Stateless
@LocalBean
@Named
public class PictureDAO extends CrudDAO<Long, Picture> {

}
