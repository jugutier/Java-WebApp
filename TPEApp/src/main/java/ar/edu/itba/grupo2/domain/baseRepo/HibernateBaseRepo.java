package ar.edu.itba.grupo2.domain.baseRepo;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
public class HibernateBaseRepo<T> implements BaseRepo<T> {

	protected SessionFactory sessionFactory;

	protected Class<? extends T> tClass;

	
	@SuppressWarnings("unchecked")
	public HibernateBaseRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.tClass = (Class<? extends T>) type.getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final int id) {
		return (T) sessionFactory.getCurrentSession().get(tClass, id);
	}

	@Override
	public List<? extends T> getAll() {
		@SuppressWarnings("unchecked")
		List<? extends T> results = createCriteria().list();

		return results;
	}

	protected Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(tClass);
	}

	@Override
	public void delete(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	@Override
	public T save(T entity) {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

}
