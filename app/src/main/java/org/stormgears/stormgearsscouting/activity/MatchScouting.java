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

		System.out.println("protocol to use: " + AppPrefs.protocolToUse);
		System.out.println("phone number: " + AppPrefs.phoneNumber);
		System.out.println("event code: " + AppPrefs.eventCode);
		System.out.println("password: " + AppPrefs.password);
		System.out.println("server url: " + AppPrefs.serverUrl);
		System.out.println("internet: " + AppPrefs.pInternet);
		System.out.println("send sms: " + AppPrefs.pSmsSend);
		System.out.println("read sms: " + AppPrefs.pSmsRead);
	}
}
