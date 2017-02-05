package org.stormgears.stormgearsscoutingv20.activity.scouting_activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.stormgears.stormgearsscoutingv20.R;
import org.stormgears.stormgearsscoutingv20.data.Data;
import org.stormgears.stormgearsscoutingv20.util.Constants;

import java.util.Arrays;

/**
 * Created by michael on 1/29/17.
 */

public class ScoutingActivity extends AppCompatActivity
{
	String scoutType;

	EditText         txt_m_teamNumber;
	EditText         txt_m_matchNumber;
	Spinner          spn_m_matchType;
	RadioGroup       opts_m_alliance;
	Spinner          spn_m_auto_gearStrategy;
	NumberPicker     np_m_auto_lowShots;
	NumberPicker     np_m_auto_highShots;
	CheckBox         chk_m_auto_crossedBaseline;
	NumberPicker     np_m_tele_gearsPlaced;
	NumberPicker     np_m_tele_lowShots;
	NumberPicker     np_m_tele_highShots;
	Spinner          spn_m_tele_percentShotsMissed;
	EditText         txt_m_tele_rotorsSpinning;
	Spinner          spn_m_tele_ballRetrievalMethod;
	Spinner          spn_m_tele_robotClimbStatus;
	CheckBox         chk_m_tele_airshipReadyForTakeoff;
	EditText         txt_m_comments;
	Spinner          spn_m_overallStrategy;
	EditText         txt_m_redMatchPoints;
	EditText         txt_m_blueMatchPoints;
	EditText         txt_m_redRankingPoints;
	EditText         txt_m_blueRankingPoints;

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
					} else
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
		}
		else if (scoutType.equals(Constants.PIT_SCOUT))
		{
			
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
			
		}
	}
	
	public void populateData()
	{
		if (scoutType.equals(Constants.MATCH_SCOUT))
		{
			if (!txt_m_teamNumber.getText().toString().equals(""))
				Data.teamNumber = Integer.parseInt(txt_m_teamNumber.getText().toString());
			if (!txt_m_matchNumber.getText().toString().equals(""))
				Data.m_matchNumber = Integer.parseInt(txt_m_matchNumber.getText().toString());

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

		}
	}
}
