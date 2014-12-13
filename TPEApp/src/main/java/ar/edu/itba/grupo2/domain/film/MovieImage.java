package ar.edu.itba.grupo2.domain.film;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;

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

	MovieImage() {
	}

	public MovieImage(String name, String contentType, Integer length,
			byte[] content) {
		setId(null);
		this.name = name;
		this.contentType = contentType;
		this.length = length;
		this.content = content;

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

	void setName(String name) {
		this.name = name;
	}

	void setContent(byte[] content) {
		this.content = content;
	}

	void setLength(Integer length) {
		this.length = length;
	}

	void setContentType(String contentType) {
		this.contentType = contentType;
	}

}