package com.saikrupa.app.ui.models;

import javax.swing.DefaultComboBoxModel;

import com.saikrupa.app.dto.PaymentStatus;

public class PaymentStatusModel extends DefaultComboBoxModel<PaymentStatus> {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentStatusModel() {
		super();
		getPaymentStatusData();
	}
	
	private void getPaymentStatusData() {
		addElement(PaymentStatus.PAID);
		addElement(PaymentStatus.PENDING);
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
