package com.saikrupa.app.ui.models;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.saikrupa.app.dto.ProductData;

public class ProductListCellRenderer implements ListCellRenderer<ProductData> {

	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	public ProductListCellRenderer() {
		// TODO Auto-generated constructor stub
	}

	public Component getListCellRendererComponent(JList<? extends ProductData> paramJList, ProductData data,
			int paramInt, boolean paramBoolean1, boolean paramBoolean2) {

		JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(paramJList, data, paramInt, paramBoolean1,
				paramBoolean2);
		if (label == null || data == null) {
			label.setText("-- Not Available --");
		} else {
			label.setText(data.getName());			
		}
		return label;
	}

}
