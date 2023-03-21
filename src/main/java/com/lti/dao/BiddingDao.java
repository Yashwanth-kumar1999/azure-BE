package com.lti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import com.lti.entity.Bidding;
import com.lti.entity.Farmer;
import com.lti.entity.SellRequest;
import com.lti.entity.Sold_history;


@Component
public class BiddingDao extends GenericDao {
	
	

	public List<Object> getAllBiddingDetails(){
		
		return entityManager.createQuery("select s from Bidding s").getResultList();
	}
	
	public List<Bidding> getFarmerBidding(Farmer farmer){
		
//		Query q=entityManager.createQuery("select s.date,s.msp,s.soldPrice,s.totalPrice,a.cropName,a.quantity from Sold_history s join s.sellRequest a  where a.farmer=53");
		
		return	entityManager.createQuery("select s from Bidding s join s.sellRequest a  where a.farmer=:no").setParameter("no", farmer).getResultList();
			
		}
	
public List<Bidding> getAllFilteredRequest(){  //filtered 
		
		return  entityManager.createQuery("select s from Bidding s where s.status!='approved' and s.status!='rejected' ").getResultList();
	}

	

}
