package com.saikrupa.app.service;

import com.saikrupa.app.dto.ExpenseData;

public interface ExpenseService {
	public ExpenseData createExpense(ExpenseData expenseData) throws Exception;
	public ExpenseData updateExpense(ExpenseData expenseData) throws Exception;
}
