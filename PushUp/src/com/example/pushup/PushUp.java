package com.example.pushup;

import android.app.*;
import android.graphics.*;
import android.hardware.*;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class PushUp extends Activity 
{
	private static boolean m_Stop = false;
	private static SensorManager m_SensorManager = null;
	private static ProximitySensor m_Proximity = null;
	private static TextView textView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		// Set our main view
		setContentView(R.layout.activity_push_up);
		
		// Obtain layout handles
		textView = (TextView) findViewById ( R.id.PushUps );
		final ImageButton Control = (ImageButton) findViewById ( R.id.control );
		
		// Initialize our proximity sensor
		m_Proximity = new ProximitySensor ( this, textView );
		
		// Setup variables
		m_SensorManager = m_Proximity.Manager();
		
		if ( savedInstanceState == null )
			textView.setText("0");
		else
		{
			// Reset variables
			String pushups = savedInstanceState.getSerializable( "PUSHUPS" ).toString();
			m_Stop = savedInstanceState.getSerializable ( "STATE" ).toString() == "1" ? true : false;
			m_Proximity.Pushups( Integer.parseInt( pushups) );
			textView.setText(pushups);
			
			// Restore control state
			if ( !m_Stop )
			{
				Control.setImageDrawable( getResources ().getDrawable(R.drawable.stop) );
				m_Stop = true;
				m_Proximity.Start();
			}
			else
			{
				Control.setImageDrawable( getResources ().getDrawable(R.drawable.start) );
				m_Stop = false;
				m_Proximity.Stop();
				textView.setText("0");
			}
		}
		
		// Register the sensor
		m_SensorManager.registerListener(m_Proximity, 
										 m_Proximity.Sensor(), 
										 SensorManager.SENSOR_DELAY_FASTEST);
		
		// Add a onClick listener.
		Control.setOnClickListener( new OnClickListener () {

			public void onClick(View v) {
				if ( !m_Stop )
				{
					Control.setImageDrawable( getResources ().getDrawable(R.drawable.stop) );
					m_Stop = true;
					m_Proximity.Start();
				}
				else
				{
					Control.setImageDrawable( getResources ().getDrawable(R.drawable.start) );
					m_Stop = false;
					m_Proximity.Stop();
				}
			}
		} );
	}
	
	@Override
	protected void onSaveInstanceState ( Bundle out )
	{
		super.onSaveInstanceState(out);
		
		out.putSerializable("PUSHUPS", m_Proximity.Pushups());
		out.putSerializable("STATE", m_Stop);
	}
	
	@Override
	protected void onResume ()
	{
		super.onResume();
		
		// Register the sensor
		m_SensorManager.registerListener(m_Proximity, 
				 m_Proximity.Sensor(), 
				 SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause ()
	{
		super.onResume();
		
		// Unregister the sensor
		m_SensorManager.unregisterListener(m_Proximity);
	}
}
