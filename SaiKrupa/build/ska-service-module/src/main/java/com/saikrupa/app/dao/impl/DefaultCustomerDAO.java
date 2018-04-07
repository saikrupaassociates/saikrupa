package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.DefaultPaymentModeDAO;
import com.saikrupa.app.dao.PaymentModeDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.CustomerData;
import com.saikrupa.app.dto.PaymentEntryData;
import com.saikrupa.app.dto.PaymentTypeData;

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

	public List<PaymentEntryData> findAdhocPaymentByCustomer(String customerCode) {
		String sql = "SELECT CODE, AD_PAYMENT_ENTRY_NO, AD_PAYABLE_AMOUNT, "
				+ "PAID_AMOUNT, PAYMENT_DATE, AD_ADJUSTED, PAYMENT_MODE, CHEQUE_NO "
				+ "FROM ADHOC_PAYMENT_ENTRY WHERE CUSTOMER_CODE = ?";
		
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		List<PaymentEntryData> entries = new ArrayList<PaymentEntryData>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, customerCode);
			ResultSet rs = ps.executeQuery();
			PaymentModeDAO paymentModeDAO = new DefaultPaymentModeDAO();

			while(rs.next()) {
				PaymentEntryData data = new PaymentEntryData();
				data.setCode(rs.getInt(1));
				data.setEntryNumber(rs.getInt(2));
				data.setPayableAmount(rs.getDouble(3));
				data.setAmount(rs.getDouble(4));
				data.setPaymentDate(rs.getDate(5));
				data.setAdjusted(rs.getInt(6) == 1 ? true : false);
				data.setPaymentMode(paymentModeDAO.getPaymentModeByCode(rs.getString(7)));
				data.setChequeNumber(rs.getString(8));
				
				entries.add(data);
			}	
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return entries;
	}
}
