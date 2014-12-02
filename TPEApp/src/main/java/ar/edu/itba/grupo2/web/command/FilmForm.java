package ar.edu.itba.grupo2.web.command;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;

import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.genre.Genre;

public class FilmForm {
	private int id;
	private String name;
	private Date releaseDate;
	private String director;
	private List<Genre> genres;
	private int length;
	private String description;
	private List<FileUpload> movieImage;
	private boolean deleteImage;
	
	public FilmForm (){}
	
	public FilmForm(final Film film) {
		setId(film.getId());
		setName(film.getName());
		setReleaseDate(film.getReleaseDate());
		setDirector(film.getDirector());
		setGenres(film.getGenres());
		setLength(film.getLength());
		setDescription(film.getDescription());
	}
	
	public int getId(){
		return this.id;
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
	
	public List<FileUpload> getMovieImage() {
		return movieImage;
	}
	
	public boolean isDeleteImage() {
		return deleteImage;
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
	
	public void setId(int id){
		this.id = id;
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
	
	public void setMovieImage(List<FileUpload> movieImage) {
		this.movieImage = movieImage;
	}
	
	public void setDeleteImage(boolean deleteImage) {
		this.deleteImage = deleteImage;
	}
}
