package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.VendorData;

public class VendorTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Vendor Name", "Contact Person Name", "Contact No", "Address / LandMark" };

	private List<VendorData> vendorDataList;

	public VendorTableModel(List<VendorData> VendorDataList) {
		this.vendorDataList = VendorDataList;	
	}

	

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return vendorDataList.size();
	}

	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		VendorData data = vendorDataList.get(row);
		if (col == 0) {
			return data.getName();
		} else if (col == 1) {
			if(data.getContactPersons().isEmpty()) {
				return "N/A";
			}			
			return data.getContactPersons().get(0).getName();
		} else if (col == 2) {
			if(data.getContactPersons().isEmpty()) {
				return data.getPrimaryContactNo();
			}
			return data.getContactPersons().get(0).getPrimaryContact();
		} else if (col == 3) {
			if(data.getContactPersons().isEmpty()) {
				return "N/A";
			}			
			AddressData address = data.getContactPersons().get(0).getAddress();
			return (address == null ? "N/A - Add" : address.getLine1());
		}
		return "---";
	}

	public void setValueAt(Object value, int row, int col) {
		
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public List<VendorData> getVendorDataList() {
		return vendorDataList;
	}

	public void setVendorDataList(List<VendorData> vendorDataList) {
		this.vendorDataList = vendorDataList;
	}

}
