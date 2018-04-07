package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.dto.ProductData;
import com.saikrupa.app.ui.component.AppWebLabel;
import com.saikrupa.app.ui.component.AppWebPanel;
import com.saikrupa.app.ui.models.OrderEntryTableModel;
import com.saikrupa.app.ui.models.ProductListCellRenderer;
import com.saikrupa.app.ui.models.ProductSelectionModel;

public class AddOrderEntryPanel extends AppWebPanel {

	private JTabbedPane orderPane;
	private static final long serialVersionUID = 1L;
	private WebComboBox productComboBox;
	
	private OrderData order;
	private ManageOrderDialog owner;
	private WebTable orderEntryTable;

	public AddOrderEntryPanel(ManageOrderDialog owner) {
		this.owner = owner;
		setSize(new Dimension(600, 800));
		this.orderPane = owner.getOrderTabbedPane();
		order = new OrderData();
		order.setOrderStatus(OrderStatus.CREATED);
		order.setPaymentStatus(PaymentStatus.PENDING);
		buildUI();
	}
	
	public AddOrderEntryPanel(JTabbedPane orderPane) {
		setSize(new Dimension(600, 800));
		this.orderPane = orderPane;
		order = new OrderData();
		order.setOrderStatus(OrderStatus.CREATED);
		order.setPaymentStatus(PaymentStatus.PENDING);
		buildUI();
	}

	@SuppressWarnings("unchecked")
	private void buildUI() {
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
		setLayout(new BorderLayout());
		WebLabel l1 = new WebLabel("Select Product : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		productComboBox = new WebComboBox(new ProductSelectionModel());
		productComboBox.setRenderer(new ProductListCellRenderer());		
		
		productComboBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				performselectionDetail(productComboBox.getSelectedItem());
				addToOrderButton.setEnabled(false);
			}			
		});
		
		WebPanel selectionPanel = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		selectionPanel.add(l1);
		selectionPanel.add(productComboBox);
		
		add(selectionPanel, BorderLayout.NORTH);
		buildDetailPanel();
	}
	
	private WebLabel codeLabel;
	private WebLabel nameLabel;
	private WebLabel availableQuantityLabel;
	private WebLabel reservedQuantityLabel;
	
	private WebTextField orderQuantityText;
	private WebTextField priceText;
	private WebTextField transportationCostText;
	private WebTextField discountText;
	
	
	private WebPanel productDetailPanel;
	
	private WebButton nextButton;
	private WebButton addToOrderButton;	
	private WebLabel totalOrderCostLabel;
	
	private void buildDetailPanel() {
		productDetailPanel = new WebPanel();	
		
		GridBagLayout layout = new GridBagLayout();
		productDetailPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		WebLabel l1 = new WebLabel("Product Code : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		codeLabel = new WebLabel("DEFAULT");

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(15, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		productDetailPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(codeLabel, c);
		productDetailPanel.add(codeLabel);
		
		WebLabel l2 = new WebLabel("Product Name : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		nameLabel = new WebLabel("DEFAULT");
		

		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(15, 30, 10, 0); // Left padding
		layout.setConstraints(l2, c);
		productDetailPanel.add(l2);

		c.gridx = 3;
		c.gridy = 0;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(nameLabel, c);
		productDetailPanel.add(nameLabel);
		
		WebLabel l3 = new WebLabel("Available Quantity : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		availableQuantityLabel = new WebLabel("DEFAULT");

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(15, 0, 10, 0); // Left padding
		layout.setConstraints(l3, c);
		productDetailPanel.add(l3);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(availableQuantityLabel, c);
		productDetailPanel.add(availableQuantityLabel);
		
		WebLabel l4 = new WebLabel("Reserved Quantity : ", SwingConstants.RIGHT);
		l4.setFont(applyLabelFont());
		reservedQuantityLabel = new WebLabel("DEFAULT");

		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(15, 30, 10, 0); // Left padding
		layout.setConstraints(l4, c);
		productDetailPanel.add(l4);

		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(reservedQuantityLabel, c);
		productDetailPanel.add(reservedQuantityLabel);
		
		WebLabel l5 = new WebLabel("Order Quantity : ", SwingConstants.RIGHT);
		l5.setFont(applyLabelFont());
		orderQuantityText = new WebTextField(10);
		
		orderQuantityText.setText("1200");		

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(15, 0, 10, 0); // Left padding
		layout.setConstraints(l5, c);
		productDetailPanel.add(l5);

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(orderQuantityText, c);
		productDetailPanel.add(orderQuantityText);
		
		WebLabel l6 = new WebLabel("Unit Price : ", SwingConstants.RIGHT);
		l6.setFont(applyLabelFont());
		priceText = new WebTextField(10);

		c.gridx = 2;
		c.gridy = 3;
		c.insets = new Insets(15, 30, 10, 0); // Left padding
		layout.setConstraints(l6, c);
		productDetailPanel.add(l6);

		c.gridx = 3;
		c.gridy = 3;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(priceText, c);
		productDetailPanel.add(priceText);
		
		
		//private WebTextField transportationCostText;
		
		WebLabel l7 = new WebLabel("Delivery Cost : ", SwingConstants.RIGHT);
		l7.setFont(applyLabelFont());
		
		transportationCostText = new WebTextField(10);
		transportationCostText.setText("0.0");
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(15, 0, 10, 0); // Left padding
		layout.setConstraints(l7, c);
		productDetailPanel.add(l7);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(transportationCostText, c);
		productDetailPanel.add(transportationCostText);
		
		//private WebTextField discountText;
		
		WebLabel l8 = new WebLabel("Applied Discount : ", SwingConstants.RIGHT);
		l8.setFont(applyLabelFont());
		discountText = new WebTextField(10);
		discountText.setText("0.0");
		c.gridx = 2;
		c.gridy = 4;
		c.insets = new Insets(15, 30, 10, 0); // Left padding
		layout.setConstraints(l8, c);
		productDetailPanel.add(l8);

		c.gridx = 3;
		c.gridy = 4;
		c.insets = new Insets(15, 10, 10, 0); // Left padding

		layout.setConstraints(discountText, c);
		productDetailPanel.add(discountText);
		
		final WebButton calculateButton = new WebButton("Calculate Total");
		calculateButton.setFont(applyLabelFont());
		c.gridx = 2;
		c.gridy = 7;
		c.insets = new Insets(15, 10, 10, 0); // Left padding
		layout.setConstraints(calculateButton, c);
		productDetailPanel.add(calculateButton);
		
		addToOrderButton = new WebButton("Add to Order");
		addToOrderButton.setFont(applyLabelFont());
		c.gridx = 3;
		c.gridy = 7;
		c.insets = new Insets(15, 10, 10, 0); // Left padding
		layout.setConstraints(addToOrderButton, c);
		productDetailPanel.add(addToOrderButton);
		addToOrderButton.setEnabled(false);
		
		nextButton = new WebButton("Next...");
		nextButton.setFont(applyLabelFont());
		nextButton.setEnabled(false);
		
		ActionListener listener = new ActionListener() {
			
			public void actionPerformed(ActionEvent paramActionEvent) {
				if(paramActionEvent.getSource() == calculateButton) {					
					if(!orderQuantityText.getText().isEmpty()) {
						if(isNumeric(orderQuantityText.getText())) {
							if(Double.valueOf(orderQuantityText.getText()) > Double.valueOf(availableQuantityLabel.getText())) {
								showMessage("Ordered Quantity exceeds Available Quantity", orderQuantityText);
								return;
							}
						}
					}
					if(priceText.getText().isEmpty() || !isNumeric(priceText.getText())) {
						showMessage("Unit price is empty or invalid", priceText);
					} else if(!isNumeric(discountText.getText())) {
						showMessage("Discount is invalid", discountText);
					} else if(!isNumeric(transportationCostText.getText())) {
						showMessage("Transportation Cost is invalid", transportationCostText);
					}else {
						if(!isNumeric(priceText.getText())) {
							showMessage("Unit Price is invalid", priceText);
						} else if(!isNumeric(orderQuantityText.getText())) {
							showMessage("Ordered Quantity is invalid", orderQuantityText);
						} else {
							Double totalPrice = Double.valueOf(priceText.getText()) * Double.valueOf(orderQuantityText.getText());							
							Double deliveryCost = 0.0;
							Double discount = 0.0;
							
							if(!transportationCostText.getText().isEmpty()) {
								deliveryCost = Double.valueOf(transportationCostText.getText());
							}
							
							if(!discountText.getText().isEmpty()) {
								discount = Double.valueOf(discountText.getText());
							}						
							
							totalPrice = totalPrice - discount + deliveryCost;							
							
							if(order.getTotalPrice() == null) {
								order.setTotalPrice(totalPrice);
								
							} else {
								order.setTotalPrice(order.getTotalPrice() + totalPrice);
							}
							totalOrderCostLabel.setText(String.valueOf(order.getTotalPrice()));
							addToOrderButton.setEnabled(true);
							calculateButton.setEnabled(false);
						}						
					}					
				}
				
				if(paramActionEvent.getSource() == addToOrderButton) {
					OrderEntryData entry = new OrderEntryData();					
					if(!transportationCostText.getText().isEmpty()) {
						entry.setTransportationCost(Double.valueOf(transportationCostText.getText()));
					}
					
					if(!discountText.getText().isEmpty()) {
						entry.setDiscount(Double.valueOf(discountText.getText()));
					}
					
					entry.setProduct((ProductData)productComboBox.getSelectedItem());
					entry.setOrderedQuantity(Integer.valueOf(orderQuantityText.getText()));
					entry.setPrice(Double.valueOf(priceText.getText()));
					entry.setTransportationCost(transportationCostText.getText().isEmpty() ? 0 : Double.valueOf(transportationCostText.getText()));					
					order.getOrderEntries().add(entry);
					entry.setEntryNumber(order.getOrderEntries().size());					
					updateOrderEntryTableModel(entry);
					addToOrderButton.setEnabled(false);
					nextButton.setEnabled(true);
				}
				if(paramActionEvent.getSource() == nextButton) {
					owner.setOrderData(order);
					orderPane.setEnabledAt(1, true);
					orderPane.setSelectedIndex(1);
				}
				
			}			
		};
		calculateButton.addActionListener(listener);
		addToOrderButton.addActionListener(listener);
		nextButton.addActionListener(listener);
		
		totalOrderCostLabel = new WebLabel("0.0");
		totalOrderCostLabel.setForeground(Color.BLUE);
		totalOrderCostLabel.setFont(applyLabelFont());
		WebPanel totalOrderCostPanel = new WebPanel(new FlowLayout(FlowLayout.LEFT));		
		
		WebLabel totalOrderValueLabel = new WebLabel("Total Order Value : ");
		totalOrderValueLabel.setFont(applyLabelFont());
		
		totalOrderCostPanel.add(totalOrderValueLabel);
		totalOrderCostPanel.add(totalOrderCostLabel);
		
		WebPanel nextButtonPanel = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
		nextButtonPanel.add(nextButton);
		
		WebPanel southPanel = new WebPanel(new BorderLayout());
		southPanel.add(totalOrderCostPanel, BorderLayout.WEST);
		southPanel.add(nextButtonPanel, BorderLayout.EAST);
		
		WebPanel southMainPanel = new WebPanel();
		southMainPanel.setLayout(new BorderLayout());
		southMainPanel.add(southPanel, BorderLayout.CENTER);
		
		WebPanel p = buildOrderEntryTablePanel();
		southMainPanel.add(p, BorderLayout.SOUTH);
		add(southMainPanel, BorderLayout.SOUTH);		
	}
	
	private void updateOrderEntryTableModel(OrderEntryData entry) {
		OrderEntryTableModel model = (OrderEntryTableModel) orderEntryTable.getModel();
		model.getOrderEntryDataList().add(entry);
		model.fireTableDataChanged();
		revalidate();
	}
	
	private void performselectionDetail(Object selectedItem) {	
		ProductData product = (ProductData) selectedItem;
		if(product == null || product.getCode().equals("dummy")) {
			return;
		}
		codeLabel.setText(product.getCode());
		nameLabel.setText(product.getName());
		availableQuantityLabel.setText(product.getInventory().getTotalAvailableQuantity().toString());
		reservedQuantityLabel.setText(product.getInventory().getTotalReservedQuantity().toString());
		if(product.getPriceRow() != null && product.getPriceRow().getPrice() != null) {
			priceText.setText(product.getPriceRow().getPrice().toString());
		} else {
			priceText.setText("");
		}
		productDetailPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Product Info", SwingConstants.CENTER, SwingConstants.CENTER, applyLabelFont()));
		add(productDetailPanel, BorderLayout.CENTER);
		revalidate();
	}
	
	private boolean isNumeric(String text) {
		boolean numeric = true;
		if(!text.isEmpty()) {
			try {
				Double.valueOf(text);
			} catch (NumberFormatException ne) {
				numeric = false;
			}
		}		
		return numeric;
	}
	
	private WebPanel buildOrderEntryTablePanel() {
		WebPanel contentPanel = new WebPanel();
		contentPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		contentPanel.setLayout(new BorderLayout());

		orderEntryTable = new WebTable(new OrderEntryTableModel(new ArrayList<OrderEntryData>()));
		contentPanel.add(new WebScrollPane(orderEntryTable), BorderLayout.SOUTH);

		orderEntryTable.getTableHeader().setFont(applyLabelFont());
		orderEntryTable.setRowHeight(25);
		orderEntryTable.setFont(applyTableFont());
		return contentPanel;		
	}
	
	
	private void showMessage(String message, Component invoker) {
		final WebPopOver popOver = new WebPopOver(AddOrderEntryPanel.this);
		popOver.setCloseOnFocusLoss(true);
		popOver.setMargin(10);
		popOver.setLayout(new VerticalFlowLayout());
		popOver.add(new AppWebLabel(message));
		popOver.show(invoker);
	}

}
