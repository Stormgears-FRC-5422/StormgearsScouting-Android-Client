package org.stormgears.stormgearsscouting.activity;

import android.os.Bundle;

import org.stormgears.stormgearsscouting.R;
import org.stormgears.stormgearsscouting.activity.scouting_activity.ScoutingActivity;
import org.stormgears.stormgearsscouting.data.Data;
import org.stormgears.stormgearsscouting.util.AppPrefs;
import org.stormgears.stormgearsscouting.util.Constants;

public class MatchScouting extends ScoutingActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_scouting);
		setTitle(R.string.match_scouting_activity_name);

		setScoutType(Constants.MATCH_SCOUT);

		initialize();

		requestVitalPermissions();

		Data.scoutType = Constants.MATCH_SCOUT;
	}
}
