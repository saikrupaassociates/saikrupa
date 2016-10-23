package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.saikrupa.app.dto.CustomerData;
import com.saikrupa.app.service.CustomerService;
import com.saikrupa.app.service.impl.DefaultCustomerService;
import com.saikrupa.app.ui.models.CustomerTableModel;
import com.saikrupa.app.ui.order.CustomerDetailPanel;

public class DisplayCustomerListDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WebButton useCustomerButton;

	private CustomerDetailPanel ownerPanel;
	private WebTable customerContentTable;

	public DisplayCustomerListDialog(CustomerDetailPanel panel) {
		super(panel.getOwner(), true);
		this.ownerPanel = panel;
		setTitle("Select Customer...");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		useCustomerButton = new WebButton("Use this Customer");
		useCustomerButton.setActionCommand("USE_CUSTOMER");
		buildGUI();
		setLocationRelativeTo(panel.getOwner());
		setModal(true);
	}
	
	public DisplayCustomerListDialog(SKAMainApp owner) {
		super(owner, true);
		setTitle("Select Customer...");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		useCustomerButton = new WebButton("Use this Customer");
		useCustomerButton.setActionCommand("CUSTOMER_REPORT");
		buildGUI();
		setLocationRelativeTo(owner);
		setModal(true);
	}

	private void buildGUI() {
		customerContentTable = new WebTable();
		loadCustomerData();
		customerContentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(new WebScrollPane(customerContentTable));

		customerContentTable.getTableHeader().setFont(applyTableFont());
		customerContentTable.setRowHeight(35);
		customerContentTable.setFont(applyTableFont());

		getContentPane().add(new WebScrollPane(customerContentTable), BorderLayout.CENTER);

		WebPanel buttonPanel = new WebPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		useCustomerButton.setFont(applyLabelFont());
		useCustomerButton.setEnabled(false);
		

		WebButton cancelButton = new WebButton("Cancel");
		cancelButton.setFont(applyLabelFont());
		cancelButton.setActionCommand("CANCEL");

		customerContentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				if (customerContentTable.getSelectedRow() != -1) {
					useCustomerButton.setEnabled(true);
				}
			}
		});
		
		final MouseAdapter mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					if(ownerPanel != null) {
						performSelectionOperation(customerContentTable, ownerPanel);
					}					
				}
			}
		};
		if(ownerPanel != null) {
			customerContentTable.addMouseListener(mouseListener);
		}
		

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equalsIgnoreCase("USE_CUSTOMER")) {
					performSelectionOperation(customerContentTable, ownerPanel);
				} else if(e.getActionCommand().equalsIgnoreCase("CUSTOMER_REPORT")) {					
					performSelectionOperation();
				} else if(e.getActionCommand().equalsIgnoreCase("CANCEL")) {					
					customerContentTable.setSelectedRow(-1);
				}
				dispose();
			}
		};
		useCustomerButton.addActionListener(listener);
		cancelButton.addActionListener(listener);

		buttonPanel.add(useCustomerButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setSize(new Dimension(1000, 400));
	}

	private void loadCustomerData() {
		CustomerService service = new DefaultCustomerService();
		List<CustomerData> customers = service.getAllCustomers();
		try {
			customerContentTable.setModel(new CustomerTableModel(customers));
		} catch (Exception w) {
			w.printStackTrace();
		}
	}
	
	public CustomerData performSelectionOperation() {
		int selection = customerContentTable.getSelectedRow();
		if(selection != -1) {
			CustomerTableModel model = (CustomerTableModel) customerContentTable.getModel();
			return model.getCustomerDataList().get(selection);
		}
		return null;
	}

	private void performSelectionOperation(WebTable contentTable, CustomerDetailPanel panel) {
		int selection = contentTable.getSelectedRow();
		CustomerTableModel model = (CustomerTableModel) contentTable.getModel();
		CustomerData data = model.getCustomerDataList().get(selection);
		panel.updateCustomerData(data);
	}

}
