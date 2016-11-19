package com.saikrupa.app.dao.impl;

import java.util.List;

import com.saikrupa.app.dto.CustomerData;

public interface CustomerDAO {
	public List<CustomerData> findAllCustomers();
	
	public CustomerData findCustomerByCode(String code);
}
