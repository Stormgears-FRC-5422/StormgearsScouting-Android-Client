package org.stormgears.stormgearsscoutingv20.util;

import android.content.SharedPreferences;

/**
 * Extra public methods
 */

public class Utils
{
	public static void initializePrefs(SharedPreferences sharedPreferences)
	{
		AppPrefs.protocolToUse = sharedPreferences.getString(Constants.PROTOCOL_TO_USE_KEY, Constants.DEFAULT_PROTOCOL_TO_USE);
		AppPrefs.password      = sharedPreferences.getString(Constants.PASSWORD_KEY, Constants.DEFAULT_PASSWORD);
		AppPrefs.eventCode     = sharedPreferences.getString(Constants.EVENT_CODE_KEY, Constants.DEFAULT_EVENT_CODE);
		AppPrefs.serverUrl     = sharedPreferences.getString(Constants.SERVER_URL_KEY, Constants.DEFAULT_SERVER_URL);
		AppPrefs.phoneNumber   = sharedPreferences.getString(Constants.SMS_PHONE_NUMBER_KEY, Constants.DEFAULT_SMS_PHONE_NUMBER);
	}
}
