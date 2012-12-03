package an.dpr.rubenslocatephone.broadcast;

import java.util.Calendar;

import an.dpr.rubenslocatephone.config.Settings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = BootBroadcastReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "boot, este si lo rulamooooos");
		SharedPreferences.Editor editor = Settings.getPreferences(context).edit();
		editor.putString("login", String.valueOf(Calendar.getInstance().getTimeInMillis()));
		editor.commit();
	}

}
