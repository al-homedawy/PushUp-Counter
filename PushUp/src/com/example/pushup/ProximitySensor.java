package com.example.pushup;

import android.content.Context;
import android.hardware.*;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.widget.*;

public class ProximitySensor implements SensorEventListener
{
	// Private Variables
	private SensorManager m_SensorManager = null;
	private Sensor m_Sensor = null;
	private Context m_Context = null;
	private TextView m_TextView = null;
	
	// PushUp values
	private float m_Prev = -1;
	private float m_Distance = -1;
	private float m_Range = 0;
	private int m_PushUp = 0;
	private boolean m_Start = false;
	private ToneGenerator tg = null;
	
	// Constructor
	public ProximitySensor ( Context rootView, TextView targetControl )
	{
		tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
		
		// Setup variables
		m_Context = rootView;
		m_Distance = 0.0f;
		m_TextView = targetControl;

		// Obtain hardware data
		m_SensorManager = (SensorManager) m_Context.getSystemService ( Context.SENSOR_SERVICE );
		m_Sensor = m_SensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		m_Range = m_Sensor.getMaximumRange() / 100;
	}
	
	public void onSensorChanged(SensorEvent event) 
	{		
		
		try
		{
			if ( m_Start )
			{
				// Update the distance
				m_Prev = m_Distance;
				m_Distance = event.values [0] / 100;
				
				if ( m_Prev == m_Range && 
					 m_Distance == 0 )
				{
					// Beep
					tg.startTone(ToneGenerator.TONE_PROP_BEEP);
				    
				    // Increment push-up counter
				    ++m_PushUp;
				    
				    m_TextView.setText( Integer.toString(m_PushUp) );
				}
			}
		} catch (Exception e) {
			Log.d("%s", e.getMessage());
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) 
	{
		// Do nothing here for now :(
	}
	
	public int Pushups ()
	{
		return m_PushUp;
	}
	
	public void Pushups ( int pushups )
	{
		m_PushUp = pushups;
	}
	
	public float Distance ()
	{
		return m_Distance;
	}
	
	public SensorManager Manager ()
	{
		return m_SensorManager;
	}
	
	public Sensor Sensor ()
	{
		return m_Sensor;
	}
	
	public void Start ()
	{
		Log.d ("%s", "Called");
		m_Start = true;
	}
	
	public void Stop ()
	{
		m_Prev = -1;
		m_Distance = -1;
		m_PushUp = 0;
		m_Start = false;
	}
}
