package com.saikrupa.app.ui;

import java.awt.BorderLayout;
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
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.saikrupa.app.dto.InvestmentData;
import com.saikrupa.app.dto.InvestorData;
import com.saikrupa.app.ui.models.InvestmentTableModel;

public class UpdateInvestmentDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WebLabel investorNameLabel;
	private WebLabel primaryContact;
	private WebLabel totalInvestmentsLabel;
	private WebTable investmentTable;
	
	public UpdateInvestmentDialog(SKAMainApp owner, InvestorData data) {
		
		
		super(owner, true);
		setTitle("Display Investments - "+data.getName());
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);		
		buildGUI(owner, data);
		setLocationRelativeTo(owner);
		setResizable(false);			
	}
	
	private Double getTotalInvestment(InvestorData data) {
		double amount = 0;
		for(InvestmentData investment : data.getInvestments()) {
			amount = amount + investment.getAmount();
		}
		return Double.valueOf(amount);
	}
	
	

	private void buildGUI(SKAMainApp owner, InvestorData data) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Name : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		investorNameLabel = new WebLabel(data.getName());
		investorNameLabel.setFont(owner.getTableFont());
		

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(investorNameLabel, c);
		formPanel.add(investorNameLabel);
		
		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		primaryContact = new WebLabel(data.getPrimaryContact());
		primaryContact.setFont(owner.getTableFont());
		
		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);
		
		WebLabel l3 = new WebLabel("Total Investment : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		totalInvestmentsLabel = new WebLabel(String.format("%,.2f", getTotalInvestment(data)));		
		totalInvestmentsLabel.setFont(applyLabelFont());

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);
		
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(totalInvestmentsLabel, c);
		formPanel.add(totalInvestmentsLabel);
		
		
		WebLabel l4 = new WebLabel("Investment Details : ", SwingConstants.RIGHT);
		l4.setFont(applyLabelFont());
		investmentTable = new WebTable(new InvestmentTableModel(data.getInvestments()));
		investmentTable.getColumnModel().getColumn(0).setMinWidth(200);
		investmentTable.getColumnModel().getColumn(1).setMinWidth(200);
		investmentTable.getColumnModel().getColumn(2).setMinWidth(200);
		
		investmentTable.getTableHeader().setFont(applyLabelFont());
		investmentTable.setRowHeight(35);
		investmentTable.setFont(applyLabelFont());
		
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l4, c);
		formPanel.add(l4);
		
		
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(0, 10, 10, 60); // Left padding
		
		
		WebScrollPane areaScroll = new WebScrollPane(investmentTable);
		areaScroll.setBorder(BorderFactory.createEtchedBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);
		
		
		WebButton okButton = new WebButton("Close");
		okButton.setFont(applyLabelFont());
		
		okButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		c.gridx = 1;
		c.gridy = 5;
		
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 10, 10, 0); // Left padding
		layout.setConstraints(okButton, c);
		formPanel.add(okButton);

		
		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		getContentPane().add(new GroupPanel(), BorderLayout.SOUTH);
		getContentPane().add(new GroupPanel(), BorderLayout.EAST);
		getContentPane().add(new GroupPanel(), BorderLayout.WEST);
		getContentPane().add(new GroupPanel(), BorderLayout.NORTH);
		pack();
	}
}
