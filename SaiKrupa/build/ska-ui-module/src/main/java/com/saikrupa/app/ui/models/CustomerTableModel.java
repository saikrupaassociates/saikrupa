package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.CustomerData;

public class CustomerTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Customer Name", "Contact No", "Address " };

	private List<CustomerData> customerDataList;

	public CustomerTableModel(List<CustomerData> customerDataList) {
		this.customerDataList = customerDataList;	
	}

	

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return customerDataList.size();
	}

	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		CustomerData data = customerDataList.get(row);
		if (col == 0) {
			return data.getName();
		} else if (col == 1) {
			if(data.getPrimaryContact().isEmpty()) {
				return "N/A";
			}			
			return data.getPrimaryContact();
		} else if (col == 2) {
			return getAddressData(data);
		}
		return "---";
	}

	private Object getAddressData(CustomerData data) {
		if(data.getAddress() == null) {
			return "N/A";
		}
		AddressData address = data.getAddress();
		return address.getLine1()+"|" +address.getLine2()+"|"+address.getLandmark();
	}



	public void setValueAt(Object value, int row, int col) {
		
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}



	public List<CustomerData> getCustomerDataList() {
		return customerDataList;
	}



	public void setCustomerDataList(List<CustomerData> customerDataList) {
		this.customerDataList = customerDataList;
	}

	

}
