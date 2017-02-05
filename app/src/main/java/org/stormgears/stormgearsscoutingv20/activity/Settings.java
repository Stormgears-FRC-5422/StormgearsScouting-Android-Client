package org.stormgears.stormgearsscoutingv20.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.stormgears.stormgearsscoutingv20.R;
import org.stormgears.stormgearsscoutingv20.fragment.Preferences;
import org.stormgears.stormgearsscoutingv20.util.Constants;


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

	void savePrefs()
	{



		
	}

}
