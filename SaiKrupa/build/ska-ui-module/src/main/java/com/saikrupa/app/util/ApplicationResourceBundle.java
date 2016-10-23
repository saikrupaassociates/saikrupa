package com.saikrupa.app.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ApplicationResourceBundle {

	private static ApplicationResourceBundle appResourceBundle;
	private ResourceBundle bundle;
	
	private ApplicationResourceBundle() {
		bundle = ResourceBundle.getBundle("ApplicationResources", Locale.getDefault());
		if(bundle == null) {
			System.out.println("Could not loaded");			
		}		
	}
	
	public static ApplicationResourceBundle getApplicationResourceBundle() {
		if(appResourceBundle == null) {
			appResourceBundle = new ApplicationResourceBundle();
		}
		return appResourceBundle;
	}
	
	public String getResourceValue(String resourceKey) {
		return bundle.getString(resourceKey);
	}
	
	public void dispose() {
		appResourceBundle = null;
	}

}
