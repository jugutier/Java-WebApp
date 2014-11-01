package ar.edu.itba.grupo2.web.command;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.domain.image.MovieImage;

public class FilmForm {
	private int id;
	private String name;
	private String releaseDate;
	private String director;
	private List<Genre> genres;
	private int length;
	private String description;
	private MultipartFile movieImage;
	
	public FilmForm (){}
	
	public int getId(){
		return this.id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getReleaseDate() {
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
	
	public MultipartFile getMovieImage() {
		return movieImage;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setReleaseDate(String releaseDate) {
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
	
	public void setMovieImage(MultipartFile movieImage) {
		this.movieImage = movieImage;
	}
}