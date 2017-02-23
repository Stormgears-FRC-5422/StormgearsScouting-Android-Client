package org.stormgears.stormgearsscouting.fragment;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

import org.stormgears.stormgearsscouting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Preferences extends PreferenceFragment
{
	public Preferences()
	{
		// Required empty public constructor
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_all);
	}
}
