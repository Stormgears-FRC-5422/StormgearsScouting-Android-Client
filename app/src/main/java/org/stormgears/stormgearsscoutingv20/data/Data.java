package org.stormgears.stormgearsscoutingv20.data;

/**
 * Created by michael on 1/24/17.
 */

public class Data
{
	public static String   password;
	public static String   eventCode;
	public static String   scoutType;
	public static int      teamNumber = -1;
	public static int      m_matchNumber = -1;
	public static String   m_matchType = "";
	public static String   m_allianceColor = "";
	public static String   m_auto_gearStrategy = "";
	public static int      m_auto_lowShots = -1;
	public static int      m_auto_highShots = -1;
	public static int      m_auto_percentShotsMissed = -1;
	public static boolean  m_auto_crossedBaseline = false;
	public static int      m_tele_gearsPlaced = -1;
	public static int      m_tele_lowShots = -1;
	public static int      m_tele_highShots = -1;
	public static String   m_tele_percentShotsMissed = "";
	public static int      m_tele_rotorsSpinning = -1;
	public static String   m_tele_ballRetrievalMethod = "";
	public static String   m_tele_robotClimbStatus = "";
	public static boolean  m_tele_airshipReadyForTakeoff = false;
	public static String   m_comments = "";
	public static String   m_overallStrategy = "";
	public static int      m_redMatchPoints = -1;
	public static int      m_blueMatchPoints = -1;
	public static int      m_redRankingPoints = -1;
	public static int      m_blueRankingPoints = -1;
	public static String   p_teamName = "";
	public static String   p_drivetrainType = "";
	public static String   p_matchStrategy = "";
	public static String   p_robotStrengths = "";
	public static String   p_robotWeaknesses = "";
	public static String   p_otherComments = "";

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
		m_auto_percentShotsMissed = Math.round(( (float) m_auto_lowShots + m_auto_highShots) / 20 * 100);
	}
}
