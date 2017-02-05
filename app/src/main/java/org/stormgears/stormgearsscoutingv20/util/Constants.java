package org.stormgears.stormgearsscoutingv20.util;

/**
 * Created by michael on 1/12/17.
 */

public class Constants
{
	// Intent Extra Keys
	public static final String MATCH_SCOUTING_FORM_KEY = "org.stormgears.stormgearsscoutingv20.match_form_contents";

	// Scouting Types
	public static final String MATCH_SCOUT = "match";
	public static final String PIT_SCOUT = "pit";

	// String arrays
	public static String[] MATCH_TYPES;
	public static String[] AUTO_GEAR_STRATEGIES;
	public static String[] TELE_SHOTS_MISSED;
	public static String[] TELE_BALL_RETRIEVAL_METHOD;
	public static String[] TELE_ROBOT_CLIMB_STATUS;
	public static String[] MATCH_STRATEGIES;

	public static enum connection_method
	{
		SMS,
		POST
	}

	// SMS stuff
	public static final String SMS_PROT = "sms";
	public static final String PHONE_NUMBER = "";
	public static final int SMS_CHAR_LIMIT = 160;
	public static final String MSG_PREFIX = "STORMGEARS_SCOUT";

	// HTTPS stuff
	public static final String HTTPS_PROT = "https";

	// Settings Stuff
	public static final String PREFS_NAME = "org.stormgears.stormgearsscoutingv20.scoutingPreferences";

	public static final String PROTOCOL_TO_USE_KEY = "org.stormgears.stormgearsscoutingv20.preferences.protocolToUse";
	public static final String EVENT_CODE_KEY = "org.stormgears.stormgearsscoutingv20.preferences.eventCode";
	public static final String PASSWORD_KEY = "org.stormgears.stormgearsscoutingv20.preferences.password";
	public static final String SERVER_URL_KEY = "org.stormgears.stormgearsscoutingv20.preferences.serverUrl";
	public static final String SMS_PHONE_NUMBER_KEY = "org.stormgears.stormgearsscoutingv20.preferences.phoneNumber";


	public static final String DEFAULT_PASSWORD = "horses";
	public static final String DEFAULT_EVENT_CODE = "xxxxx2017";
	public static final String DEFAULT_PROTOCOL_TO_USE = HTTPS_PROT;
	public static final String DEFAULT_SERVER_URL = "https://asdf.ghjkl.zxcvbnm.com";
	public static final String DEFAULT_SMS_PHONE_NUMBER = "(000) 000 0000";



}
