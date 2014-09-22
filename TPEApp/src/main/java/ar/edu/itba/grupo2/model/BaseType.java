package ar.edu.itba.grupo2.model;

public abstract class BaseType {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public boolean isNew() {
		return getId() == -1;
	}
}
