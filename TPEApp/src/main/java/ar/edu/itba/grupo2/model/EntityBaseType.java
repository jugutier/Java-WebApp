package ar.edu.itba.grupo2.model;

abstract class EntityBaseType {
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
