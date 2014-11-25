package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.basic.Label;

import ar.edu.itba.grupo2.domain.film.Film;

public class FilmDetailsPage extends BasePage {
	
	public FilmDetailsPage(final Film film) {
		super();
		
		add(new Label("title", film.getName()));
	}
}
