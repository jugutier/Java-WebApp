package ar.edu.itba.grupo2.domain.baseRepo;

import java.util.List;

public interface BaseRepo<T> {
	T get(final int id);
	List<? extends T> getAll();
	void delete(T entity);
	T save(T entity);
}