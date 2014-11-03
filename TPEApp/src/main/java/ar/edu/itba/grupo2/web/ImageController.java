package ar.edu.itba.grupo2.web;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.image.MovieImage;
import ar.edu.itba.grupo2.domain.user.UserRepo;

@Controller
public class ImageController extends BaseController {

	private final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.	
	private final ServletContext servletContext;
	
	private static final String DEFAULT_MOVIE_IMAGE = "/resources/img/default.png";
	
	@Autowired
	public ImageController(UserRepo userRepo, ServletContext servletContext) {
		super(userRepo);
		this.servletContext = servletContext;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addImage(
			@RequestParam(value = "filmId", required = true) Film film,
			@RequestParam(value = "file", required = true) MultipartFile file) {

		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			String contentType = file.getContentType();
			Integer length = (int) file.getSize();

			byte[] imageData = new byte[length];

			try {
				InputStream fileInputStream = file.getInputStream();
				fileInputStream.read(imageData);
				fileInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			MovieImage movieImage = film.getMovieImage();
			if(movieImage == null){
				film.setFilmImage(new MovieImage(name, contentType, length,
						imageData, film));
			}else{
				movieImage.setName(name);
				movieImage.setContentType(contentType);
				movieImage.setLength(length);
				movieImage.setContent(imageData);
			}
			
		}
		return "redirect:../film/" + film.getId() + "/details";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String deleteImage(
			@RequestParam(value = "filmId", required = false) Film film) {
		film.setFilmImage(null);
		return "redirect:/admin/manageAllImages";
	}
	
	private byte[] getDefaultImage() throws IOException {
		InputStream in = servletContext.getResourceAsStream(DEFAULT_MOVIE_IMAGE);
		try {
		    return IOUtils.toByteArray(in);
		}
		finally {
		    in.close();
		}
	}

	// Display the image...
	@RequestMapping(method = RequestMethod.GET, value = "image/get/{id}")
	public void get(Model model, @PathVariable("id") Film film,
			HttpServletResponse response) throws ServletException, IOException {
		if (film == null) {
			Logger.getRootLogger().error("null image");
		}
		
		MovieImage image = film.getMovieImage();

		if (image == null) {
			byte[] bytes = getDefaultImage();
			image = new MovieImage("", "image/png", bytes.length, bytes, null);
		}

		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setContentType(image.getContentType());
		response.setHeader("Content-Length", String.valueOf(image.getLength()));
		response.setHeader("Content-Disposition",
				"inline; filename=\"" + image.getName() + "\"");
		BufferedOutputStream output = null;
		output = new BufferedOutputStream(response.getOutputStream(),
				DEFAULT_BUFFER_SIZE);
		output.write(image.getContent(), 0, image.getLength());
		close(output);
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				Logger.getRootLogger().error(e);
			}
		}
	}

}
