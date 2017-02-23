package org.stormgears.stormgearsscouting.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.stormgears.stormgearsscouting.R;
import org.stormgears.stormgearsscouting.dialog.SendDataDialog;
import org.stormgears.stormgearsscouting.util.Constants;
import org.stormgears.stormgearsscouting.util.Utils;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();

		displayWarnings();
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
			case R.id.main_item_settings:
			{
				// Launch settings user interface
				Context context = MainActivity.this;
				Intent intent = new Intent(context, Settings.class);
				context.startActivity(intent);

				return true;
			}

			case R.id.main_item_send_cached:
			{
				// Open a dialog to view cached data
				SendDataDialog dialog = new SendDataDialog();
				dialog.show(getFragmentManager(), "this is a tag!");

				return true;
			}

			case R.id.main_item_clear_cached:
				// Open a dialog asking user if they are sure they want to clear cached data
				new AlertDialog.Builder(this)
						.setTitle("Clear Cached Data")
						.setMessage("Are you sure you want to clear cached data?")
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
								// Clear cached data
								SharedPreferences.Editor editor = Utils.sharedPreferences.edit();
								editor.putString(Constants.SAVED_PROTOBUFS_KEY, "");
								editor.commit();
							}
						})
						.setIcon(android.R.drawable.ic_dialog_alert)
						.show();
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
		Utils.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Utils.activity = this;
		Utils.initializePrefs();
	}

	private void displayWarnings()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

	}
}
