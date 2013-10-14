package com.manish.sendsmsinbackground;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author manish
 * 
 */

public class MainActivity extends Activity {

	EditText editTextPhone, editTextMessage;
	Button btnSubmit, btnStart;
	String phoneNumber, message;
	static HashMap<String, String> recorArray = null;
	Context context = MainActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * get id's fro xml
		 */
		editTextPhone = (EditText) findViewById(R.id.editText1);
		editTextMessage = (EditText) findViewById(R.id.editText2);
		btnSubmit = (Button) findViewById(R.id.button1);
		btnStart = (Button) findViewById(R.id.button2);
		/**
		 * create object of hashmap
		 */
		recorArray = new HashMap<String, String>();
		/**
		 * button's onclick listner object
		 */
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phoneNumber = editTextPhone.getText().toString();
				message = editTextMessage.getText().toString();
				recorArray.put(phoneNumber, message);
			}
		});

		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(recorArray);
				fireAlarm();

			}
		});

	}

	/**
	 * send sms method
	 */
	public static void sendSms() {

		Set<?> set = recorArray.entrySet();
		// Get an iterator
		Iterator<?> i = set.iterator();
		// Display elements
		while (i.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry) i.next();
			System.out.print(me.getKey() + ": ");
			System.out.println(me.getValue());

			try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(me.getKey().toString(), null, me
						.getValue().toString(), null, null);
				System.out.println("message sent");
			} catch (Exception e) {
				System.out.println("sending failed!");
				e.printStackTrace();
			}
		}
	}

	public void fireAlarm() {
		/**
		 * call broadcost reciver for send sms
		 */
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.setAction("com.manish.alarm.ACTION");
		PendingIntent pendingIntent = PendingIntent
				.getBroadcast(MainActivity.this, 0, intent,
						PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		AlarmManager alarm = (AlarmManager) MainActivity.this
				.getSystemService(Context.ALARM_SERVICE);
		alarm.cancel(pendingIntent);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				500000, pendingIntent);
	}
}
