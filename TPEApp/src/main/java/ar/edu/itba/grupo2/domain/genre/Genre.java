package ar.edu.itba.grupo2.domain.genre;

import javax.persistence.Column;
import javax.persistence.Entity;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;

@Entity
public class Genre extends EntityBaseType{
	@Column(length=30,nullable=false)private String genre;
	Genre(){}
	public Genre(String genre){
		this.genre = genre;
	}
	public String getGenre() {
		return genre;
	}
}/**
public enum Genre {
	ADVENTURE("Aventura"), THRILLER("Suspenso"), HORROR("Terror"), DRAMA("Drama"), DOCUMENTARY(
			"Documental");
	private String description;

	Genre(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}

	public static Genre fromString(String genre) {		
		if (genre.equals("Aventura")) {
			return ADVENTURE;
		} else if (genre.equals("Suspenso")) {
			return THRILLER;
		} else if (genre.equals("Terror")) {
			return HORROR;
		} else if (genre.equals("Drama")) {
			return DRAMA;
		} else if (genre.equals("Documental")) {
			return DOCUMENTARY;
		}
		return null;
	}
}**/