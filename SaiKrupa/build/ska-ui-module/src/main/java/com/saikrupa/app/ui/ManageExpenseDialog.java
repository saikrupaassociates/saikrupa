package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import com.alee.extended.date.WebDateField;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.saikrupa.app.dao.ExpenseDAO;
import com.saikrupa.app.dao.impl.DefaultExpenseDAO;
import com.saikrupa.app.dto.ExpenseData;
import com.saikrupa.app.dto.ExpenseTypeData;
import com.saikrupa.app.dto.InvestorData;
import com.saikrupa.app.dto.PaymentTypeData;
import com.saikrupa.app.dto.VendorData;
import com.saikrupa.app.service.ExpenseService;
import com.saikrupa.app.service.impl.DefaultExpenseService;
import com.saikrupa.app.ui.component.AppTextField;
import com.saikrupa.app.ui.component.AppWebLabel;
import com.saikrupa.app.ui.models.ExpenseTableModel;
import com.saikrupa.app.ui.models.ExpenseTypeListCellRenderer;
import com.saikrupa.app.ui.models.ExpenseTypeModel;
import com.saikrupa.app.ui.models.InvestorListCellRenderer;
import com.saikrupa.app.ui.models.InvestorModel;
import com.saikrupa.app.ui.models.PaymentTypeModel;
import com.saikrupa.app.ui.models.paymentTypeListCellRenderer;

public class ManageExpenseDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AppTextField paidToVendorText;
	private WebComboBox expCategoryCombo;
	private WebTextField expAmount;
	private WebDateField expenseDate;
	private WebComboBox paymentTypeCombo;
	private WebComboBox investorCombo;
	private WebTextArea area;
	private WebButton expenseButton;
	private WebLabel expCodeLabel;

	public ManageExpenseDialog(SKAMainApp owner) {
		super(owner, true);
		setTitle("Create New Expense...");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(owner);
		expenseButton.setActionCommand("CREATE_EXPENSE");
		setLocationRelativeTo(owner);
		setResizable(false);
	}

	public ManageExpenseDialog(SKAMainApp sdMainApp, ExpenseData data) {
		this(sdMainApp);
		setTitle("View / Update Expense - " + data.getCode());
		loadExpenseData(data);
	}

	private void loadExpenseData(ExpenseData data) {
		expCodeLabel = new WebLabel(data.getCode());
		expenseButton.setText("Update Expense");
		expenseButton.setActionCommand("UPDATE_EXPENSE");

		paidToVendorText.setModel(data.getVendor());
		paidToVendorText.setText(data.getVendor().getName());

		expAmount.setText(data.getAmount().toString());
		expenseDate.setDate(data.getExpenseDate());

		PaymentTypeModel paymentTypeModel = ((PaymentTypeModel) paymentTypeCombo.getModel());
		paymentTypeModel.setSelectedItem(data.getPaymentData());

		InvestorModel investorModel = (InvestorModel) investorCombo.getModel();
		investorModel.setSelectedItem(data.getPaidBy());

		ExpenseTypeModel expTypeModel = (ExpenseTypeModel) expCategoryCombo.getModel();
		expTypeModel.setSelectedItem(data.getExpenseType());

		area.setText(data.getComments());
	}

	@SuppressWarnings("unchecked")
	private void buildGUI(final SKAMainApp owner) {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Expense Category : ", SwingConstants.RIGHT);
		l1.setFont(applyLabelFont());
		expCategoryCombo = new WebComboBox(new ExpenseTypeModel());
		expCategoryCombo.setRenderer(new ExpenseTypeListCellRenderer());

		WebLabel l2 = new WebLabel("Paid To : ", SwingConstants.RIGHT);
		l2.setFont(applyLabelFont());
		paidToVendorText = new AppTextField(15);
		paidToVendorText.setEditable(false);
		WebButton searchButton = new WebButton();
		searchButton.setBackground(Color.white);
		searchButton.setIcon(createImageIcon("search.png"));
		paidToVendorText.setModel(new VendorData());

		paidToVendorText.setTrailingComponent(searchButton);

		WebLabel l3 = new WebLabel("Amount : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
		expAmount = new WebTextField(15);

		WebLabel l4 = new WebLabel("Date : ", SwingConstants.RIGHT);
		l4.setFont(applyLabelFont());
		expenseDate = new WebDateField(new Date());

		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 0, 0);
		layout.setConstraints(expCategoryCombo, c);
		formPanel.add(expCategoryCombo);

		c.insets = new Insets(0, 30, 0, 0);

		c.gridx = 2;
		c.gridy = 0;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 3;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 0, 0);
		layout.setConstraints(paidToVendorText, c);
		formPanel.add(paidToVendorText);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(expAmount, c);
		formPanel.add(expAmount);

		c.insets = new Insets(0, 30, 0, 0);

		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(expenseDate, c);
		formPanel.add(expenseDate);

		WebLabel l6 = new WebLabel("Payment Mode : ", SwingConstants.RIGHT);
		l6.setFont(applyLabelFont());
		paymentTypeCombo = new WebComboBox(new PaymentTypeModel());
		paymentTypeCombo.setRenderer(new paymentTypeListCellRenderer());

		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		layout.setConstraints(l6, c);
		formPanel.add(l6);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(paymentTypeCombo, c);
		formPanel.add(paymentTypeCombo);

		WebLabel l7 = new WebLabel("Paid By : ", SwingConstants.RIGHT);
		l7.setFont(applyLabelFont());
		investorCombo = new WebComboBox(new InvestorModel());
		investorCombo.setRenderer(new InvestorListCellRenderer());

		c.gridx = 2;
		c.gridy = 4;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		layout.setConstraints(l7, c);
		formPanel.add(l7);

		c.gridx = 3;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(investorCombo, c);
		formPanel.add(investorCombo);

		WebLabel l5 = new WebLabel("Description : ", SwingConstants.RIGHT);
		l5.setFont(applyLabelFont());
		area = new WebTextArea(5, 10);

		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 3;
		WebScrollPane areaScroll = new WebScrollPane(area);
		areaScroll.setBorder(expAmount.getBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);

		expenseButton = new WebButton("Create Expense");
		expenseButton.setFont(applyLabelFont());
		expenseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (expAmount.getText().trim().isEmpty() || !isNumeric(expAmount.getText().trim())) {
					final WebPopOver popOver = new WebPopOver(ManageExpenseDialog.this);
					popOver.setCloseOnFocusLoss(true);
					popOver.setMargin(10);
					popOver.setLayout(new VerticalFlowLayout());
					popOver.add(new AppWebLabel("Expense Amount is not filled or invalid"));
					popOver.show((WebTextField) expAmount);
				} else if (paidToVendorText.getText().trim().isEmpty()) {
					final WebPopOver popOver = new WebPopOver(ManageExpenseDialog.this);
					popOver.setCloseOnFocusLoss(true);
					popOver.setMargin(10);
					popOver.setLayout(new VerticalFlowLayout());
					popOver.add(new AppWebLabel("Vendor / Supplier is not selected"));
					popOver.show((AppTextField) paidToVendorText);
				} else {
					dispose();
					ExpenseData expenseData = new ExpenseData();
					expenseData.setExpenseType((ExpenseTypeData) expCategoryCombo.getSelectedItem());
					expenseData.setAmount(Double.valueOf(expAmount.getText()));
					expenseData.setExpenseDate(expenseDate.getDate());
					expenseData.setComments(area.getText());

					PaymentTypeData paymentType = (PaymentTypeData) paymentTypeCombo.getSelectedItem();
					expenseData.setPaymentData(paymentType);

					InvestorData investor = (InvestorData) investorCombo.getSelectedItem();
					expenseData.setPaidBy(investor);

					expenseData.setVendor((VendorData) paidToVendorText.getModel());
					if (("CREATE_EXPENSE").equalsIgnoreCase(e.getActionCommand())) {
						processCreateExpenseEvent(expenseData, owner);
					} else {
						expenseData.setCode(expCodeLabel.getText());
						processUpdateExpenseEvent(expenseData, owner);
					}
				}
			}
		});

		c.gridx = 1;
		c.gridy = 7;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 1;

		layout.setConstraints(expenseButton, c);
		formPanel.add(expenseButton);

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayVendorList(owner);
			}
		});
		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	private void displayVendorList(SKAMainApp owner) {
		DisplayVendorListDialog dialog = new DisplayVendorListDialog(this);
		dialog.setVisible(true);
	}

	protected void processUpdateExpenseEvent(ExpenseData expenseData, SKAMainApp owner) {
		ExpenseService service = new DefaultExpenseService();
		try {
			service.updateExpense(expenseData);
			ExpenseTableModel model = (ExpenseTableModel) owner.getExpenseContentTable().getModel();
			ExpenseDAO dao = new DefaultExpenseDAO();
			model.setExpenseDataList(dao.findAllExpenses());
			model.fireTableDataChanged();
			showSuccessNotification();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void processCreateExpenseEvent(ExpenseData expenseData, SKAMainApp owner) {
		ExpenseService service = new DefaultExpenseService();
		try {
			ExpenseData insertedExpense = service.createExpense(expenseData);
			ExpenseTableModel model = (ExpenseTableModel) owner.getExpenseContentTable().getModel();
			model.getExpenseDataList().add(insertedExpense);
			model.fireTableDataChanged();
			showSuccessNotification();
		} catch (Exception e) {
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

	public AppTextField getPaidToVendorText() {
		return paidToVendorText;
	}
}
