package com.lti.dao;

import org.springframework.stereotype.Component;

import com.lti.entity.Bidder;
import com.lti.entity.Farmer;
import com.lti.entity.Users;

@Component("bidderDao")
public class BidderDao extends GenericDao {

	
	public Bidder getFarmer(Users user) {
		return (Bidder) entityManager.createQuery("select s from Bidder s where s.user=:id").setParameter("id", user).getSingleResult();
	}
}
