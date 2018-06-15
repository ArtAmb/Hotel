package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.NewsDAO;
import hotel.domain.Booking;
import hotel.domain.News;
import lombok.Data;
/**
 * @author Karolina B¹tkowska
 */
@Stateless
@Named
@Data
@LocalBean
public class NewsController {

	private Integer id;
	private String title;
	private String description;
	

	private News query = new News();
	
	@EJB
	private NewsDAO newsDAO;

	public String saveNews() {

		
		News news = new News();
		news.setTitle(title);
		news.setDescription(description);
		

		newsDAO.save(news);
		
		return "booking";
	}
	
	public List<News> findAllNews(){
		return newsDAO.findAll();
	}
	
	public News findOne(Long id){
		return newsDAO.findOne(id);
	}
	
	public List<News> findByQuery(){
		return newsDAO.findByQuery(query);
	}
	
	

}
