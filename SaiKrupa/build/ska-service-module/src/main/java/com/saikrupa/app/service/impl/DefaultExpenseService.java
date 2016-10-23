package com.saikrupa.app.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ExpenseData;
import com.saikrupa.app.service.ExpenseService;

public class DefaultExpenseService implements ExpenseService {

	public DefaultExpenseService() {
		// TODO Auto-generated constructor stub
	}

	public ExpenseData createExpense(ExpenseData expenseData) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();

		connection.setAutoCommit(false);
		
		final String SQL_CREATE_EXPENSE = "INSERT INTO EXPENSE (EXPENSE_DATE, AMOUNT, VENDOR_CODE, INVESTOR_CODE, CATEGORY_CODE, NOTES, PAYMEMT_CODE) VALUES(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(SQL_CREATE_EXPENSE, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setDate(1, new java.sql.Date(expenseData.getExpenseDate().getTime()));
		ps.setDouble(2, expenseData.getAmount());
		ps.setInt(3,  Integer.valueOf(expenseData.getVendor().getCode()).intValue());
		ps.setInt(4,  Integer.valueOf(expenseData.getPaidBy().getCode()).intValue());
		ps.setInt(5,  Integer.valueOf(expenseData.getExpenseType().getCode()).intValue());
		ps.setString(6, expenseData.getComments());		
		ps.setInt(7,  Integer.valueOf(expenseData.getPaymentData().getCode()).intValue());
		
		int rowCount = ps.executeUpdate();
		if (rowCount > 0) {
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			String code = keys.getString(1);
			expenseData.setCode(code);
			connection.commit();
		} else {	
			connection.rollback();
			throw new Exception("Expense Data could not be persisted");			
		}
		ps.close();
		return expenseData;
	}

	public ExpenseData updateExpense(ExpenseData expenseData) throws Exception {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		
		connection.setAutoCommit(false);
		final String SQL_CREATE_EXPENSE = "UPDATE EXPENSE SET EXPENSE_DATE=?, AMOUNT=?, VENDOR_CODE=?, INVESTOR_CODE=?, CATEGORY_CODE=?, NOTES=?, PAYMEMT_CODE = ? WHERE CODE = ?";
		PreparedStatement ps = connection.prepareStatement(SQL_CREATE_EXPENSE);
		ps.setDate(1, new java.sql.Date(expenseData.getExpenseDate().getTime()));
		ps.setDouble(2, expenseData.getAmount());
		ps.setInt(3,  Integer.valueOf(expenseData.getVendor().getCode()).intValue());
		ps.setInt(4,  Integer.valueOf(expenseData.getPaidBy().getCode()).intValue());
		ps.setInt(5,  Integer.valueOf(expenseData.getExpenseType().getCode()).intValue());
		ps.setString(6, expenseData.getComments());		
		ps.setInt(7,  Integer.valueOf(expenseData.getPaymentData().getCode()).intValue());
		ps.setInt(8,  Integer.valueOf(expenseData.getCode()).intValue());
		
		int rowCount = ps.executeUpdate();
		if (rowCount > 0) {			
			connection.commit();
		} else {	
			connection.rollback();
			throw new Exception("Expense Data could not be persisted");			
		}
		ps.close();
		return expenseData;
	}

}
