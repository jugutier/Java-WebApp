package ar.edu.itba.grupo2.dao.exceptions;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class RegisterErrorException extends Exception{
	
	private List<String> errors = new ArrayList<String>();
	
	public RegisterErrorException(List<String> errors){
		
		this.errors=errors;
	}
	
	public List<String> getErrors(){
		return errors;
	}

}
