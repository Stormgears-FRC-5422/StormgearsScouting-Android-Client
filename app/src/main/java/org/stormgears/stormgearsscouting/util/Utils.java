package org.stormgears.stormgearsscouting.util;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Extra public methods
 */

public class Utils
{
	public static SharedPreferences sharedPreferences;
	public static Activity activity;

	public static void setSharedPreferences(SharedPreferences shared)
	{
		sharedPreferences = shared;
	}

	public static void initializePrefs()
	{
		AppPrefs.protocolToUse = sharedPreferences.getString(Constants.PROTOCOL_TO_USE_KEY, Constants.DEFAULT_PROTOCOL_TO_USE);
		AppPrefs.password      = sharedPreferences.getString(Constants.PASSWORD_KEY, Constants.DEFAULT_PASSWORD);
		AppPrefs.eventCode     = sharedPreferences.getString(Constants.EVENT_CODE_KEY, Constants.DEFAULT_EVENT_CODE);
		AppPrefs.serverUrl     = sharedPreferences.getString(Constants.SERVER_URL_KEY, Constants.DEFAULT_SERVER_URL);
		AppPrefs.phoneNumber   = sharedPreferences.getString(Constants.SMS_PHONE_NUMBER_KEY, Constants.DEFAULT_SMS_PHONE_NUMBER);

		AppPrefs.pInternet     = ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
		AppPrefs.pSmsSend      = ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
		AppPrefs.pSmsRead      = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
	}
}
