package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import com.alee.extended.date.WebDateField;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.time.ClockType;
import com.alee.extended.time.WebClock;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextField;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.saikrupa.app.dao.InvestorDAO;
import com.saikrupa.app.dao.impl.DefaultInvestorDAO;
import com.saikrupa.app.dto.InvestmentData;
import com.saikrupa.app.dto.InvestorData;
import com.saikrupa.app.service.InvestorService;
import com.saikrupa.app.service.impl.DefaultInvestorService;
import com.saikrupa.app.ui.component.AppWebLabel;
import com.saikrupa.app.ui.models.InvestorListCellRenderer;
import com.saikrupa.app.ui.models.InvestorModel;
import com.saikrupa.app.ui.models.InvestorTableModel;

public class ManageInvestmentDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private WebComboBox investorCombo;
	private WebTextField amountTextField;
	private WebDateField investDateField;


	public ManageInvestmentDialog(SKAMainApp owner) {
		super(owner, true);
		setTitle("Create Investment");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(owner);
		setLocationRelativeTo(owner);
		setResizable(false);
	}
	
	public ManageInvestmentDialog(SKAMainApp owner, InvestorData data) {
		this(owner);
		setTitle("Create Investment");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(owner);
		setLocationRelativeTo(owner);
		setResizable(false);
		
		loadInvestorData(data);
	}

	private void loadInvestorData(InvestorData data) {
		
		
	}

	@SuppressWarnings("unchecked")
	private void buildGUI(SKAMainApp owner) {
		WebPanel formPanel = new WebPanel();		
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Investor Name : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		investorCombo = new WebComboBox(new InvestorModel());
		investorCombo.setRenderer(new InvestorListCellRenderer());

		WebLabel l2 = new WebLabel("Amount : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		amountTextField = new WebTextField(15);

		WebLabel l3 = new WebLabel("Invested On : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		investDateField = new WebDateField();

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(investorCombo, c);
		formPanel.add(investorCombo);

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(amountTextField, c);
		formPanel.add(amountTextField);

		c.gridx = 0;
		c.gridy = 2;

		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(investDateField, c);
		formPanel.add(investDateField);

		WebButton createInvestmentButton = new WebButton("Add Investment");
		createInvestmentButton.setFont(applyLabelFont());
		
		createInvestmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(amountTextField.getText().isEmpty() || !isNumeric(amountTextField.getText())) {
					final WebPopOver popOver = new WebPopOver(ManageInvestmentDialog.this);
					popOver.setCloseOnFocusLoss(true);
					popOver.setMargin(10);
					popOver.setLayout(new VerticalFlowLayout());
					popOver.add(new AppWebLabel("Investment Amount is missing or invalid"));
					popOver.show((WebTextField) amountTextField);
				} else if(investDateField.getText().isEmpty()) {
					final WebPopOver popOver = new WebPopOver(ManageInvestmentDialog.this);
					popOver.setCloseOnFocusLoss(true);
					popOver.setMargin(10);
					popOver.setLayout(new VerticalFlowLayout());
					popOver.add(new AppWebLabel("Investment Date is missing"));
					popOver.show((WebDateField) investDateField);
				} else {
					dispose();
					InvestmentData data = new InvestmentData();
					data.setAmount(Double.valueOf(amountTextField.getText()));
					data.setInvestor((InvestorData) investorCombo.getSelectedItem());
					data.setInvestmentDate(investDateField.getDate());					
					addInvestment(data);
				}				
			}
		});

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0); // Left padding
		c.gridwidth = 1;
		layout.setConstraints(createInvestmentButton, c);
		formPanel.add(createInvestmentButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		getContentPane().add(new GroupPanel(), BorderLayout.SOUTH);
		getContentPane().add(new GroupPanel(), BorderLayout.EAST);
		getContentPane().add(new GroupPanel(), BorderLayout.WEST);
		getContentPane().add(new GroupPanel(), BorderLayout.NORTH);
		pack();
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

	protected void addInvestment(InvestmentData data) {
		processCreateInvestmentEvent(data, (SKAMainApp) getOwner());

	}

	protected void processCreateInvestmentEvent(InvestmentData data, SKAMainApp owner) {
		InvestorService service = new DefaultInvestorService();
		try {
			service.addInvestment(data);
			InvestorDAO dao = new DefaultInvestorDAO();

			owner.getInvestorContentTable().setModel(new InvestorTableModel(dao.findInvestors()));
			((InvestorTableModel) owner.getInvestorContentTable().getModel()).fireTableDataChanged();

			final WebNotification notificationPopup = new WebNotification();
			notificationPopup.setIcon(NotificationIcon.plus);
			notificationPopup.setDisplayTime(3000);

			final WebClock clock = new WebClock();
			clock.setClockType(ClockType.timer);
			clock.setTimeLeft(6000);
			clock.setTimePattern("'Investment added Successfully' ");
			clock.setFont(applyLabelFont());
			clock.setForeground(Color.BLACK);
			notificationPopup.setContent(new GroupPanel(clock));
			NotificationManager.showNotification(notificationPopup);
			clock.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
