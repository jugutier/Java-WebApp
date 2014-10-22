package ar.edu.itba.grupo2.domain.common;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
public abstract class HibernateBaseRepo<T> implements BaseRepo<T> {

	protected SessionFactory sessionFactory;

	protected Class<T> tClass;

	
	@SuppressWarnings("unchecked")
	public HibernateBaseRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.tClass = (Class<T>) type.getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final int id) {
		return (T) sessionFactory.getCurrentSession().get(tClass, id);
	}

	@Override
	public List<T> getAll() {
		@SuppressWarnings("unchecked")
		List<T> results = createCriteria().list();

		return results;
	}

	protected Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(tClass);
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> find(String hql, Object... params) {
		return createQuery(hql,params).list();
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> limitedFind(String hql, int limit,Object... params) {
		return createQuery(hql,params).setMaxResults(limit).list();
	}
	
	private Query createQuery(String hql, Object... params){
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query;
	}

	
	@Override
	public void delete(final T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	@Override
	public T save(final T entity) {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

}
