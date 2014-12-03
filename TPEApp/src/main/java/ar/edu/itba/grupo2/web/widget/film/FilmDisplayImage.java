package ar.edu.itba.grupo2.web.widget.film;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;

import ar.edu.itba.grupo2.domain.film.Film;

@SuppressWarnings("serial")
public class FilmDisplayImage extends Panel {

	public FilmDisplayImage(String id, IModel<Film> film) {
		super(id, film);
		
		add(new NonCachingImage("thumbnail", new AbstractReadOnlyModel<DynamicImageResource>() {
			@Override
			public DynamicImageResource getObject() {
				if (film().getMovieImage() == null) {
					return null;
				}
				DynamicImageResource dir = new DynamicImageResource() {
					@Override
					protected byte[] getImageData(Attributes attributes) {
						return film().getMovieImage().getContent();
					}
				};
				dir.setFormat(film().getMovieImage().getContentType());
				return dir;
			}
		}) {
			@Override
			public boolean isVisible() {
				return film().getMovieImage() != null;
			}
		});
		
		add(new WebMarkupContainer("defaultThumbnail") {
			@Override
			public boolean isVisible() {
				return film().getMovieImage() == null;
			}
		});
	}
	
	private Film film() {
		return (Film) getDefaultModelObject();
	}

}
