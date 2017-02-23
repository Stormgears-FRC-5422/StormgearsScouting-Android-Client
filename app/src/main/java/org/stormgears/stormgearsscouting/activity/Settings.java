package org.stormgears.stormgearsscouting.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.stormgears.stormgearsscouting.R;
import org.stormgears.stormgearsscouting.fragment.Preferences;
import org.stormgears.stormgearsscouting.util.Utils;


public class Settings extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle(R.string.settings_activity_name);

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(android.R.id.content, new Preferences());
		ft.commit();
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		// Update the preferences of the app once the Settings activity closes
		Utils.initializePrefs();
	}
}
