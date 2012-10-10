package an.dpr.rubenslocatephone.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = SMSBroadcastReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "este si lo rulamooooos");
	}

}
