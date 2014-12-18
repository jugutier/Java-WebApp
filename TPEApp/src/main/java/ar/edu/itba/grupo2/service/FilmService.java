package ar.edu.itba.grupo2.service;

import java.io.File;

public interface FilmService {

	public int getStock(String filmName);

	public void getFromFile(File csvFile) throws Exception;

}