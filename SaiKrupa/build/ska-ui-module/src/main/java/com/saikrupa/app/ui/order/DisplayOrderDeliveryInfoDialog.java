package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.ui.BaseAppDialog;
import com.saikrupa.app.ui.SKAMainApp;
import com.saikrupa.app.util.DateUtil;

public class DisplayOrderDeliveryInfoDialog extends BaseAppDialog {

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	public DisplayOrderDeliveryInfoDialog(SKAMainApp sdMainApp, OrderEntryData data) {
		super(sdMainApp, true);
		setTitle("View Delivery Detail - "+data.getCode());
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(sdMainApp, data);
		setLocationRelativeTo(sdMainApp);
		setResizable(false);
		
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
		final WebLabel codeText = new WebLabel(data.getOrder().getCode());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); 
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); 

		layout.setConstraints(codeText, c);
		formPanel.add(codeText);

		WebLabel l2 = new WebLabel("Product Name : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		final WebLabel productNameText = new WebLabel(data.getProduct().getName());

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 10, 0); 
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 10, 0); // Left padding

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
		c.insets = new Insets(0, 0, 10, 0); 
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 10, 0); // Left padding

		layout.setConstraints(deliveryQuantity, c);
		formPanel.add(deliveryQuantity);
		
		WebLabel l4 = new WebLabel("Delivery Date : ", SwingConstants.RIGHT);		
		l4.setFont(applyLabelFont());
		final WebLabel deliveryDateText = new WebLabel(DateUtil.convertToStandard(data.getDeliveryData().getDeliveryDate()));

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0, 0, 10, 0); 
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(0, 0, 10, 0); // Left padding

		layout.setConstraints(deliveryDateText, c);
		formPanel.add(deliveryDateText);	
		
		WebLabel l5 = new WebLabel("Delivery Challan No : ", SwingConstants.RIGHT);		
		l5.setFont(applyLabelFont());
		final WebLabel deliveryChallanText = new WebLabel();
		deliveryChallanText.setText(data.getDeliveryData().getDeliveryReceiptNo());


		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0, 0, 10, 0); 
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(0, 0, 10, 0); // Left padding

		layout.setConstraints(deliveryChallanText, c);
		formPanel.add(deliveryChallanText);
		
		WebLabel l6 = new WebLabel("Delivery Vehicle : ", SwingConstants.RIGHT);		
		l6.setFont(applyLabelFont());
		final WebLabel deliveryVehicleText = new WebLabel(data.getDeliveryData().getDeliveryVehicleNo());

		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(0, 0, 10, 0); 
		layout.setConstraints(l6, c);
		formPanel.add(l6);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(0, 0, 10, 0); // Left padding

		layout.setConstraints(deliveryVehicleText, c);
		formPanel.add(deliveryVehicleText);	
		
		final WebButton closeDialogButton = new WebButton("Close");
		closeDialogButton.setFont(applyLabelFont());		
		
		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(0, 0, 10, 0);
		c.gridwidth = 3;
		layout.setConstraints(new WebPanel(), c);
		formPanel.add(new WebPanel());
		
		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(0, 0, 10, 0);
		c.gridwidth = 3;
		layout.setConstraints(closeDialogButton, c);
		formPanel.add(closeDialogButton);
		
		
		
		closeDialogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		getContentPane().add(formPanel, BorderLayout.CENTER);
		pack();
		
	}
}
