/**
 * 
 */
package com.wordpress.andreiribas.cdi_weld_jpa_example.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

/**
 * @author Andrei Gonçalves Ribas
 *
 */
public interface GenericDao<T, I extends Serializable> {

	T save(T t);

    void delete(T t);

    T find(I id);
    
    long count();

	T merge(T t);
	
	EntityManager getEntityManager();
    
}
