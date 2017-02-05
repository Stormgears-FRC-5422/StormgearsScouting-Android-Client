package org.stormgears.stormgearsscoutingv20.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.stormgears.stormgearsscoutingv20.R;

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
