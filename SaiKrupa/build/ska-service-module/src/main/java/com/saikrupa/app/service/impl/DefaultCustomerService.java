package com.saikrupa.app.service.impl;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.OrderDAO;
import com.saikrupa.app.dao.impl.DefaultOrderDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.AddressType;
import com.saikrupa.app.dto.CustomerData;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.service.CustomerService;



public class DefaultCustomerService implements CustomerService {

	public DefaultCustomerService() {
		// TODO Auto-generated constructor stub
	}

	public List<CustomerData> getAllCustomers() {
		String sql_sel_customer = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY FROM CUSTOMER";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<CustomerData> customerList = new ArrayList<CustomerData>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql_sel_customer);
			ResultSet records = stmt.executeQuery();
			while (records.next()) {
				CustomerData customer = new CustomerData();
				customer.setCode(records.getString(1));
				customer.setName(records.getString(2));
				customer.setPrimaryContact(records.getString(3));
				AddressData address = getCustomerDeliveryAddressByCode(customer.getCode());
				customer.setAddress(address);
				customerList.add(customer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customerList;
	}

	public AddressData getCustomerDeliveryAddressByCode(final String customerCode) {
		final String sql = "SELECT ADDRESS_CODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LANDMARK, ADDRESS_ZIP, ADDRESS_TYPE  FROM customer_address where customer_code = ?";
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		AddressData address = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, customerCode);
			ResultSet records = stmt.executeQuery();
			while (records.next()) {	
				address = new AddressData();
				address.setCode(records.getString(1));
				address.setLine1(records.getString(2));
				address.setLine2(records.getString(3));
				address.setLandmark(records.getString(4));
				address.setZipCode(records.getString(5));
				address.setAddressType(AddressType.DELIVERY);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}

	public CustomerData getCustomerByCode(String customerCode) {
		String sql_sel_customer = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY FROM CUSTOMER where CODE=?";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		CustomerData customer = null;
		try {
			
			PreparedStatement stmt = connection.prepareStatement(sql_sel_customer);
			stmt.setInt(1, Integer.valueOf(customerCode));
			ResultSet records = stmt.executeQuery();
			while (records.next()) {
				customer = new CustomerData();
				customer.setCode(records.getString(1));
				customer.setName(records.getString(2));
				customer.setPrimaryContact(records.getString(3));
				AddressData address = getCustomerDeliveryAddressByCode(customer.getCode());
				customer.setAddress(address);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customer;
	}

	public List<CustomerData> getAllCustomersByOrders() {
		OrderDAO orderDAO = new DefaultOrderDAO();
		List<CustomerData> orderCustomers = new ArrayList<CustomerData>();
		List<OrderData> orders = orderDAO.findAllOrders();
		for(OrderData order : orders) {
			CustomerData customer = order.getCustomer();			
			AddressData deliveryAddress = order.getDeliveryAddress();
			customer.setAddress(deliveryAddress);
			orderCustomers.add(customer);
		}
		return orderCustomers;
	}
}
