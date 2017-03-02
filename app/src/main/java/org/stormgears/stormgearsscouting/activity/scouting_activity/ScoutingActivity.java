package org.stormgears.stormgearsscouting.activity.scouting_activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.stormgears.stormgearsscouting.R;
import org.stormgears.stormgearsscouting.activity.Settings;
import org.stormgears.stormgearsscouting.data.Data;
import org.stormgears.stormgearsscouting.util.AppPrefs;
import org.stormgears.stormgearsscouting.util.Constants;

import java.util.Arrays;

/**
 * Base activity class for MatchScouting and PitScouting
 */

public class ScoutingActivity extends AppCompatActivity
{
	String scoutType;

	EditText txt_m_teamNumber;
	EditText txt_m_matchNumber;
	Spinner spn_m_matchType;
	RadioGroup opts_m_alliance;
	Spinner spn_m_auto_gearStrategy;
	NumberPicker np_m_auto_lowShots;
	NumberPicker np_m_auto_highShots;
	CheckBox chk_m_auto_crossedBaseline;
	NumberPicker np_m_tele_gearsPlaced;
	NumberPicker np_m_tele_lowShots;
	NumberPicker np_m_tele_highShots;
	Spinner spn_m_tele_percentShotsMissed;
	EditText txt_m_tele_rotorsSpinning;
	Spinner spn_m_tele_ballRetrievalMethod;
	Spinner spn_m_tele_robotClimbStatus;
	CheckBox chk_m_tele_airshipReadyForTakeoff;
	EditText txt_m_comments;
	Spinner spn_m_overallStrategy;
	EditText txt_m_redMatchPoints;
	EditText txt_m_blueMatchPoints;
	EditText txt_m_redRankingPoints;
	EditText txt_m_blueRankingPoints;

	EditText txt_p_teamNumber;
	EditText txt_p_teamName;
	Spinner spn_p_driveTrainType;
	EditText txt_p_matchStrategy;
	EditText txt_p_robotStrengths;
	EditText txt_p_robotWeaknesses;
	EditText txt_p_comments;

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case Constants.MY_PERMISSIONS_REQUEST_SMS_SEND:
			{
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					AppPrefs.pSmsSend = true;
					AppPrefs.pSmsRead = true;
				} else
				{
					AppPrefs.pSmsSend = false;
					AppPrefs.pSmsRead = false;
				}
				return;
			}
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		// pop
		populateFields();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		populateFields();
	}

	@Override
	protected void onStop()
	{
		// so super
		super.onStop();

		populateData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_scouting, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.scouting_item_save_locally:
			{
				populateData();

				if (!checkMissingItems())
					return true;

				new AlertDialog.Builder(this)
						.setTitle("Saving Data Locally")
						.setMessage("Data will be saved to the device. To send it to the server later, " +
								"go to the main menu. Then click the three dots, and then 'Send cached data...'")
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								// Save data locally
								Data.saveDataLocally();
							}
						})
						.setIcon(android.R.drawable.ic_dialog_alert)
						.show();

				return true;
			}
			case R.id.scouting_item_reset:
			{
				resetFields();
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onClickSend(View view)
	{
		populateData();

		// Error check for missing phone number or HTTP address
		if (AppPrefs.protocolToUse.equals(Constants.SMS_PROT) &&
				(AppPrefs.phoneNumber.equals(Constants.DEFAULT_SMS_PHONE_NUMBER) ||
						AppPrefs.phoneNumber.equals("")))
		{
			new AlertDialog.Builder(this)
					.setTitle("Default Phone Number in Use")
					.setMessage("Data will not be sent because the phone number has not been changed from " +
							"default. Tap 'Change' to open Settings.")
					.setPositiveButton("Change", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// Open settings
							Context context = ScoutingActivity.this;
							Intent intent = new Intent(context, Settings.class);
							context.startActivity(intent);
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// Do nothing
						}
					})
					.setIcon(android.R.drawable.ic_dialog_alert)
					.show();

			return;
		}
		else if (AppPrefs.serverUrl.equals(Constants.DEFAULT_SERVER_URL) ||
				AppPrefs.serverUrl.equals(""))
		{
			new AlertDialog.Builder(this)
					.setTitle("Default Server URL in Use")
					.setMessage("Data will not be sent because the server URL has not been changed from " +
							"default. Tap 'Change' to open Settings.")
					.setPositiveButton("Change", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// Open settings
							Context context = ScoutingActivity.this;
							Intent intent = new Intent(context, Settings.class);
							context.startActivity(intent);
						}
					})
					.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// Do nothing
						}
					})
					.setIcon(android.R.drawable.ic_dialog_alert)
					.show();

			return;
		}

		if (!checkMissingItems())
			return;

		Data.sendData(AppPrefs.protocolToUse, Data.getAsString(), this, false);
	}

	public void setScoutType(String scoutType)
	{
		this.scoutType = scoutType;
	}

	public void initialize()
	{
		if (scoutType.equals(Constants.MATCH_SCOUT))
		{
			txt_m_teamNumber = (EditText) findViewById(R.id.txt_m_team_number);
			txt_m_matchNumber = (EditText) findViewById(R.id.txt_m_match_number);

			txt_m_redRankingPoints = (EditText) findViewById(R.id.txt_m_red_ranking_points);
			txt_m_blueRankingPoints = (EditText) findViewById(R.id.txt_m_blue_ranking_points);
			spn_m_matchType = (Spinner) findViewById(R.id.spn_m_match_type);
			spn_m_matchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
			{
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
				{
					// If the match type is set to "Playoff" disable anything related to ranking points
					if (position == Arrays.asList(Constants.MATCH_TYPES).indexOf("Playoff"))
					{
						txt_m_redRankingPoints.setEnabled(false);
						txt_m_blueRankingPoints.setEnabled(false);

						Data.m_redRankingPoints = -1;
						Data.m_blueRankingPoints = -1;

						txt_m_redRankingPoints.setText("");
						txt_m_blueRankingPoints.setText("");
					}
					else
					{
						txt_m_redRankingPoints.setEnabled(true);
						txt_m_blueRankingPoints.setEnabled(true);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent)
				{

				}
			});

			opts_m_alliance = (RadioGroup) findViewById(R.id.opts_m_alliance);
			spn_m_auto_gearStrategy = (Spinner) findViewById(R.id.spn_m_auto_gear_strategy);

			np_m_auto_lowShots = (NumberPicker) findViewById(R.id.np_m_auto_low_shots);
			np_m_auto_lowShots.setMinValue(0);
			np_m_auto_lowShots.setMaxValue(10);
			np_m_auto_highShots = (NumberPicker) findViewById(R.id.np_m_auto_high_shots);
			np_m_auto_highShots.setMinValue(0);
			np_m_auto_highShots.setMaxValue(10);

			chk_m_auto_crossedBaseline = (CheckBox) findViewById(R.id.chk_m_auto_crossed_baseline);

			np_m_tele_gearsPlaced = (NumberPicker) findViewById(R.id.np_m_tele_gears_placed);
			np_m_tele_gearsPlaced.setMinValue(0);
			np_m_tele_gearsPlaced.setMaxValue(18);

			np_m_tele_lowShots = (NumberPicker) findViewById(R.id.np_m_tele_low_shots);
			np_m_tele_lowShots.setMinValue(0);
			np_m_tele_lowShots.setMaxValue(500);
			np_m_tele_highShots = (NumberPicker) findViewById(R.id.np_m_tele_high_shots);
			np_m_tele_highShots.setMinValue(0);
			np_m_tele_highShots.setMaxValue(500);

			spn_m_tele_percentShotsMissed = (Spinner) findViewById(R.id.spn_m_tele_percent_shots_missed);
			txt_m_tele_rotorsSpinning = (EditText) findViewById(R.id.txt_m_tele_rotors_spinning);
			spn_m_tele_ballRetrievalMethod = (Spinner) findViewById(R.id.spn_m_tele_ball_retrieval_method);
			spn_m_tele_robotClimbStatus = (Spinner) findViewById(R.id.spn_m_tele_robot_climb_status);
			chk_m_tele_airshipReadyForTakeoff = (CheckBox) findViewById(R.id.chk_m_tele_airship_ready_for_takeoff);
			txt_m_comments = (EditText) findViewById(R.id.txt_m_comments);
			spn_m_overallStrategy = (Spinner) findViewById(R.id.spn_m_strategy);
			txt_m_redMatchPoints = (EditText) findViewById(R.id.txt_m_red_match_points);
			txt_m_blueMatchPoints = (EditText) findViewById(R.id.txt_m_blue_match_points);
		} else if (scoutType.equals(Constants.PIT_SCOUT))
		{
			txt_p_teamNumber = (EditText) findViewById(R.id.txt_p_team_number);
			txt_p_teamName = (EditText) findViewById(R.id.txt_p_team_name);
			spn_p_driveTrainType = (Spinner) findViewById(R.id.spn_p_drive_train_type);
			txt_p_matchStrategy = (EditText) findViewById(R.id.txt_p_match_strategy);
			txt_p_robotStrengths = (EditText) findViewById(R.id.txt_p_robot_srengths);
			txt_p_robotWeaknesses = (EditText) findViewById(R.id.txt_p_robot_weaknesses);
			txt_p_comments = (EditText) findViewById(R.id.txt_p_comments);
		}
	}

	public void populateFields()
	{
		if (scoutType.equals(Constants.MATCH_SCOUT))
		{
			if (Data.teamNumber != -1)
				txt_m_teamNumber.setText(Data.teamNumber + "");
			if (Data.m_matchNumber != -1)
				txt_m_matchNumber.setText(Data.m_matchNumber + "");
			if (!Data.m_matchType.equals(""))
				spn_m_matchType.setSelection(Arrays.asList(Constants.MATCH_TYPES).indexOf(Data.m_matchType));
			if (Data.m_allianceColor.equals("Blue"))
				opts_m_alliance.check(R.id.opt_m_alliance_blue);
			if (!Data.m_auto_gearStrategy.equals(""))
				spn_m_auto_gearStrategy.setSelection(Arrays.asList(Constants.AUTO_GEAR_STRATEGIES).indexOf(Data.m_auto_gearStrategy));
			if (Data.m_auto_lowShots != -1)
				np_m_auto_lowShots.setValue(Data.m_auto_lowShots);
			if (Data.m_auto_highShots != -1)
				np_m_auto_highShots.setValue(Data.m_auto_highShots);
			if (Data.m_auto_crossedBaseline)
				chk_m_auto_crossedBaseline.setChecked(true);
			if (Data.m_tele_gearsPlaced != -1)
				np_m_tele_gearsPlaced.setValue(Data.m_tele_gearsPlaced);
			if (Data.m_tele_lowShots != -1)
				np_m_tele_lowShots.setValue(Data.m_tele_lowShots);
			if (Data.m_tele_highShots != -1)
				np_m_tele_highShots.setValue(Data.m_tele_highShots);
			if (!Data.m_tele_percentShotsMissed.equals(""))
				spn_m_tele_percentShotsMissed.setSelection(Arrays.asList(Constants.TELE_SHOTS_MISSED).indexOf(Data.m_tele_percentShotsMissed));
			if (Data.m_tele_rotorsSpinning != -1)
				txt_m_tele_rotorsSpinning.setText(Data.m_tele_rotorsSpinning + "");
			if (!Data.m_tele_ballRetrievalMethod.equals(""))
				spn_m_tele_ballRetrievalMethod.setSelection(Arrays.asList(Constants.TELE_BALL_RETRIEVAL_METHOD).indexOf(Data.m_tele_ballRetrievalMethod));
			if (!Data.m_tele_robotClimbStatus.equals(""))
				spn_m_tele_robotClimbStatus.setSelection(Arrays.asList((Constants.TELE_ROBOT_CLIMB_STATUS)).indexOf(Data.m_tele_robotClimbStatus));
			if (Data.m_tele_airshipReadyForTakeoff)
				chk_m_tele_airshipReadyForTakeoff.setChecked(true);
			if (!Data.m_comments.equals(""))
				txt_m_comments.setText(Data.m_comments + "");
			if (!Data.m_overallStrategy.equals(""))
				spn_m_overallStrategy.setSelection(Arrays.asList(Constants.MATCH_STRATEGIES).indexOf(Data.m_overallStrategy));
			if (Data.m_redMatchPoints != -1)
				txt_m_redMatchPoints.setText(Data.m_redMatchPoints + "");
			if (Data.m_blueMatchPoints != -1)
				txt_m_blueMatchPoints.setText(Data.m_blueMatchPoints + "");
			if (Data.m_redRankingPoints != -1)
				txt_m_redRankingPoints.setText(Data.m_redRankingPoints + "");
			if (Data.m_blueRankingPoints != -1)
				txt_m_blueRankingPoints.setText(Data.m_blueRankingPoints + "");
		}
		else if (scoutType.equals(Constants.PIT_SCOUT))
		{
			if (Data.teamNumber != -1)
				txt_p_teamNumber.setText(Data.teamNumber + "");
			if (!Data.p_teamName.equals(""))
				txt_p_teamName.setText(Data.p_teamName);
			if (!Data.p_drivetrainType.equals(""))
				spn_p_driveTrainType.setSelection(Arrays.asList(Constants.DRIVETRAIN_TYPES).indexOf(Data.p_drivetrainType));
			if (!Data.p_matchStrategy.equals(""))
				txt_p_matchStrategy.setText(Data.p_matchStrategy);
			if (!Data.p_robotStrengths.equals(""))
				txt_p_robotStrengths.setText(Data.p_robotStrengths);
			if (!Data.p_robotWeaknesses.equals(""))
				txt_p_robotWeaknesses.setText(Data.p_robotWeaknesses);
			if (!Data.p_otherComments.equals(""))
				txt_p_comments.setText(Data.p_otherComments);
		}
	}

	public void populateData()
	{
		Data.resetMatchScoutingData();
		Data.resetPitScoutingData();

		if (scoutType.equals(Constants.MATCH_SCOUT))
		{
			if (!txt_m_teamNumber.getText().toString().equals(""))
				Data.teamNumber = Integer.parseInt(txt_m_teamNumber.getText().toString());
			else
				Data.teamNumber = -1;
			if (!txt_m_matchNumber.getText().toString().equals(""))
				Data.m_matchNumber = Integer.parseInt(txt_m_matchNumber.getText().toString());
			else
				Data.m_matchNumber = -1;

			Data.m_matchType = spn_m_matchType.getSelectedItem().toString();

			if (opts_m_alliance.getCheckedRadioButtonId() == R.id.opt_m_alliance_blue)
				Data.m_allianceColor = "Blue";
			else
				Data.m_allianceColor = "Red";

			Data.m_auto_gearStrategy = spn_m_auto_gearStrategy.getSelectedItem().toString();
			Data.m_auto_lowShots = np_m_auto_lowShots.getValue();
			Data.m_auto_highShots = np_m_auto_highShots.getValue();
			Data.calculateAutoPercentShotsMissed();
			Data.m_auto_crossedBaseline = chk_m_auto_crossedBaseline.isChecked();
			Data.m_tele_gearsPlaced = np_m_tele_gearsPlaced.getValue();
			Data.m_tele_lowShots = np_m_tele_lowShots.getValue();
			Data.m_tele_highShots = np_m_tele_highShots.getValue();
			Data.m_tele_percentShotsMissed = spn_m_tele_percentShotsMissed.getSelectedItem().toString();

			if (!txt_m_tele_rotorsSpinning.getText().toString().equals(""))
				Data.m_tele_rotorsSpinning = Integer.parseInt(txt_m_tele_rotorsSpinning.getText().toString());

			Data.m_tele_ballRetrievalMethod = spn_m_tele_ballRetrievalMethod.getSelectedItem().toString();
			Data.m_tele_robotClimbStatus = spn_m_tele_robotClimbStatus.getSelectedItem().toString();
			Data.m_tele_airshipReadyForTakeoff = chk_m_tele_airshipReadyForTakeoff.isChecked();
			Data.m_comments = txt_m_comments.getText().toString();
			Data.m_overallStrategy = spn_m_overallStrategy.getSelectedItem().toString();

			if (!txt_m_redMatchPoints.getText().toString().equals(""))
				Data.m_redMatchPoints = Integer.parseInt(txt_m_redMatchPoints.getText().toString());
			if (!txt_m_blueMatchPoints.getText().toString().equals(""))
				Data.m_blueMatchPoints = Integer.parseInt(txt_m_blueMatchPoints.getText().toString());
			if (!txt_m_redRankingPoints.getText().toString().equals(""))
				Data.m_redRankingPoints = Integer.parseInt(txt_m_redRankingPoints.getText().toString());
			if (!txt_m_blueRankingPoints.getText().toString().equals(""))
				Data.m_blueRankingPoints = Integer.parseInt(txt_m_blueRankingPoints.getText().toString());
		}
		else if (scoutType.equals(Constants.PIT_SCOUT))
		{
			if (!txt_p_teamNumber.getText().toString().equals(""))
				Data.teamNumber = Integer.parseInt(txt_p_teamNumber.getText().toString());

			Data.p_teamName = txt_p_teamName.getText().toString();

			Data.p_drivetrainType = spn_p_driveTrainType.getSelectedItem().toString();

			Data.p_matchStrategy = txt_p_matchStrategy.getText().toString();
			Data.p_robotStrengths = txt_p_robotStrengths.getText().toString();
			Data.p_robotWeaknesses = txt_p_robotWeaknesses.getText().toString();
			Data.p_otherComments = txt_p_comments.getText().toString();
		}
	}

	private void resetFields()
	{
		if (scoutType.equals(Constants.MATCH_SCOUT))
		{
			txt_m_teamNumber.setText("");
			txt_m_matchNumber.setText("");
			spn_m_matchType.setSelection(0);
			opts_m_alliance.check(R.id.opt_m_alliance_red);
			spn_m_auto_gearStrategy.setSelection(0);
			np_m_auto_lowShots.setValue(0);
			np_m_auto_highShots.setValue(0);
			chk_m_auto_crossedBaseline.setChecked(false);
			np_m_tele_gearsPlaced.setValue(0);
			np_m_tele_lowShots.setValue(0);
			np_m_tele_highShots.setValue(0);
			spn_m_tele_percentShotsMissed.setSelection(0);
			txt_m_tele_rotorsSpinning.setText("");
			spn_m_tele_ballRetrievalMethod.setSelection(0);
			spn_m_tele_robotClimbStatus.setSelection(0);
			chk_m_tele_airshipReadyForTakeoff.setChecked(false);
			txt_m_comments.setText("");
			spn_m_overallStrategy.setSelection(0);
			txt_m_redMatchPoints.setText("");
			txt_m_blueMatchPoints.setText("");
			txt_m_redRankingPoints.setText("");
			txt_m_blueRankingPoints.setText("");
		}
		else if (scoutType.equals(Constants.PIT_SCOUT))
		{
			txt_p_teamNumber.setText("");
			txt_p_teamName.setText("");
			spn_p_driveTrainType.setSelection(0);
			txt_p_matchStrategy.setText("");
			txt_p_robotStrengths.setText("");
			txt_p_robotWeaknesses.setText("");
			txt_p_comments.setText("");
		}
	}

	public void requestVitalPermissions()
	{
		if (AppPrefs.protocolToUse.equals(Constants.SMS_PROT))
		{
			// Check for SMS send permission
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
			{
				if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
				{
					final Activity activityF = this;
					new AlertDialog.Builder(this)
							.setTitle("Permission Request")
							.setMessage("StormgearsScouting requires the SMS permission to be granted in order to" +
									" send the scouting data over SMS")
							.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
									// Ask for permission
									ActivityCompat.requestPermissions(activityF,
											new String[]{Manifest.permission.SEND_SMS},
											Constants.MY_PERMISSIONS_REQUEST_SMS_SEND);
								}
							})
							.setIcon(android.R.drawable.ic_dialog_alert)
							.show();
				}
				else
				{
					// Ask for permission
					ActivityCompat.requestPermissions(this,
							new String[]{Manifest.permission.SEND_SMS},
							Constants.MY_PERMISSIONS_REQUEST_SMS_SEND);
				}
			}

			// Check for SMS read permission
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
			{
				if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS))
				{
					final Activity activityF = this;
					new AlertDialog.Builder(this)
							.setTitle("Permission Request")
							.setMessage("StormgearsScouting requires the SMS permission to be granted in order to" +
									"read a response from the server to verify that the data has been sent")
							.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
									// Ask for permission
									ActivityCompat.requestPermissions(activityF,
											new String[]{Manifest.permission.READ_SMS},
											Constants.MY_PERMISSIONS_REQUEST_SMS_READ);
								}
							})
							.setIcon(android.R.drawable.ic_dialog_alert)
							.show();
				}
				else
				{
					// Ask for permission
					ActivityCompat.requestPermissions(this,
							new String[]{Manifest.permission.READ_SMS},
							Constants.MY_PERMISSIONS_REQUEST_SMS_READ);
				}
			}
		}
	}

	private boolean checkMissingItems()
	{
		// Error check for required items

		String missingItems = "";

		if (Data.teamNumber == -1)
			missingItems += "\nTeam Number";
		if (scoutType.equals(Constants.MATCH_SCOUT) && Data.m_matchNumber == -1)
			missingItems += "\nMatch Number";

		if (!missingItems.equals(""))
		{
			new AlertDialog.Builder(this)
					.setTitle("Missing Data")
					.setMessage("The following items are missing:\n" + missingItems)
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							// Do nothing
						}
					})
					.setIcon(android.R.drawable.ic_dialog_alert)
					.show();

			return false;
		}
		else
			return true;
	}
}
