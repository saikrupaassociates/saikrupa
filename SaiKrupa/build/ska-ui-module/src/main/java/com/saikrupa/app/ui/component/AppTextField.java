package com.saikrupa.app.ui.component;

import javax.swing.text.Document;

import com.alee.laf.text.WebTextField;

public class AppTextField extends WebTextField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object model;

	public AppTextField() {
		// TODO Auto-generated constructor stub
	}
	
	public AppTextField(Object model) {
		this.model = model;
	}

	public AppTextField(boolean drawBorder) {
		super(drawBorder);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(int columns) {
		super(columns);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(String text, boolean drawBorder) {
		super(text, drawBorder);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(int columns, boolean drawBorder) {
		super(columns, drawBorder);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(String text, int columns) {
		super(text, columns);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(String text, int columns, boolean drawBorder) {
		super(text, columns, drawBorder);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		// TODO Auto-generated constructor stub
	}

	public AppTextField(Document doc, String text, int columns, boolean drawBorder) {
		super(doc, text, columns, drawBorder);
		// TODO Auto-generated constructor stub
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

}
