package com.saikrupa.app.ui.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.saikrupa.app.dto.InvestmentData;

public class InvestmentTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Investment Amount", "Investment Date", "Currency" };

	private List<InvestmentData> investmentDataList;

	public InvestmentTableModel(List<InvestmentData> investmentDataList) {
		this.investmentDataList = investmentDataList;	
	}

	

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return investmentDataList.size();
	}

	public Object getValueAt(int row, int col) {		
		// TODO Auto-generated method stub
		InvestmentData data = investmentDataList.get(row);
		
		if (col == 0) {
			return data.getAmount();
		} else if (col == 1) {						
			return getFormattedDate(data.getInvestmentDate());
		} else if (col == 2) {
			return "INR";
		} 
		return "---";
	}
	
	private String getFormattedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		return sdf.format(date);
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}



	public List<InvestmentData> getInvestmentDataList() {
		return investmentDataList;
	}



	public void setInvestmentDataList(List<InvestmentData> investmentDataList) {
		this.investmentDataList = investmentDataList;
	}
	
}
