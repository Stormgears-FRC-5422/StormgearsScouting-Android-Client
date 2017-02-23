package org.stormgears.stormgearsscouting.data;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.stormgears.stormgearsscouting.sms.SmsDataSender;
import org.stormgears.stormgearsscouting.util.AppPrefs;
import org.stormgears.stormgearsscouting.util.BaseX;
import org.stormgears.stormgearsscouting.util.Constants;
import org.stormgears.stormgearsscouting.util.Utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 1/24/17.
 */

public class Data
{
	private static ScoutingData.Event.Builder scoutingData;

	public static String scoutType;
	public static int teamNumber = -1;
	public static int m_matchNumber = -1;
	public static String m_matchType = "";
	public static String m_allianceColor = "";
	public static String m_auto_gearStrategy = "";
	public static int m_auto_lowShots = -1;
	public static int m_auto_highShots = -1;
	private static int m_auto_percentShotsMissed = -1;
	public static boolean m_auto_crossedBaseline = false;
	public static int m_tele_gearsPlaced = -1;
	public static int m_tele_lowShots = -1;
	public static int m_tele_highShots = -1;
	public static String m_tele_percentShotsMissed = "";
	public static int m_tele_rotorsSpinning = -1;
	public static String m_tele_ballRetrievalMethod = "";
	public static String m_tele_robotClimbStatus = "";
	public static boolean m_tele_airshipReadyForTakeoff = false;
	public static String m_comments = "";
	public static String m_overallStrategy = "";
	public static int m_redMatchPoints = -1;
	public static int m_blueMatchPoints = -1;
	public static int m_redRankingPoints = -1;
	public static int m_blueRankingPoints = -1;
	public static String p_teamName = "";
	public static String p_drivetrainType = "";
	public static String p_matchStrategy = "";
	public static String p_robotStrengths = "";
	public static String p_robotWeaknesses = "";
	public static String p_otherComments = "";

	public static void resetMatchScoutingData()
	{
		m_matchNumber = -1;
		m_matchType = "";
		m_allianceColor = "";
		m_auto_gearStrategy = "";
		m_auto_lowShots = -1;
		m_auto_highShots = -1;
		m_auto_percentShotsMissed = -1;
		m_auto_crossedBaseline = false;
		m_tele_gearsPlaced = -1;
		m_tele_lowShots = -1;
		m_tele_highShots = -1;
		m_tele_percentShotsMissed = "";
		m_tele_rotorsSpinning = -1;
		m_tele_ballRetrievalMethod = "";
		m_tele_robotClimbStatus = "";
		m_tele_airshipReadyForTakeoff = false;
		m_comments = "";
		m_overallStrategy = "";
		m_redMatchPoints = -1;
		m_blueMatchPoints = -1;
		m_redRankingPoints = -1;
		m_blueRankingPoints = -1;
	}

	public static void resetPitScoutingData()
	{
		p_teamName = "";
		p_drivetrainType = "";
		p_matchStrategy = "";
		p_robotStrengths = "";
		p_robotWeaknesses = "";
		p_otherComments = "";
	}

	public static void calculateAutoPercentShotsMissed()
	{
		m_auto_percentShotsMissed = Math.round(((float) m_auto_lowShots + m_auto_highShots) / 20 * 100);
	}

	private static void prepareProtobuf()
	{
		scoutingData = ScoutingData.Event.newBuilder();

		scoutingData.setPassword(AppPrefs.password);
		scoutingData.setEventCode(AppPrefs.eventCode);
		scoutingData.setScoutType(scoutType);
		scoutingData.setMMatchNumber(m_matchNumber);
		scoutingData.setMMatchType(m_matchType);
		scoutingData.setMAllianceColor(m_allianceColor);
		scoutingData.setMAutoGearStrategy(m_auto_gearStrategy);
		scoutingData.setMAutoLowShots(m_auto_lowShots);
		scoutingData.setMAutoHighShots(m_auto_highShots);
		scoutingData.setMAutoPercentShotsMissed(m_auto_percentShotsMissed);
		scoutingData.setMAutoCrossedBaseline(m_auto_crossedBaseline);
		scoutingData.setMTeleGearsPlaced(m_tele_gearsPlaced);
		scoutingData.setMTeleLowShots(m_tele_lowShots);
		scoutingData.setMTeleHighShots(m_tele_highShots);
		scoutingData.setMTelePercentShotsMissed(m_tele_percentShotsMissed);
		scoutingData.setMTeleRotorsSpinning(m_tele_rotorsSpinning);
		scoutingData.setMTeleBallRetrievalMethod(m_tele_ballRetrievalMethod);
		scoutingData.setMTeleRobotClimbStatus(m_tele_robotClimbStatus);
		scoutingData.setMTeleAirshipReadyForTakeoff(m_tele_airshipReadyForTakeoff);
		scoutingData.setMComments(m_comments);
		scoutingData.setMOverallStrategy(m_overallStrategy);
		scoutingData.setMRedMatchPoints(m_redMatchPoints);
		scoutingData.setMBlueMatchPoints(m_blueMatchPoints);
		scoutingData.setMRedRankingPoints(m_redRankingPoints);
		scoutingData.setMBlueRankingPoints(m_blueRankingPoints);
		scoutingData.setPTeamName(p_teamName);
		scoutingData.setPDrivetraintype(p_drivetrainType);
		scoutingData.setPMatchStrategy(p_matchStrategy);
		scoutingData.setPRobotStrengths(p_robotStrengths);
		scoutingData.setPRobotWeaknesses(p_robotWeaknesses);
		scoutingData.setPOtherComments(p_otherComments);
	}

	public static String getAsString()
	{
		prepareProtobuf();
		return new String(scoutingData.build().toByteArray());
	}

	public static byte[] getBytes()
	{
		prepareProtobuf();
		return scoutingData.build().toByteArray();
	}

	public static void sendData(String protocol, String data, Activity activity)
	{
		if (protocol.equals(Constants.HTTPS_PROT))  // INTERNET permission is already given to app by Android
		{
			final String content = data;

			// Start a new HTTP[S] POST request
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						System.out.println("Sending data... to: " + AppPrefs.serverUrl);

						// Send data to server
						HttpURLConnection connection = (HttpURLConnection) new URL(AppPrefs.serverUrl).openConnection();
						connection.setDoOutput(true);

						connection.setRequestMethod("POST");
						connection.setRequestProperty("Content-Type", "application/x-protobuf");

						DataOutputStream dOutput = new DataOutputStream(connection.getOutputStream());
						dOutput.writeBytes(content);
						dOutput.close();

						// Handle response from server
						BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String response = br.readLine();

						System.out.println(response);
					}
					catch (Exception e)
					{
						Log.e("[Internet Error]", e.toString());
						// TODO: Properly handle error in sending
					}
				}
			}).start();
		}
		else if (AppPrefs.protocolToUse.equals(Constants.SMS_PROT))
		{
			if (AppPrefs.pSmsSend && AppPrefs.pSmsRead)
			{
				byte[] dataOut = data.getBytes();
				BaseX baseX = new BaseX();
				String encoded = baseX.encode(dataOut);

				SmsDataSender smsDataSender = new SmsDataSender(AppPrefs.phoneNumber);
				smsDataSender.sendSms(Constants.MSG_PREFIX + encoded);
			}
			else
			{
				final Activity activityF = activity;
				new AlertDialog.Builder(activity)
						.setTitle("Error Sending SMS")
						.setMessage("Please grant StormgearsScouting permission to send/receive SMSs in order to send" +
								" data over the SMS protocol. Be sure to tap 'Send Form' again once the permission has" +
								" been granted.")
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Ask for permission
								ActivityCompat.requestPermissions(activityF,
										new String[]{Manifest.permission.SEND_SMS},
										Constants.MY_PERMISSIONS_REQUEST_SMS_SEND);
							}
						})
						.setIcon(android.R.drawable.ic_dialog_alert)
						.show();
			}
		}
	}

	public static void saveDataLocally()
	{
		// Get a timestamp
		SimpleDateFormat s = new SimpleDateFormat("MM/dd/YY HH:MM:SS");
		String format = s.format(new Date());

		SharedPreferences.Editor editor = Utils.sharedPreferences.edit();
		String saved = Utils.sharedPreferences.getString(Constants.SAVED_PROTOBUFS_KEY, "");
		String toSave = format + "!!!" + getAsString() + "!---!";
		editor.putString(Constants.SAVED_PROTOBUFS_KEY, saved + toSave);
		editor.commit();
	}
}
