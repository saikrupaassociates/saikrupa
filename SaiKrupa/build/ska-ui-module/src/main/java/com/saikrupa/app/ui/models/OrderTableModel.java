package com.saikrupa.app.ui.models;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.util.DateUtil;

public class OrderTableModel extends AbstractTableModel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"Code", "Customer","Location", "Quantity", "Order Amount", "Ordered Date", "Order Status", "Delivery Status", "Payment Status", "Delivery Date"};
	
	private List<OrderData> orderDataList;

	public OrderTableModel(List<OrderData> orderDataList) {
		this.orderDataList = orderDataList;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return orderDataList.size();
	}

	public Object getValueAt(int row, int col) {
		OrderData data = orderDataList.get(row);
		if(col == 0) {
			return data.getCode();
		} else if(col == 1) {
			return data.getCustomer().getName();
		} else if(col == 2) {
			return data.getDeliveryAddress().getLine1();
		} else if(col == 3) {
			OrderEntryData entry = data.getOrderEntries().get(0); 
			return entry.getOrderedQuantity();
		} else if(col == 4) {
			return getFormattedPrice(getOrderTotalValue(data));			
		} else if(col == 5) {
			return DateUtil.convertToString("dd-MMM-yyyy", data.getCreatedDate());
		} else if(col == 6) {
			return data.getOrderStatus().toString();
		} else if(col == 7) {
			return data.getDeliveryStatus().toString();
		} else if(col == 8) {
			return data.getPaymentStatus().toString();
		} else if(col == 9) {
			if(data.getOrderEntries().get(0).getDeliveryData() != null) {
				return DateUtil.convertToString("dd-MMM-yyyy", data.getOrderEntries().get(0).getDeliveryData().getDeliveryDate());
			}
			return "";
		} 
		
		return "---";
	}
	
	private Double getOrderTotalValue(OrderData data) {
		Double amount = 0.0;
		for(OrderEntryData entry : data.getOrderEntries()) {
			amount = amount + (entry.getOrderedQuantity() * entry.getPrice() - entry.getDiscount() + entry.getTransportationCost());
		}
		return amount;
	}
	
	private String getFormattedPrice(Double value) {
		Format df = new DecimalFormat("###,###,000.00");	
		return df.format(value);
	}
	
	public void setValueAt(Object value, int row, int col) {
		
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public List<OrderData> getOrderDataList() {
		return orderDataList;
	}

	public void setOrderDataList(List<OrderData> orderDataList) {
		this.orderDataList = orderDataList;
	}



	

	

}
