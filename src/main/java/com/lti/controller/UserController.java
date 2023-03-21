package com.lti.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lti.dao.BidderDao;
import com.lti.dao.BiddingDao;
import com.lti.dao.FarmerDao;
import com.lti.dao.GenericDao;
import com.lti.dao.SellRequestDao;
import com.lti.dao.SoldHistoryDao;
import com.lti.dao.UserDao;
import com.lti.dto.Login;
import com.lti.dto.LoginStatus;
import com.lti.dto.RegisterStatus;
import com.lti.dto.SellRequestStatus;
import com.lti.dto.SoldData;
import com.lti.dto.Statuss;
import com.lti.dto.item;
import com.lti.entity.Bidder;
import com.lti.entity.Bidding;
import com.lti.entity.Farmer;
import com.lti.entity.SellRequest;
import com.lti.entity.Sold_history;
import com.lti.entity.Users;
import com.lti.exception.UserServiceException;
import com.lti.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SoldHistoryDao soldHistoryDao;

	@Autowired
	private GenericDao genericDao;
	@Autowired
	private UserDao userdao;
	@Autowired
	BiddingDao biddingDao;

	@Autowired
	SellRequestDao sellRequestDao;

	@Autowired
	BidderDao bidderDao;

	@RequestMapping("/register.api") // harsha
	public RegisterStatus register(@RequestBody Users user) {
		try {

			int user_id = userService.register(user);
			userService.addUserType(user_id);
			RegisterStatus status = new RegisterStatus();
			status.setStatus(true);
			status.setMessageIfAny("successful!");
			status.setRegisteredUserId(user_id);

			return status;

		} catch (UserServiceException e) {
			RegisterStatus status = new RegisterStatus();
			status.setStatus(true);
			status.setMessageIfAny(e.getMessage());
			return status;
		}
	}
	
	

	@RequestMapping("/loginn.api") /// harsha
	public LoginStatus login(@RequestBody Login login) {
		try {
			Users user = userService.login(login);
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(true);
			loginStatus.setUserType(user.getUserType());
			loginStatus.setUserId(user.getUser_id());
			loginStatus.setUserName(user.getName());
			loginStatus.setMessageIfAny("logged in successfully!");
			return loginStatus;

		} catch (UserServiceException e) {
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(false);
			loginStatus.setMessageIfAny(e.getMessage());
			return loginStatus;

		}
	}

	@GetMapping("/getFarmer/{id}")    //yashwanth  //getting farmer id in farmer welcome page
	public ResponseEntity<Farmer> getFarmer(@PathVariable("id") int id)  {
		
		try {
			Users user= userdao.fetchById(Users.class, id);
			return new ResponseEntity<>(userService.getFarmer(user), HttpStatus.OK);
		} catch(Exception e){
			
		  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}

	
	// farmer place sellRequest page
	@RequestMapping("/sellRequest/{Fid}.api") // yashwanth placing sellRequest
	public SellRequestStatus sellRequest(@RequestBody SellRequest sellRequest, @PathVariable("Fid") int Fid) {
		try {
		int id = userService.placeSellRequest(sellRequest, Fid);
		SellRequestStatus sellRequestStatus = new SellRequestStatus();
		sellRequestStatus.setRequestId(id);
		sellRequestStatus.setStatus(true);
		sellRequestStatus.setMessageIfAny("sell request placed successfully!");
		return sellRequestStatus;
		}
		 catch(Exception e){
				
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
	}
//	
//	@RequestMapping("/sellRequest") // dynamic code   //done     //yashwanth
//	public SellRequestStatus sellRequest(@RequestBody SellRequest sellRequest) {
//		int id = userService.placeSellRequest(sellRequest);
//		SellRequestStatus sellRequestStatus = new SellRequestStatus();
//		sellRequestStatus.setRequestId(id);
//		sellRequestStatus.setStatus(true);
//		sellRequestStatus.setMessageIfAny("sell request placed successfully!");
//		return sellRequestStatus;
//	}

	
	//farmer soldHisotry page
	@GetMapping("/getAllSoldHistory") // riya
	public ResponseEntity<List<Object>> getAll() {
		try {

		return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}

	//filter sellRequest !==aprroved
	@GetMapping("/getFilteredSellRequest") // yashwanth
	public ResponseEntity<List<SellRequest>> getFilteredSellRequest() {
		try {

		return new ResponseEntity<>(userService.getFilteredSellRequest(), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}
 // all sell Requests   //approved //rejected //waitin for appproval
	@GetMapping("/getAllFarmerSellRequest") // yashwanth
	public ResponseEntity<List<SellRequest>> getAllSellRequest() {
		try {

		return new ResponseEntity<>(userService.getFarmerSellRequest(), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}
  //perticular farmer soldHisotry
	@GetMapping("/getAllFarmerSoldHistory/{id}") // riya
	public ResponseEntity<List<Sold_history>> getOne(@PathVariable("id") int id) {
		try {
		Farmer farmer = soldHistoryDao.fetchById(Farmer.class, id);
		return new ResponseEntity<>(userService.getFarmerSoldHistory(farmer), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}

	
	//all bidding details  including approved & rejected
	@GetMapping("/getAllBiddingDetails") // surendra
	public ResponseEntity<List<Object>> getAllBiddingDetails() {
		try {

		return new ResponseEntity<>(userService.getBiddingDetails(), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}

	
	//bidding on perticular farmer sell request
	@GetMapping("/getFarmerBIdding/{id}") // surendra
	public ResponseEntity<List<Bidding>> getFarmerBIdding(@PathVariable("id") int id) {
		try {
		Farmer farmer = biddingDao.fetchById(Farmer.class, id);
		return new ResponseEntity<>(userService.getFarmerBidding(farmer), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
	}

	
	//getting perticular bidder id
	@GetMapping("/getBidder/{id}") // surendra
	public ResponseEntity<Bidder> getBidder(@PathVariable("id") int id) {
		try {

		Users user = userdao.fetchById(Users.class, id);
		return new ResponseEntity<>(userService.getBidder(user), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}

//	
	//updating current bid with latest bid for a perticular bid_no
	@RequestMapping("/updateApi/{id}/{bidderId}/{amount}") // surendra
	public int updateApi(@PathVariable("id") int id, @PathVariable("bidderId") int bidderId,
			@PathVariable("amount") int amount) {
		try {

		int BidId = userService.updateBidding(id, bidderId, amount);
		return BidId;
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}

	// updating sellrequest status on approval
	@Transactional // working code
	@Modifying
	@RequestMapping("/updateSellApi/{id}") // surendra
	public int updateSellApi(@PathVariable("id") int id) {
		try {

		SellRequest sell = genericDao.fetchById(SellRequest.class, id);
		sell.setStatus("approved");
		SellRequest updatedSell = (SellRequest) genericDao.save(sell);
		return updatedSell.getId();
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
	}

	@Modifying
	@Transactional // working code  // updating bidrequest status on appproval
	@RequestMapping("/updateBiddingRequest/{id}") // riya
	public int updateTodo(@PathVariable("id") int id) {
		try {
		Bidding bid = genericDao.fetchById(Bidding.class, id);
		bid.setStatus("approved");
		Bidding updatedBidding = (Bidding) genericDao.save(bid);
		return updatedBidding.getBidId();
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
	}

	// admin bidding approval page
	//   admin bidding approval data
	@GetMapping("/getFilteredBiddingRequest") // filtered bidding //riya
	public ResponseEntity<List<Bidding>> getFilteredBiddingRequest() {
		try {

		return new ResponseEntity<>(userService.getFilteredBiddingRequest(), HttpStatus.OK);
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}

	}
	
	// marketplace sellrequest data=approved
	@GetMapping("/getApprovedFilteredSellRequest")  //yashwanth equal to approve
	public ResponseEntity<List<SellRequest>> getApprovedFilteredSellRequest() {
		try {
			return new ResponseEntity<>(userService.getApprovedSellRequest(), HttpStatus.OK);
		}catch(Exception e) {
			 throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		

	}
	
	

//	@Modifying
//	@Transactional
//	@RequestMapping("/updateSellApi2/{id}")
//	public SellRequest updateSellApi2(@PathVariable("id") int id) {
//		
//		return sellRequestDao.updateSell(id);
//	}

	
	//admin sell request ppage 1
	// logic for passing data from sellrequest to  bidding 
	//updating sellRequest status to approved
	@RequestMapping("/approveSellRequest/{id}") // working code //amarnath
	public int approveSell(@PathVariable("id") int id) {
		try {

		int biddingId = userService.approveSellRequest(id);
		return biddingId;
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
	}

//	@RequestMapping("/approveBiddding/{id}/{amount}")
//	public int approveBidding(@PathVariable("id") int id ,@PathVariable("amount") int amount) {
//		
//		return userService.approveBidding(id, amount);
//		
//	}

//	@RequestMapping("/approveBid/{id}/{amount}")
//	public int approveBidding(@PathVariable("id") int id ,@PathVariable("amount") int amount) {
//
//		
//		return userService.approvedBidding(id, amount);
//		
//	}

//	@RequestMapping("/approveBid")    //working code
//	public int approveBidding() {
//
//		
//	  int soldId= userService.approvedBidding(55, 2000);
//		return soldId;
//	}
	
	//passing data from bidding table to sold history table
	// updating bidding status to approve
	@RequestMapping("/approveBid/{id}/{amount}") // amarnath
	public int approveBidding(@PathVariable("id") int id, @PathVariable("amount") int amount) {
		try {

		int soldId = userService.approvedBidding(id, amount);
		return soldId;
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
	}
	
	//reject button logic //sellRequest
	//updating sell Request status to rejected
	@Transactional    //working code  
    @Modifying
    @RequestMapping("/updateRejectStatus/{id}") //surendra
    public int updateRejectStatus(@PathVariable("id") int id) {
		try {
		

        SellRequest sell= genericDao.fetchById(SellRequest.class,id);
        sell.setStatus("rejected");
        SellRequest updatedSell= (SellRequest) genericDao.save(sell);
    return    updatedSell.getId();
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
    }
	
	//reject button logic //bid request
	//updating bidding request status to rejected
	@Modifying
    @Transactional    // working code
    @RequestMapping("/rejectBiddingRequest/{id}")    //riya
    public int rejectBidding(@PathVariable("id") int id) {
		try {
        Bidding bid= genericDao.fetchById(Bidding.class,id);
        bid.setStatus("rejected");
        Bidding updatedBidding=  (Bidding) genericDao.save(bid);
        return updatedBidding.getBidId();
		}
		catch(Exception e){
			
			  throw	new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
    }

}
