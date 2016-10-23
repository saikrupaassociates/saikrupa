package com.saikrupa.app.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.InvestmentData;
import com.saikrupa.app.dto.InvestorData;
import com.saikrupa.app.service.InvestorService;

public class DefaultInvestorService implements InvestorService {

	public DefaultInvestorService() {
		// TODO Auto-generated constructor stub
	}

	public InvestorData createInvestor(InvestorData investor) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		connection.setAutoCommit(false);

		final String SQL_CREATE_VENDOR = "INSERT INTO INVESTOR (NAME, CONTACT_PRIMARY, CONTACT_SECONDARY) VALUES(?,?,?)";
		PreparedStatement ps = connection.prepareStatement(SQL_CREATE_VENDOR, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, investor.getName());
		ps.setString(2, investor.getPrimaryContact());
		ps.setString(3,  investor.getSecondaryContact());
		if(!investor.getInvestments().isEmpty()) {
			addInvestment(investor.getInvestments().get(0));
		}
		//investor.getInvestments().cl
		int rowCount = ps.executeUpdate();
		if (rowCount > 0) {
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			String code = keys.getString(1);
			investor.setCode(code);
		} else {			
			throw new Exception("Investor Data could not be loaded");
		}
		ps.close();
		return investor;
	}

	public InvestorData updateInvestor(InvestorData investor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public InvestmentData addInvestment(InvestmentData investment) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		connection.setAutoCommit(false);

		final String SQL_CREATE_VENDOR = "INSERT INTO INVESTMENT (NAME, INVESTED_ON, AMOUNT, INVESTOR_CODE) VALUES(?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(SQL_CREATE_VENDOR, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, investment.getInvestor().getCode());
		ps.setDate(2, new java.sql.Date(investment.getInvestmentDate().getTime()));
		ps.setDouble(3, investment.getAmount());
		ps.setString(4,  investment.getInvestor().getCode());
		
		int rowCount = ps.executeUpdate();
		if (rowCount > 0) {
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			String code = keys.getString(1);
			investment.setCode(code);
			connection.commit();
		} else {
			ps.close();
			throw new Exception("Investor Data could not be loaded");
		}
		ps.close();
		return investment;
	}

	public List<InvestmentData> addInvestments(List<InvestmentData> investments) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
