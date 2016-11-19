package com.saikrupa.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saikrupa.app.dao.ExpenseTypeDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ExpenseTypeData;

public class DefaultExpenseTypeDAO implements ExpenseTypeDAO {

	public DefaultExpenseTypeDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<ExpenseTypeData> getExpenseTypes() {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		final String sql = "select * from EXPENSE_CATEGORY";
		ArrayList<ExpenseTypeData> list = new ArrayList<ExpenseTypeData>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ExpenseTypeData data = new ExpenseTypeData();
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setDescription(rs.getString(3));
				list.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ExpenseTypeData findExpenseTypeByCode(String code) {
		PersistentManager manager = PersistentManager.getPersistentManager();
		Connection connection = manager.getConnection();
		final String sql = "select * from EXPENSE_CATEGORY where EXP_CAT_CODE = ?";
		ExpenseTypeData data = new ExpenseTypeData();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, code);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				
				data.setCode(rs.getString(1));
				data.setName(rs.getString(2));
				data.setDescription(rs.getString(3));				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
