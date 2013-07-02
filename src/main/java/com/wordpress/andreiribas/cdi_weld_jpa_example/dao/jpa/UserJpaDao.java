/**
 * 
 */
package com.wordpress.andreiribas.cdi_weld_jpa_example.dao.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.wordpress.andreiribas.cdi_weld_jpa_example.dao.UserDao;
import com.wordpress.andreiribas.cdi_weld_jpa_example.domain.User;

/**
 * @author Andrei Gonçalves Ribas
 *
 */
@Named
@ApplicationScoped
public class UserJpaDao extends GenericJpaDao<User, Long> implements UserDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2163306125212923115L;
	
	protected UserJpaDao() {}
	
	@Inject
	public UserJpaDao(EntityManager entityManager) {
		super(entityManager, User.class);
	}
	
}
