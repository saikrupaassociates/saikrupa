package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.ApplicationUserDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ApplicationUserData;
import com.saikrupa.app.security.SecurityUtil;

public class DefaultApplicationUserDAO implements ApplicationUserDAO {

	public DefaultApplicationUserDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<ApplicationUserData> findAllUsers() {
		String sql = "SELECT CODE, PASSCODE, NAME, CONTACT, CREATED_DATE, USER_ROLE FROM APPLICATION_USER";

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
				userData.setUserRole(rs.getInt(6));
				userDataCol.add(userData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDataCol;
	}

	public ApplicationUserData findUserByCode(String code) {
		String sql = "SELECT CODE, PASSCODE, NAME, CONTACT, CREATED_DATE, USER_ROLE FROM APPLICATION_USER WHERE CODE=?";

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
				userData.setPassword(SecurityUtil.decrypt(rs.getString(2)).toCharArray());
				userData.setName(rs.getString(3));
				userData.setContactNumber(rs.getLong(4));
				userData.setCreatedDate((java.util.Date)rs.getDate(5));
				userData.setUserRole(rs.getInt(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userData;
	}

	public void changePassword(ApplicationUserData user) throws Exception {
		String sql = "UPDATE APPLICATION_USER SET PASSCODE= ? WHERE CODE=?";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, SecurityUtil.encrypt(String.valueOf(user.getPassword())));
			ps.setString(2, user.getUserId());
			int count = ps.executeUpdate();
			if(count  < 1) {
				throw new Exception("Password could not be updated");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
