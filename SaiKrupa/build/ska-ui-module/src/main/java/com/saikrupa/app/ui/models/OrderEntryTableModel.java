package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.saikrupa.app.dto.OrderEntryData;

public class OrderEntryTableModel extends AbstractTableModel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"Product Name", "Quantity", "Unit Price","Delivery", "Discount", "Total Price"};
	
	private List<OrderEntryData> orderEntryDataList;

	public OrderEntryTableModel(List<OrderEntryData> orderEntryDataList) {
		this.orderEntryDataList = orderEntryDataList;
	}

	public int getColumnCount() {
		return columnNames.length;
	}
	
	public int getRowCount() {		
		return orderEntryDataList.size();
	}

	public Object getValueAt(int row, int col) {
		OrderEntryData data = orderEntryDataList.get(row);
		if(col == 0) {
			return data.getProduct().getName();
		} else if(col == 1) {
			return data.getOrderedQuantity();
		} else if(col == 2) {
			return data.getPrice();
		} else if(col == 3) {
			return data.getTransportationCost();
		} else if(col == 4) {
				return data.getDiscount();
		} else if(col == 5) {				
			return getOrderTotalValue(data);
		}
		return "---";
	}
	
	private Double getOrderTotalValue(OrderEntryData entry) {
		Double amount = 0.0;
		return amount = amount + (entry.getOrderedQuantity() * entry.getPrice() - entry.getDiscount() + entry.getTransportationCost());
	}	
	
	public void setValueAt(Object value, int row, int col) {
		
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public List<OrderEntryData> getOrderEntryDataList() {
		return orderEntryDataList;
	}

	public void setOrderEntryDataList(List<OrderEntryData> orderEntryDataList) {
		this.orderEntryDataList = orderEntryDataList;
	}
}
