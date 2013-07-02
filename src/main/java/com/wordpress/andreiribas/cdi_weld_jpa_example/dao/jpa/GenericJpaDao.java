package com.wordpress.andreiribas.cdi_weld_jpa_example.dao.jpa;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.wordpress.andreiribas.cdi_weld_jpa_example.dao.GenericDao;

/**
 * @author Andrei Gonçalves Ribas
 *
 */
public abstract class GenericJpaDao<T, I extends Serializable> implements GenericDao<T, I>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2973854079424656407L;

	private final EntityManager entityManager;
	
	private final Class<T> entityClass;
	
	protected GenericJpaDao() {
		this(null, null);
	}
	
	@Inject
	public GenericJpaDao(EntityManager entityManager, Class<T> entityClass) {
		
		this.entityManager = entityManager;
		
		this.entityClass = entityClass;
		
	}
	
	@Override
	public T save(final T t) {
		this.entityManager.persist(t);
		return t;
	}
	
	@Override
	public T merge(final T t) {
		this.entityManager.merge(t);
		return t;
	}

	@Override
	public void delete(T t) {
		this.entityManager.remove(t);
	}
	
	@Override
	public T find(I id) {
		return this.entityManager.find(entityClass, id);
	}
	
	@Override
	public long count() {
		
		Query query = this.entityManager.createQuery(String.format("SELECT COUNT(%s) FROM %s %s", this.entityClass.getSimpleName(), this.entityClass.getSimpleName(), this.entityClass.getSimpleName()));
				
		return ((Long) query.getSingleResult()).longValue();
		
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
}