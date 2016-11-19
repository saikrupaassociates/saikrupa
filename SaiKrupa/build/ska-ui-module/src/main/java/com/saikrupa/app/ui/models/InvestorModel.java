package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.saikrupa.app.dao.InvestorDAO;
import com.saikrupa.app.dao.impl.DefaultInvestorDAO;
import com.saikrupa.app.dto.InvestorData;

public class InvestorModel extends DefaultComboBoxModel<InvestorData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvestorModel() {
		super();
		getInvestorData();
	}
	
	private void getInvestorData() {			
		InvestorDAO dao = new DefaultInvestorDAO();
		List<InvestorData> dataList = dao.findInvestors();
		for(int i = 0; i < dataList.size(); i++) {
			InvestorData data = dataList.get(i);
			addElement(data);
		}		
	}
	
	@Override
    public InvestorData getSelectedItem() {
		InvestorData data = (InvestorData) super.getSelectedItem();
		return data;
	}
	
	@Override
	public void setSelectedItem(Object anObject) {
		InvestorData data = (InvestorData) anObject;
		super.setSelectedItem(data);		
	}
}
