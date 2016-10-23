package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.DefaultPaymentModeDAO;
import com.saikrupa.app.dao.ExpenseDAO;
import com.saikrupa.app.dao.ExpenseTypeDAO;
import com.saikrupa.app.dao.InvestorDAO;
import com.saikrupa.app.dao.PaymentModeDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ExpenseData;
import com.saikrupa.app.dto.ExpenseTypeData;
import com.saikrupa.app.dto.InvestorData;
import com.saikrupa.app.dto.PaymentTypeData;
import com.saikrupa.app.dto.VendorData;
import com.saikrupa.app.service.impl.DefaultVendorService;

public class DefaultExpenseDAO implements ExpenseDAO {

	public DefaultExpenseDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<ExpenseData> findAllExpenses() {
		List<ExpenseData> expList = new ArrayList<ExpenseData>();
		final String sql = "SELECT CODE, EXPENSE_DATE, AMOUNT, VENDOR_CODE, INVESTOR_CODE, CATEGORY_CODE, PAYMEMT_CODE, NOTES FROM EXPENSE ORDER BY EXPENSE_DATE DESC";
		
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ExpenseData data = new ExpenseData();
				data.setCode(rs.getString(1));
				data.setExpenseDate((java.util.Date)rs.getDate(2));
				data.setAmount(rs.getDouble(3));
				
				String vendorCode = rs.getString(4);
				DefaultVendorService vendorService = new DefaultVendorService();
				VendorData vendor = vendorService.findVendorByCode(vendorCode);
				data.setVendor(vendor);
				
				String paidByCode = rs.getString(5);
				InvestorDAO investorDAO = new DefaultInvestorDAO();
				InvestorData investor = investorDAO.findInvestorByCode(paidByCode);
				data.setPaidBy(investor);
				
				String categoryCode = rs.getString(6);
				ExpenseTypeDAO expTypeDAO = new DefaultExpenseTypeDAO();
				ExpenseTypeData expType = expTypeDAO.findExpenseTypeByCode(categoryCode);
				data.setExpenseType(expType);
				
				
				String paymentMode = rs.getString(7);
				PaymentModeDAO paymentModeDAO = new DefaultPaymentModeDAO();
				PaymentTypeData paymentData = paymentModeDAO.getPaymentModeByCode(paymentMode);
				data.setPaymentData(paymentData);
				
				data.setComments(rs.getString(8));
				expList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expList;
	}

	public ExpenseData findExpenseByCode(String expenseCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
