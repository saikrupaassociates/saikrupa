package com.saikrupa.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.PaymentTypeData;

public class DefaultPaymentModeDAO implements PaymentModeDAO {

	public DefaultPaymentModeDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<PaymentTypeData> getPaymentModes() {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		final String sql = "select * from PAYMENT_MODE";
		ArrayList<PaymentTypeData> list = new ArrayList<PaymentTypeData>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				PaymentTypeData data = new PaymentTypeData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));				
				list.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public PaymentTypeData getPaymentModeByCode(String code) {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		final String sql = "select * from PAYMENT_MODE where PAYMEMT_CODE = ?";
		PaymentTypeData data = new PaymentTypeData();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, Integer.valueOf(code));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
