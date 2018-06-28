package hotel.ejb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;

import hotel.domain.Picture;
import lombok.Data;

/**
 * @author Karolina B¹tkowska
 */
@RequestScoped
@Named
@Data
@ManagedBean
public class PhotoController {
	private Part file;
	private String fileContent;
	private Picture picture;
	

	public void upload() {
		try {
			
			fileContent = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
			//picture = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
			
		} catch (IOException e) {
			// Error handling
		}
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part) value;
		if (file.getSize() > 1024) {
			msgs.add(new FacesMessage("Plik zbyt duzy"));
		}
		if (!"image/jpg".equals(file.getContentType())) {
			msgs.add(new FacesMessage("Zly plik"));
		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}
}
