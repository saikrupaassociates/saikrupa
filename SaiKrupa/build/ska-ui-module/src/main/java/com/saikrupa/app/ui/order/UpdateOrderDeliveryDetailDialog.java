package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.extended.date.WebDateField;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.saikrupa.app.dto.DeliveryData;
import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.ui.BaseAppDialog;
import com.saikrupa.app.ui.SKAMainApp;

public class UpdateOrderDeliveryDetailDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WebTextField receiptNoText;
	private WebDateField deliveryDateText;
	private WebTextField deliveryVehicleText;
	private WebTextField actualQuantityText;
	
	private UpdateOrderDialog parentDialog;
	
	private OrderEntryData currentOrderEntry;

	public UpdateOrderDeliveryDetailDialog(SKAMainApp owner) {
		super(owner);		
	}
	

	public UpdateOrderDeliveryDetailDialog(UpdateOrderDialog dialog, OrderEntryData data) {
		super(dialog);
		setParentDialog(dialog);
		setCurrentOrderEntry(data);
		setTitle("Update Order Delivery Detail");		
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(dialog, data);
		setLocationRelativeTo(dialog);
		setResizable(false);
	}
	
	private void buildGUI(UpdateOrderDialog dialog, final OrderEntryData data) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Order Number : ", SwingConstants.RIGHT);
		final WebLabel codeText = new WebLabel(data.getOrder().getCode());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(codeText, c);
		formPanel.add(codeText);

		WebLabel l2 = new WebLabel("Delivery Item : ", SwingConstants.RIGHT);
		final WebLabel nameText = new WebLabel(data.getProduct().getName());

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(nameText, c);
		formPanel.add(nameText);

		WebLabel l3 = new WebLabel("Actual Quantity : ", SwingConstants.RIGHT);
		actualQuantityText = new WebTextField(15);
		actualQuantityText.setText(String.valueOf(data.getOrderedQuantity()));

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(actualQuantityText, c);
		formPanel.add(actualQuantityText);

		WebLabel l4 = new WebLabel("Delivery Receipt No : ", SwingConstants.RIGHT);
		receiptNoText = new WebTextField(15);
		if(data.getDeliveryData() != null) {
			receiptNoText.setText(String.valueOf(data.getDeliveryData().getDeliveryReceiptNo()));
		}

		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 3;

		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(receiptNoText, c);
		formPanel.add(receiptNoText);

		WebLabel l5 = new WebLabel("Delivery Date : ", SwingConstants.RIGHT);
		deliveryDateText = new WebDateField(new Date());
		
		if(data.getDeliveryData() != null) {
			deliveryDateText.setDate(data.getDeliveryData().getDeliveryDate());
		}

		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(deliveryDateText, c);
		formPanel.add(deliveryDateText);

		WebLabel l6 = new WebLabel("Delivery Vehicle : ", SwingConstants.RIGHT);
		deliveryVehicleText = new WebTextField(15);
		
		if(data.getDeliveryData() != null) {
			deliveryVehicleText.setText(data.getDeliveryData().getDeliveryVehicleNo());
		}

		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l6, c);
		formPanel.add(l6);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(deliveryVehicleText, c);
		formPanel.add(deliveryVehicleText);

		WebButton updateDeliveryDataButton = new WebButton("Update");
		
		updateDeliveryDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDeliveryData(data);
			}
		});

		c.gridx = 1;
		c.gridy = 8;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		c.gridwidth = 1;
		layout.setConstraints(updateDeliveryDataButton, c);
		formPanel.add(updateDeliveryDataButton);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {				
				getParentDialog().getCurrentOrder().setDeliveryStatus(DeliveryStatus.SHIPPING);
				getParentDialog().resetDeliveryStatus();
			}
		});

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	protected void updateDeliveryData(OrderEntryData data) {		
		DeliveryData delivery = new DeliveryData();
		delivery.setOrderEntryData(data);
		delivery.setDeliveryReceiptNo(receiptNoText.getText());
		delivery.setDeliveryVehicleNo(deliveryVehicleText.getText());
		delivery.setDeliveryDate(deliveryDateText.getDate());
		if(actualQuantityText.getText() == null || actualQuantityText.getText().trim().length() < 1) {
			delivery.setActualDeliveryQuantity(getCurrentOrderEntry().getOrderedQuantity());
		} else {
			delivery.setActualDeliveryQuantity(Double.valueOf(actualQuantityText.getText()));
		}
		
		data.setDeliveryData(delivery);	
		getParentDialog().setCurrentOrder(data.getOrder());
		getParentDialog().notifyParent();
		dispose();
	}

	public UpdateOrderDialog getParentDialog() {
		return parentDialog;
	}

	public void setParentDialog(UpdateOrderDialog parentDialog) {
		this.parentDialog = parentDialog;
	}


	public OrderEntryData getCurrentOrderEntry() {
		return currentOrderEntry;
	}


	public void setCurrentOrderEntry(OrderEntryData currentOrderEntry) {
		this.currentOrderEntry = currentOrderEntry;
	}
}
