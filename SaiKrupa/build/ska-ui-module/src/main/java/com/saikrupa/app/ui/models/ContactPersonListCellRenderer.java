package com.saikrupa.app.ui.models;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.saikrupa.app.dto.ContactPerson;

@SuppressWarnings("rawtypes")
public class ContactPersonListCellRenderer implements ListCellRenderer {

	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	public Component getListCellRendererComponent(JList list, Object contactPerson, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (contactPerson instanceof ContactPerson) {
			ContactPerson person = (ContactPerson) contactPerson;
			JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, person, index, isSelected,
					cellHasFocus);
			if(person.getAddress() != null) {
				renderer.setText(person.getName() + " - " + person.getPrimaryContact() +"@ " +person.getAddress().getLandmark());
			} else {
				renderer.setText(person.getName() + " - " + person.getPrimaryContact());
			}
			
			return renderer;
		}
		return defaultRenderer;

	}

}
