package com.saikrupa.app.dto;

public class ContactPerson {
	private String code;
	private String name;
	private String primaryContact;
	private String secondaryContact;
	
	private VendorData vendor;
	
	private boolean active;
	
	private AddressData address;
	
	public ContactPerson() {
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getSecondaryContact() {
		return secondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	public AddressData getAddress() {
		return address;
	}

	public void setAddress(AddressData address) {
		this.address = address;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public VendorData getVendor() {
		return vendor;
	}

	public void setVendor(VendorData vendor) {
		this.vendor = vendor;
	}

	

}
