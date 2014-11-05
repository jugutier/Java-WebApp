package ar.edu.itba.grupo2.domain.genre;

import javax.persistence.Column;
import javax.persistence.Entity;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;

@Entity
public class Genre extends EntityBaseType{
	@Column(length=30,nullable=false)private String genre;
	Genre(){}
	
	public String getGenre() {
		return genre;
	}
	@Override
	public String toString() {
		return genre;
	}
	@Override
	public boolean equals(Object obj) {
		return ((Genre)obj).genre.equals(genre);
	}
}