package org.stormgears.stormgearsscoutingv20.sms;

import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by michael on 1/24/17.
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
