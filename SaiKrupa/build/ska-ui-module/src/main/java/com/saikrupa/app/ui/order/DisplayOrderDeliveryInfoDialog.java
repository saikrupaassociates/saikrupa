package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.ui.BaseAppDialog;
import com.saikrupa.app.ui.SKAMainApp;
import com.saikrupa.app.util.DateUtil;

public class DisplayOrderDeliveryInfoDialog extends BaseAppDialog {

	
	private static final long serialVersionUID = 1L;
	
	public DisplayOrderDeliveryInfoDialog(UpdateOrderDialog dialog, OrderEntryData data) {
		super(dialog);
		setTitle("View Order Delivery Detail");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI_Update(data);
		setLocationRelativeTo(dialog);
		setResizable(false);
		
	}
	
	private void buildGUI_Update(final OrderEntryData data) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Order Number : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		WebLabel t1 = new WebLabel(data.getOrder().getCode());

		WebLabel l2 = new WebLabel("Product : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		WebLabel t2 = new WebLabel(data.getProduct().getName());

		WebLabel l3 = new WebLabel("Ordered Quantity : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		WebLabel t3 = new WebLabel(String.valueOf(data.getOrderedQuantity()));
		
		WebLabel l31 = new WebLabel("Delivered Quantity : ", SwingConstants.RIGHT);
		l31.setFont(applyLabelFont());
		WebLabel actualQuantityText = new WebLabel(String.valueOf(data.getDeliveryData().getActualDeliveryQuantity()));

		WebLabel l4 = new WebLabel("Delivery Date : ", SwingConstants.RIGHT);
		l4.setFont(applyLabelFont());
		WebLabel deliveryDateText = new WebLabel(DateUtil.simpleDateText(data.getDeliveryData().getDeliveryDate()));		

		
		WebLabel l41 = new WebLabel("Delivery Challan No : ", SwingConstants.RIGHT);
		l41.setFont(applyLabelFont());
		WebLabel receiptNoText = new WebLabel(data.getDeliveryData().getDeliveryReceiptNo());
				
		
		WebLabel l5 = new WebLabel("Delivery Vehicle No : ", SwingConstants.RIGHT);
		l5.setFont(applyLabelFont());
		WebLabel deliveryVehicleText = new WebLabel(data.getDeliveryData().getDeliveryVehicleNo());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 15, 5, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(5, 15, 5, 0);
		layout.setConstraints(t1, c);
		formPanel.add(t1);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(15, 0, 5, 0); // top padding
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(15, 15, 5, 0);
		layout.setConstraints(t2, c);
		formPanel.add(t2);

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(15, 0, 5, 0); // top padding
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(15, 15, 5, 0);
		layout.setConstraints(t3, c);
		formPanel.add(t3);

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(15, 0, 0, 0); // top padding
		layout.setConstraints(l31, c);
		formPanel.add(l31);

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(15, 15, 0, 0);
		layout.setConstraints(actualQuantityText, c);
		formPanel.add(actualQuantityText);
		
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(15, 0, 0, 0); // top padding
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(15, 15, 0, 0);
		layout.setConstraints(deliveryDateText, c);
		formPanel.add(deliveryDateText);
		
		
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(15, 0, 0, 0); // top padding
		layout.setConstraints(l41, c);
		formPanel.add(l41);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(15, 15, 0, 0);
		layout.setConstraints(receiptNoText, c);
		formPanel.add(receiptNoText);

		
		
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(15, 0, 0, 0); // top padding
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(15, 15, 0, 0);
		layout.setConstraints(deliveryVehicleText, c);
		formPanel.add(deliveryVehicleText);		
		
			
		
		
		WebButton cancelButton = new WebButton("Close");		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});		

		cancelButton.setFont(applyLabelFont());
		WebPanel buttPanel = new WebPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		buttPanel.add(cancelButton);

		c.gridx = 1;
		c.gridy = 7;
		c.insets = new Insets(15, 15, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(buttPanel, c);
		formPanel.add(buttPanel);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		getContentPane().add(new GroupPanel(new WebPanel()), BorderLayout.EAST);
		getContentPane().add(new GroupPanel(new WebPanel()), BorderLayout.WEST);
		pack();
	}

	private void buildGUI(final SKAMainApp owner, final OrderEntryData data) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Order Number : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 10, 0); 
		layout.setConstraints(l1, c);
		formPanel.add(l1);
		
		final WebLabel codeText = new WebLabel(data.getOrder().getCode());
		//codeText.setFont(applyLabelFont());
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 0); 
		layout.setConstraints(codeText, c);
		formPanel.add(codeText);

		WebLabel l2 = new WebLabel("Product Name : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		final WebLabel productNameText = new WebLabel(data.getProduct().getName());

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 10, 0); 
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 0);

		layout.setConstraints(productNameText, c);
		formPanel.add(productNameText);	
		
		
		WebLabel l3 = new WebLabel("Quantity Delivered : ", SwingConstants.RIGHT);		
		l3.setFont(applyLabelFont());
		final WebLabel deliveryQuantity = new WebLabel(String.valueOf(data.getDeliveryData().getActualDeliveryQuantity()));
		if(data.getDeliveryData().getActualDeliveryQuantity() != data.getOrderedQuantity()) {			
			deliveryQuantity.setFont(applyLabelFont());
			deliveryQuantity.setForeground(Color.RED);
		}		

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10, 0, 10, 0); 
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(10, 10, 10, 0);
		layout.setConstraints(deliveryQuantity, c);
		formPanel.add(deliveryQuantity);
		
		WebLabel l4 = new WebLabel("Delivery Date : ", SwingConstants.RIGHT);		
		l4.setFont(applyLabelFont());
		final WebLabel deliveryDateText = new WebLabel(DateUtil.convertToStandard(data.getDeliveryData().getDeliveryDate()));

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10, 0, 10, 0);  
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 10, 0);
		layout.setConstraints(deliveryDateText, c);
		formPanel.add(deliveryDateText);	
		
		WebLabel l5 = new WebLabel("Delivery Challan No : ", SwingConstants.RIGHT);		
		l5.setFont(applyLabelFont());
		final WebLabel deliveryChallanText = new WebLabel();
		deliveryChallanText.setText(data.getDeliveryData().getDeliveryReceiptNo());

		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10, 0, 10, 0); 
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 10, 0);
		layout.setConstraints(deliveryChallanText, c);
		formPanel.add(deliveryChallanText);
		
		WebLabel l6 = new WebLabel("Delivery Vehicle : ", SwingConstants.RIGHT);		
		l6.setFont(applyLabelFont());
		final WebLabel deliveryVehicleText = new WebLabel(data.getDeliveryData().getDeliveryVehicleNo());

		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(10, 0, 10, 0); 
		layout.setConstraints(l6, c);
		formPanel.add(l6);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 10, 0);
		layout.setConstraints(deliveryVehicleText, c);
		formPanel.add(deliveryVehicleText);	
		
		final WebButton closeDialogButton = new WebButton("Close");
		closeDialogButton.setFont(applyLabelFont());		
		
		WebPanel buttonPanel = new WebPanel(false);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(closeDialogButton);
		
		
		closeDialogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		getContentPane().add(formPanel, BorderLayout.CENTER);
		getContentPane().add(new WebPanel(true), BorderLayout.EAST);
		getContentPane().add(new WebPanel(true), BorderLayout.WEST);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);		
		getContentPane().add(new WebPanel(true), BorderLayout.NORTH);
		pack();
	
	}
}
