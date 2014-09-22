package ar.edu.itba.grupo2.utils;

public class ValidationUtilities {

	public static boolean isEmail(String email) {
		return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	public static boolean paramEmpty(String param) {
		return (param == null || (param.matches("^\\s*$") || param.isEmpty()));
	}

}
