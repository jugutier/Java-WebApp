package ar.edu.itba.grupo2.web;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

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

	@Autowired
	public ImageController(UserRepo userRepo) {
		super(userRepo);
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
			film.setFilmImage(new MovieImage(name, contentType, length,
					imageData, film));
		}
		return "redirect:../film/filmDetails?id=" + film.getId();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String deleteImage(
			@RequestParam(value = "filmId", required = false) Film film) {
		film.setFilmImage(null);
		return "redirect:/admin/manageAllImages";
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
			
//			// load default "no-image"
//			BufferedImage originalImage = ImageIO.read( ClassLoader.getSystemResource( "resources/img/defaultFilm.png" ) );
//			//BufferedImage originalImage = ImageIO.read(new File("resources/img/defaultFilm.png"));
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write( originalImage, "jpg", baos );
//			baos.flush();
//			byte[] imageData = baos.toByteArray();
//			baos.close();
			
			//FORMA 2
			/*FileSystemResource file = new FileSystemResource("GAJAmdb/resources/img/defaultFilm.png");
			//File file = new File("resources/img/defaultFilm.png");
			byte[] imageData = new byte[(int) file.getFile().length()];
			 
			try {
			    InputStream fileInputStream = file.getInputStream();
			    fileInputStream.read(imageData);
			    fileInputStream.close();
			} catch (Exception e) {
			    e.printStackTrace();
			}*/
			//FORMA 3
			/*File file = new File("GAJAmdb/resources/img/defaultFilm.png");
			byte[] imageData = new byte[(int) file.length()];
			 
			try {
			    FileInputStream fileInputStream = new FileInputStream(file);
			    fileInputStream.read(imageData);
			    fileInputStream.close();
			} catch (Exception e) {
			    e.printStackTrace();
			}
			 
			image = new MovieImage("default.png","image/png", imageData.length,imageData);
			*/
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
