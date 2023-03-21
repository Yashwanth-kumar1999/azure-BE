package com.lti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.lti.entity.Users;

@Component
public class UserDao extends GenericDao {

	public boolean isUserPresent(String email) {
		return (Long) entityManager.createQuery("select count(c) from Users c where c.email=:em")
				                   .setParameter("em", email)
				                   .getSingleResult() == 1 ? true : false;
	}
	
	public boolean isValidUser(String email,String password) {
		return (Long) entityManager
									.createQuery("select c.user_id from Users c where c.email=:em and c.password=:pw")
									.setParameter("em", email)
									.setParameter("pw", password)
									.getSingleResult()==1?true:false;
	}

	
	public int isValidUser2(String email,String password) {
		
		return (Integer) entityManager
				.createQuery("select c.user_id from Users c where c.email=:em and c.password=:pw")
				.setParameter("em", email)
				.setParameter("pw", password)
				.getSingleResult();
	}
	public List<Users> fetchUsers() {
		return entityManager.createQuery("select c from Users c").getResultList();
	}

}

