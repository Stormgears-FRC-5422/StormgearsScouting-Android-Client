package org.stormgears.stormgearsscoutingv20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.stormgears.stormgearsscoutingv20.data.Scoutingdata;
import org.stormgears.stormgearsscoutingv20.sms.SmsDataSender;
import org.stormgears.stormgearsscoutingv20.util.BaseX;

public class MatchScouting extends AppCompatActivity
{
	TextView txtTest;

	int autoLowShots = 0, autoHighShots = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_scouting);

		setTitle(R.string.match_scouting_activity_name);

		final NumberPicker npAutoLowShots = (NumberPicker) findViewById(R.id.np_m_auto_low_shots);
		npAutoLowShots.setMinValue(0);
		npAutoLowShots.setMaxValue(10);
		npAutoLowShots.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal)
			{
				autoLowShots = newVal;
			}
		});

		final NumberPicker npAutoHighShots = (NumberPicker) findViewById(R.id.np_m_auto_high_shots);
		npAutoHighShots.setMinValue(0);
		npAutoHighShots.setMaxValue(10);
		npAutoHighShots.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal)
			{
				autoHighShots = newVal;
			}
		});
	}

	public void onClickSend(View view)
	{
//		Context context = MatchScouting.this;
//		Intent intent = new Intent(context, BluetoothSender.class);
//		intent.putExtra(Constants.MATCH_SCOUTING_FORM_KEY, txtTest.getText() + "");
//		context.startActivity(intent);

		Scoutingdata.Event.Builder scoutingData = Scoutingdata.Event.newBuilder();

		scoutingData.setPassword("***REMOVED***");
		scoutingData.setEventCode("nhwin2017");
		scoutingData.setScoutType("match");
		scoutingData.setTeamNumber(5422);
		scoutingData.setMMatchNumber(1);
		scoutingData.setMMatchType("qual");
		scoutingData.setMAllianceColor("Red");
		scoutingData.setMAutoGearStrategy("Placed Gear");
		scoutingData.setMAutoLowShots(0);
		scoutingData.setMAutoHighShots(10);
		scoutingData.setMAutoPercentShotsMissed(0);
		scoutingData.setMAutoCrossedBaseline(true);
		scoutingData.setMTeleGearsPlaced(5);
		scoutingData.setMTeleLowShots(30);
		scoutingData.setMTeleHighShots(60);
		scoutingData.setMTelePercentShotsMissed("Some");
		scoutingData.setMTeleRotorsSpinning(3);
		scoutingData.setMTeleBallRetrievalMethod("Retrieves Balls from Field");
		scoutingData.setMTeleRobotClimbStatus("Climbed Rope and Activated Touchpad");
		scoutingData.setMTeleAirshipReadyForTakeoff(false);
		scoutingData.setMComments("They missed some of their shots, but they are really good at gears.");
		scoutingData.setMRedMatchPoints(144);
		scoutingData.setMBlueMatchPoints(56);
		scoutingData.setMRedRankingPoints(3);
		scoutingData.setMBlueRankingPoints(0);

		byte[] dataOut = scoutingData.build().toByteArray();
		BaseX baseX = new BaseX();
		String encoded = baseX.encode(dataOut);

		SmsDataSender smsDataSender = new SmsDataSender(Constants.PHONE_NUMBER);
		smsDataSender.sendSms(Constants.MSG_PREFIX + encoded);

	}
}
