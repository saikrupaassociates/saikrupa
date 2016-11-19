package com.saikrupa.app.ui.models;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.saikrupa.app.dao.ProductDAO;
import com.saikrupa.app.dao.impl.DefaultProductDAO;
import com.saikrupa.app.dto.ProductData;

public class ProductSelectionModel extends DefaultComboBoxModel<ProductData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductSelectionModel() {
		super();
		getProductData();
	}
	
	private void getProductData() {			
		ProductDAO dao = new DefaultProductDAO();
		List<ProductData> dataList = dao.findAllProducts();
		for(int i = 0; i < dataList.size(); i++) {
			ProductData data = dataList.get(i);
			addElement(data);
		}		
		insertElementAt(new ProductData(), 0);
	}
	
	@Override
    public ProductData getSelectedItem() {
		ProductData data = (ProductData) super.getSelectedItem();
		if(data.getCode() != null) {
			return data;
		}
		return null;
	}
	
	@Override
	public void setSelectedItem(Object anObject) {
		ProductData data = (ProductData) anObject;
		super.setSelectedItem(data);		
	}
}
