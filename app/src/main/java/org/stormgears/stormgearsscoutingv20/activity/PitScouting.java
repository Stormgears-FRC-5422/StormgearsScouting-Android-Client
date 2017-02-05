package org.stormgears.stormgearsscoutingv20.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.stormgears.stormgearsscoutingv20.R;
import org.stormgears.stormgearsscoutingv20.activity.scouting_activity.ScoutingActivity;
import org.stormgears.stormgearsscoutingv20.data.Scoutingdata;
import org.stormgears.stormgearsscoutingv20.sms.SmsDataSender;
import org.stormgears.stormgearsscoutingv20.util.BaseX;
import org.stormgears.stormgearsscoutingv20.util.Constants;

public class PitScouting extends ScoutingActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pit_scouting);
		setTitle(R.string.pit_scouting_activity_name);

		setScoutType(Constants.PIT_SCOUT);

		initialize();
	}

	@Override
	protected void onStart()
	{
		super.onStart();

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
		super.onStop();

		populateData();
	}

	public void onClickSend(View view)
	{
		populateData();

		Scoutingdata.Event.Builder scoutingData = Scoutingdata.Event.newBuilder();

		byte[] dataOut = scoutingData.build().toByteArray();
		BaseX baseX = new BaseX();
		String encoded = baseX.encode(dataOut);

		SmsDataSender smsDataSender = new SmsDataSender(Constants.PHONE_NUMBER);
		smsDataSender.sendSms(Constants.MSG_PREFIX + encoded);
	}
}
