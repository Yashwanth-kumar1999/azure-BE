package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;
				//surendra
@Entity
@Table(name="Bidder")
public class Bidder {

	@Id
	@GeneratedValue
	private int bidder_id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private Users user;

	public int getBidder_id() {
		return bidder_id;
	}

	public void setBidder_id(int bidder_id) {
		this.bidder_id = bidder_id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}


	
	
}
