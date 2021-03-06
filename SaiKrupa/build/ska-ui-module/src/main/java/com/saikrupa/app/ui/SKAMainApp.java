package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.time.ClockType;
import com.alee.extended.time.WebClock;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;
import com.alee.managers.tooltip.TooltipManager;
import com.saikrupa.app.dao.ApplicationUserDAO;
import com.saikrupa.app.dao.EmployeeDAO;
import com.saikrupa.app.dao.ExpenseDAO;
import com.saikrupa.app.dao.InvestorDAO;
import com.saikrupa.app.dao.OrderDAO;
import com.saikrupa.app.dao.ProductDAO;
import com.saikrupa.app.dao.impl.DefaultApplicationUserDAO;
import com.saikrupa.app.dao.impl.DefaultEmployeeDAO;
import com.saikrupa.app.dao.impl.DefaultExpenseDAO;
import com.saikrupa.app.dao.impl.DefaultInvestorDAO;
import com.saikrupa.app.dao.impl.DefaultOrderDAO;
import com.saikrupa.app.dao.impl.DefaultProductDAO;
import com.saikrupa.app.db.PersistentManager;
import com.saikrupa.app.dto.ApplicationUserData;
import com.saikrupa.app.dto.CustomerData;
import com.saikrupa.app.dto.DeliveryStatus;
import com.saikrupa.app.dto.EmployeeData;
import com.saikrupa.app.dto.ExpenseData;
import com.saikrupa.app.dto.InvestorData;
import com.saikrupa.app.dto.OrderData;
import com.saikrupa.app.dto.PaymentStatus;
import com.saikrupa.app.dto.ProductData;
import com.saikrupa.app.service.VendorService;
import com.saikrupa.app.service.impl.DefaultVendorService;
import com.saikrupa.app.service.report.ReportService;
import com.saikrupa.app.service.report.impl.ConsolidatedOrderReportService;
import com.saikrupa.app.service.report.impl.CustomerOrderReportService;
import com.saikrupa.app.service.report.impl.DeliveredPendingPaymentOrderReportService;
import com.saikrupa.app.service.report.impl.PendingDeliveryOrderReportService;
import com.saikrupa.app.service.report.impl.PendingPaymentOrderReportService;
import com.saikrupa.app.session.ApplicationSession;
import com.saikrupa.app.ui.component.AppWebLabel;
import com.saikrupa.app.ui.models.EmployeeTableModel;
import com.saikrupa.app.ui.models.ExpenseTableModel;
import com.saikrupa.app.ui.models.InvestorTableModel;
import com.saikrupa.app.ui.models.OrderTableModel;
import com.saikrupa.app.ui.models.ProductTableModel;
import com.saikrupa.app.ui.models.VendorTableModel;
import com.saikrupa.app.ui.order.ManageOrderDialog;
import com.saikrupa.app.ui.order.UpdateOrderDialog;
import com.saikrupa.app.util.ApplicationResourceBundle;

public class SKAMainApp extends WebFrame {

	/**
	 * BLA BLA 
	 */
	private static final long serialVersionUID = 1L;

	private WebTable expenseContentTable;
	private WebTable vendorContentTable;
	private WebTable investorContentTable;
	private WebTable orderContentTable;
	private WebTable productContentTable;
	private WebTable employeeContentTable;
	private WebButton exportReportButton;
	
	private WebMenu profileMenu;

	public SKAMainApp() {
		super();
		init();
		setIconImage(createImageIcon("appLOGO.jpg").getImage());
	}

	private void init() {
		if (!isDBConnectionOK()) {
			WebOptionPane.showMessageDialog(this, "DB Connection not available");
			System.exit(0);
		}
		final ApplicationResourceBundle bundle = ApplicationResourceBundle.getApplicationResourceBundle();
		setTitle(bundle.getResourceValue("app.main.title"));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitApplication(bundle);
			}
		});
		loadDefaultPage();
	}

	private boolean isDBConnectionOK() {
		boolean connected = true;
		try {
			ExpenseDAO dao = new DefaultExpenseDAO();
			dao.findAllExpenses();
		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
		}
		return connected;
	}

	private void loadDefaultPage() {
		showLogin();
	}

	private void showLogin() {
		final WebPanel loginPanel = new WebPanel(new FlowLayout(FlowLayout.RIGHT));

		WebPanel formPanel = new WebPanel();
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("User ID : ", SwingConstants.RIGHT);
		l1.setFont(getLabelFont());
		final WebTextField userNameText = new WebTextField(15);
		userNameText.setInputPrompt("Employee ID");
		userNameText.setInputPromptFont(userNameText.getFont().deriveFont(Font.ITALIC));
		

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0); // Left padding
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(userNameText, c);
		formPanel.add(userNameText);

		WebLabel l2 = new WebLabel("Password : ", SwingConstants.RIGHT);
		l2.setFont(getLabelFont());
		final WebPasswordField passwordText = new WebPasswordField(15);
		passwordText.setInputPrompt("Enter password");
		passwordText.setInputPromptFont(passwordText.getFont().deriveFont(Font.ITALIC));

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0); // Left padding

		layout.setConstraints(passwordText, c);
		formPanel.add(passwordText);

		final WebButton loginButton = new WebButton();
		loginButton.setIcon(createImageIcon("login-button.png"));
		loginButton.setToolTipText("Login");

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userNameText.getText().isEmpty()) {
					final WebPopOver popOver = new WebPopOver(SKAMainApp.this);
					popOver.setCloseOnFocusLoss(true);
					popOver.setMargin(10);
					popOver.setLayout(new VerticalFlowLayout());
					popOver.add(new AppWebLabel("User Name is Missing"));
					popOver.show((WebTextField) userNameText);					
				} else if(passwordText.getPassword().toString().length() < 1) {
					final WebPopOver popOver = new WebPopOver(SKAMainApp.this);
					popOver.setCloseOnFocusLoss(true);
					popOver.setMargin(10);
					popOver.setLayout(new VerticalFlowLayout());
					popOver.add(new AppWebLabel("Password is Missing"));
					popOver.show((WebPasswordField) passwordText);
				} else if(!userExists(userNameText.getText())) {
					final WebPopOver popOver = new WebPopOver(SKAMainApp.this);
					popOver.setCloseOnFocusLoss(true);
					popOver.setMargin(10);
					popOver.setLayout(new VerticalFlowLayout());
					popOver.add(new AppWebLabel("User Name does not exists"));
					popOver.show((WebTextField) userNameText);
				} else {
					ApplicationUserDAO userDao = new DefaultApplicationUserDAO();
					ApplicationUserData userData = userDao.findUserByCode(userNameText.getText());
					if(!loginValid(String.valueOf(passwordText.getPassword()), String.valueOf(userData.getPassword()))) {
						final WebPopOver popOver = new WebPopOver(SKAMainApp.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Invalid Login Credentials."));
						popOver.show((WebButton) loginButton);
					} else {
						ApplicationSession session = ApplicationSession.getSession();
						session.setCurrentUser(userData);
						getContentPane().remove(loginPanel);
						setupMenus();
						decorateSouthPanel();
						revalidate();
					}
				}
			}
		});
		loginButton.setFont(getLabelFont());
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);
		layout.setConstraints(loginButton, c);
		formPanel.add(loginButton);

		ImageIcon icon = createImageIcon("appLOGO.jpg");
		WebDecoratedImage img3 = new WebDecoratedImage(icon);
		img3.setGrayscale(false, false);
		img3.setRound(2);

		loginPanel.add(formPanel);
		getContentPane().add(img3, BorderLayout.CENTER);
		getContentPane().add(loginPanel, BorderLayout.NORTH);
	}
	
	private boolean userExists(String userName) {
		ApplicationUserDAO userDao = new DefaultApplicationUserDAO();
		ApplicationUserData userData = userDao.findUserByCode(userName);
		return userData != null;
	}	

	private boolean loginValid(String value1, String value2) {
		return value1.equals(value2);
	}

	public ImageIcon createImageIcon(String path) {
		this.getClass().getClassLoader();
		java.net.URL imgURL = ClassLoader.getSystemResource("icons/" + path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file:  --> " + path);
			return null;
		}
	}

	public Font getMenuFont() {
		return new Font("Verdana", Font.BOLD, 13);
	}

	public Font getTableFont() {
		return new Font("Verdana", Font.BOLD, 13);
	}

	public Font getLabelFont() {
		return new Font("Verdana", Font.BOLD, 12);
	}

	private void exitApplication(ApplicationResourceBundle bundle) {
		int confirmed = WebOptionPane.showConfirmDialog(SKAMainApp.this, bundle.getResourceValue("app.main.exit.title"),
				"Confirm", WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE);
		if (confirmed == WebOptionPane.YES_OPTION) {
			PersistentManager manager = PersistentManager.getPersistentManager();
			manager.closeConnection();
			System.exit(0);
		} else {
			setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		}

	}

	private SDMenuBar menubar;

	private void setupMenus() {
		ApplicationUserData loggedOnUser = ApplicationSession.getSession().getCurrentUser();

		menubar = new SDMenuBar();
		WebMenu optionMenu = new WebMenu("Operations");
		optionMenu.setFont(getMenuFont());
		optionMenu.setToolTipText("Available Operations");

		WebMenu manageMenu = new WebMenu("Manage...");
		manageMenu.setFont(getMenuFont());
		manageMenu.setToolTipText("Manage Operations");

		WebLabel tip = new WebLabel("Expense");
		TooltipManager.setTooltip(tip, "Expense", TooltipWay.trailing);
		tip.setMargin(4);

		final WebMenuItem expenseMenuItem = new WebMenuItem("Expense");
		expenseMenuItem.setFont(getMenuFont());
		expenseMenuItem.setToolTipText("manage Expense");

		final WebMenuItem vendorMenuItem = new WebMenuItem("Vendor / Supplier");
		vendorMenuItem.setFont(getMenuFont());
		vendorMenuItem.setToolTipText("manage Vendor or Supplier");

		final WebMenuItem employeeMenuItem = new WebMenuItem("Employee");
		employeeMenuItem.setFont(getMenuFont());
		employeeMenuItem.setToolTipText("manage Employee");

		final WebMenuItem investMenuItem = new WebMenuItem("Investment");
		investMenuItem.setFont(getMenuFont());
		investMenuItem.setToolTipText("manage Investment");

		final WebMenuItem orderMenuItem = new WebMenuItem("Order");
		orderMenuItem.setFont(getMenuFont());
		orderMenuItem.setToolTipText("manage Order");

		final WebMenuItem productMenuItem = new WebMenuItem("Product");
		productMenuItem.setFont(getMenuFont());
		productMenuItem.setToolTipText("manage Product");

		final WebMenuItem exitMenuItem = new WebMenuItem("Exit...");
		exitMenuItem.setFont(getMenuFont());
		exitMenuItem.setToolTipText("Exit from App...");
		
		profileMenu = new WebMenu("User Settings");
		profileMenu.setFont(getMenuFont());
		
		final WebMenuItem changePasswordMenuItem = new WebMenuItem("Change Password...");
		changePasswordMenuItem.setFont(getMenuFont());
		changePasswordMenuItem.setToolTipText("Change Password");
		changePasswordMenuItem.setActionCommand("CHANGE_PASSWORD");
		profileMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		profileMenu.add(changePasswordMenuItem);
		
		final WebMenuItem logOffMenuItem = new WebMenuItem("Log off");
		logOffMenuItem.setFont(getMenuFont());
		logOffMenuItem.setToolTipText("Log Off");
		logOffMenuItem.setActionCommand("LOG_OFF");
		logOffMenuItem.addActionListener(actionListener());
		profileMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		profileMenu.add(logOffMenuItem);

		manageMenu.add(vendorMenuItem);
		manageMenu.addSeparator();

		if (loggedOnUser.isAdmin()) {
			manageMenu.add(employeeMenuItem);
			manageMenu.addSeparator();
		}

		if (loggedOnUser.isAdmin()) {
			manageMenu.add(investMenuItem);
			manageMenu.addSeparator();
		}

		manageMenu.add(productMenuItem);
		manageMenu.addSeparator();

		manageMenu.add(expenseMenuItem);
		manageMenu.addSeparator();

		manageMenu.add(orderMenuItem);

		optionMenu.add(manageMenu);
		optionMenu.addSeparator();
		optionMenu.add(exitMenuItem);

		WebMenu reportMenu = new WebMenu("Reports");
		reportMenu.setFont(getMenuFont());
		reportMenu.setToolTipText("Execute Reports");	

		
		final WebMenu expenseReportItem = new WebMenu("Expenses");
		expenseReportItem.setFont(getMenuFont());
		expenseReportItem.setToolTipText("Expense Reports");		
		reportMenu.add(expenseReportItem);
		
		final WebMenuItem expenseConsolidatedItem = new WebMenuItem("Consolidated Expense");
		expenseConsolidatedItem.setFont(getMenuFont());
		expenseConsolidatedItem.setToolTipText("Consolidated Expense Report");		
		expenseReportItem.add(expenseConsolidatedItem);
		expenseConsolidatedItem.setActionCommand("EXPENSE_CONSOLIDATED");
		expenseConsolidatedItem.addActionListener(actionListener());
		
		final WebMenuItem expenseByCategoryItem = new WebMenuItem("Expense By Category");
		expenseByCategoryItem.setFont(getMenuFont());
		expenseByCategoryItem.setToolTipText("Consolidated Expense Report");		
		expenseReportItem.add(expenseByCategoryItem);
		expenseByCategoryItem.setActionCommand("EXPENSE_BY_CATEGORY");
		expenseByCategoryItem.addActionListener(actionListener());
		
		final WebMenuItem expenseByPayeeItem = new WebMenuItem("Expense By Payee");
		expenseByPayeeItem.setFont(getMenuFont());
		expenseByPayeeItem.setToolTipText("Payment received by an entity");		
		expenseReportItem.add(expenseByPayeeItem);
		expenseByPayeeItem.setActionCommand("EXPENSE_BY_PAYEE");
		expenseByPayeeItem.addActionListener(actionListener());
		
		final WebMenuItem expenseByDateItem = new WebMenuItem("Expense By Date");
		expenseByDateItem.setFont(getMenuFont());
		expenseByDateItem.setToolTipText("Expenses between Dates");		
		expenseReportItem.add(expenseByDateItem);
		expenseByDateItem.setActionCommand("EXPENSE_BY_DATE");
		expenseByDateItem.addActionListener(actionListener());
		

		final WebMenu buildReportItem = new WebMenu("Standard Order Reports");
		buildReportItem.setFont(getMenuFont());
		buildReportItem.setToolTipText("Find Orders with specific search criteria");

		final WebMenuItem orderPendingPaymentItem = new WebMenuItem("Pending Payment");
		orderPendingPaymentItem.setFont(getMenuFont());
		orderPendingPaymentItem.setToolTipText("Find Orders with Pending Payment");

		final WebMenuItem orderPendingDeliveryItem = new WebMenuItem("Pending Delivery");
		orderPendingDeliveryItem.setFont(getMenuFont());
		orderPendingDeliveryItem.setToolTipText("Find Orders with Pending Delivery");

		final WebMenuItem orderByCustomerItem = new WebMenuItem("Order By Customer");
		orderByCustomerItem.setFont(getMenuFont());
		orderByCustomerItem.setToolTipText("Find Orders by Customer");

		final WebMenuItem consolidatedOrderItem = new WebMenuItem("Consolidated - All Orders");
		consolidatedOrderItem.setFont(getMenuFont());
		consolidatedOrderItem.setToolTipText("All Orders placed");

		final WebMenuItem deliveredPendingPaymentItem = new WebMenuItem("Delivered - Pending Payment");
		deliveredPendingPaymentItem.setFont(getMenuFont());
		deliveredPendingPaymentItem.setToolTipText("Delivery Completed but payment is pending");

		final WebMenuItem orderDeliveryConflictItem = new WebMenuItem("Delivery Quantity Discrepency");
		orderDeliveryConflictItem.setFont(getMenuFont());
		orderDeliveryConflictItem.setToolTipText("Find Orders with Delivery Quantity Discrepency");

		buildReportItem.add(orderPendingPaymentItem);
		buildReportItem.add(orderPendingDeliveryItem);
		buildReportItem.add(orderByCustomerItem);
		buildReportItem.add(deliveredPendingPaymentItem);

		buildReportItem.add(consolidatedOrderItem);
		buildReportItem.add(orderDeliveryConflictItem);
		reportMenu.add(buildReportItem);		
				
		menubar.add(optionMenu);
		menubar.add(reportMenu);		
		menubar.add(Box.createHorizontalGlue());
		menubar.add(profileMenu);			
		setJMenuBar(menubar);

		//Standard Menu Items
		expenseMenuItem.setActionCommand("MANAGE_EXPENSE");
		vendorMenuItem.setActionCommand("MANAGE_VENDOR");
		employeeMenuItem.setActionCommand("MANAGE_EMPLOYEE");
		exitMenuItem.setActionCommand("EXIT_APP");
		investMenuItem.setActionCommand("MANAGE_INVESTMENT");
		orderMenuItem.setActionCommand("MANAGE_ORDER");
		productMenuItem.setActionCommand("MANAGE_PRODUCT");
		
		//Order report Menu Items
		orderPendingDeliveryItem.setActionCommand("REPORT_ORDER_PENDING_DELIVERY");
		orderPendingPaymentItem.setActionCommand("REPORT_ORDER_PENDING_PAYMENT");
		orderDeliveryConflictItem.setActionCommand("REPORT_DELIVERY_QUANTITY_MISMATCH");
		orderByCustomerItem.setActionCommand("REPORT_ORDER_BY_CUSTOMER");
		consolidatedOrderItem.setActionCommand("REPORT_ORDER_CONSOLIDATED");
		deliveredPendingPaymentItem.setActionCommand("DELIVERED_PENDING_PAYMENT");

		expenseMenuItem.addActionListener(actionListener());
		exitMenuItem.addActionListener(actionListener());
		vendorMenuItem.addActionListener(actionListener());
		employeeMenuItem.addActionListener(actionListener());
		investMenuItem.addActionListener(actionListener());
		orderMenuItem.addActionListener(actionListener());
		productMenuItem.addActionListener(actionListener());
		buildReportItem.addActionListener(actionListener());

		orderPendingDeliveryItem.addActionListener(actionListener());
		orderPendingPaymentItem.addActionListener(actionListener());
		orderDeliveryConflictItem.addActionListener(actionListener());
		orderByCustomerItem.addActionListener(actionListener());
		consolidatedOrderItem.addActionListener(actionListener());
		deliveredPendingPaymentItem.addActionListener(actionListener());
		changePasswordMenuItem.addActionListener(actionListener());
	}

	private ActionListener actionListener() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final ApplicationResourceBundle bundle = ApplicationResourceBundle.getApplicationResourceBundle();
				if (e.getActionCommand().equalsIgnoreCase("MANAGE_EXPENSE")) {
					displayExpenseScreen(bundle);
				} else if (e.getActionCommand().equalsIgnoreCase("EXIT_APP")) {
					exitApplication(bundle);
				} else if (e.getActionCommand().equalsIgnoreCase("MANAGE_VENDOR")) {
					displayVendorScreen(bundle);
				} else if (e.getActionCommand().equalsIgnoreCase("MANAGE_EMPLOYEE")) {
					displayEmployeeScreen(bundle);
				} else if (e.getActionCommand().equalsIgnoreCase("MANAGE_INVESTMENT")) {
					displayInvestmentScreen(bundle);
				} else if (e.getActionCommand().equalsIgnoreCase("MANAGE_ORDER")) {
					displayOrderScreen(bundle);
				} else if (e.getActionCommand().equalsIgnoreCase("MANAGE_PRODUCT")) {
					displayProductScreen(bundle);
				} else if (e.getActionCommand().equalsIgnoreCase("REPORT_ORDER_PENDING_DELIVERY")) {
					buildOrderReport(bundle, DeliveryStatus.SHIPPING.toString(), null, e);
				} else if (e.getActionCommand().equalsIgnoreCase("REPORT_ORDER_PENDING_PAYMENT")) {
					buildOrderReport(bundle, PaymentStatus.PENDING.toString(), null, e);
				} else if (e.getActionCommand().equalsIgnoreCase("DELIVERED_PENDING_PAYMENT")) {
					buildOrderReport(bundle, "DELIVERED_PENDING_PAYMENT", null, e);
				} else if (e.getActionCommand().equalsIgnoreCase("REPORT_ORDER_CONSOLIDATED")) {
					buildOrderReport(bundle, e.getActionCommand(), null, e);
				} else if (e.getActionCommand().equalsIgnoreCase("REPORT_ORDER_BY_CUSTOMER")) {
					DisplayCustomerListDialog dialog = new DisplayCustomerListDialog(SKAMainApp.this);
					dialog.setVisible(true);
					CustomerData customer = dialog.performSelectionOperation();
					if (customer != null) {
						Integer[] params = new Integer[1];
						params[0] = Integer.valueOf(customer.getCode());
						buildOrderReport(bundle, "REPORT_ORDER_BY_CUSTOMER", params, e);
					}

				} else if (e.getActionCommand().equalsIgnoreCase("REPORT_DELIVERY_QUANTITY_MISMATCH")) {
					buildOrderReport(bundle, "DELIVERY_QUANTITY_MISMATCH", null, e);
				} else if (e.getActionCommand().equals("LOG_OFF")) {
					processLogoff();
				} else if (e.getActionCommand().equals("CHANGE_PASSWORD")) {
					processChangePassword();
				} else if(e.getActionCommand().equalsIgnoreCase("EXPENSE_CONSOLIDATED")) {
					buildExpenseReport(bundle, "EXPENSE_CONSOLIDATED", null, e);
				}
			}
		};
		return listener;
	}

	private void processChangePassword() {
		ChangePasswordDialog d = new ChangePasswordDialog(this);
		d.setVisible(true);
	}

	private void processLogoff() {
		getContentPane().removeAll();
		menubar.removeAll();
		showLogin();
		revalidate();
	}

	private void exportReport(String actionCommand) {
		OrderTableModel model = (OrderTableModel) orderContentTable.getModel();
		WebFileChooser chooser = new WebFileChooser(System.getProperty("user.dir"));
		chooser.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf", "PDF");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Save Report As...");
		int selection = chooser.showSaveDialog(this);
		if (selection == WebFileChooser.APPROVE_OPTION) {
			ReportService reportService = null;
			if ("REPORT_ORDER_PENDING_PAYMENT".equalsIgnoreCase(actionCommand)) {
				reportService = new PendingPaymentOrderReportService(model.getOrderDataList());
			} else if ("REPORT_ORDER_PENDING_DELIVERY".equalsIgnoreCase(actionCommand)) {
				chooser.setSelectedFile(new File("OrderReport_pendingDelivery.pdf"));

				reportService = new PendingDeliveryOrderReportService(model.getOrderDataList());
			} else if ("REPORT_ORDER_BY_CUSTOMER".equalsIgnoreCase(actionCommand)) {
				String name = (String) model.getOrderDataList().get(0).getCustomer().getName();
				chooser.setSelectedFile(new File("OrderReport_ByCustomer_" + name + ".pdf"));
				reportService = new CustomerOrderReportService(model.getOrderDataList());
			} else if ("REPORT_ORDER_CONSOLIDATED".equalsIgnoreCase(actionCommand)) {
				chooser.setSelectedFile(new File("OrderReport_Consolidated.pdf"));
				reportService = new ConsolidatedOrderReportService(model.getOrderDataList());
			} else if ("DELIVERED_PENDING_PAYMENT".equalsIgnoreCase(actionCommand)) {
				chooser.setSelectedFile(new File("OrderReport_Delivered_PendingPayment.pdf"));
				reportService = new DeliveredPendingPaymentOrderReportService(model.getOrderDataList());
			}
			reportService.saveReport(chooser.getSelectedFile().getAbsolutePath());
			showSuccessNotification();
		}
	}
	
	private void buildExpenseReport(ApplicationResourceBundle bundle, final String status, Object[] params, ActionEvent e) {
		getContentPane().removeAll();
		WebPanel basePanel = new WebPanel(true);
		exportReportButton = new WebButton("Export Report");
		exportReportButton.setFont(getLabelFont());
		exportReportButton.setActionCommand(e.getActionCommand());
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());
		
		exportReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exportReport(exportReportButton.getActionCommand());
				WebOptionPane.showMessageDialog(SKAMainApp.this, e.getActionCommand());
			}
		});
		
		ExpenseDAO expenseDAO = new DefaultExpenseDAO();
		ExpenseTableModel expenseTableModel = new ExpenseTableModel(expenseDAO.searchExpenseWithFilter(status, params));
		expenseContentTable = new WebTable(expenseTableModel);
		
		contentPanel.add(new WebScrollPane(expenseContentTable));
		if (expenseTableModel != null && !expenseTableModel.getExpenseDataList().isEmpty()) {
			basePanel.add(exportReportButton);
		}

		expenseContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		expenseContentTable.setRowHeight(35);
		expenseContentTable.setFont(new Font("verdana", Font.PLAIN, 14));
		
//		MouseListener mouseListener = new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				if (e.getSource() == expenseContentTable) {
//					if (e.getClickCount() > 1) {
//						int currentRow = expenseContentTable.getSelectedRow();
//						ExpenseTableModel model = (ExpenseTableModel) expenseContentTable.getModel();
//						ExpenseData data = model.getExpenseDataList().get(currentRow);						
//					}
//				}
//			}
//		};
//		expenseContentTable.addMouseListener(mouseListener);

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();
	}

	private void buildOrderReport(ApplicationResourceBundle bundle, final String status, Object[] params, ActionEvent e) {
		getContentPane().removeAll();
		WebPanel basePanel = new WebPanel(true);
		exportReportButton = new WebButton("Export Report");
		exportReportButton.setFont(getLabelFont());
		exportReportButton.setActionCommand(e.getActionCommand());
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());

		exportReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportReport(exportReportButton.getActionCommand());
			}
		});

		OrderDAO orderDAO = new DefaultOrderDAO();
		OrderTableModel orderTableModel = new OrderTableModel(orderDAO.searchOrderWithFilter(status, params));
		orderContentTable = new WebTable(orderTableModel);

		contentPanel.add(new WebScrollPane(orderContentTable));
		if (orderTableModel != null && !orderTableModel.getOrderDataList().isEmpty()) {
			basePanel.add(exportReportButton);
		}

		orderContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		orderContentTable.setRowHeight(35);
		orderContentTable.setFont(new Font("verdana", Font.PLAIN, 14));

//		MouseListener mouseListener = new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				if (e.getSource() == orderContentTable) {
//					if (e.getClickCount() > 1) {
//						int currentRow = orderContentTable.getSelectedRow();
//						OrderTableModel model = (OrderTableModel) orderContentTable.getModel();
//						OrderData data = model.getOrderDataList().get(currentRow);
//						displayOrderUpdateDialog(data);
//					}
//				}
//			}
//		};
//		orderContentTable.addMouseListener(mouseListener);

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();

	}

	private void displayOrderScreen(ApplicationResourceBundle bundle) {
		getContentPane().removeAll();
		final WebButton createOrderButton = new WebButton("Create Order");
		createOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				displayCreateOrderDialog();
			}
		});
		WebPanel basePanel = new WebPanel(true);
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		basePanel.add(createOrderButton);

		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());

		OrderDAO orderDAO = new DefaultOrderDAO();

		orderContentTable = new WebTable(new OrderTableModel(orderDAO.findAllOrders()));
		contentPanel.add(new WebScrollPane(orderContentTable));

		orderContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		orderContentTable.setRowHeight(35);
		orderContentTable.setFont(new Font("verdana", Font.PLAIN, 14));

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == orderContentTable) {
					if (e.getClickCount() > 1) {
						int currentRow = orderContentTable.getSelectedRow();
						OrderTableModel model = (OrderTableModel) orderContentTable.getModel();
						OrderData data = model.getOrderDataList().get(currentRow);
						displayOrderUpdateDialog(data);

					}

				}
			}

		};
		orderContentTable.addMouseListener(mouseListener);

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();

	}

	private void displayOrderUpdateDialog(OrderData data) {
		UpdateOrderDialog dialog = new UpdateOrderDialog(this, data);
		dialog.setVisible(true);

	}

	private void displayEmployeeUpdateDialog(EmployeeData data) {
		UpdateEmployeeDialog dialog = new UpdateEmployeeDialog(this, data);
		dialog.setVisible(true);
	}

	private void displayProductScreen(ApplicationResourceBundle bundle) {
		getContentPane().removeAll();
		final WebButton createProductButton = new WebButton("Create Product");
		createProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// displayCreateProductDialog(SDMainApp.this);
			}

		});
		WebPanel basePanel = new WebPanel(true);
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		basePanel.add(createProductButton);
		createProductButton.setEnabled(false);

		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());

		ProductDAO productDAO = new DefaultProductDAO();

		productContentTable = new WebTable(new ProductTableModel(productDAO.findAllProducts()));
		contentPanel.add(new WebScrollPane(productContentTable));

		productContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));

		productContentTable.getColumnModel().getColumn(1).setMinWidth(230);
		productContentTable.getColumnModel().getColumn(1).setMaxWidth(230);

		productContentTable.getColumnModel().getColumn(5).setMinWidth(220);
		productContentTable.getColumnModel().getColumn(5).setMaxWidth(220);

		productContentTable.getColumnModel().getColumn(6).setMinWidth(220);
		productContentTable.getColumnModel().getColumn(6).setMaxWidth(220);

		productContentTable.setRowHeight(35);
		productContentTable.setFont(new Font("verdana", Font.PLAIN, 14));

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == productContentTable) {
					if (e.getClickCount() > 1) {
						int currentRow = productContentTable.getSelectedRow();
						ProductTableModel model = (ProductTableModel) productContentTable.getModel();
						ProductData data = model.getProductDataList().get(currentRow);
						showProduct(data);
					}

				}
			}
		};
		productContentTable.addMouseListener(mouseListener);

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();
	}

	private void displayInvestmentScreen(ApplicationResourceBundle bundle) {
		getContentPane().removeAll();
		final WebButton createInvestmentButton = new WebButton("Create Investment");
		createInvestmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				displayCreateInvestmentDialog(SKAMainApp.this);
			}
		});

		WebPanel basePanel = new WebPanel(true);
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		basePanel.add(createInvestmentButton);

		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());

		InvestorDAO dao = new DefaultInvestorDAO();
		investorContentTable = new WebTable(new InvestorTableModel(dao.findInvestors()));
		contentPanel.add(new WebScrollPane(investorContentTable));

		investorContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		investorContentTable.setRowHeight(35);
		investorContentTable.setFont(new Font("verdana", Font.PLAIN, 14));
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == investorContentTable) {
					if (e.getClickCount() > 1) {
						int currentRow = investorContentTable.getSelectedRow();
						InvestorTableModel model = (InvestorTableModel) investorContentTable.getModel();
						InvestorData data = model.getInvestorDataList().get(currentRow);
						showInvestments(data);
					}
				}
			}
		};
		investorContentTable.addMouseListener(mouseListener);

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();
	}

	private void decorateSouthPanel() {
		WebPanel southPanel = new WebPanel(true);
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 4));
		ApplicationSession session = ApplicationSession.getSession();
		ApplicationUserData userData = (ApplicationUserData) session.getProperty("CURRENT_USER");
		if (userData != null) {
			WebLabel user = new WebLabel(userData.getName());
			//WebButton changePasswordButton = new WebButton("Change Password");

			user.setFont(getLabelFont());
			southPanel.add(new WebLabel("Logged On : "));
			southPanel.add(user);

			WebButton logoffButton = new WebButton();
			logoffButton.setIcon(createImageIcon("button_logoff.png"));
			southPanel.add(logoffButton);
			logoffButton.setActionCommand("LOG_OFF");
			logoffButton.setToolTipText("Log off");
			logoffButton.addActionListener(actionListener());

//			southPanel.add(changePasswordButton);
//			changePasswordButton.setActionCommand("CHANGE_PASSWORD");
//			changePasswordButton.addActionListener(actionListener());

		}
		getContentPane().add(southPanel, BorderLayout.SOUTH);
	}

	private void displayCreateOrderDialog() {
		ManageOrderDialog orderDialog = new ManageOrderDialog(this);
		orderDialog.setVisible(true);

	}

	private void displayCreateVendorDialog(SKAMainApp owner) {
		CreateVendorDialog vendorDialog = new CreateVendorDialog(owner);
		vendorDialog.setVisible(true);
	}

	private void displayCreateEmployeeDialog(SKAMainApp owner) {
		CreateEmployeeDialog dialog = new CreateEmployeeDialog(owner);
		dialog.setVisible(true);
	}

	private void displayCreateInvestmentDialog(SKAMainApp owner) {
		ManageInvestmentDialog dialog = new ManageInvestmentDialog(owner);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private void displayCreateExpenseDialog(SKAMainApp owner) {
		ManageExpenseDialog expenseDialog = new ManageExpenseDialog(owner);
		expenseDialog.setVisible(true);
	}

	private void displayEmployeeScreen(ApplicationResourceBundle bundle) {
		getContentPane().removeAll();
		final WebButton createVendorButton = new WebButton("Create Employee");
		createVendorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				displayCreateEmployeeDialog(SKAMainApp.this);
			}
		});

		WebPanel basePanel = new WebPanel(true);
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		basePanel.add(createVendorButton);

		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());

		EmployeeDAO dao = new DefaultEmployeeDAO();
		employeeContentTable = new WebTable(new EmployeeTableModel(dao.getAllEmployees()));
		contentPanel.add(new WebScrollPane(employeeContentTable));

		employeeContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		employeeContentTable.setRowHeight(35);
		employeeContentTable.setFont(new Font("verdana", Font.PLAIN, 14));

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == employeeContentTable) {
					if (e.getClickCount() > 1) {
						int currentRow = employeeContentTable.getSelectedRow();
						EmployeeTableModel model = (EmployeeTableModel) employeeContentTable.getModel();
						EmployeeData data = model.getEmployeeDataList().get(currentRow);
						displayEmployeeUpdateDialog(data);
					}
				}
			}
		};
		employeeContentTable.addMouseListener(mouseListener);

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();
	}

	private void displayVendorScreen(ApplicationResourceBundle bundle) {
		getContentPane().removeAll();
		final WebButton createVendorButton = new WebButton("Create Vendor");
		createVendorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				displayCreateVendorDialog(SKAMainApp.this);
			}
		});

		WebPanel basePanel = new WebPanel(true);
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		basePanel.add(createVendorButton);

		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());

		VendorService vendorService = new DefaultVendorService();
		vendorContentTable = new WebTable(new VendorTableModel(vendorService.getAllVendors()));
		contentPanel.add(new WebScrollPane(vendorContentTable));

		vendorContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		vendorContentTable.setRowHeight(35);
		vendorContentTable.setFont(new Font("verdana", Font.PLAIN, 14));

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();
	}

	private void displayExpenseScreen(ApplicationResourceBundle bundle) {
		getContentPane().removeAll();
		final WebButton createExpenseButton = new WebButton("Create Expense");
		createExpenseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				displayCreateExpenseDialog(SKAMainApp.this);
			}

		});
		WebPanel basePanel = new WebPanel(true);
		basePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		basePanel.add(createExpenseButton);
		WebPanel contentPanel = new WebPanel(true);
		contentPanel.setLayout(new BorderLayout());

		ExpenseDAO expenseDAO = new DefaultExpenseDAO();

		expenseContentTable = new WebTable(new ExpenseTableModel(expenseDAO.findAllExpenses()));
		contentPanel.add(new WebScrollPane(expenseContentTable));
		expenseContentTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		expenseContentTable.setRowHeight(35);
		expenseContentTable.setFont(new Font("verdana", Font.PLAIN, 14));

		expenseContentTable.getColumnModel().getColumn(0).setMaxWidth(110);
		expenseContentTable.getColumnModel().getColumn(0).setMinWidth(110);

		expenseContentTable.getColumnModel().getColumn(1).setMaxWidth(370);
		expenseContentTable.getColumnModel().getColumn(1).setMinWidth(370);

		expenseContentTable.getColumnModel().getColumn(2).setMaxWidth(90);
		expenseContentTable.getColumnModel().getColumn(2).setMinWidth(90);

		expenseContentTable.getColumnModel().getColumn(3).setMaxWidth(140);
		expenseContentTable.getColumnModel().getColumn(3).setMinWidth(140);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == expenseContentTable) {
					if (e.getClickCount() > 1) {
						int currentRow = expenseContentTable.getSelectedRow();
						ExpenseTableModel model = (ExpenseTableModel) expenseContentTable.getModel();
						ExpenseData data = model.getExpenseDataList().get(currentRow);
						showExpense(data);
					}

				} else if (e.getSource() == investorContentTable) {
					if (e.getClickCount() > 1) {
						int currentRow = investorContentTable.getSelectedRow();
						InvestorTableModel model = (InvestorTableModel) investorContentTable.getModel();
						InvestorData data = model.getInvestorDataList().get(currentRow);
						showInvestments(data);
					}
				}
			}
		};
		expenseContentTable.addMouseListener(mouseListener);

		WebSplitPane splitPane = new WebSplitPane(com.alee.laf.splitpane.WebSplitPane.VERTICAL_SPLIT, contentPanel,
				basePanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setContinuousLayout(false);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		decorateSouthPanel();
		revalidate();
	}

	private void showInvestments(InvestorData data) {
		UpdateInvestmentDialog d = new UpdateInvestmentDialog(this, data);
		d.setVisible(true);

	}

	private void showProduct(ProductData data) {
		ManageProductDialog dialog = new ManageProductDialog(this, data);
		dialog.setVisible(true);
	}

	private void showExpense(ExpenseData data) {
		ManageExpenseDialog d = new ManageExpenseDialog(this, data);
		d.setVisible(true);

	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					WebLookAndFeel.install(true);
					WebLookAndFeel.setDecorateFrames(true);
					WebLookAndFeel.setDecorateDialogs(true);

					// Opening SDMainApp
					SKAMainApp app = new SKAMainApp();
					app.setSize(new Dimension(1200, 1000));
					app.setResizable(true);
					app.setVisible(true);
				}
			});
		} catch (Exception e) {
			System.out.println("Unable to load WebLookAndFeel...");
		}
	}

	public WebTable getExpenseContentTable() {
		return expenseContentTable;
	}

	public WebTable getVendorContentTable() {
		return vendorContentTable;
	}

	public WebTable getInvestorContentTable() {
		return investorContentTable;
	}

	public void setInvestorContentTable(WebTable investorContentTable) {
		this.investorContentTable = investorContentTable;
	}

	public WebTable getOrderContentTable() {
		return orderContentTable;
	}

	public void setOrderContentTable(WebTable orderContentTable) {
		this.orderContentTable = orderContentTable;
	}

	public WebTable getProductContentTable() {
		return productContentTable;
	}

	public void setProductContentTable(WebTable productContentTable) {
		this.productContentTable = productContentTable;
	}

	public WebTable getEmployeeContentTable() {
		return employeeContentTable;
	}

	public void setEmployeeContentTable(WebTable employeeContentTable) {
		this.employeeContentTable = employeeContentTable;
	}

	public void showSuccessNotification() {
		final WebNotification notificationPopup = new WebNotification();
		notificationPopup.setIcon(NotificationIcon.plus);
		notificationPopup.setDisplayTime(3000);

		final WebClock clock = new WebClock();
		clock.setClockType(ClockType.timer);
		clock.setTimeLeft(3000);
		clock.setTimePattern("'Operation Successfully executed !!!' ");
		clock.setFont(new Font("Verdana", Font.BOLD, 14));
		clock.setForeground(Color.BLACK);
		notificationPopup.setContent(new GroupPanel(clock));
		NotificationManager.showNotification(notificationPopup);
		clock.start();
	}
}
