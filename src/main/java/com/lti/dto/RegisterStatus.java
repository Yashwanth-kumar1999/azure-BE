package com.lti.dto;

public class RegisterStatus {

	private boolean status;
	private String messageIfAny;
	private int registeredUserId;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessageIfAny() {
		return messageIfAny;
	}
	
	public void setMessageIfAny(String messageIfAny) {
		this.messageIfAny = messageIfAny;
	}
	
	public int getRegisteredUserId() {
		return registeredUserId;
	}
	
	public void setRegisteredUserId(int registeredUserId) {
		this.registeredUserId = registeredUserId;
	}
	
	
}
