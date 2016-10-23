package com.saikrupa.app.ui.models;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;

public class ExpenseTableRenderer extends WebLabel implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean rendered = false;

	public ExpenseTableRenderer() {
		rendered = false;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (column > 6 && !rendered) {
			//setBackground(Color.WHITE);
			WebButton button = new WebButton("Edit / Update");			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});		
			this.add(button);			
			rendered = true;
		}
		return this;
	}

}
