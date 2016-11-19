package com.saikrupa.app.ui.models;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.saikrupa.app.dto.PaymentTypeData;

public class paymentTypeListCellRenderer implements ListCellRenderer<PaymentTypeData> {
	
	 protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	public paymentTypeListCellRenderer() {
		// TODO Auto-generated constructor stub
	}

	

	public Component getListCellRendererComponent(JList<? extends PaymentTypeData> paramJList, PaymentTypeData data,
			int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
		
		JLabel label = (JLabel)defaultRenderer.getListCellRendererComponent(paramJList, data, paramInt, paramBoolean1, paramBoolean2);
		label.setText(data.getName());		
		return label;
	}
}
