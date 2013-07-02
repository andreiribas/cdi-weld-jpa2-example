/**
 * 
 */
package com.wordpress.andreiribas.cdi_weld_jpa_example.dao.jpa;


import java.util.Date;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.wordpress.andreiribas.cdi_weld_jpa_example.cdi.junit.WeldJUnit4Runner;
import com.wordpress.andreiribas.cdi_weld_jpa_example.dao.UserDao;
import com.wordpress.andreiribas.cdi_weld_jpa_example.domain.User;

/**
 * @author Andrei Gonçalves Ribas
 *
 */
@RunWith(WeldJUnit4Runner.class)
public class UserDaoTest {
		
	@Inject
	private UserDao fixture;

	@Test
	public void testCreateNewUser() {
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at ", new Date().getTime()));
		
		newUser = fixture.save(newUser);
		
		TestCase.assertNotNull(newUser);
		
	}
	
	@Test
	public void testCreateNewUserAndUpdateItsNameWithoutMerge() {
		
		fixture.getEntityManager().getTransaction().begin();
		
		long numberOfUsers = fixture.count();
			
		TestCase.assertNotSame(-1L, numberOfUsers);
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at %s.", new Date().getTime()));
		
		fixture.save(newUser);
		
		//new user is managed by jpa now.
		
		fixture.getEntityManager().getTransaction().commit();
		
		//newUser is updated without using merge, because it`s already managed
		newUser.setName(String.format("Old User updated at %s.", new Date().getTime()));
		
		fixture.getEntityManager().getTransaction().begin();
		
		Long id = newUser.getId();
		
		TestCase.assertNotNull(id);
		
		User updatedUser = fixture.find(id);
		
		TestCase.assertNotNull(updatedUser);
		
		TestCase.assertTrue(updatedUser.getName().startsWith("Old User updated at "));
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		TestCase.assertEquals(numberOfUsers + 1, fixture.count());
		
		fixture.getEntityManager().getTransaction().commit();
		
	}
	
	@Test
	public void testCreateNewUserAndUpdateItsNameWithMerge() {
		
		fixture.getEntityManager().getTransaction().begin();
		
		long numberOfUsers = fixture.count();
			
		TestCase.assertNotSame(-1L, numberOfUsers);
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at %s.", new Date().getTime()));
		
		fixture.save(newUser);
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		Long id = newUser.getId();
		
		TestCase.assertNotNull(id);
		
		User updatedUser = new User(id);
		
		updatedUser.setName(String.format("Old User updated at %s.", new Date().getTime()));
			
		//newUser is updated using merge, because it`s already managed
		
		updatedUser = fixture.merge(updatedUser);
		
		TestCase.assertNotNull(updatedUser);
		
		TestCase.assertTrue(updatedUser.getName().startsWith("Old User updated at "));
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		updatedUser = fixture.find(id);
		
		TestCase.assertTrue(updatedUser.getName().startsWith("Old User updated at "));
		
		TestCase.assertEquals(numberOfUsers + 1, fixture.count());
		
		fixture.getEntityManager().getTransaction().commit();
		
	}
	
	@Test
	public void testCreateNewUserAndDeleteIt() {
		
		fixture.getEntityManager().getTransaction().begin();
		
		long numberOfUsers = fixture.count();
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at ", new Date().getTime()));
		
		newUser = fixture.save(newUser);
		
		TestCase.assertNotNull(newUser);
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		Long id = newUser.getId();
		
		User updatedUser = fixture.find(id);
		
		fixture.delete(updatedUser);
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		TestCase.assertEquals(numberOfUsers, fixture.count());
		
		fixture.getEntityManager().getTransaction().commit();
		
	}
	
}
