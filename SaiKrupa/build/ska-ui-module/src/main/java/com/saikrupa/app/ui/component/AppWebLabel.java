package com.saikrupa.app.ui.component;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.Icon;

import com.alee.laf.label.WebLabel;

public class AppWebLabel extends WebLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppWebLabel() {
		applyLabelFont();
	}

	public AppWebLabel(Insets margin) {
		super(margin);
		applyLabelFont();
	}

	public AppWebLabel(Icon icon) {
		super(icon);
		applyLabelFont();
	}

	public AppWebLabel(int horizontalAlignment) {
		super(horizontalAlignment);
		applyLabelFont();
	}

	public AppWebLabel(String text) {
		super(text);
		applyLabelFont();
	}

	public AppWebLabel(Icon icon, Insets margin) {
		super(icon, margin);
		applyLabelFont();
	}

	public AppWebLabel(int horizontalAlignment, Insets margin) {
		super(horizontalAlignment, margin);
		applyLabelFont();
	}

	public AppWebLabel(Icon icon, int horizontalAlignment) {
		super(icon, horizontalAlignment);
		applyLabelFont();
	}

	public AppWebLabel(String text, Icon icon) {
		super(text, icon);
		applyLabelFont();
	}

	public AppWebLabel(Icon icon, int horizontalAlignment, Insets margin) {
		super(icon, horizontalAlignment, margin);
		applyLabelFont();
	}

	public AppWebLabel(String text, Insets margin, Object... data) {
		super(text, margin, data);
		applyLabelFont();
	}

	public AppWebLabel(String text, int horizontalAlignment, Object... data) {
		super(text, horizontalAlignment, data);
		applyLabelFont();
	}

	public AppWebLabel(String text, int horizontalAlignment, Insets margin, Object... data) {
		super(text, horizontalAlignment, margin, data);
		applyLabelFont();
	}

	public AppWebLabel(String text, Icon icon, Insets margin, Object... data) {
		super(text, icon, margin, data);
		applyLabelFont();
	}

	public AppWebLabel(String text, Icon icon, int horizontalAlignment, Object... data) {
		super(text, icon, horizontalAlignment, data);
		applyLabelFont();
	}

	public AppWebLabel(String text, Icon icon, int horizontalAlignment, Insets margin, Object... data) {
		super(text, icon, horizontalAlignment, margin, data);
		applyLabelFont();
	}
	
	public void applyLabelFont() {
		setFont(new Font("verdana", Font.BOLD, 12));
	}

}
