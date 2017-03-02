package org.stormgears.stormgearsscouting.activity;

import android.os.Bundle;

import org.stormgears.stormgearsscouting.R;
import org.stormgears.stormgearsscouting.activity.scouting_activity.ScoutingActivity;
import org.stormgears.stormgearsscouting.data.Data;
import org.stormgears.stormgearsscouting.util.Constants;

public class PitScouting extends ScoutingActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pit_scouting);
		setTitle(R.string.pit_scouting_activity_name);

		setScoutType(Constants.PIT_SCOUT);

		initialize();

		requestVitalPermissions();

		Data.scoutType = Constants.PIT_SCOUT;
	}
}
