package ar.edu.itba.grupo2.utils;

public class ValidationUtilities {

	public static boolean isEmail(String email) {
		return !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	}

	public static boolean paramEmpty(String param) {
		return (param == null || (param.matches("^\\s*$") || param.isEmpty()));
	}

	public static boolean isWithinLength(String value, int min, int max) {
		return value.length() >= min && value.length() <= max;
	}

}
