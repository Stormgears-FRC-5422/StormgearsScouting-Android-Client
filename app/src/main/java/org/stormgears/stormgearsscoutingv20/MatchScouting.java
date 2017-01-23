package org.stormgears.stormgearsscoutingv20;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

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

		final NumberPicker npAutoLowShots = (NumberPicker) findViewById(R.id.np_auto_low_shots);
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

		final NumberPicker npAutoHighShots = (NumberPicker) findViewById(R.id.np_auto_high_shots);
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
		Context context = MatchScouting.this;
		Intent intent = new Intent(context, BluetoothSender.class);
		intent.putExtra(Constants.MATCH_SCOUTING_FORM_KEY, txtTest.getText() + "");
		context.startActivity(intent);
	}
}
