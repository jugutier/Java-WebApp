package ar.edu.itba.grupo2.domain.common;

import java.util.List;

public interface BaseRepo<T> {
	T get(final int id);
	List<T> getAll();
	void delete(T entity);
	T save(T entity);
}