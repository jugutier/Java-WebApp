package ar.edu.itba.grupo2.domain.common;

import java.util.List;

public interface BaseRepo<T> {
	T get(final int id);
	List<T> getAll();
	void delete(final T entity);
	T save(final T entity);
}