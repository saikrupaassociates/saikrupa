package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.saikrupa.app.dto.EmployeeData;
import com.saikrupa.app.dto.ProductData;
import com.saikrupa.app.util.DateUtil;

public class ProductTableModel extends AbstractTableModel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Code", "Name", "Available", "Reserved","Rejected","Last Updated On", "Updated By"};
	
	private List<ProductData> productDataList;

	public ProductTableModel(List<ProductData> productDataList) {
		this.productDataList = productDataList;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return productDataList.size();
	}

	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		ProductData data = productDataList.get(row);
		if(col == 0) {
			return data.getCode();
		} else if(col == 1) {
			return data.getName();
		} else if(col == 2) {
			return data.getInventory().getTotalAvailableQuantity();
		} else if(col == 3) {
			return data.getInventory().getTotalReservedQuantity();
		}  else if(col == 4) {
			return data.getInventory().getTotalDamagedQuantity();
		}  else if(col == 5) {
			return DateUtil.convertToString("dd-MMM-yyyy hh:mm:ss a", data.getInventory().getLastUpdatedDate());
		}   else if(col == 6) {	
			EmployeeData updatedBy = data.getInventory().getLastUpdatedBy();
			if(updatedBy == null) {
				return "N/A";
			}
			return updatedBy.getName();
			
		}  
		return "---";
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public List<ProductData> getProductDataList() {
		return productDataList;
	}

	public void setProductDataList(List<ProductData> productDataList) {
		this.productDataList = productDataList;
	}

	

}
