package com.lti.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import com.lti.dao.BidderDao;
import com.lti.dao.BiddingDao;
import com.lti.dao.FarmerDao;
import com.lti.dao.GenericDao;
import com.lti.dao.SellRequestDao;
import com.lti.dao.SoldHistoryDao;
import com.lti.dao.UserDao;
import com.lti.dto.Login;
import com.lti.entity.Bidder;
import com.lti.entity.Bidding;
import com.lti.entity.Farmer;
import com.lti.entity.SellRequest;
import com.lti.entity.Sold_history;
import com.lti.entity.Users;
import com.lti.exception.UserServiceException;

@Component("userService")
@Transactional
public class UserService {

	
	@Autowired
	private GenericDao genericDao;
	@Autowired
	private SellRequestDao sellRequestDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FarmerDao farmerDao;

	@Autowired
	private BiddingDao biddingDao;

	@Autowired
	private BidderDao bidderDao;

	@Autowired
	private SoldHistoryDao soldHistoryDao;

	public int register(Users user) {

		if (userDao.isUserPresent(user.getEmail())) {
			throw new UserServiceException("user already registered");
		} else {
			Users updatedUser = (Users) userDao.save(user);

			return updatedUser.getUser_id();
		}
	}

	public void addUserType(int user_id) {
		Users userObj = (Users) farmerDao.fetchById(Users.class, user_id);
		if (userObj.getUserType().equals("farmer")) {
			Farmer farmer = new Farmer();
			farmer.setUser(userObj);
			farmerDao.save(farmer);
		}
		if (userObj.getUserType().equals("bidder")) {
			Bidder bidder = new Bidder();
			bidder.setUser(userObj);
			bidderDao.save(bidder);
		}
	}

	public Users login(Login login) {
		try {
			int user_id = userDao.isValidUser2(login.getEmail(), login.getPassword());
			Users user = userDao.fetchById(Users.class, user_id);
			return user;
		} catch (NoResultException e) {
			throw new UserServiceException("invalid email/password");
		}
	}

	public int placeSellRequest(SellRequest sellRequest,int id) {
		try {

			Farmer farmer = (Farmer) farmerDao.fetchById(Farmer.class, id); //102
			sellRequest.setFarmer(farmer);
			SellRequest updatedSellRequest = (SellRequest) sellRequestDao.save(sellRequest);

			return updatedSellRequest.getId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	public List<Object> getAll() {

		return soldHistoryDao.getAllSoldHisotry();

	}

	public List<Sold_history> getFarmerSoldHistory(Farmer farmer) {

		return soldHistoryDao.getAllFarmerSoldHisotry(farmer);

	}

	public List<Object> getBiddingDetails() {
		return biddingDao.getAllBiddingDetails();
	}
	
	public List<Bidding> getFarmerBidding(Farmer farmer){
		return biddingDao.getFarmerBidding(farmer);
	}
	
	public Farmer getFarmer(Users user) {
		return farmerDao.getFarmer(user);
	}
	
	public Bidder getBidder(Users user) {
		return bidderDao.getFarmer(user);
	}
	
	

	
	public List<SellRequest> getFarmerSellRequest(){
		return  sellRequestDao.getAllRequest();
	}
	
	public List<SellRequest> getFilteredSellRequest(){    //not equal to approve   //filtered sellReqquest
		return  sellRequestDao.getAllFilteredRequest();
	}
	
	public List<SellRequest> getApprovedSellRequest(){     //filtered sellReqquest
		return  sellRequestDao.getAllApprovedRequest();
	}


	
	public int updateBidding(int biddingId,int bidderId,int amount) {
		
		Bidder bidder= genericDao.fetchById(Bidder.class, bidderId);
//		SellRequest sellRequest=genericDao.fetchById(SellRequest.class,sellRequestId );
		Bidding bidding= genericDao.fetchById(Bidding.class, biddingId);
		bidding.setCurrentBid(amount);
		bidding.setBidder(bidder);
		bidding.setStatus("waiting for approval");
		
		Bidding updatedbidding=(Bidding) biddingDao.save(bidding);
			
		return updatedbidding.getBidId();
	}
	
//	public int approveSellRequest(int id) {     xxx
//		
//		SellRequest updatedSell= genericDao.fetchById(SellRequest.class, id);
//				
//			Bidding newbidding= new Bidding();
//			
//			newbidding.setSellRequest(updatedSell);
//		Bidding  approvedbidding =  (Bidding) genericDao.save(newbidding);
//		
//		return approvedbidding.getBidId();
//	}
	
	
	
//public int approveSellRequest(int id) {      //working code
//		
//		SellRequest updatedSell= genericDao.fetchById(SellRequest.class, id);
//				
//		updatedSell.setStatus("approved");
//	    SellRequest newSell=(SellRequest) 	genericDao.save(updatedSell);
//			Bidding newbidding= new Bidding();
//			
//			newbidding.setSellRequest(newSell);
//			
//		Bidding  approvedbidding =  (Bidding) genericDao.save(newbidding);
//		
//		return approvedbidding.getBidId();
//	}
public int approveSellRequest(int id) {
		
		SellRequest updatedSell= genericDao.fetchById(SellRequest.class, id);
				
		updatedSell.setStatus("approved");
	    SellRequest newSell=(SellRequest) 	genericDao.save(updatedSell);
			Bidding newbidding= new Bidding();
			
			newbidding.setSellRequest(newSell);
			newbidding.setStatus("waiting for approval");
			
		Bidding  approvedbidding =  (Bidding) genericDao.save(newbidding);
		
		return approvedbidding.getBidId();
	}
	
public int approvedBidding(int id,int amount) {
		
	SellRequest sellRequest= genericDao.fetchById(SellRequest.class, id);
	
	Sold_history sold= new Sold_history();
	sold.setSellRequest(sellRequest);
	sold.setDate(LocalDate.now());
	sold.setSoldPrice(amount);
	Sold_history updatedSold= (Sold_history) genericDao.save(sold);
	
		return updatedSold.getSoldId();
	}
	
	public List<Bidding> getFilteredBiddingRequest(){
		
		return biddingDao.getAllFilteredRequest();
	}
	
	
	
	
	
	


}
