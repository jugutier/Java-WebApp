package ar.edu.itba.grupo2.model;

public enum Genre {
	ACTION("Acción"), THRILLER("Suspenso"), HORROR("Terror"), DRAMA("Drama"), DOCUMENTARY(
			"Documental");
	private String description;

	private Genre(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}

	public static Genre fromString(String genre) {		
		if (genre.equals("Acción")) {
			return ACTION;
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
}
