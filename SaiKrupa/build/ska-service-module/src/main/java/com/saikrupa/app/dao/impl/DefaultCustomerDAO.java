package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.CustomerData;

public class DefaultCustomerDAO implements CustomerDAO {

	public List<CustomerData> findAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomerData findCustomerByCode(String code) {
		String sql = "select code, name, contact_primary, contact_secondary from customer where code = ?";
		
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		CustomerData data = null;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				data = new CustomerData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setPrimaryContact(rs.getString(3));
				data.setSecondaryContact(rs.getString(4));
			}	
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
}
