package ar.edu.itba.grupo2.web;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.genre.Genre;

@SuppressWarnings("serial")
public class LoadFilmsPage extends BasePage {
	
	@SpringBean
	private FilmRepo films;
	
	private transient List<FileUpload> fileUpload;

	public LoadFilmsPage() {
		super();
		
		Form<LoadFilmsPage> form = new Form<LoadFilmsPage>("form", new CompoundPropertyModel<LoadFilmsPage>(this)) {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				FileUpload file = fileUpload.get(0);
				File csvFile = new File(file.getClientFileName());
				try {
					file.writeTo(csvFile);
					
					CSVParser parser = CSVParser.parse(csvFile, Charset.forName("UTF-8"), 
							CSVFormat.DEFAULT.withHeader("title","releaseDate","director","length","genre","description"));
					List<CSVRecord> records = parser.getRecords();
					
					DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date releaseDate = null;
					Film film;
					
					// Remove header
					records.remove(0);
					for(CSVRecord r: records){
						try {
							releaseDate = outputDateFormat.parse(r.get("releaseDate"));
							List<Genre> genres = new ArrayList<Genre>();
							String[] genresValues = r.get("genre").replace("\"", "").split(","); 
							for(String genre: genresValues){
								genres.add(films.getGenre(genre));
							}
							film = new Film.Builder().creationDate(new Date())
									.releaseDate(releaseDate)
									.name(r.get("title"))
									.director(r.get("director"))
									.genres(genres)
									.length(Integer.parseInt(r.get("length").trim()))
									.description(r.get("description")).build();
							films.save(film);
						} catch (ParseException e) {
							// TODO Localize
							error("Ocurrió un error al cargar las películas");
						}
					}
				} catch (IOException e) {
					// TODO Localize
					error("Ocurrió un error al cargar las películas");
				}
			}
		};
		
		FileUploadField fileInput = new FileUploadField("fileUpload");
		
		form.add(fileInput);
		add(form);
	}
}
