package ar.edu.itba.grupo2.domain.report;

public enum Resolution {
	DELETE("Eliminar comentario"), DISCARDREPORT("Descartar denuncia");
	
	private String description;

	private Resolution(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}
}
