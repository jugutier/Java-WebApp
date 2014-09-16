package ar.edu.itba.grupo2.model;

public enum Genre {
	ACTION("Accion"), THRILLER("Suspenso"), HORROR("Terror"), DRAMA("Drama"), DOCUMENTARY(
			"Documental");
	private String description;

	private Genre(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}

	public static Genre fromString(String genre) {		
		//TODO: fix
		System.out.println("from string genre:"+genre + genre.equalsIgnoreCase("Suspenso") +"Suspenso");
		if (genre.equals("Accion")) {
			return ACTION;
		} else if (genre.compareTo("Suspenso")==0) {
			System.out.println("Not null");
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
