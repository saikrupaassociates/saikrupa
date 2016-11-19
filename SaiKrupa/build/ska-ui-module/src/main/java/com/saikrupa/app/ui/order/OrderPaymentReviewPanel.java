package com.saikrupa.app.ui.order;

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

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.dto.PaymentTypeData;
import com.saikrupa.app.service.OrderService;
import com.saikrupa.app.service.impl.DefaultOrderService;
import com.saikrupa.app.ui.component.AppWebPanel;
import com.saikrupa.app.ui.models.PaymentStatusModel;
import com.saikrupa.app.ui.models.PaymentTypeModel;
import com.saikrupa.app.ui.models.paymentTypeListCellRenderer;

public class OrderPaymentReviewPanel extends AppWebPanel {

	private static final long serialVersionUID = 1L;

	private ManageOrderDialog owner;

	private WebComboBox paymentStatusCombo;
	private WebComboBox paymentModeCombo;	
	
	private WebLabel totalOrderCostLabel;
	private WebPanel customerDetailPanel;
	private WebButton submitOrderButton;

	public OrderPaymentReviewPanel(ManageOrderDialog owner) {
		this.owner = owner;
		buildUI();
	}

	private void buildUI() {
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
		setLayout(new BorderLayout());
		buildDetailPanel();
	}

	@SuppressWarnings("unchecked")
	private void buildDetailPanel() {
		customerDetailPanel = new WebPanel();
		GridBagLayout layout = new GridBagLayout();
		customerDetailPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Payment Status : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		paymentStatusCombo = new WebComboBox(new PaymentStatusModel());	
		paymentStatusCombo.setActionCommand("PAYMENT_STATUS");

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		customerDetailPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(paymentStatusCombo, c);
		customerDetailPanel.add(paymentStatusCombo);

		final WebLabel l2 = new WebLabel("Payment Mode : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		paymentModeCombo = new WebComboBox(new PaymentTypeModel());
		paymentModeCombo.setRenderer(new paymentTypeListCellRenderer());
		paymentModeCombo.setEnabled(false);
		l2.setEnabled(false);
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l2, c);
		customerDetailPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(paymentModeCombo, c);
		customerDetailPanel.add(paymentModeCombo);		
		
		
		WebLabel l3 = new WebLabel("Total Order Value : ", SwingConstants.RIGHT);	
		l3.setFont(applyLabelFont());
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l3, c);
		customerDetailPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		totalOrderCostLabel = new WebLabel("0.0");
		totalOrderCostLabel.setForeground(Color.BLUE);
		totalOrderCostLabel.setFont(new Font("verdana", Font.BOLD, 12));

		layout.setConstraints(totalOrderCostLabel, c);
		customerDetailPanel.add(totalOrderCostLabel);
		

		submitOrderButton = new WebButton("Place Order");
		submitOrderButton.setFontStyle(Font.BOLD);
		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		layout.setConstraints(submitOrderButton, c);
		customerDetailPanel.add(submitOrderButton);

		add(customerDetailPanel, BorderLayout.WEST);

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("PLACE_ORDER")) {
					validateAndPlaceOrder();
				} else if(e.getActionCommand().equalsIgnoreCase("PAYMENT_STATUS")) {
					PaymentStatus status = (PaymentStatus) paymentStatusCombo.getSelectedItem();
					if(status == PaymentStatus.PAID) {
						paymentModeCombo.setEnabled(true);
						l2.setEnabled(true);
					} else if(status == PaymentStatus.PENDING) {
						paymentModeCombo.setEnabled(false);
						l2.setEnabled(false);
					}
				}
				
			}
		};
		submitOrderButton.setActionCommand("PLACE_ORDER");
		submitOrderButton.addActionListener(listener);
		paymentStatusCombo.addActionListener(listener);
	}

	protected void validateAndPlaceOrder() {
		OrderData currentOrder = owner.getOrderData();	
		PaymentStatus paymentStatus = (PaymentStatus) paymentStatusCombo.getSelectedItem();
		PaymentTypeData paymentMode = (PaymentTypeData) paymentModeCombo.getSelectedItem();
		currentOrder.setPaymentStatus(paymentStatus);
		currentOrder.setPaymentMode(paymentMode);
		
		if(paymentStatus == PaymentStatus.PAID) {
			currentOrder.setOrderStatus(OrderStatus.CONFIRMED);
		}		
		try {
			OrderService orderService = new DefaultOrderService();
			orderService.createOrder(currentOrder);
			owner.processPostOrderCreateEvent();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public WebLabel getTotalOrderCostLabel() {
		return totalOrderCostLabel;
	}

	public void setTotalOrderCostLabel(WebLabel totalOrderCostLabel) {
		this.totalOrderCostLabel = totalOrderCostLabel;
	}


}
