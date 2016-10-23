package com.saikrupa.app.ui.models;

import javax.swing.DefaultComboBoxModel;

import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.service.ConfigurationService;
import com.saikrupa.app.service.impl.DefaultConfigurationService;

public class OrderStatusModel extends DefaultComboBoxModel<OrderStatus> {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderStatusModel() {
		super();
		getOrderStatusData();
	}
	
	private void getOrderStatusData() {
		ConfigurationService service = new DefaultConfigurationService();
		for(OrderStatus status : service.getConfiguredOrderStatuses()) {
			addElement(status);
		}
	}
	
	@Override
    public Object getSelectedItem() {		
		return super.getSelectedItem();
	}
	
	@Override
	public void setSelectedItem(Object anObject) {
		super.setSelectedItem(anObject);	
			
	}
}
