package com.saikrupa.app.dao;

import java.util.List;

import com.saikrupa.app.dto.ExpenseTypeData;

public interface ExpenseTypeDAO {
	public List<ExpenseTypeData> getExpenseTypes();
	public ExpenseTypeData findExpenseTypeByCode(String code);
}
