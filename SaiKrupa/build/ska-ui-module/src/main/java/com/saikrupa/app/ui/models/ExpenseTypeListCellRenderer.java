package com.saikrupa.app.ui.models;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.saikrupa.app.dto.ExpenseTypeData;

public class ExpenseTypeListCellRenderer implements ListCellRenderer<ExpenseTypeData> {
	
	 protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	public ExpenseTypeListCellRenderer() {
		// TODO Auto-generated constructor stub
	}

	

	public Component getListCellRendererComponent(JList<? extends ExpenseTypeData> paramJList, ExpenseTypeData data,
			int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
		
		JLabel label = (JLabel)defaultRenderer.getListCellRendererComponent(paramJList, data, paramInt, paramBoolean1, paramBoolean2);
		label.setText(data.getName());
		
		return label;
	}

}
