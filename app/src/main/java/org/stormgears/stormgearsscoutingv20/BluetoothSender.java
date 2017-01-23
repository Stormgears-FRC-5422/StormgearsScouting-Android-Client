package org.stormgears.stormgearsscoutingv20;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothSender extends AppCompatActivity
{
	TextView out;
	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter btAdapter;
	private BluetoothSocket btSocket;
	private OutputStream outStream;

	// Well known SPP UUID
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private static String address = "F4:5C:89:90:3B:98";

	/**
	 * Called when the activity is first created.
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_sender);

		out = (TextView) findViewById(R.id.txt_log);

		out.append("\n...In onCreate()...");

		btAdapter = BluetoothAdapter.getDefaultAdapter();
		CheckBTState();
	}

	public void onStart()
	{
		super.onStart();
		out.append("\n...In onStart()...");
	}

	public void onResume()
	{
		super.onResume();

		Bundle extras = getIntent().getExtras();

		out.append("\n...In onResume...\n...Attempting client connect...");

		// Set up a pointer to the remote node using it's address.
		BluetoothDevice device = btAdapter.getRemoteDevice(address);

		// Two things are needed to make a connection:
		//   A MAC address, which we got above.
		//   A Service ID or UUID.  In this case we are using the UUID for SPP.
		try
		{
			btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
		}
		catch (IOException e)
		{
			AlertBox("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
		}

		// Discovery is resource intensive.  Make sure it isn't going on
		// when you attempt to connect and pass your message.
		btAdapter.cancelDiscovery();

		// Establish the connection.  This will block until it connects.
		try
		{
			btSocket.connect();
			out.append("\n...Connection established and data link opened...");
		}
		catch (IOException e)
		{
			try
			{
				btSocket.close();
			}
			catch (IOException e2)
			{
				AlertBox("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
			}
		}

		// Create a data stream so we can talk to server.
		out.append("\n...Sending message to server...");

		String message;
		if (extras != null)
			message = extras.getString(Constants.MATCH_SCOUTING_FORM_KEY);
		else
			message = "no_data";

		System.out.println(message);
		out.append("\n\n...The message that we will send to the server is: " + message);

		try
		{
			outStream = btSocket.getOutputStream();
		}
		catch (IOException e)
		{
			AlertBox("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
		}

		byte[] msgBuffer = message.getBytes();
		try
		{
			outStream.write(msgBuffer);
		}
		catch (IOException e)
		{
			String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
			if (address.equals("00:00:00:00:00:00"))
				msg = msg + ".\n\nUpdate your server address in Settings.";
			msg = msg + ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";

			AlertBox("Fatal Error", msg);
		}
	}

	public void onPause()
	{
		super.onPause();

		//out.append("\n...Hello\n");
		InputStream inStream;
		try
		{
			inStream = btSocket.getInputStream();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
			String lineRead = bReader.readLine();
			out.append("\n..." + lineRead + "\n");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		out.append("\n...In onPause()...");

		if (outStream != null)
		{
			try
			{
				outStream.flush();
			}
			catch (IOException e)
			{
				AlertBox("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
			}
		}

		try
		{
			btSocket.close();
		}
		catch (IOException e2)
		{
			AlertBox("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
		}
	}

	public void onStop()
	{
		super.onStop();
		out.append("\n...In onStop()...");
	}

	public void onDestroy()
	{
		super.onDestroy();
		out.append("\n...In onDestroy()...");
	}

	private void CheckBTState()
	{
		// Check for Bluetooth support and then check to make sure it is turned on
		// Emulator doesn't support Bluetooth and will return null
		if (btAdapter == null)
		{
			AlertBox("Fatal Error", "Bluetooth Not supported. Aborting.");
		}
		else
		{
			if (btAdapter.isEnabled())
				out.append("\n...Bluetooth is enabled...");
			else
			{
				// Prompt user to turn on Bluetooth
				Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}
	}

	public void AlertBox(String title, String message)
	{
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(message + " Press OK to exit.")
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface arg0, int arg1)
					{
						finish();
					}
				}).show();
	}
}