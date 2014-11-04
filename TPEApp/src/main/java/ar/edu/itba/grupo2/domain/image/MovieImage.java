package ar.edu.itba.grupo2.domain.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;
import ar.edu.itba.grupo2.domain.film.Film;

@Entity
@Table(name = "image")
public class MovieImage extends EntityBaseType {

	@Column(length = 50,nullable=false)
	private String name;

	@Column(length = 50,nullable=false)
	private String contentType;

	@Column(nullable=false)
	private Integer length;

	@Column(nullable= false)
	@Lob
	private byte[] content;

	@OneToOne
	private Film film;

	public MovieImage() {
	}

	public MovieImage(String name, String contentType, Integer length,
			byte[] content, Film film) {
		setId(null);
		this.name = name;
		this.contentType = contentType;
		this.length = length;
		this.content = content;
		this.film = film;

	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public Integer getLength() {
		return length;
	}

	public byte[] getContent() {
		return content;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setFilm(Film film) {
		this.film = film;
	}

}