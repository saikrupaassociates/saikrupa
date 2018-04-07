package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.extended.date.WebDateField;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.saikrupa.app.dao.impl.CustomerDAO;
import com.saikrupa.app.dao.impl.DefaultCustomerDAO;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.dto.PaymentEntryData;
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
	private WebDateField orderCreatedDate;

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

//		final WebLabel l2 = new WebLabel("Payment Mode : ", SwingConstants.RIGHT);
//		l2.setFont(applyLabelFont());
		paymentModeCombo = new WebComboBox(new PaymentTypeModel());
		paymentModeCombo.setRenderer(new paymentTypeListCellRenderer());
		paymentModeCombo.setEnabled(false);
//		l2.setEnabled(false);
//		
//		c.gridx = 0;
//		c.gridy = 1;
//		c.insets = new Insets(0, 0, 10, 0); // Left padding
//		layout.setConstraints(l2, c);
//		customerDetailPanel.add(l2);
//
//		c.gridx = 1;
//		c.gridy = 1;
//		c.insets = new Insets(0, 10, 10, 0); // Left padding
//
//		layout.setConstraints(paymentModeCombo, c);
//		customerDetailPanel.add(paymentModeCombo);
		
		final WebLabel l2 = new WebLabel("Order Date : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());		
		orderCreatedDate = new WebDateField(new Date());
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l2, c);
		customerDetailPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(orderCreatedDate, c);
		customerDetailPanel.add(orderCreatedDate);
		
		
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
		currentOrder.setCreatedDate(orderCreatedDate.getDate());
		
		if(paymentStatus == PaymentStatus.PAID) {
			currentOrder.setOrderStatus(OrderStatus.CONFIRMED);
		}		
		try {
			List<PaymentEntryData> unadjustedPaymentEntries = getUnadjustedPaymentEntry(currentOrder);
			if(!unadjustedPaymentEntries.isEmpty()) {				
				final String message = "Advance Amount available from this customer. Would you like to adjust on this order?"; 
				int confirmed = WebOptionPane.showConfirmDialog(this, message,
						"Confirm", WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE);
				if(confirmed == WebOptionPane.YES_OPTION) {
					processWithUnadjustedPayments(unadjustedPaymentEntries, currentOrder);
				}
			} else {
				OrderService orderService = new DefaultOrderService();
				orderService.createOrder(currentOrder);
				owner.processPostOrderCreateEvent();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processWithUnadjustedPayments(List<PaymentEntryData> unadjustedPaymentEntries, OrderData currentOrder) {		
		List<PaymentEntryData> paymentsToReconcile = new ArrayList<PaymentEntryData>();
		List<PaymentEntryData> paymentsToAdd = new ArrayList<PaymentEntryData>();
		
		for(PaymentEntryData unadjustedEntry : unadjustedPaymentEntries) {
			PaymentEntryData ped = new PaymentEntryData();
			Double balanceAvailable = unadjustedEntry.getAmount() - unadjustedEntry.getPayableAmount();			
			if(balanceAvailable >= currentOrder.getTotalPrice()) { //PAID
				ped.setPaymentStatus(PaymentStatus.PAID);
				ped.setPayableAmount(currentOrder.getTotalPrice());
				ped.setAmount(currentOrder.getTotalPrice());
				ped.setPaymentMode(unadjustedEntry.getPaymentMode());
				ped.setChequeNumber(unadjustedEntry.getChequeNumber());
				ped.setPaymentDate(new Date());		
				
				unadjustedEntry.setPayableAmount(unadjustedEntry.getPayableAmount() + currentOrder.getTotalPrice());
				
			} else if(balanceAvailable < currentOrder.getTotalPrice()) { //PARTIAL
				ped.setPaymentStatus(PaymentStatus.PARTIAL);
				ped.setPayableAmount(currentOrder.getTotalPrice());
				ped.setAmount(balanceAvailable);
				ped.setPaymentMode(unadjustedEntry.getPaymentMode());
				ped.setChequeNumber(unadjustedEntry.getChequeNumber());
				ped.setPaymentDate(new Date());				
				unadjustedEntry.setPayableAmount(unadjustedEntry.getPayableAmount() + balanceAvailable);				
			}			
			paymentsToAdd.add(ped);
			paymentsToReconcile.add(unadjustedEntry);

		}
		if(!paymentsToAdd.isEmpty() && !paymentsToReconcile.isEmpty()) {
			OrderService orderService = new DefaultOrderService();
			orderService.updateOrderPayment(currentOrder, paymentsToAdd, paymentsToReconcile);
			owner.processPostOrderCreateEvent();
		}	
	}

	private List<PaymentEntryData> getUnadjustedPaymentEntry(final OrderData orderData) {
		List<PaymentEntryData> unadjustedEntries = new ArrayList<PaymentEntryData>();
		CustomerDAO customerDAO = new DefaultCustomerDAO();
		List<PaymentEntryData> existingPaymentEntries = customerDAO.findAdhocPaymentByCustomer(orderData.getCustomer().getCode());
		if(!existingPaymentEntries.isEmpty()) {
			for(PaymentEntryData entry : existingPaymentEntries) {
				if(entry.getPayableAmount() - entry.getAmount() < 0) { //Overpaid Entry
					unadjustedEntries.add(entry);
				}
			}
		}
		return unadjustedEntries;		
	}
	
	

	public WebLabel getTotalOrderCostLabel() {
		return totalOrderCostLabel;
	}

	public void setTotalOrderCostLabel(WebLabel totalOrderCostLabel) {
		this.totalOrderCostLabel = totalOrderCostLabel;
	}


}
