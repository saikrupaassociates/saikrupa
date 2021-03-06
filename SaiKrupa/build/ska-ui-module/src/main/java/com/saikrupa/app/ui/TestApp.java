package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.alee.extended.button.WebSplitButton;
import com.alee.extended.date.WebDateField;
import com.alee.extended.menu.DynamicMenuType;
import com.alee.extended.menu.WebDynamicMenu;
import com.alee.extended.menu.WebDynamicMenuItem;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.utils.SwingUtils;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.saikrupa.app.dao.impl.DefaultOrderDAO;
import com.saikrupa.app.dto.InvestmentData;
import com.saikrupa.app.dto.OrderEntryData;
import com.saikrupa.app.dto.OrderStatus;
import com.saikrupa.app.ui.models.DeliveryStatusModel;
import com.saikrupa.app.ui.models.InvestmentTableModel;
import com.saikrupa.app.ui.models.OrderEntryTableModel;
import com.saikrupa.app.ui.models.PaymentStatusModel;

public class TestApp extends WebFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Webcam webcam = null;
	private WebPanel photoPanel;
	private WebPanel imagePanel;

	public TestApp() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// showInputOption();
		showLogin();
	}

	private void southPanelShow() {
		final WebSplitButton splitButton = new WebSplitButton("Click me", WebLookAndFeel.getIcon(16));

		// Menu for split button
		final WebPopupMenu popupMenu = new WebPopupMenu();
		popupMenu.add(new WebMenuItem("Menu item 1", WebLookAndFeel.getIcon(16), Hotkey.ALT_X));
		popupMenu.add(new WebMenuItem("Menu item 2", Hotkey.D));
		popupMenu.addSeparator();
		popupMenu.add(new WebMenuItem("Menu item 3", Hotkey.ESCAPE));
		splitButton.setPopupMenu(popupMenu);

		getContentPane().add(new GroupPanel(splitButton), BorderLayout.NORTH);
		setSize(700, 800);

	}

	private void employeeCreate() {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Employee Name : ", SwingConstants.RIGHT);
		final WebTextField nameText = new WebTextField("30");
		nameText.setText("Prasun Kumar Sarangi");

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(nameText, c);
		formPanel.add(nameText);

		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		final WebTextField contactText = new WebTextField("15");
		contactText.setText("9035476943");

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(contactText, c);
		formPanel.add(contactText);

		WebLabel l3 = new WebLabel("Date of Joining : ", SwingConstants.RIGHT);
		final WebDateField dojField = new WebDateField(new Date());

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(dojField, c);
		formPanel.add(dojField);

		WebLabel l4 = new WebLabel("ID Proof Document : ", SwingConstants.RIGHT);
		final WebTextField idProofDocumentField = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 3;

		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(idProofDocumentField, c);
		formPanel.add(idProofDocumentField);

		WebLabel l5 = new WebLabel("Address Proof Document : ", SwingConstants.RIGHT);
		final WebTextField addressProofField = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(addressProofField, c);
		formPanel.add(addressProofField);

		imagePanel = new WebPanel();
		imagePanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout imageLayout = new GridBagLayout();
		imagePanel.setLayout(imageLayout);
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.VERTICAL;

		photoPanel = new WebPanel(new FlowLayout(FlowLayout.CENTER));
		photoPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		ImageIcon image = new ImageIcon("D:/gaga/image/download.jpg");
		WebLabel imageLabel = new WebLabel("", image, WebLabel.CENTER);
		photoPanel.add(imageLabel);

		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(0, 10, 10, 0);
		imageLayout.setConstraints(photoPanel, c1);
		imagePanel.add(photoPanel);

		final WebButton startCaptureButton = new WebButton("Start Capture");
		startCaptureButton.setActionCommand("START_WEBCAM");
		c1.gridx = 0;
		c1.gridy = 1;
		c1.insets = new Insets(0, 10, 10, 0);
		imageLayout.setConstraints(startCaptureButton, c1);
		imagePanel.add(startCaptureButton);

		WebButton captureButton = new WebButton("Capture Photo");
		captureButton.setActionCommand("CAPTURE");
		c1.gridx = 0;
		c1.gridy = 2;
		c1.insets = new Insets(0, 10, 10, 0);
		imageLayout.setConstraints(captureButton, c1);
		imagePanel.add(captureButton);

		WebButton createEmployeeButton = new WebButton("Create");

		c.gridx = 1;
		c.gridy = 8;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(createEmployeeButton, c);
		formPanel.add(createEmployeeButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.WEST);
		getContentPane().add(new GroupPanel(imagePanel, new JPanel()), BorderLayout.EAST);
		pack();

		ActionListener l = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getActionCommand().equalsIgnoreCase("START_WEBCAM")) {
					webcam = Webcam.getDefault();
					// webcam.setViewSize(new Dimension(640, 480));
					if (webcam != null) {
						imagePanel.remove(photoPanel);
						WebcamPanel panel = new WebcamPanel(webcam);
						panel.setFPSDisplayed(true);
						panel.setMirrored(false);
						imagePanel.add(panel);
						revalidate();

					} else {
						System.out.println("No webcam detected");
					}
				} else if (e.getActionCommand().equalsIgnoreCase("CAPTURE")) {
					if (webcam != null) {
						webcam.open();
						BufferedImage image = webcam.getImage();
						try {
							ImageIO.write(image, "PNG", new File("D:/gaga/capture/test.png"));
							webcam.close();

							ImageIcon capturedImage = new ImageIcon("D:/gaga/capture/test.png");
							photoPanel.removeAll();
							WebLabel label = new WebLabel("", capturedImage, WebLabel.CENTER);
							photoPanel.add(label);
							revalidate();

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			}
		};
		createEmployeeButton.addActionListener(l);
		startCaptureButton.addActionListener(l);
		captureButton.addActionListener(l);
	}

	private void showLogin() {
		WebPanel formPanel = new WebPanel();
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Current Password : ", SwingConstants.RIGHT);
		final WebPasswordField currentPasswordText = new WebPasswordField(10);

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(currentPasswordText, c);
		formPanel.add(currentPasswordText);

		WebLabel l2 = new WebLabel("New Password : ", SwingConstants.RIGHT);
		final WebPasswordField newPasswordField = new WebPasswordField(10);

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(newPasswordField, c);
		formPanel.add(newPasswordField);

		/**
		 * 
		 */
		WebLabel l3 = new WebLabel("Re-enter Password : ", SwingConstants.RIGHT);
		final WebPasswordField repeatPasswordText = new WebPasswordField(10);

		c.gridx = 0;
		c.gridy = 2;

		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(repeatPasswordText, c);
		formPanel.add(repeatPasswordText);

		WebButton loginButton = new WebButton("Change Password");
		WebButton cancelButton = new WebButton("Cancel");
		WebPanel buttonPanel = new WebPanel(true);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);

		// WebPanel loginPanel = new WebPanel(new BorderLayout());
		// loginPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		// loginPanel.add(formPanel, BorderLayout.CENTER);
		getContentPane().add(formPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}

	private void progressBar() {
		WebPanel formPanel = new WebPanel(new BorderLayout());
		final WebProgressBar progressBar1 = new WebProgressBar(0, 100);
		progressBar1.setValue(0);
		progressBar1.setIndeterminate(false);
		progressBar1.setStringPainted(true);
		progressBar1.setVisible(true);

	}

	private void showInputOption() {
		WebPanel formPanel = new WebPanel(new FlowLayout());
		WebButton button = new WebButton("Click");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Object[] selectionValues = { OrderStatus.COMPLETED, OrderStatus.CONFIRMED, OrderStatus.DELIVERED };
				OrderStatus initialSelectionValue = OrderStatus.COMPLETED;
				OrderStatus data = (OrderStatus) WebOptionPane.showInputDialog(TestApp.this, "Order Status",
						"Select Order Status", WebOptionPane.QUESTION_MESSAGE, null, selectionValues,
						initialSelectionValue);
				System.out.println(data.toString());
			}
		});
		formPanel.add(button);
		getContentPane().add(formPanel, BorderLayout.NORTH);
		pack();
		setVisible(true);

	}

	private WebPanel orderLineDetailPanel() {
		WebPanel formPanel = new WebPanel(new BorderLayout());
		WebLabel l1 = new WebLabel("  Ordered Line Items", SwingConstants.LEFT);
		List<OrderEntryData> entries = new DefaultOrderDAO().findAllOrders().get(0).getOrderEntries();
		OrderEntryTableModel model = new OrderEntryTableModel(entries);
		WebTable lineDetailTable = new WebTable(model);
		WebScrollPane scrollPane = new WebScrollPane(lineDetailTable);

		lineDetailTable.getTableHeader().setFont(new Font("verdana", Font.BOLD, 14));
		lineDetailTable.setRowHeight(35);
		lineDetailTable.setFont(new Font("verdana", Font.PLAIN, 14));

		formPanel.add(l1, BorderLayout.NORTH);
		formPanel.add(scrollPane, BorderLayout.CENTER);

		return formPanel;

	}

	private WebPanel displayOrderUpdateScreen() {
		WebPanel mainPanel = new WebPanel(new BorderLayout());
		WebPanel linePanel = orderLineDetailPanel();
		mainPanel.add(linePanel, BorderLayout.NORTH);

		WebPanel formPanel = new WebPanel();
		mainPanel.add(formPanel, BorderLayout.WEST);

		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NORTHEAST;

		WebLabel l1 = new WebLabel("Order Status : ", SwingConstants.RIGHT);
		final WebLabel orderStatusLabel = new WebLabel("SHIPMENT PENDING");

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(orderStatusLabel, c);
		formPanel.add(orderStatusLabel);

		WebLabel l2 = new WebLabel("Payment Status : ", SwingConstants.RIGHT);
		final WebComboBox paymentStatusCombo = new WebComboBox(new PaymentStatusModel());

		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(0, 20, 10, 0);
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 3;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(paymentStatusCombo, c);
		formPanel.add(paymentStatusCombo);

		WebLabel l3 = new WebLabel("Delivery Status : ", SwingConstants.RIGHT);
		final WebComboBox deliveryStatusCombo = new WebComboBox(new DeliveryStatusModel());

		c.gridx = 4;
		c.gridy = 0;
		c.insets = new Insets(0, 20, 10, 0);
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 5;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(deliveryStatusCombo, c);
		formPanel.add(deliveryStatusCombo);
		return mainPanel;

	}

	private void testShowProduct() {

		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Order Number : ", SwingConstants.RIGHT);
		final WebLabel codeText = new WebLabel("1004");

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(codeText, c);
		formPanel.add(codeText);

		WebLabel l2 = new WebLabel("Delivery Item : ", SwingConstants.RIGHT);
		final WebLabel nameText = new WebLabel("9 INCH Standard");

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(nameText, c);
		formPanel.add(nameText);

		WebLabel l3 = new WebLabel("Actual Quantity : ", SwingConstants.RIGHT);
		final WebTextField actualQuantityText = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(actualQuantityText, c);
		formPanel.add(actualQuantityText);

		WebLabel l4 = new WebLabel("Delivery Receipt No : ", SwingConstants.RIGHT);
		final WebTextField receiptNoText = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 3;

		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(receiptNoText, c);
		formPanel.add(receiptNoText);

		WebLabel l5 = new WebLabel("Delivery Date : ", SwingConstants.RIGHT);
		final WebTextField deliveryDateText = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(deliveryDateText, c);
		formPanel.add(deliveryDateText);

		WebLabel l6 = new WebLabel("Delivery Vehicle : ", SwingConstants.RIGHT);
		final WebTextField deliveryVehicleText = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l6, c);
		formPanel.add(l6);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(deliveryVehicleText, c);
		formPanel.add(deliveryVehicleText);

		// WebLabel l7 = new WebLabel("Quantity Added : ",
		// SwingConstants.RIGHT);
		// final WebTextField addedQuantityText = new WebTextField(15);
		//
		// c.gridx = 0;
		// c.gridy = 6;
		// c.anchor = GridBagConstraints.NORTH;
		// layout.setConstraints(l7, c);
		// formPanel.add(l7);
		//
		// c.gridx = 1;
		// c.gridy = 6;
		// c.insets = new Insets(0, 10, 5, 0);
		//
		// layout.setConstraints(addedQuantityText, c);
		// formPanel.add(addedQuantityText);
		//
		// WebLabel l8 = new WebLabel("Quantity Reserved : ",
		// SwingConstants.RIGHT);
		// final WebTextField reservedQuantityText = new WebTextField(15);
		//
		// c.gridx = 0;
		// c.gridy = 7;
		// c.anchor = GridBagConstraints.NORTH;
		// layout.setConstraints(l8, c);
		// formPanel.add(l8);
		//
		// c.gridx = 1;
		// c.gridy = 7;
		// c.insets = new Insets(0, 10, 5, 0);
		//
		// layout.setConstraints(reservedQuantityText, c);
		// formPanel.add(reservedQuantityText);

		WebButton updateProductButton = new WebButton("Update");
		updateProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		c.gridx = 1;
		c.gridy = 8;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(updateProductButton, c);
		formPanel.add(updateProductButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	private void testInvestmentdetailPage() {

		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Name : ", SwingConstants.RIGHT);
		final WebLabel vendorNameText = new WebLabel("Prasun Kumar Sarangi");

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(vendorNameText, c);
		formPanel.add(vendorNameText);

		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		final WebLabel primaryContact = new WebLabel("9035476943");

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);

		WebLabel l3 = new WebLabel("Total Investment : ", SwingConstants.RIGHT);
		final WebLabel totalInvestmentsLabel = new WebLabel("1,65,000.00");

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(totalInvestmentsLabel, c);
		formPanel.add(totalInvestmentsLabel);

		WebLabel l4 = new WebLabel("Investment Details : ", SwingConstants.RIGHT);
		WebTable investmentTable = new WebTable(new InvestmentTableModel(new ArrayList<InvestmentData>()));
		investmentTable.getColumnModel().getColumn(0).setMinWidth(200);
		investmentTable.getColumnModel().getColumn(1).setMinWidth(200);
		investmentTable.getColumnModel().getColumn(2).setMinWidth(200);

		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 20;
		c.insets = new Insets(0, 0, 0, 20);
		// c.weightx = 100.0;
		// c.weighty = 100.0;

		WebScrollPane areaScroll = new WebScrollPane(investmentTable);
		areaScroll.setBorder(primaryContact.getBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	private void testDynamicPopup() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				if (SwingUtils.isLeftMouseButton(e)) {
					createMenu().showMenu(e.getComponent(), e.getPoint());
				}
			}

		});
	}

	protected WebDynamicMenu createMenu() {
		final WebDynamicMenu menu = new WebDynamicMenu();
		menu.setType(DynamicMenuType.roll);
		menu.setHideType(DynamicMenuType.fade);
		menu.setRadius(100);
		menu.setStepProgress(0.06f);

		final int amount = 5;
		for (int i = 0; i < amount; i++) {
			final int number = i;
			final WebDynamicMenuItem item = new WebDynamicMenuItem();
			item.setText("Hello" + i);
			item.setMargin(new Insets(0, 0, 10, 30));
			item.setPaintBorder(true);
			menu.addItem(item);
		}

		return menu;
	}

	private void buildUI_Investor() {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Investor Name : ", SwingConstants.RIGHT);
		final WebTextField vendorNameText = new WebTextField(15);

		WebLabel l2 = new WebLabel("Amount : ", SwingConstants.RIGHT);
		WebTextField primaryContact = new WebTextField(15);

		WebLabel l3 = new WebLabel("Invested On : ", SwingConstants.RIGHT);
		WebDateField investDate = new WebDateField();

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(vendorNameText, c);
		formPanel.add(vendorNameText);

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);

		c.gridx = 0;
		c.gridy = 2;

		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(investDate, c);
		formPanel.add(investDate);

		WebButton createVendorButton = new WebButton("Create Vendor");
		createVendorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(createVendorButton, c);
		formPanel.add(createVendorButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();

	}

	private void buildGUI_New1() {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Vendor Name : ", SwingConstants.RIGHT);
		final WebTextField vendorNameText = new WebTextField(15);

		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		WebTextField primaryContact = new WebTextField(15);

		WebLabel l3 = new WebLabel("Contact Person : ", SwingConstants.RIGHT);
		WebList contactPersonsList = new WebList();

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(vendorNameText, c);
		formPanel.add(vendorNameText);

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 0, 0);
		c.gridwidth = 3;
		WebScrollPane areaScroll = new WebScrollPane(contactPersonsList);
		areaScroll.setBorder(primaryContact.getBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);

		WebButton createVendorButton = new WebButton("Create Vendor");
		createVendorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(createVendorButton, c);
		formPanel.add(createVendorButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();

	}

	private void buildUI_New_Vendor() {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Vendor Name : ", SwingConstants.RIGHT);
		final WebTextField vendorNameText = new WebTextField(15);

		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		WebTextField primaryContact = new WebTextField(15);

		WebLabel l3 = new WebLabel("Contact Person : ", SwingConstants.RIGHT);
		WebList contactPersonsList = new WebList();

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(vendorNameText, c);
		formPanel.add(vendorNameText);

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(0, 10, 0, 0);
		c.gridwidth = 3;
		WebScrollPane areaScroll = new WebScrollPane(contactPersonsList);
		areaScroll.setBorder(primaryContact.getBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);

		c.gridx = 4;
		c.gridy = 2;
		c.insets = new Insets(0, 1, 0, 0);
		// c.anchor = GridBagConstraints.NORTH;
		WebButton addContactPerson = new WebButton();
		addContactPerson.setText("+");
		layout.setConstraints(addContactPerson, c);
		formPanel.add(addContactPerson);

		WebButton createVendorButton = new WebButton("Create Vendor");
		createVendorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(createVendorButton, c);
		formPanel.add(createVendorButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	private void buildUI_New_Contact() {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Contact Name : ", SwingConstants.RIGHT);
		final WebTextField vendorNameText = new WebTextField(15);

		WebLabel l2 = new WebLabel("Primary Contact No : ", SwingConstants.RIGHT);
		WebTextField primaryContact = new WebTextField(15);

		WebLabel l3 = new WebLabel("Secondary Contact No : ", SwingConstants.RIGHT);
		WebTextField secondaryContact = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 10, 0);
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(vendorNameText, c);
		formPanel.add(vendorNameText);

		c.gridx = 0;
		c.gridy = 1;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 10, 10, 0);

		layout.setConstraints(primaryContact, c);
		formPanel.add(primaryContact);

		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;

		layout.setConstraints(secondaryContact, c);
		formPanel.add(secondaryContact);

		WebLabel addressHeader = new WebLabel("Communication Address ", SwingConstants.RIGHT);
		addressHeader.setForeground(Color.BLUE);

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(20, 10, 0, 0);
		layout.setConstraints(addressHeader, c);
		formPanel.add(addressHeader);

		WebLabel line1 = new WebLabel("Address Line 1:", SwingConstants.RIGHT);
		WebTextField line1Text = new WebTextField(30);

		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(line1, c);
		formPanel.add(line1);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(line1Text, c);
		formPanel.add(line1Text);

		WebLabel line2 = new WebLabel("Address Line 2:", SwingConstants.RIGHT);
		WebTextField line2Text = new WebTextField(30);

		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(line2, c);
		formPanel.add(line2);

		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(line2Text, c);
		formPanel.add(line2Text);

		WebLabel line3 = new WebLabel("Address Line 3:", SwingConstants.RIGHT);
		WebTextField line3Text = new WebTextField(30);

		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(line3, c);
		formPanel.add(line3);

		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(line3Text, c);
		formPanel.add(line3Text);

		WebButton addContactButton = new WebButton("Add Contact");
		addContactButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		c.gridx = 1;
		c.gridy = 7;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(addContactButton, c);
		formPanel.add(addContactButton);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();
	}

	private void buildGUI() {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Expense Category : ", SwingConstants.RIGHT);
		WebTextField t1 = new WebTextField(20);

		WebLabel l2 = new WebLabel("Description : ", SwingConstants.RIGHT);
		WebTextField t2 = new WebTextField(20);

		WebLabel l3 = new WebLabel("Amount : ", SwingConstants.RIGHT);
		WebTextField t3 = new WebTextField(20);

		WebLabel l4 = new WebLabel("Date : ", SwingConstants.RIGHT);
		WebTextField t4 = new WebTextField(20);

		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 15, 0, 0);
		layout.setConstraints(t1, c);
		formPanel.add(t1);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(15, 0, 0, 0); // top padding
		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(15, 15, 0, 0);
		layout.setConstraints(t2, c);
		formPanel.add(t2);

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(15, 0, 0, 0); // top padding
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(15, 15, 0, 0);
		layout.setConstraints(t3, c);
		formPanel.add(t3);

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(15, 0, 0, 0); // top padding
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(15, 15, 0, 0);
		layout.setConstraints(t4, c);
		formPanel.add(t4);

		WebButton b1 = new WebButton("Create");
		WebButton b2 = new WebButton("Cancel");

		WebPanel buttPanel = new WebPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		buttPanel.add(b1);
		buttPanel.add(b2);

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(15, 15, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(buttPanel, c);
		formPanel.add(buttPanel);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		setSize(500, 800);

	}

	private void buildGUI_New() {
		WebPanel formPanel = new WebPanel();
		formPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Expense Type : ", SwingConstants.RIGHT);
		WebComboBox c1 = new WebComboBox();
		c1.addItem("Unit Setup");
		c1.addItem("Transformer Setup");
		c1.addItem("Machine Booking");
		c1.addItem("Machine Installation");
		c1.addItem("Electrical Installation");

		WebTextField t1 = new WebTextField(15);

		WebLabel l2 = new WebLabel("Paid To : ", SwingConstants.RIGHT);
		WebTextField t2 = new WebTextField(15);

		WebLabel l3 = new WebLabel("Amount : ", SwingConstants.RIGHT);
		WebTextField t3 = new WebTextField(15);

		WebLabel l4 = new WebLabel("Date : ", SwingConstants.RIGHT);
		WebTextField t4 = new WebTextField(15);

		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(l1, c);
		formPanel.add(l1);

		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 0, 0);
		layout.setConstraints(c1, c);
		formPanel.add(c1);

		c.insets = new Insets(0, 30, 0, 0);

		c.gridx = 2;
		c.gridy = 0;

		layout.setConstraints(l2, c);
		formPanel.add(l2);

		c.gridx = 3;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 0, 0);
		layout.setConstraints(t2, c);
		formPanel.add(t2);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		layout.setConstraints(l3, c);
		formPanel.add(l3);

		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(t3, c);
		formPanel.add(t3);

		c.insets = new Insets(0, 30, 0, 0);

		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		layout.setConstraints(l4, c);
		formPanel.add(l4);

		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 0, 0);
		layout.setConstraints(t4, c);
		formPanel.add(t4);

		WebLabel l5 = new WebLabel("Description : ", SwingConstants.RIGHT);
		WebTextArea area = new WebTextArea(5, 10);

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l5, c);
		formPanel.add(l5);

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 3;
		WebScrollPane areaScroll = new WebScrollPane(area);
		areaScroll.setBorder(t3.getBorder());
		layout.setConstraints(areaScroll, c);
		formPanel.add(areaScroll);

		WebButton b1 = new WebButton("Create");

		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridwidth = 1;
		layout.setConstraints(b1, c);
		formPanel.add(b1);

		getContentPane().add(new GroupPanel(formPanel), BorderLayout.CENTER);
		pack();

	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					WebLookAndFeel.install(true);
					WebLookAndFeel.setDecorateFrames(true);
					WebLookAndFeel.setDecorateDialogs(true);

					TestApp app = new TestApp();
					app.setVisible(true);

				}
			});
		} catch (Exception e) {
			System.out.println("Unable to load WebLookAndFeel...");
		}

	}

}
