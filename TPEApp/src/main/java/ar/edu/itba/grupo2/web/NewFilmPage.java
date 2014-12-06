package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.MovieImage;
import ar.edu.itba.grupo2.web.command.FilmForm;
import ar.edu.itba.grupo2.web.widget.film.FilmEditForm;

@SuppressWarnings("serial")
public class NewFilmPage extends BasePage {

	@SuppressWarnings("rawtypes")
	public NewFilmPage() {
		super();
		
		Form<?> form = new Form("form");
		
		IModel<FilmForm> filmFormModel = new LoadableDetachableModel<FilmForm>() {
			@Override
			protected FilmForm load() {
				return new FilmForm();
			}
		};
		
		setDefaultModel(filmFormModel);
		
		form.add(new FilmEditForm("filmForm", filmFormModel));
		form.add(new SubmitLink("submit", filmFormModel) {
			@Override
			public void onSubmit() {
				// TODO Add film
				super.onSubmit();
				
				FilmForm filmForm = (FilmForm) getDefaultModelObject();
				
				Film film = new Film.Builder()
					.name(filmForm.getName())
					.releaseDate(filmForm.getReleaseDate())
					.director(filmForm.getDirector())
					.length(filmForm.getLength())
					.genres(filmForm.getGenres())
					.description(filmForm.getDescription())
					.build();
				
				if (filmForm.getMovieImage() != null) {
					FileUpload file = filmForm.getMovieImage().get(0);
					MovieImage movieImage = new MovieImage(file.getClientFileName(), file.getContentType(), (int) file.getSize(), file.getBytes(), film);
					
					film.setFilmImage(movieImage);
				}
				
				films.save(film);
				
				setResponsePage(new FilmDetailsPage(film));
			}
		});
		
		form.setMultiPart(true);
		
		add(form);
	}
}