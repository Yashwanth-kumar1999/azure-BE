package com.lti.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;


@Component
public class GenericDao {

	@PersistenceContext
	protected EntityManager entityManager;
	
	public Object save(Object obj) {
	return	entityManager.merge(obj);
		}
	public <E> E fetchById(Class<E> clazz,Object pk) {
		E e=entityManager.find(clazz, pk);
		
		return e;
	}
	
}
