package com.example.pushup;

import android.os.Bundle;
import android.app.*;
import android.content.Intent;
import android.widget.*;
import android.widget.TabHost.TabSpec;

/*
 * GUI
 * OnSaveInstanceState
 * Notification
 */

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		// Set the main content view
		setContentView(R.layout.activity_main);
		
		// Obtain our tab host
		TabHost tabHost = getTabHost ();
		
		// Create our intents
		Intent launchMain = new Intent ( this, PushUp.class );
		Intent launchAbout = new Intent ( this, About.class );
		
		// Create our main tab
		TabSpec mainTab = tabHost.newTabSpec("Main");
		mainTab.setIndicator("Main");
		mainTab.setContent(launchMain);
		tabHost.addTab(mainTab);
		
		// Create our About tab
		TabSpec aboutTab = tabHost.newTabSpec("About");
		aboutTab.setIndicator("About");
		aboutTab.setContent(launchAbout);
		tabHost.addTab(aboutTab);
	}
}
