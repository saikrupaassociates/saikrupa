package com.saikrupa.app.ui.models;

import javax.swing.DefaultComboBoxModel;

import com.saikrupa.app.dao.DefaultPaymentModeDAO;
import com.saikrupa.app.dao.PaymentModeDAO;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.dto.PaymentTypeData;

public class PaymentTypeModel extends DefaultComboBoxModel<PaymentTypeData> {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentTypeModel() {
		super();
		getPaymentTypeData();
	}
	
	private void getPaymentTypeData() {			
		PaymentModeDAO dao = new DefaultPaymentModeDAO();		
		for(PaymentTypeData data : dao.getPaymentModes()) {
			if(data.getCode().equalsIgnoreCase(PaymentStatus.PENDING.name())) {
				setSelectedItem(data);
			}
			addElement(data);
		}		
	}
	
	@Override
    public PaymentTypeData getSelectedItem() {
		PaymentTypeData data = (PaymentTypeData) super.getSelectedItem();
		return data;
	}
	
	@Override
	public void setSelectedItem(Object anObject) {
		PaymentTypeData data = (PaymentTypeData) anObject;
		super.setSelectedItem(data);		
	}
}
