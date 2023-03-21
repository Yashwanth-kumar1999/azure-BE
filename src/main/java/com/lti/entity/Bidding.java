package com.lti.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
						//surendra
@Entity
@Table(name="Bidding_details")
public class Bidding{

	@Id
	@GeneratedValue
	@Column(name="bid_id")
	private int bidId;
	
	@Column(name="current_bid")
	private int currentBid;
	
	@OneToOne
	@JoinColumn(name="bidder_id")
	private Bidder bidder;
	
	private String status;
	
	
	public String getStatus() {
		return status;
	}	


	public void setStatus(String status) {
		this.status = status;
	}


	@OneToOne
	@JoinColumn(name="request_id")
	private  SellRequest sellRequest;


	public int getBidId() {
		return bidId;
	}


	public void setBidId(int bidId) {
		this.bidId = bidId;
	}


	public int getCurrentBid() {
		return currentBid;
	}


	public void setCurrentBid(int currentBid) {
		this.currentBid = currentBid;
	}


	public Bidder getBidder() {
		return bidder;
	}


	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}


	public SellRequest getSellRequest() {
		return sellRequest;
	}


	public void setSellRequest(SellRequest sellRequest) {
		this.sellRequest = sellRequest;
	}


	


	

	
}
