package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.common.EntityModel;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.image.MovieImage;
import ar.edu.itba.grupo2.web.command.FilmForm;
import ar.edu.itba.grupo2.web.widget.film.FilmEditForm;

@SuppressWarnings("serial")
public class EditFilmPage extends BasePage {

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
		
		setDefaultModel(new EntityModel<Film>(Film.class, film));
		
		form.add(new FilmEditForm("filmForm", filmFormModel));
		form.add(new SubmitLink("submit", filmFormModel) {
			@Override
			public void onSubmit() {
				// TODO Edit film
				super.onSubmit();
				
				FilmForm filmForm = (FilmForm) getDefaultModelObject();
				Film film = film();
				
				film.setName(filmForm.getName());
				film.setReleaseDate(filmForm.getReleaseDate());
				film.setDirector(filmForm.getDirector());
				film.setLength(filmForm.getLength());
				film.setGenres(filmForm.getGenres());
				film.setDescription(filmForm.getDescription());
				
				if (filmForm.isDeleteImage()) {
					film.setFilmImage(null);
				}
				
				// TODO Find out what gets serialized when changing a film's image
				if (filmForm.getMovieImage() != null) {
					FileUpload file = filmForm.getMovieImage().get(0);
					MovieImage movieImage = new MovieImage(file.getClientFileName(), file.getContentType(), (int) file.getSize(), file.getBytes(), film);
					
					film.setFilmImage(movieImage);
				}
				
				setResponsePage(new FilmDetailsPage(film));
			}
		});
		
		form.setMultiPart(true);
		
		add(form);
	}
	
	private Film film() {
		return (Film) getDefaultModelObject();
	}
}
