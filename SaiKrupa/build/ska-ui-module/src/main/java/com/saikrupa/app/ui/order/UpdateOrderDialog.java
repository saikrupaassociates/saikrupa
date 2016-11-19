package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebEditorPane;
import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.service.OrderService;
import com.saikrupa.app.service.impl.DefaultOrderService;
import com.saikrupa.app.ui.BaseAppDialog;
import com.saikrupa.app.ui.SKAMainApp;
import com.saikrupa.app.ui.models.OrderEntryTableModel;

public class UpdateOrderDialog extends BaseAppDialog {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private OrderData currentOrder;
	
	private SKAMainApp parentFrame;

	public UpdateOrderDialog(SKAMainApp sdMainApp, OrderData data) {
		super(sdMainApp, true);
		setCurrentOrder(data);
		setParentFrame(sdMainApp);
		setTitle("View / Update Order");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(sdMainApp, data);
		setLocationRelativeTo(sdMainApp);
		setResizable(false);
	}

	public UpdateOrderDialog(SKAMainApp owner) {

	}

	private WebTable lineDetailTable;

	private WebPanel orderLineDetailPanel(final OrderData data) {
		WebPanel formPanel = new WebPanel(new BorderLayout());
		WebLabel l1 = new WebLabel("  Ordered Line Items", SwingConstants.LEFT);
		l1.setFont(applyLabelFont());
		List<OrderEntryData> entries = data.getOrderEntries();
		OrderEntryTableModel model = new OrderEntryTableModel(entries);
		lineDetailTable = new WebTable(model);

		WebScrollPane scrollPane = new WebScrollPane(lineDetailTable);

		lineDetailTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 12));
		lineDetailTable.setRowHeight(25);
		lineDetailTable.setFont(new Font("verdana", Font.PLAIN, 12));

		formPanel.add(l1, BorderLayout.NORTH);
		formPanel.add(scrollPane, BorderLayout.CENTER);

		return formPanel;

	}

	private WebButton updateButton;

	private WebLabel paymentStatusLabel;
	private WebLabel deliveryStatusLabel;

	private void buildGUI(final SKAMainApp owner, final OrderData data) {
		WebPanel mainPanel = new WebPanel(new BorderLayout());
		WebPanel linePanel = orderLineDetailPanel(data);
		mainPanel.add(linePanel, BorderLayout.NORTH);

		WebPanel formPanel = new WebPanel();
		mainPanel.add(formPanel, BorderLayout.WEST);

		WebPanel dummyPanel = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
		mainPanel.add(dummyPanel, BorderLayout.SOUTH);
		dummyPanel.add(new WebLabel(""));

		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		layout.preferredLayoutSize(owner);

		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel custNameLabel = new WebLabel("Customer Name :", SwingConstants.RIGHT);
		WebLabel custNameValue = new WebLabel(data.getCustomer().getName());
		custNameLabel.setFont(applyLabelFont());
		custNameValue.setFont(applyLabelFont());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10); // Left padding
		layout.setConstraints(custNameLabel, c);
		formPanel.add(custNameLabel);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 10, 30); // Left padding
		layout.setConstraints(custNameValue, c);
		formPanel.add(custNameValue);

		WebLabel l1 = new WebLabel("Order Amount :", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());

		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(10, 40, 10, 10); // Left padding
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		final WebLabel orderAmountLabel = new WebLabel(String.valueOf(data.getTotalPrice()));
		orderAmountLabel.setFont(applyLabelFont());
		orderAmountLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		c.gridx = 3;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 10, 30);
		layout.setConstraints(orderAmountLabel, c);
		formPanel.add(orderAmountLabel);

		WebLabel l2 = new WebLabel("Payment Status :", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());

		c.gridx = 4;
		c.gridy = 0;
		c.insets = new Insets(10, 40, 10, 10); // Left padding
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		paymentStatusLabel = new WebLabel(data.getPaymentStatus().toString());
		paymentStatusLabel.setFont(applyLabelFont());

		WebButton paymentStatusLookupBtn = new WebButton();
		paymentStatusLookupBtn.setBackground(Color.white);
		paymentStatusLookupBtn.setIcon(createImageIcon("search.png"));

		WebPanel paymentStatusPanel = new WebPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
		paymentStatusPanel.add(paymentStatusLabel);
		paymentStatusPanel.add(paymentStatusLookupBtn);

		c.gridx = 5;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 10, 0); // Left padding
		layout.setConstraints(paymentStatusPanel, c);
		formPanel.add(paymentStatusPanel);

		

		WebLabel addressLabel = new WebLabel("Delivery Address :", SwingConstants.RIGHT);
		addressLabel.setFont(applyLabelFont());
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		c.insets = new Insets(10, 10, 10, 10); // Left padding
		layout.setConstraints(addressLabel, c);
		formPanel.add(addressLabel);

		WebEditorPane addressLabelValue = new WebEditorPane();
		StringBuffer value = new StringBuffer();
		value.append(data.getDeliveryAddress().getLine1()).append("\n");
		value.append(data.getDeliveryAddress().getLine2()).append("\n");
		value.append(data.getDeliveryAddress().getLandmark()).append("\n");
		value.append(data.getDeliveryAddress().getZipCode());

		addressLabelValue.setText(value.toString());
		addressLabelValue.setFont(applyLabelFont());
		addressLabelValue.setEditable(false);
		addressLabelValue.setSize(100, 100);
		addressLabelValue.setBackground(getBackground());

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(10, 0, 0, 0);
		c.gridwidth = 2;
		WebScrollPane areaScroll = new WebScrollPane(addressLabelValue);
		areaScroll.setBorder(BorderFactory.createEmptyBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);

		WebLabel l3 = new WebLabel("Order Status :", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());

		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(10, 40, 10, 10);
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		WebLabel orderStatusLabel = new WebLabel(data.getOrderStatus().toString());
		orderStatusLabel.setFont(applyLabelFont());
		
		if(data.getOrderStatus() == OrderStatus.COMPLETED) {
			orderStatusLabel.setForeground(Color.GREEN);
		}

		c.gridx = 5;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 10, 0); // Left padding
		layout.setConstraints(orderStatusLabel, c);
		formPanel.add(orderStatusLabel);

		WebLabel l4 = new WebLabel("Delivery Status :", SwingConstants.RIGHT);
		l4.setFont(applyLabelFont());

		c.gridx = 3;
		c.gridy = 2;
		c.insets = new Insets(10, 40, 10, 10);
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		deliveryStatusLabel = new WebLabel(data.getDeliveryStatus().toString());
		deliveryStatusLabel.setFont(applyLabelFont());

		WebButton deliveryStatusLookupBtn = new WebButton();
		deliveryStatusLookupBtn.setBackground(Color.white);
		deliveryStatusLookupBtn.setIcon(createImageIcon("search.png"));

		WebPanel deliveryStatusPanel = new WebPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
		deliveryStatusPanel.add(deliveryStatusLabel);
		deliveryStatusPanel.add(deliveryStatusLookupBtn);

		c.gridx = 5;
		c.gridy = 2;
		c.insets = new Insets(10, 0, 10, 0); // Left padding
		layout.setConstraints(deliveryStatusPanel, c);
		formPanel.add(deliveryStatusPanel);

		updateButton = new WebButton("Update");
		updateButton.setFont(applyLabelFont());

		final WebButton cancelButton = new WebButton("Cancel");
		cancelButton.setFont(applyLabelFont());

		WebPanel buttonPanel = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(updateButton);
		buttonPanel.add(cancelButton);

		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		c.insets = new Insets(10, 10, 10, 10); // Left padding
		c.gridwidth = 7;
		layout.setConstraints(buttonPanel, c);
		formPanel.add(buttonPanel);

		updateButton.setActionCommand("UPDATE_ORDER");
		cancelButton.setActionCommand("CANCEL");
		deliveryStatusLookupBtn.setActionCommand("LOOKUP_DELIVERY_STATUS");
		paymentStatusLookupBtn.setActionCommand("LOOKUP_PAYMENT_STATUS");

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equalsIgnoreCase("LOOKUP_PAYMENT_STATUS")) {
					PaymentStatus[] paymentStatuses = { PaymentStatus.PAID, PaymentStatus.PENDING };
					PaymentStatus selected = (PaymentStatus) WebOptionPane.showInputDialog(UpdateOrderDialog.this,
							"Payment Status", "Select Payment Status", WebOptionPane.QUESTION_MESSAGE, null,
							paymentStatuses, getCurrentOrder().getPaymentStatus());
					if (selected != null && getCurrentOrder().getPaymentStatus() != selected) {
						paymentStatusLabel.setText(selected.toString());
						getCurrentOrder().setPaymentStatus(selected);
					}

				} else if (e.getActionCommand().equalsIgnoreCase("LOOKUP_DELIVERY_STATUS")) {
					DeliveryStatus[] deliveryStatuses = { DeliveryStatus.SHIPPED, DeliveryStatus.SHIPPING };
					DeliveryStatus selected = (DeliveryStatus) WebOptionPane.showInputDialog(UpdateOrderDialog.this,
							"Delivery Status", "Select Delivery Status", WebOptionPane.QUESTION_MESSAGE, null,
							deliveryStatuses, getCurrentOrder().getDeliveryStatus());

					if (selected != null && getCurrentOrder().getDeliveryStatus() != selected) {
						if (selected == DeliveryStatus.SHIPPED) {
							displayUpdateDeliveryDetailDialog();
						}
						deliveryStatusLabel.setText(selected.toString());
					}
				} else if (e.getActionCommand().equalsIgnoreCase("UPDATE_ORDER")) {
					updateOrderData(owner);
					dispose();
				} else if (e.getActionCommand().equalsIgnoreCase("CANCEL")) {
					dispose(); 
				} else if (e.getActionCommand().equalsIgnoreCase("SHOW_DELIVERY_DETAIL")) {
					displayDeliveryDetailDialog();
				}
			}			
		};

		updateButton.addActionListener(listener);
		cancelButton.addActionListener(listener);
		deliveryStatusLookupBtn.addActionListener(listener);
		paymentStatusLookupBtn.addActionListener(listener);
		
		if (data.getPaymentStatus() == PaymentStatus.PAID) {
			paymentStatusLookupBtn.setEnabled(false);
			
		}
		if (data.getDeliveryStatus() == DeliveryStatus.SHIPPED) {
			deliveryStatusLookupBtn.setIcon(createImageIcon("info.png"));
			deliveryStatusLookupBtn.setActionCommand("SHOW_DELIVERY_DETAIL");
			deliveryStatusLookupBtn.setToolTipText("Display Shipping Detail");
		}
		
		if (data.getOrderStatus() == OrderStatus.COMPLETED) {
			updateButton.setEnabled(false);
		}

		getContentPane().add(mainPanel, BorderLayout.CENTER);
		pack();
		// setResizable(false);
	}
	
	private void displayDeliveryDetailDialog() {
		DisplayOrderDeliveryInfoDialog dialog = new DisplayOrderDeliveryInfoDialog(getParentFrame() , getCurrentOrder().getOrderEntries().get(0));
		dialog.setVisible(true);
	}

	private void displayUpdateDeliveryDetailDialog() {
		lineDetailTable.setSelectedRow(0);
		int selectedLine = lineDetailTable.getSelectedRow();
		OrderEntryTableModel entryTableModel = (OrderEntryTableModel) lineDetailTable.getModel();
		OrderEntryData orderEntry = entryTableModel.getOrderEntryDataList().get(selectedLine);
		UpdateOrderDeliveryDetailDialog dialog = new UpdateOrderDeliveryDetailDialog(this, orderEntry);
		dialog.setVisible(true);
	}

	private void updateOrderData(SKAMainApp owner) {
		getCurrentOrder().setPaymentStatus(PaymentStatus.valueOf(paymentStatusLabel.getText()));
		getCurrentOrder().setDeliveryStatus(DeliveryStatus.valueOf(deliveryStatusLabel.getText()));

		if (getCurrentOrder().getPaymentStatus() == PaymentStatus.PAID) {
			getCurrentOrder().setOrderStatus(OrderStatus.CONFIRMED);
		}

		if (getCurrentOrder().getPaymentStatus() == PaymentStatus.PENDING) {
			getCurrentOrder().setOrderStatus(OrderStatus.CREATED);
		}
		if (getCurrentOrder().getDeliveryStatus() == DeliveryStatus.SHIPPED) {
			getCurrentOrder().setOrderStatus(OrderStatus.DELIVERED);
		}

		if (getCurrentOrder().getDeliveryStatus() == DeliveryStatus.SHIPPED
				&& getCurrentOrder().getPaymentStatus() == PaymentStatus.PAID) {
			getCurrentOrder().setOrderStatus(OrderStatus.COMPLETED);
		}
		processUpdateOrderEvent(owner);
	}

	protected void processUpdateOrderEvent(SKAMainApp owner) {
		try {
			OrderService service = new DefaultOrderService();
			service.updateOrderStatus(getCurrentOrder());
			dispose();
			showSuccessNotification();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void notifyParent() {
		updateButton.setEnabled(true);
	}

	public void resetDeliveryStatus() {
		deliveryStatusLabel.setText(getCurrentOrder().getDeliveryStatus().toString());
	}

	public OrderData getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(OrderData currentOrder) {
		this.currentOrder = currentOrder;
	}

	public SKAMainApp getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(SKAMainApp parentFrame) {
		this.parentFrame = parentFrame;
	}
}
