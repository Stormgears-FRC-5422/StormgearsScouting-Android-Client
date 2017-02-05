package org.stormgears.stormgearsscoutingv20.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.stormgears.stormgearsscoutingv20.R;
import org.stormgears.stormgearsscoutingv20.util.AppPrefs;
import org.stormgears.stormgearsscoutingv20.util.Constants;
import org.stormgears.stormgearsscoutingv20.util.Utils;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.item_settings:
			{
				// Launch settings user interface
				Context context = MainActivity.this;
				Intent intent = new Intent(context, Settings.class);
				context.startActivity(intent);

				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onClickMatchScouting(View view)
	{
		// Launch match scouting user interface
		Context context = MainActivity.this;
		Intent intent = new Intent(context, MatchScouting.class);
		context.startActivity(intent);
	}

	public void onClickPitScouting(View view)
	{
		// Launch pit scouting user interface
		Context context = MainActivity.this;
		Intent intent = new Intent(context, PitScouting.class);
		context.startActivity(intent);
	}

	private void initialize()
	{
		// Initialize the String arrays from those provided in strings.xml
		Resources resources = getResources();

		Constants.MATCH_TYPES = resources.getStringArray(R.array.match_types);
		Constants.AUTO_GEAR_STRATEGIES = resources.getStringArray(R.array.auto_gear_strategies);
		Constants.TELE_SHOTS_MISSED = resources.getStringArray(R.array.tele_shots_missed);
		Constants.TELE_BALL_RETRIEVAL_METHOD = resources.getStringArray(R.array.tele_ball_retrieval_method);
		Constants.TELE_ROBOT_CLIMB_STATUS = resources.getStringArray(R.array.tele_robot_climb_status);
		Constants.MATCH_STRATEGIES = resources.getStringArray(R.array.match_strategies);

		// Initialize SharedPreferences for this app; use default SharedPreferences
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Utils.initializePrefs(sharedPreferences);
	}
}
