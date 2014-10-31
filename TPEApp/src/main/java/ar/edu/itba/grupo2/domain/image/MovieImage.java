package ar.edu.itba.grupo2.domain.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import ar.edu.itba.grupo2.domain.common.EntityBaseType;

@Entity
@Table
public class MovieImage extends EntityBaseType {

	@Column
	private String name;

	@Column
	private String contentType;

	@Column
	private Integer length;

	@Column
	@Lob
	private byte[] content;

	MovieImage() {
	}

	public MovieImage(String name, String contentType, Integer length,
			byte[] content) {
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

}