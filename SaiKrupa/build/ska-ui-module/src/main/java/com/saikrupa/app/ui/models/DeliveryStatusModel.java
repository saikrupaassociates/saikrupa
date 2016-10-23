package com.saikrupa.app.ui.models;

import javax.swing.DefaultComboBoxModel;

import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.service.ConfigurationService;
import com.saikrupa.app.service.impl.DefaultConfigurationService;

public class DeliveryStatusModel extends DefaultComboBoxModel<DeliveryStatus> {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeliveryStatusModel() {
		super();
		getDeliveryStatusData();
	}
	
	private void getDeliveryStatusData() {
		ConfigurationService service = new DefaultConfigurationService();
		for(DeliveryStatus status : service.getConfiguredDeliveryStatuses()) {
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
