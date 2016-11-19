package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.saikrupa.app.dto.VendorData;
import com.saikrupa.app.service.VendorService;
import com.saikrupa.app.service.impl.DefaultVendorService;
import com.saikrupa.app.ui.component.AppWebLabel;
import com.saikrupa.app.ui.models.ContactPersonListCellRenderer;
import com.saikrupa.app.ui.models.ContactPersonListModel;
import com.saikrupa.app.ui.models.VendorTableModel;

public class CreateVendorDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WebList contactPersonsList;

	public CreateVendorDialog(SKAMainApp owner) {
		super(owner, true);
		setTitle("Create New Vendor...");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(owner);
		setLocationRelativeTo(owner);
		setResizable(false);
	}

	@SuppressWarnings("unchecked")
	private void buildGUI(final SKAMainApp owner) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Vendor Name : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		final WebTextField vendorNameText = new WebTextField(30);

		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		final WebTextField primaryContact = new WebTextField(15);

		WebLabel l3 = new WebLabel("Contact Person : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		contactPersonsList = new WebList(new ContactPersonListModel());
		contactPersonsList.setCellRenderer(new ContactPersonListCellRenderer());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(vendorNameText, c);
		formPanel.add(vendorNameText);

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 0, 0); // Left padding
		c.gridwidth = 3;
		WebScrollPane areaScroll = new WebScrollPane(contactPersonsList);
		areaScroll.setBorder(primaryContact.getBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);

		WebButton addContactPersonButton = new WebButton();
		addContactPersonButton.setBackground(Color.white);
		addContactPersonButton.setIcon(createImageIcon("plus.png"));
		addContactPersonButton.setActionCommand("CREATE_CONTACT");

		c.gridx = 4;
		c.gridy = 2;
		c.insets = new Insets(0, 1, 0, 0); // Left padding

		layout.setConstraints(addContactPersonButton, c);
		formPanel.add(addContactPersonButton);

		WebButton createVendorButton = new WebButton("Create Vendor");
		createVendorButton.setFont(applyLabelFont());
		createVendorButton.setActionCommand("CREATE_VENDOR");

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equalsIgnoreCase("CREATE_CONTACT")) {
					displayAddContactPersonDialog(CreateVendorDialog.this);
				} else if (e.getActionCommand().equalsIgnoreCase("CREATE_VENDOR")) {
					if (vendorNameText.getText().isEmpty()) {
						final WebPopOver popOver = new WebPopOver(CreateVendorDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Missing Vendor Name"));
						popOver.show((WebTextField) vendorNameText);
					} else if (primaryContact.getText().isEmpty()) {
						final WebPopOver popOver = new WebPopOver(CreateVendorDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Missing Vendor Primary Contact Number"));
						popOver.show((WebTextField) primaryContact);
					}
					ContactPersonListModel listModel = (ContactPersonListModel) contactPersonsList.getModel();
					if (listModel.getContactList().isEmpty()) {
						final WebPopOver popOver = new WebPopOver(CreateVendorDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Missing Vendor Contact Person"));
						popOver.show((WebList) contactPersonsList);
					} else {
						VendorData vendor = new VendorData();
						vendor.setName(vendorNameText.getText());
						vendor.setPrimaryContactNo(primaryContact.getText());

						vendor.setContactPersons(listModel.getContactList());
						if (processCreateVendorEvent(vendor)) {
							dispose();
							VendorTableModel tableModel = (VendorTableModel) owner.getVendorContentTable().getModel();
							tableModel.getVendorDataList().add(vendor);
							tableModel.fireTableDataChanged();
							showSuccessNotification();
						}
					}
				}
			}
		};
		createVendorButton.addActionListener(listener);
		addContactPersonButton.addActionListener(listener);

		c.gridx = 2;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		c.gridwidth = 1;
		layout.setConstraints(createVendorButton, c);
		formPanel.add(createVendorButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	private void displayAddContactPersonDialog(CreateVendorDialog createVendorDialog) {
		CreateContactPersonDialog contactPersonDialog = new CreateContactPersonDialog(this);
		contactPersonDialog.setVisible(true);
	}

	private boolean processCreateVendorEvent(final VendorData vendor) {
		VendorService service = new DefaultVendorService();
		try {
			service.createVendor(vendor);
		} catch (Exception w) {
			w.printStackTrace();
			return false;
		}

		return true;
	}

	public WebList getContactPersonsList() {
		return contactPersonsList;
	}
}
