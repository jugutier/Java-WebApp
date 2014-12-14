package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.MovieImage;
import ar.edu.itba.grupo2.web.command.FilmForm;
import ar.edu.itba.grupo2.web.widget.film.FilmEditForm;
import ar.edu.itba.grupo2.web.widget.film.FilmTitle;

@SuppressWarnings("serial")
public class EditFilmPage extends BasePage {
	
	private transient List<FileUpload> fileUpload;

	@SuppressWarnings("rawtypes")
	public EditFilmPage(Film film) {
		super();
		
		Form<?> form = new Form("form");
		
		IModel<FilmForm> filmFormModel = new LoadableDetachableModel<FilmForm>() {
			@Override
			protected FilmForm load() {
				return new FilmForm(film());
			}
		};
		
		IModel<Film> model = new EntityModel<Film>(Film.class, film);
		setDefaultModel(model);
		
		form.add(new FilmEditForm("filmForm", filmFormModel, new PropertyModel<List<FileUpload>>(this, "fileUpload")));
		form.add(new SubmitLink("submit", filmFormModel) {
			@Override
			public void onSubmit() {
				super.onSubmit();
				
				FilmForm filmForm = (FilmForm) getDefaultModelObject();
				
				film().setName(filmForm.getName());
				film().setReleaseDate(filmForm.getReleaseDate());
				film().setDirector(filmForm.getDirector());
				film().setLength(filmForm.getLength());
				film().setGenres(filmForm.getGenres());
				film().setDescription(filmForm.getDescription());
				
				if (filmForm.isDeleteImage()) {
					film().setFilmImage(null);
				}
				
				if (fileUpload != null) {
					FileUpload file = fileUpload.get(0);
					MovieImage movieImage = new MovieImage(file.getClientFileName(), file.getContentType(), (int) file.getSize(), file.getBytes());
					
					film().setFilmImage(movieImage);
				}
				
				setResponsePage(new FilmDetailsPage(film()));
			}
		});
		
		form.setMultiPart(true);
		
		add(form);
		add(new FilmTitle("filmTitle", model));
	}
	
	private Film film() {
		return (Film) getDefaultModelObject();
	}
}
