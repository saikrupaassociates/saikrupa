package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.saikrupa.app.dao.ExpenseTypeDAO;
import com.saikrupa.app.dao.impl.DefaultExpenseTypeDAO;
import com.saikrupa.app.dto.ExpenseTypeData;

public class ExpenseTypeModel extends DefaultComboBoxModel<ExpenseTypeData> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpenseTypeModel() {
		super();
		getExpenseTypeData();
	}
	
	private void getExpenseTypeData() {			
		ExpenseTypeDAO dao = new DefaultExpenseTypeDAO();
		List<ExpenseTypeData> dataList = dao.getExpenseTypes();
		for(int i = 0; i < dataList.size(); i++) {
			ExpenseTypeData data = dataList.get(i);
			addElement(data);
		}
		
	}
	
	@Override
    public ExpenseTypeData getSelectedItem() {
		ExpenseTypeData data = (ExpenseTypeData) super.getSelectedItem();
		return data;
	}
	
	@Override
	public void setSelectedItem(Object anObject) {
		ExpenseTypeData data = (ExpenseTypeData) anObject;
		super.setSelectedItem(data);		
	}
}
