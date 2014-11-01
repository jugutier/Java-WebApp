package ar.edu.itba.grupo2.web.command;

import java.util.Date;
import java.util.List;

import ar.edu.itba.grupo2.domain.genre.Genre;

public class FilmForm {
	private int filmId;
	private String name;
	private Date releaseDate;
	private String director;
	private List<Genre> genres;
	private int length;
	private String description;
	
	public FilmForm (){}
	
	public int getFilmId(){
		return this.filmId;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public String getDirector() {
		return director;
	}
	
	public List<Genre> getGenres() {
		return genres;
	}
	
	public int getLength() {
		return length;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public void setFilmId(int filmId){
		this.filmId = filmId;
	}
	
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
