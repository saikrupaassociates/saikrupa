package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.ApplicationUserDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ApplicationUserData;

public class DefaultApplicationUserDAO implements ApplicationUserDAO {

	public DefaultApplicationUserDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<ApplicationUserData> findAllUsers() {
		String sql = "SELECT CODE, PASSCODE, NAME, CONTACT, CREATED_DATE FROM APPLICATION_USER";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();	
		List<ApplicationUserData> userDataCol = new ArrayList<ApplicationUserData>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ApplicationUserData userData = new ApplicationUserData();
				userData.setUserId(rs.getString(1));				
				userData.setPassword(rs.getString(2).toCharArray());
				userData.setName(rs.getString(3));
				userData.setContactNumber(rs.getLong(4));
				userData.setCreatedDate((java.util.Date)rs.getDate(5));
				userDataCol.add(userData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDataCol;
	}

	public ApplicationUserData findUserByCode(String code) {
		String sql = "SELECT CODE, PASSCODE, NAME, CONTACT, CREATED_DATE FROM APPLICATION_USER WHERE CODE=?";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();		
		ApplicationUserData userData = null;
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userData = new ApplicationUserData();	
				userData.setUserId(rs.getString(1));				
				userData.setPassword(rs.getString(2).toCharArray());
				userData.setName(rs.getString(3));
				userData.setContactNumber(rs.getLong(4));
				userData.setCreatedDate((java.util.Date)rs.getDate(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userData;
	}

}
