package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.FeatureDAO;
import hotel.domain.Feature;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class FeatureController {

	private Integer id;
	private String name;


	private Feature query = new Feature();
	
	@EJB
	private FeatureDAO featureDAO;

	public String saveFeature() {

		Feature feature = new Feature();
		feature.setName(name);
		

		featureDAO.save(feature);
		
		return "feature";
	}
	
	public List<Feature> findAllFeatures(){
		return featureDAO.findAll();
	}
	
	public Feature findOne(Long id){
		return featureDAO.findOne(id);
	}
	
	public List<Feature> findByQuery(){
		return featureDAO.findByQuery(query);
	}
	
	
	
	
	

}
