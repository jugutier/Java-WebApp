package ar.edu.itba.grupo2.dao.exceptions;

@SuppressWarnings("serial")
public class ConfigPropertiesException extends RuntimeException {
	@Override
	public String getLocalizedMessage() {
		return "config.properties not found";
	}
}
