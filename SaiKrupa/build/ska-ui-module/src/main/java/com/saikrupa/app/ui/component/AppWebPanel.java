package com.saikrupa.app.ui.component;

import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;

import com.alee.extended.painter.Painter;
import com.alee.laf.panel.WebPanel;

public class AppWebPanel extends WebPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppWebPanel() {
		
	}

	public AppWebPanel(boolean decorated) {
		super(decorated);
		
	}

	public AppWebPanel(Component component) {
		super(component);
		
	}

	public AppWebPanel(Painter painter) {
		super(painter);
		
	}

	public AppWebPanel(LayoutManager layout) {
		super(layout);
		
	}

	public AppWebPanel(String styleId) {
		super(styleId);
		
	}

	public AppWebPanel(boolean decorated, LayoutManager layout) {
		super(decorated, layout);
		
	}

	public AppWebPanel(boolean decorated, Component component) {
		super(decorated, component);
		
	}

	public AppWebPanel(LayoutManager layout, Painter painter) {
		super(layout, painter);
		
	}

	public AppWebPanel(Painter painter, Component component) {
		super(painter, component);
		
	}

	public AppWebPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
	}

	public AppWebPanel(LayoutManager layout, Component... components) {
		super(layout, components);
		
	}

	public AppWebPanel(String styleId, LayoutManager layout) {
		super(styleId, layout);
		
	}

	public AppWebPanel(String styleId, Component component) {
		super(styleId, component);
	}
	
	
	public Font applyLabelFont() {
		return new Font("verdana", Font.BOLD, 12);
	}
	
	public Font applyTableFont() {
		return new Font("verdana", Font.BOLD, 12);
	}

}
