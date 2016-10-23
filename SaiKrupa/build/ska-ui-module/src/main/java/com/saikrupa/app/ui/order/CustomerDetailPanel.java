package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.saikrupa.app.dto.AddressData;
import com.saikrupa.app.dto.CustomerData;
import com.saikrupa.app.ui.DisplayCustomerListDialog;
import com.saikrupa.app.ui.component.AppTextField;
import com.saikrupa.app.ui.component.AppWebLabel;
import com.saikrupa.app.ui.component.AppWebPanel;

public class CustomerDetailPanel extends AppWebPanel {

	private static final long serialVersionUID = 1L;

	private ManageOrderDialog owner;

	private AppTextField customerNameText;
	private WebTextField contactNoText;

	private WebTextField addressLine1;
	private WebTextField addressLine2;
	private WebTextField landMark;
	private WebTextField pinCodeText;

	private WebPanel customerDetailPanel;

	private WebButton continueButton;

	public CustomerDetailPanel(ManageOrderDialog owner) {
		this.owner = owner;
		buildUI();
	}

	private void buildUI() {
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
		setLayout(new BorderLayout());
		buildDetailPanel();
	}

	private void buildDetailPanel() {
		customerDetailPanel = new WebPanel();
		GridBagLayout layout = new GridBagLayout();
		customerDetailPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Customer Name : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		
		customerNameText = new AppTextField(20);		
		WebButton searchButton = new WebButton();
		searchButton.setBackground(Color.white);
		searchButton.setIcon(createImageIcon("search.png"));
		searchButton.setActionCommand("SEARCH_CUSTOMER");
		//customerNameText.setModel(new CustomerData());
		customerNameText.setTrailingComponent(searchButton);

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		customerDetailPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(customerNameText, c);
		customerDetailPanel.add(customerNameText);
		
		WebLabel l2 = new WebLabel("Contact No : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		contactNoText = new WebTextField(20);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l2, c);
		customerDetailPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(contactNoText, c);
		customerDetailPanel.add(contactNoText);

		// ******************************************

		WebLabel addressHeader = new WebLabel("Delivery Address ", SwingConstants.RIGHT);
		addressHeader.setFont(applyLabelFont());
		addressHeader.setForeground(Color.BLUE);

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(20, 10, 0, 0); // Left padding
		layout.setConstraints(addressHeader, c);
		customerDetailPanel.add(addressHeader);

		WebLabel line1 = new WebLabel("Address Line 1 :", SwingConstants.RIGHT);
		line1.setFont(applyLabelFont());
		
		l1.setFont(applyLabelFont());
		addressLine1 = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(line1, c);
		customerDetailPanel.add(line1);

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(addressLine1, c);
		customerDetailPanel.add(addressLine1);

		WebLabel line2 = new WebLabel("Address Line 2 :", SwingConstants.RIGHT);
		line2.setFont(applyLabelFont());
		addressLine2 = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(line2, c);
		customerDetailPanel.add(line2);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(addressLine2, c);
		customerDetailPanel.add(addressLine2);

		WebLabel line3 = new WebLabel("Landmark :", SwingConstants.RIGHT);
		line3.setFont(applyLabelFont());
		landMark = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(line3, c);
		customerDetailPanel.add(line3);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(landMark, c);
		customerDetailPanel.add(landMark);

		WebLabel line4 = new WebLabel("PIN Code :", SwingConstants.RIGHT);
		line4.setFont(applyLabelFont());
		pinCodeText = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(line4, c);
		customerDetailPanel.add(line4);

		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(pinCodeText, c);
		customerDetailPanel.add(pinCodeText);

		continueButton = new WebButton("Save and Continue...");
		continueButton.setFont(applyLabelFont());
		continueButton.setActionCommand("SAVE_CONTINUE");
		
		c.gridx = 2;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(continueButton, c);
		customerDetailPanel.add(continueButton);

		add(customerDetailPanel, BorderLayout.WEST);

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().equals("SAVE_CONTINUE")) {
					if (validated()) {
						saveAndContinue();
					}
				} else if(event.getActionCommand().equals("SEARCH_CUSTOMER")) {
					DisplayCustomerListDialog d = new DisplayCustomerListDialog(CustomerDetailPanel.this);
					d.setVisible(true);
				}
				
			}
		};

		continueButton.addActionListener(listener);
		searchButton.addActionListener(listener);
	}

	private boolean validated() {
		if (customerNameText.getText().isEmpty()) {
			showMessage("Customer Name missing", customerNameText);
		} else if (contactNoText.getText().isEmpty() || contactNoText.getText().trim().length() > 10) {
			showMessage("Customer Contact is missing or Invalid", contactNoText);
		} else if (addressLine1.getText().isEmpty()) {
			showMessage("Customer Address Line 1 missing", addressLine1);
		} else {
			return true;
		}
		return false;
	}

	private void showMessage(String message, Component invoker) {
		final WebPopOver popOver = new WebPopOver(CustomerDetailPanel.this);
		popOver.setCloseOnFocusLoss(true);
		popOver.setMargin(10);
		popOver.setLayout(new VerticalFlowLayout());
		popOver.add(new AppWebLabel(message));
		popOver.show(invoker);
	}

	private void saveAndContinue() {
		CustomerData data = null;
		if(customerNameText.getModel() == null || customerNameText.getText().isEmpty()) {
			data = new CustomerData();
			data.setName(customerNameText.getText());
			data.setPrimaryContact(contactNoText.getText());
			AddressData deliveryTo = new AddressData();
			deliveryTo.setLine1(addressLine1.getText());
			deliveryTo.setLine2(addressLine2.getText());
			deliveryTo.setLandmark(landMark.getText());
			deliveryTo.setZipCode(pinCodeText.getText());
			data.setAddress(deliveryTo);
			data.setBillingAddress(deliveryTo);
			customerNameText.setModel(data);
		}
		data = (CustomerData) customerNameText.getModel();
		System.out.println("**********************************************************************");
		System.out.println("Customer Model : "+data);
		System.out.println("**********************************************************************");
		owner.getOrderData().setDeliveryAddress(data.getAddress());
		owner.getOrderData().setCustomer(data);

		owner.getOrderTabbedPane().setEnabledAt(2, true);
		owner.getOrderTabbedPane().setSelectedIndex(2);

	}
	
	private Icon createImageIcon(String path) {		
		this.getClass().getClassLoader();
		java.net.URL imgURL = ClassLoader.getSystemResource("icons/"+path);
	      if (imgURL != null) {
	         return new ImageIcon(imgURL);
	      } else {            
	         System.err.println("Couldn't find file:  --> "+path);
	         return null;
	      }
	}

	public ManageOrderDialog getOwner() {
		return owner;
	}

	public void setOwner(ManageOrderDialog owner) {
		this.owner = owner;
	}

	public AppTextField getCustomerNameText() {
		return customerNameText;
	}

	public void setCustomerNameText(AppTextField customerNameText) {
		this.customerNameText = customerNameText;
	}

	public void updateCustomerData(CustomerData data) {
		customerNameText.setModel(data);
		customerNameText.setText(data.getName());
		contactNoText.setText(data.getPrimaryContact());
		addressLine1.setText(data.getAddress().getLine1());
		addressLine2.setText(data.getAddress().getLine2());
		landMark.setText(data.getAddress().getLandmark());
		pinCodeText.setText(data.getAddress().getZipCode());
		
	}
}
