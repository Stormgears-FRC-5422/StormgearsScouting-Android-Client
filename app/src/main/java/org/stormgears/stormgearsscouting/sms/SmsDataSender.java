package org.stormgears.stormgearsscouting.sms;

import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Sends SMS data
 */

public class SmsDataSender
{
	String phoneNumber;

	public SmsDataSender(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void sendSms(String message)
	{
		try
		{
			SmsManager smsManager = SmsManager.getDefault();
			ArrayList<String> messageParts = smsManager.divideMessage(message);

			smsManager.sendMultipartTextMessage(phoneNumber, null, messageParts, null, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
