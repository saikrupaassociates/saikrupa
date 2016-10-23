package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.saikrupa.app.dto.ExpenseData;
import com.saikrupa.app.dto.InvestorData;
import com.saikrupa.app.util.DateUtil;

public class ExpenseTableModel extends AbstractTableModel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Expense #", "Expense Category", "Amount", "Date", "Paid To", "Payment Mode", "Paid By"};
	
	private List<ExpenseData> expenseDataList;

	public ExpenseTableModel(List<ExpenseData> expenseDataList) {
		this.expenseDataList = expenseDataList;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return expenseDataList.size();
	}

	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		ExpenseData data = expenseDataList.get(row);
		if(col == 0) {
			return data.getCode();
		} else if(col == 1) {
			return data.getExpenseType().getName();
		} else if(col == 2) {
			return data.getAmount();
		} else if(col == 3) {
			return DateUtil.convertToString("dd-MMM-yyyy", data.getExpenseDate());
		} else if(col == 4) {
			return (data.getVendor() == null ? "TBD" : data.getVendor().getName() );
		} else if(col == 5) {
			return data.getPaymentData().getName();
		} else if(col == 6) {
			InvestorData invData = data.getPaidBy();			
			return invData.getName();
		}
		return "---";
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public List<ExpenseData> getExpenseDataList() {
		return expenseDataList;
	}

	public void setExpenseDataList(List<ExpenseData> expenseDataList) {
		this.expenseDataList = expenseDataList;
	}

}
