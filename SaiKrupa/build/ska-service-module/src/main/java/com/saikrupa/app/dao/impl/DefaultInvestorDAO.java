package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.InvestorDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.InvestmentData;
import com.saikrupa.app.dto.InvestorData;

public class DefaultInvestorDAO implements InvestorDAO {

	public DefaultInvestorDAO() {
		// TODO Auto-generated constructor stub
	}

	public InvestorData findInvestorByName(String name) {
		List<InvestorData> invList = new ArrayList<InvestorData>();
		final String sql = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY FROM INVESTOR where name like '%?%'";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				InvestorData data = new InvestorData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setPrimaryContact(rs.getString(3));
				data.setSecondaryContact(rs.getString(4));
				invList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (invList.isEmpty() ? null : invList.get(0));
	}

	public List<InvestorData> findInvestors() {
		List<InvestorData> invList = new ArrayList<InvestorData>();
		final String sql = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY FROM INVESTOR";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				InvestorData data = new InvestorData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setPrimaryContact(rs.getString(3));
				data.setSecondaryContact(rs.getString(4));
				List<InvestmentData> investments = findInvestmentsByInvestor(data);
				data.setInvestments(investments);
				invList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invList;
	}

	public List<InvestmentData> findInvestmentsByInvestor(InvestorData investor) {
		List<InvestmentData> invList = new ArrayList<InvestmentData>();
		final String sql = "SELECT CODE, NAME, INVESTED_ON, AMOUNT FROM INVESTMENT WHERE INVESTOR_CODE = ? ORDER BY INVESTED_ON DESC";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, investor.getCode());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				InvestmentData data = new InvestmentData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setInvestmentDate(rs.getDate(3));
				data.setAmount(rs.getDouble(4));
				invList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invList;
	}

	public InvestorData findInvestorByCode(String code) {		
		final String sql = "SELECT CODE, NAME, CONTACT_PRIMARY, CONTACT_SECONDARY FROM INVESTOR where code = ?";

		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		InvestorData data = new InvestorData();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {				
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setPrimaryContact(rs.getString(3));
				data.setSecondaryContact(rs.getString(4));
				List<InvestmentData> investments = findInvestmentsByInvestor(data);
				data.setInvestments(investments);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	

}
