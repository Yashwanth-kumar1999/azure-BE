package com.lti.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.lti.entity.SellRequest;

@Component("sellReq")
public class SellRequestDao extends GenericDao {

	
	public List<SellRequest> getAllRequest(){
		
		return  entityManager.createQuery("select s from SellRequest s").getResultList();
	}
	
public List<SellRequest> getAllFilteredRequest(){  //filtered   admin dashboard logic
		
		return  entityManager.createQuery("select s from SellRequest s where s.status!='approved' and s.status!='rejected' ").getResultList();
	}


public List<SellRequest> getAllApprovedRequest(){  //filtered eqi
	
	return  entityManager.createQuery("select s from SellRequest s where s.status='approved' ").getResultList();
}

//	public SellRequest updateSell(int id) {
//		return (SellRequest) entityManager.createQuery("update SellRequest sr set sr.status=approved where sr.id=:id").setParameter("id", id) .getSingleResult();
//	}
	
}
