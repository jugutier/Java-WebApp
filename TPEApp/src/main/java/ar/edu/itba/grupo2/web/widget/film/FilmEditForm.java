package ar.edu.itba.grupo2.web.widget.film;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;

import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.web.command.FilmForm;

@SuppressWarnings("serial")
public class FilmEditForm extends Panel {
	
	@SpringBean
	private FilmRepo films;
	
	public FilmEditForm(String id, IModel<FilmForm> filmForm) {
		super(id, filmForm);
		
		createForm(filmForm);
	}
	
	private void createForm(IModel<FilmForm> filmForm) {
		
		Form<FilmForm> form = new Form<FilmForm>("form", new CompoundPropertyModel<FilmForm>(filmForm));
	
		// Film title
		TextField<String> nameTextField = new TextField<String>("name");
		nameTextField.setRequired(true);
		
		// Release date
		TextField<Date> releaseDateTextField = new TextField<Date>("releaseDate");
		releaseDateTextField.setRequired(true);
		
		// Director
		TextField<String> directorTextField = new TextField<String>("director");
		directorTextField.setRequired(true);
		
		// Length
		NumberTextField<Integer> lengthTextField = new NumberTextField<Integer>("length");
		lengthTextField.setRequired(true);
		lengthTextField.setMinimum(0);
		
		// Image
		FileUploadField imageUpload = new FileUploadField("movieImage");
		Form<FilmForm> deleteImageForm = new Form<FilmForm>("deleteImageForm", new CompoundPropertyModel<FilmForm>(filmForm)) {
			@Override
			public boolean isVisible() {
				return form().hasImage();
			};
		};
		CheckBox deleteImage = new CheckBox("deleteImage");
		deleteImageForm.add(deleteImage);
		
		
		// Description
		TextArea<String> descriptionTextArea = new TextArea<String>("description");
		descriptionTextArea.setRequired(true);
		
		// Genre list
		ChoiceRenderer<Genre> renderer = new ChoiceRenderer<Genre>("genre");
		IModel<List<Genre>> genreModel = new LoadableDetachableModel<List<Genre>>() {

			@Override
			protected List<Genre> load() {
				return films.getGenres();
			}
		};
		
		CheckBoxMultipleChoice<Genre> genresCheckBoxes = new CheckBoxMultipleChoice<Genre>("genres", genreModel, renderer);
		
		genresCheckBoxes.setSuffix("");
		genresCheckBoxes.setRequired(false);
		
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(100));
		
		form.add(nameTextField);
		form.add(releaseDateTextField);
		form.add(directorTextField);
		form.add(lengthTextField);
		form.add(imageUpload);
		form.add(deleteImageForm);
		form.add(descriptionTextArea);
		form.add(genresCheckBoxes);
		
		add(form);
	}
	
	private FilmForm form() {
		return (FilmForm) getDefaultModelObject();
	}
}
