package ar.edu.itba.grupo2.web;

import java.io.File;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.service.FilmService;

@SuppressWarnings("serial")
public class LoadFilmsPage extends BasePage {
	
	@SpringBean
	private FilmService service;
	
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
					service.getFromFile(csvFile);
					
				} catch (Exception e) {
					error(new StringResourceModel("error.loadingFilms", this, null).getString());
				}
			}
		};
		
		FileUploadField fileInput = new FileUploadField("fileUpload");
		
		form.add(fileInput);
		add(form);
	}
}
