package com.saikrupa.app.dto;

public class CustomerData extends ContactPerson {
	
	private AddressData billingAddress;
	
	public CustomerData() {
		// TODO Auto-generated constructor stub
	}

	public AddressData getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressData billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	public String toString() {
		return getName()+"||"+getPrimaryContact()+"||"+getAddress().getLine1()+"||"+getAddress().getLine2()+"||"+getAddress().getLandmark()+"||"+getAddress().getZipCode();
	}

}
