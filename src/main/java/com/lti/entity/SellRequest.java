package com.lti.entity;
								//riya
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sell_request")
public class SellRequest {

	@Id
	@GeneratedValue
	@Column(name="request_id")
	private int id;
	


	@Column(name = "crop_type")
	private String cropType;

	@Column(name = "base_price")
	private int basePrice;

	@Column(name = "crop_name")
	private String cropName;

	@Column(name = "fertilizer_type")
	private String fertilizerType;

	private int quantity;

	@Column(name = "Soil_ph_certificate_no")
	private int soilCertNo;

	

	private String status;

	
	@ManyToOne
	@JoinColumn(name ="farmer_id")
	private Farmer farmer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
	}

	public int getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getFertilizerType() {
		return fertilizerType;
	}

	public void setFertilizerType(String fertilizerType) {
		this.fertilizerType = fertilizerType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSoilCertNo() {
		return soilCertNo;
	}

	public void setSoilCertNo(int soilCertNo) {
		this.soilCertNo = soilCertNo;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	
}