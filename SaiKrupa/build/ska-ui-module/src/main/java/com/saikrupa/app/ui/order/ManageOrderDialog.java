package com.saikrupa.app.ui.order;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.table.WebTable;
import com.saikrupa.app.dao.OrderDAO;
import com.saikrupa.app.dao.impl.DefaultOrderDAO;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.ui.BaseAppDialog;
import com.saikrupa.app.ui.SKAMainApp;
import com.saikrupa.app.ui.models.OrderTableModel;

public class ManageOrderDialog extends BaseAppDialog {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane orderTabbedPane;
	private WebTable orderEntryTable;

	private OrderData orderData;
	private SKAMainApp mainApp;

	public ManageOrderDialog(SKAMainApp owner) {
		super(owner, true);
		setMainApp(owner);
		setTitle("Create New Order...");
		buildUI();
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);			
		setResizable(false);
	}	
	
	private void buildUI() {
		this.orderTabbedPane = new WebTabbedPane();
		orderTabbedPane.setMinimumSize(new Dimension(800, 600));
		orderTabbedPane.setTabPlacement(WebTabbedPane.TOP);

		orderTabbedPane.addTab("Order Item", new GroupPanel(4, false, new AddOrderEntryPanel(this)));

		// Disabled tab
		orderTabbedPane.addTab("Customer Detail", new GroupPanel(4, false, new CustomerDetailPanel(this)));
		orderTabbedPane.setEnabledAt(1, false);

		// Selected tab
		orderTabbedPane.addTab("Payment & Review", new GroupPanel(4, false, new OrderPaymentReviewPanel(this)));
		orderTabbedPane.setEnabledAt(2, false);

		orderTabbedPane.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				WebTabbedPane source = (WebTabbedPane) e.getSource();
				if (source.getSelectedIndex() == 2) {
					Component c = source.getComponentAt(2);
					if (c instanceof GroupPanel) {
						GroupPanel panel = (GroupPanel) c;
						OrderPaymentReviewPanel reviewPanel = (OrderPaymentReviewPanel) panel.getComponent(0);
						reviewPanel.getTotalOrderCostLabel().setText(String.valueOf(orderData.getTotalPrice()));
					}
				}
			}
		});
		
		getContentPane().add(orderTabbedPane, BorderLayout.CENTER);
		setMinimumSize(new Dimension(800, 600));
	}

	public ManageOrderDialog(SKAMainApp sdMainApp, OrderData data) {
		this(sdMainApp);
		setTitle("View / Update Order - " + data.getCode());
		loadOrderData(data);

	}

	public void processPostOrderCreateEvent() {
		dispose();
		OrderTableModel model = (OrderTableModel) getMainApp().getOrderContentTable().getModel();
		OrderDAO dao = new DefaultOrderDAO();
		model.setOrderDataList(dao.findAllOrders());
		model.fireTableDataChanged();
		showSuccessNotification();

	}

	private void loadOrderData(OrderData data) {

	}

	public JTabbedPane getOrderTabbedPane() {
		return orderTabbedPane;
	}

	public void setOrderTabbedPane(JTabbedPane orderTabbedPane) {
		this.orderTabbedPane = orderTabbedPane;
	}

	public WebTable getOrderEntryTable() {
		return orderEntryTable;
	}

	public void setOrderEntryTable(WebTable orderEntryTable) {
		this.orderEntryTable = orderEntryTable;
	}

	public OrderData getOrderData() {
		return orderData;
	}

	public void setOrderData(OrderData orderData) {
		this.orderData = orderData;
	}

	public SKAMainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(SKAMainApp mainApp) {
		this.mainApp = mainApp;
	}

}
