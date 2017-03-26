package org.stormgears.stormgearsscouting.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.stormgears.stormgearsscouting.R;
import org.stormgears.stormgearsscouting.data.Data;
import org.stormgears.stormgearsscouting.util.AppPrefs;
import org.stormgears.stormgearsscouting.util.Constants;
import org.stormgears.stormgearsscouting.util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Dialog that allows the user to send saved (cached) data
 */

@SuppressLint("NewApi")
public class SendDataDialog extends DialogFragment
{

	final List<String> timeStampList = new ArrayList<>();
	final List<String> dataList = new ArrayList<>();
	final Iterator<String> iterator = dataList.iterator();
	final List<String> timeAndData = new ArrayList<>();
	ArrayAdapter<String> adapter;

	String currentTimeStamp;

	boolean finished = true;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_send_cached);
		dialog.setCancelable(false);

		Button btn_sendCached_done = (Button) dialog.findViewById(R.id.btn_send_cached_done);
		btn_sendCached_done.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dialog.dismiss();
			}
		});

		ListView listCachedData = (ListView) dialog.findViewById(R.id.list_cached_data_entries);

		String cachedData = Utils.sharedPreferences.getString(Constants.SAVED_PROTOBUFS_KEY, "");
		String[] cachedDataArray = cachedData.split("!---!");

		// Prepare lists
		for (String aCachedDataArray : cachedDataArray)
		{
			if (!aCachedDataArray.equals(""))
			{
				timeStampList.add(aCachedDataArray.substring(0, aCachedDataArray.indexOf("!!!")));
				dataList.add(aCachedDataArray.substring(aCachedDataArray.indexOf("!!!") + 3));
				timeAndData.add(aCachedDataArray);
			}
		}

		adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, timeStampList);
		listCachedData.setAdapter(adapter);

		listCachedData.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final int positionF = position;

				sendData(positionF);

			}
		});

		return dialog;
	}

	private void sendData(final int position)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (!checkFinished())
				{
					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					System.out.println("waiting for write to shared to finish");
				}

				Data.sendData(AppPrefs.protocolToUse, dataList.get(position), getActivity(), true);

				int sleeps = 0;
				// Sleep the thread for 0.25 seconds repeating until data has been sent successfully
				// or the loop has run 120 times (30 seconds)
				while (!Utils.dataSent && sleeps < 120)
				{
					try
					{
						Thread.sleep(250);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					if (Utils.dataFailed)
						return;

					sleeps++;
				}

				getActivity().runOnUiThread(new Runnable()
				{
					public void run()
					{
						setFinished(false);

						adapter.remove(timeStampList.get(position));
						adapter.notifyDataSetChanged();

						timeAndData.remove(position);

						dataList.remove(position);

						updateCachedData();

						setFinished(true);
					}
				});
			}
		}).start();
	}

	private void updateCachedData()
	{
		SharedPreferences.Editor editor = Utils.sharedPreferences.edit();
		String toSave = "";

		for (String data : timeAndData)
			toSave += (data + "!---!");

		editor.putString(Constants.SAVED_PROTOBUFS_KEY, toSave);
		editor.commit();
	}

	private void setFinished(boolean finished)
	{
		this.finished = finished;
	}

	private boolean checkFinished()
	{
		return finished;
	}
}
