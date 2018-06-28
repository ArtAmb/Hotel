package hotel.ejb.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;

import hotel.dao.PictureDAO;
import hotel.domain.Picture;

/**
 * @author Karolina B¹tkowska
 */
@Stateless
@LocalBean
@Named
public class PhotoService {
	@EJB
	private PictureDAO pictureDAO;

	private final String picturesStorageUrl = "C:\\paihotel\\images\\";
	
	@PostConstruct
	public void deployAllPhotos() {
		 for(Picture picture : pictureDAO.findAll()) {
			 try {
				deploy(picture);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}
	
	public Picture upload(Part file) throws IOException {
//		String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//		Path rootLocation = Paths.get(path + "\\images\\");
		
		File root = new File(picturesStorageUrl);
		if (!root.exists())
			root.mkdirs();
		
		String fileName = generateName(file);
		Path path = Paths.get(picturesStorageUrl + fileName);
		Files.copy(file.getInputStream(), path);
		Picture picture = Picture.builder().url("").name(fileName).path(path.toString()).build();
		deploy(picture);
		return pictureDAO.save(picture);
	}
	
	public void deploy(Picture picture) throws IOException {
		String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		Path rootLocation = Paths.get(path + "\\images\\");
		File root = new File(rootLocation.toString());
		if (!root.exists())
			root.mkdirs();
		
		Files.copy(Paths.get(picture.getPath()), rootLocation.resolve(picture.getName()));
	}

	private String generateName(Part file) {
		String fileName = getFileName(file);
		String[] fileNameParts = fileName.split(Pattern.quote("."));
		String extension = fileNameParts[fileNameParts.length - 1];
		String name = "picture-" + System.currentTimeMillis() + "." + extension;
		return name;
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
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
	
	public String getPictureUrl(String name) {
		return "/images/" + name;
	}
}
