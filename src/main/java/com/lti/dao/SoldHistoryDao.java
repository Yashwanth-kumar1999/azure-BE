package com.lti.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.lti.entity.Farmer;
import com.lti.entity.Sold_history;

@Component
public class SoldHistoryDao extends GenericDao {

	public List<Object> getAllSoldHisotry(){
	
		return entityManager.createQuery("select s from Sold_history s").getResultList();
		
	}
	public List<Sold_history> getAllFarmerSoldHisotry(Farmer farmer){
		
//	Query q=entityManager.createQuery("select s.date,s.msp,s.soldPrice,s.totalPrice,a.cropName,a.quantity from Sold_history s join s.sellRequest a  where a.farmer=53");
	
	return	entityManager.createQuery("select s from Sold_history s join s.sellRequest a  where a.farmer=:no").setParameter("no", farmer).getResultList();
		
	}
	
}
