package com.saikrupa.app.ui;

import java.awt.BorderLayout;
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
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.saikrupa.app.dao.ProductDAO;
import com.saikrupa.app.dao.impl.DefaultProductDAO;
import com.saikrupa.app.dto.InventoryData;
import com.saikrupa.app.dto.InventoryEntryData;
import com.saikrupa.app.dto.ProductData;
import com.saikrupa.app.service.ProductService;
import com.saikrupa.app.service.impl.DefaultProductService;
import com.saikrupa.app.ui.component.AppWebLabel;
import com.saikrupa.app.ui.models.ProductTableModel;
import com.saikrupa.app.util.DateUtil;

public class ManageProductDialog extends BaseAppDialog {

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	public ManageProductDialog(SKAMainApp sdMainApp, ProductData data) {
		super(sdMainApp, true);
		setTitle("View / Update Product");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(sdMainApp, data);
		setLocationRelativeTo(sdMainApp);
		setResizable(false);
	}

	public ManageProductDialog(SKAMainApp owner) {	
		
	}

	private void buildGUI(final SKAMainApp owner, final ProductData data) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Product Code : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		final WebLabel codeText = new WebLabel(data.getCode());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10); 
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 0); 

		layout.setConstraints(codeText, c);
		formPanel.add(codeText);

		WebLabel l2 = new WebLabel("Name : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		final WebLabel nameText = new WebLabel(data.getName());

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 10); 
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 10); // Left padding

		layout.setConstraints(nameText, c);
		formPanel.add(nameText);

		WebLabel l3 = new WebLabel("Orderable : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		final WebLabel orderableText = new WebLabel("YES");

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10, 10, 10, 10); 		
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(10, 10, 10, 0); // Left padding

		layout.setConstraints(orderableText, c);
		formPanel.add(orderableText);

		WebLabel l4 = new WebLabel("Available Quantity : ", SwingConstants.RIGHT);
		l4.setFont(applyLabelFont());
		Double avilableQuantity = data.getInventory() != null ? data.getInventory().getTotalAvailableQuantity() : 0.0;
		final WebLabel avalQuntityText = new WebLabel(String.valueOf(avilableQuantity));

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 10, 10); 		
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 3;

		c.insets = new Insets(10, 10, 10, 10); // Left padding

		layout.setConstraints(avalQuntityText, c);
		formPanel.add(avalQuntityText);

		WebLabel l5 = new WebLabel("Reserved Quantity : ", SwingConstants.RIGHT);
		l5.setFont(applyLabelFont());
		
		Double resQuantity = data.getInventory() != null ? data.getInventory().getTotalReservedQuantity() : 0.0;
		final WebLabel resvQuntityText = new WebLabel(String.valueOf(resQuantity));

		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 10, 10); 		
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 10, 0); // Left padding

		layout.setConstraints(resvQuntityText, c);
		formPanel.add(resvQuntityText);

		WebLabel l6 = new WebLabel("Status As on : ", SwingConstants.RIGHT);
		l6.setFont(applyLabelFont());
		final WebLabel statusDateText = new WebLabel(DateUtil.convertToString("dd-MMM-yyyy", data.getInventory().getLastUpdatedDate()));

		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 10, 10); 		
		layout.setConstraints(l6, c);
		formPanel.add(l6);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 10, 10); // Left padding

		layout.setConstraints(statusDateText, c);
		formPanel.add(statusDateText);

		WebLabel l7 = new WebLabel("Quantity Added : ", SwingConstants.RIGHT);
		l7.setFont(applyLabelFont());
		final WebTextField addedQuantityText = new WebTextField(10);

		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 10, 10); 		
		layout.setConstraints(l7, c);
		formPanel.add(l7);

		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 10, 0); // Left padding

		layout.setConstraints(addedQuantityText, c);
		formPanel.add(addedQuantityText);
		
		WebLabel l10 = new WebLabel("Quantity Rejected : ", SwingConstants.RIGHT);
		l10.setFont(applyLabelFont());
		final WebTextField rejectedQuantityText = new WebTextField(10);		
		rejectedQuantityText.setText(String.valueOf(0.0));
		
		c.gridx = 0;
		c.gridy = 7;
		c.insets = new Insets(10, 10, 10, 10); 		
		layout.setConstraints(l10, c);
		formPanel.add(l10);

		c.gridx = 1;
		c.gridy = 7;
		c.insets = new Insets(10, 10, 10, 0); // Left padding
		
		layout.setConstraints(rejectedQuantityText, c);
		formPanel.add(rejectedQuantityText);
		
		
		WebLabel l8 = new WebLabel("Quantity Reserved : ", SwingConstants.RIGHT);
		l8.setFont(applyLabelFont());
		final WebTextField reservedQuantityText = new WebTextField(10);
		reservedQuantityText.setText("0.0");

		c.gridx = 0;
		c.gridy = 8;
		c.insets = new Insets(10, 10, 10, 10); 		
		layout.setConstraints(l8, c);
		formPanel.add(l8);

		c.gridx = 1;
		c.gridy = 8;
		c.insets = new Insets(10, 10, 10, 10); // Left padding

		layout.setConstraints(reservedQuantityText, c);
		formPanel.add(reservedQuantityText);
		
		final WebButton updateProductButton = new WebButton("Update");
		updateProductButton.setFont(applyLabelFont());
		
		final WebButton cancelButton = new WebButton("Cancel");
		cancelButton.setFont(applyLabelFont());
		
		ActionListener listener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == cancelButton) {
					dispose();
				}
				if(e.getSource() == updateProductButton) {
					if(addedQuantityText.getText().isEmpty()) {
						final WebPopOver popOver = new WebPopOver(ManageProductDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Specify atleast one value to update"));
						popOver.show((WebTextField) addedQuantityText);				
					} else {
						if(!isNumeric(addedQuantityText.getText())) {
							final WebPopOver popOver = new WebPopOver(ManageProductDialog.this);
							popOver.setCloseOnFocusLoss(true);
							popOver.setMargin(10);
							popOver.setLayout(new VerticalFlowLayout());
							popOver.add(new AppWebLabel("Added Quantity must be a numeric value"));
							popOver.show((WebTextField) addedQuantityText);
						} else if(!isNumeric(reservedQuantityText.getText())) {
							final WebPopOver popOver = new WebPopOver(ManageProductDialog.this);
							popOver.setCloseOnFocusLoss(true);
							popOver.setMargin(10);
							popOver.setLayout(new VerticalFlowLayout());
							popOver.add(new AppWebLabel("Reserved Quantity must be a numeric value"));
							popOver.show((WebTextField) reservedQuantityText);						
						} else {
							ProductDAO productDAO = new DefaultProductDAO();
							InventoryData inventory = productDAO.findInventoryLevelByProduct(data);
							
							InventoryEntryData entryData = new InventoryEntryData();
							entryData.setInventory(inventory);
							entryData.setAddedQuantity(Double.valueOf(addedQuantityText.getText()));
							entryData.setReservedQuantity(Double.valueOf(reservedQuantityText.getText()));
							entryData.setDamagedQuantity(Double.valueOf(rejectedQuantityText.getText()));
							processUpdateProductEvent(entryData, owner);
						}
					}
				}				
			}
		};
		
		updateProductButton.addActionListener(listener);		
		cancelButton.addActionListener(listener);
		
		c.gridx = 2;
		c.gridy = 10;
		c.insets = new Insets(10, 10, 10, 10); 	
		layout.setConstraints(updateProductButton, c);
		formPanel.add(updateProductButton);
		
		c.gridx = 3;
		c.gridy = 10;
		c.insets = new Insets(10, 10, 10, 10); 
		layout.setConstraints(cancelButton, c);
		formPanel.add(cancelButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
		
	}
	
	protected void processUpdateProductEvent(InventoryEntryData data, SKAMainApp owner) {
		ProductService productService = new DefaultProductService();
		try {
			System.out.println("Updaating Product : "+data.getInventory().getProduct().getName());
			productService.updateInventory(data);	
			dispose();
			ProductTableModel model = (ProductTableModel) owner.getProductContentTable().getModel();
			ProductDAO dao = new DefaultProductDAO();
			model.setProductDataList(dao.findAllProducts());
			model.fireTableDataChanged();		
			showSuccessNotification();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean isNumeric(String text) { 
		boolean numeric = true;
		try {
			Double.valueOf(text);
		} catch (NumberFormatException ne) {
			numeric = false;
		}
		return numeric;
	}
}
