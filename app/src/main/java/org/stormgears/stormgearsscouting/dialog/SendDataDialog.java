package org.stormgears.stormgearsscouting.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.stormgears.stormgearsscouting.R;
import org.stormgears.stormgearsscouting.data.Data;
import org.stormgears.stormgearsscouting.util.AppPrefs;
import org.stormgears.stormgearsscouting.util.Constants;
import org.stormgears.stormgearsscouting.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 2/21/17.
 */

@SuppressLint("NewApi")
public class SendDataDialog extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_send_cached);
		dialog.setTitle(R.string.send_cached_title);

		ListView listCachedData = (ListView) dialog.findViewById(R.id.list_cached_data_entries);
		List<String> timeStampList = new ArrayList<>();
		final List<String> dataList = new ArrayList<>();

		String cachedData = Utils.sharedPreferences.getString(Constants.SAVED_PROTOBUFS_KEY, "");
		String[] cachedDataArray = cachedData.split("!---!");
		System.out.println(cachedDataArray.length);

		for (int i = 0; i < cachedDataArray.length; i++)
		{
			System.out.println(cachedDataArray[i]);
			if (!cachedDataArray[i].equals(""))
			{
				timeStampList.add("Data entry on: " + cachedDataArray[i].substring(0, cachedDataArray[i].indexOf("!!!")));
				dataList.add(cachedDataArray[i].substring(cachedDataArray[i].indexOf("!!!") + 3));
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, timeStampList);
		listCachedData.setAdapter(adapter);

		listCachedData.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String data = dataList.get(position);
				Data.sendData(AppPrefs.protocolToUse, data, getActivity());
				System.out.println("sending data: " + data);
			}
		});

		return dialog;
	}
}
