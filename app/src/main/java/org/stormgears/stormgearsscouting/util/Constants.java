package org.stormgears.stormgearsscouting.util;

/**
 * Constants
 */

public class Constants
{
	// Intent Extra Keys
	public static final String MATCH_SCOUTING_FORM_KEY = "org.stormgears.stormgearsscouting.match_form_contents";

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
	public static String[] DRIVETRAIN_TYPES;

	// SMS stuff
	public static final String SMS_PROT = "SMS";
	public static final String MSG_PREFIX = "STORMGEARS_SCOUT";

	// HTTPS stuff
	public static final String HTTPS_PROT = "HTTP[S]";
	public static final String CORRECT_SERV_RESPONSE = "OK";

	// Settings Stuff
	public static final String PREFS_NAME = "org.stormgears.stormgearsscouting.scoutingPreferences";

	public static final String PROTOCOL_TO_USE_KEY = "org.stormgears.stormgearsscouting.preferences.protocolToUse";
	public static final String EVENT_CODE_KEY = "org.stormgears.stormgearsscouting.preferences.eventCode";
	public static final String PASSWORD_KEY = "org.stormgears.stormgearsscouting.preferences.password";
	public static final String SERVER_URL_KEY = "org.stormgears.stormgearsscouting.preferences.serverUrl";
	public static final String SMS_PHONE_NUMBER_KEY = "org.stormgears.stormgearsscouting.preferences.phoneNumber";

	// Default settings
	public static final String DEFAULT_PASSWORD = "horses";
	public static final String DEFAULT_EVENT_CODE = "xxxxx2017";
	public static final String DEFAULT_PROTOCOL_TO_USE = HTTPS_PROT;
	public static final String DEFAULT_SERVER_URL = "none";
	public static final String DEFAULT_SMS_PHONE_NUMBER = "none";

	// Permission request codes
	public static final int MY_PERMISSIONS_REQUEST_INTERNET = 1111;
	public static final int MY_PERMISSIONS_REQUEST_SMS_SEND = 2222;
	public static final int MY_PERMISSIONS_REQUEST_SMS_READ = 3333;

	// Locally saved scouting protobufs key
	public static final String SAVED_PROTOBUFS_KEY = "org.stormgears.stormgearsscouting.preferences.savedProtobufs";

}
