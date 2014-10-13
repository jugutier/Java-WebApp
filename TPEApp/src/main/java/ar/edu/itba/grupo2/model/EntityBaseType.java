package ar.edu.itba.grupo2.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class EntityBaseType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public boolean isNew() {
		return getId() == null;
	}
}
