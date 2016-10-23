package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.time.ClockType;
import com.alee.extended.time.WebClock;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.ContactPerson;
import com.saikrupa.app.dto.ExpenseData;
import com.saikrupa.app.ui.models.ContactPersonListModel;
import com.saikrupa.app.ui.models.ExpenseTableModel;

public class CreateContactPersonDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateContactPersonDialog(CreateVendorDialog owner) {
		super(owner, true);
		setTitle("Create Contact Person...");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(owner);
		setLocationRelativeTo(owner);
		setResizable(false);
	}

	private void buildGUI(final CreateVendorDialog owner) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Name : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		final WebTextField contactNameText = new WebTextField(15);
		
		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		final WebTextField primaryContact = new WebTextField(15);
		
		
		WebLabel l3 = new WebLabel("Secondary Contact No : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		final WebTextField secondaryContact = new WebTextField(15);
			

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding
		
		layout.setConstraints(contactNameText, c);
		formPanel.add(contactNameText);
		
		c.gridx = 0;
		c.gridy = 1;
		
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding
		
		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);
		
		
		c.gridx = 0;
		c.gridy = 2;			
		layout.setConstraints(l3, c);
		formPanel.add(l3);	
		
		c.gridx = 1;
		c.gridy = 2;	
		
		layout.setConstraints(secondaryContact, c);
		formPanel.add(secondaryContact);	
		
		WebLabel addressHeader = new WebLabel("Communication Address ", SwingConstants.RIGHT);
		addressHeader.setFont(applyLabelFont());
		addressHeader.setForeground(Color.BLUE);
		
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(20, 10, 0, 0); // Left padding		
		layout.setConstraints(addressHeader, c);
		formPanel.add(addressHeader);	
		
		WebLabel line1 = new WebLabel("Address Line 1:", SwingConstants.RIGHT);
		line1.setFont(applyLabelFont());
		final WebTextField line1Text = new WebTextField(15);
		
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0); // Left padding		
		layout.setConstraints(line1, c);
		formPanel.add(line1);	
		
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0); // Left padding		
		layout.setConstraints(line1Text, c);
		formPanel.add(line1Text);	
		
		WebLabel line2 = new WebLabel("Address Line 2:", SwingConstants.RIGHT);
		line2.setFont(applyLabelFont());
		final WebTextField line2Text = new WebTextField(15);
		
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 0, 0); // Left padding		
		layout.setConstraints(line2, c);
		formPanel.add(line2);	
		
		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 0, 0); // Left padding		
		layout.setConstraints(line2Text, c);
		formPanel.add(line2Text);		
		
		
		WebLabel line3 = new WebLabel("Landmark:", SwingConstants.RIGHT);
		line3.setFont(applyLabelFont());
		final WebTextField line3Text = new WebTextField(15);
		
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0); // Left padding		
		layout.setConstraints(line3, c);
		formPanel.add(line3);	
		
		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0); // Left padding		
		layout.setConstraints(line3Text, c);
		formPanel.add(line3Text);	
		

		WebButton addContactButton = new WebButton("Add Contact");
		addContactButton.setFont(applyLabelFont());
		addContactButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ContactPerson contact = new ContactPerson();
				contact.setName(contactNameText.getText());
				contact.setPrimaryContact(primaryContact.getText());
				contact.setSecondaryContact(secondaryContact.getText());
				
				AddressData address = new AddressData();
				address.setLine1(line1Text.getText());
				address.setLine2(line2Text.getText());
				address.setLandmark(line3Text.getText());
				contact.setAddress(address);
				ContactPersonListModel listModel = (ContactPersonListModel) owner.getContactPersonsList().getModel();
				listModel.addElement(contact);
			}
		});

		c.gridx = 1;
		c.gridy = 7;
		c.insets = new Insets(10, 10, 0, 0); // Left padding		
		layout.setConstraints(addContactButton, c);
		formPanel.add(addContactButton);					

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	protected void processCreateExpenseEvent(ExpenseData expenseData, SKAMainApp owner) {
		ExpenseTableModel model = (ExpenseTableModel) owner.getExpenseContentTable().getModel();
		model.getExpenseDataList().add(expenseData);
		model.fireTableDataChanged();

		final WebNotification notificationPopup = new WebNotification();
		notificationPopup.setIcon(NotificationIcon.plus);
		notificationPopup.setDisplayTime(3000);

		final WebClock clock = new WebClock();
		clock.setClockType(ClockType.timer);
		clock.setTimeLeft(6000);
		clock.setFont(applyLabelFont());
		clock.setTimePattern("'Expense Entry Added Successfully' ");		
		clock.setForeground(Color.BLACK);
		notificationPopup.setContent(new GroupPanel(clock));
		NotificationManager.showNotification(notificationPopup);
		clock.start();

	}
}
