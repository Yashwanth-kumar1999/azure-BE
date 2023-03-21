package com.lti.entity;
								//yashwanth
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Farmer")
public class Farmer {

	@Id
	@GeneratedValue
	private int farmer_id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private Users user;

	@OneToMany(mappedBy="farmer")
	private List<SellRequest> sellRequest;
	
	public int getFarmer_id() {
		return farmer_id;
	}

	public void setFarmer_id(int farmer_id) {
		this.farmer_id = farmer_id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
}
