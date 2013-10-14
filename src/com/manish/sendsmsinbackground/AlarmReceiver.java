package com.manish.sendsmsinbackground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	private final String SOMEACTION = "com.manish.alarm.ACTION";

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (SOMEACTION.equals(action)) {
			/**
			 * call method to send sms
			 */
			MainActivity.sendSms();

		}
	}

}