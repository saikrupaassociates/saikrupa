package com.saikrupa.app.dto;

import java.util.Date;

public class ApplicationUserData {

	private String userId;
	private String name;
	private Long contactNumber;
	private char[] password;
	private Date createdDate;
	
	private int userRole;
	
	private boolean admin;
	
	public ApplicationUserData() {
		
	}
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}



	public char[] getPassword() {
		return password;
	}



	public void setPassword(char[] password) {
		this.password = password;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public int getUserRole() {
		return userRole;
	}



	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}



	public boolean isAdmin() {
		return (getUserRole() == 1000 ? true : false);
	}



	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
