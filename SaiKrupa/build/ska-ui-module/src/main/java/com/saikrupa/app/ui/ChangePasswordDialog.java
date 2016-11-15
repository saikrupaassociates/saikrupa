package com.saikrupa.app.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebPasswordField;
import com.saikrupa.app.dao.ApplicationUserDAO;
import com.saikrupa.app.dao.impl.DefaultApplicationUserDAO;
import com.saikrupa.app.dto.ApplicationUserData;
import com.saikrupa.app.session.ApplicationSession;
import com.saikrupa.app.ui.component.AppWebLabel;

public class ChangePasswordDialog extends BaseAppDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChangePasswordDialog(SKAMainApp owner) {
		super(owner, true);
		setTitle("Change Password");
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		buildGUI(owner);
		setLocationRelativeTo(owner);
		setResizable(false);
	}

	private void buildGUI(final SKAMainApp owner) {
		WebPanel formPanel = new WebPanel();
		GridBagLayout layout = new GridBagLayout();
		formPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		WebLabel l1 = new WebLabel("Current Password : ", SwingConstants.RIGHT);
		final WebPasswordField currentPasswordText = new WebPasswordField(10);
		l1.setFont(applyLabelFont());
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
		l2.setFont(applyLabelFont());
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

		WebLabel l3 = new WebLabel("Re-enter Password : ", SwingConstants.RIGHT);
		l3.setFont(applyLabelFont());
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

		WebButton changePasswordButton = new WebButton("Change Password");
		WebButton cancelButton = new WebButton("Cancel");
		WebPanel buttonPanel = new WebPanel(true);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		buttonPanel.add(changePasswordButton);
		buttonPanel.add(cancelButton);

		changePasswordButton.setActionCommand("CHANGE");
		cancelButton.setActionCommand("CANCEL");

		getContentPane().add(formPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equalsIgnoreCase("CHANGE")) {
					char[] current = currentPasswordText.getPassword();
					char[] new1 = newPasswordField.getPassword();
					char[] new2 = repeatPasswordText.getPassword();
					ApplicationUserData userData = ApplicationSession.getSession().getCurrentUser();
					
					if (String.valueOf(current).trim().isEmpty()) {
						final WebPopOver popOver = new WebPopOver(ChangePasswordDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Specify Current Password"));
						popOver.show((WebPasswordField) currentPasswordText);
						
					} else if (!String.valueOf(current).trim().equals(String.valueOf(userData.getPassword()))) {
						final WebPopOver popOver = new WebPopOver(ChangePasswordDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Current password does not match with your logged on password"));
						popOver.show((WebPasswordField) currentPasswordText);
						
					} else if (String.valueOf(new1).trim().isEmpty()) {
						final WebPopOver popOver = new WebPopOver(ChangePasswordDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Specify New Password"));
						popOver.show((WebPasswordField) newPasswordField);
					} else if (String.valueOf(new2).trim().isEmpty()) {
						final WebPopOver popOver = new WebPopOver(ChangePasswordDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("Re-enter New Password"));
						popOver.show((WebPasswordField) repeatPasswordText);
						
					} else if (!String.valueOf(new1).trim().equals(String.valueOf(new2).trim())) {
						final WebPopOver popOver = new WebPopOver(ChangePasswordDialog.this);
						popOver.setCloseOnFocusLoss(true);
						popOver.setMargin(10);
						popOver.setLayout(new VerticalFlowLayout());
						popOver.add(new AppWebLabel("New Password entries does not match"));
						popOver.show((WebPasswordField) repeatPasswordText);
						
					} else {						
						dispose();
						userData.setPassword(new1);
						ApplicationUserDAO dao = new DefaultApplicationUserDAO();
						try {
							dao.changePassword(userData);
							showSuccessNotification();
						} catch (Exception e1) {
							showFailureNotification();
						}
					} 
				} else if (e.getActionCommand().equalsIgnoreCase("CANCEL")) {
					dispose();
				}
			}
		};

		changePasswordButton.addActionListener(listener);
		cancelButton.addActionListener(listener);
	}
}
