package com.saikrupa.app.dao;

import java.util.List;

import com.saikrupa.app.dto.ExpenseData;

public interface ExpenseDAO {
	public List<ExpenseData> findAllExpenses();
	public ExpenseData findExpenseByCode(String expenseCode);
}
