package an.dpr.rubenslocatephone.broadcast;

import an.dpr.rubenslocatephone.network.RubensService;
import an.dpr.rubenslocatephone.util.LocatePhoneContract;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = SMSBroadcastReceiver.class.getName();

	@Override
	public void onReceive(Context ctx, Intent intent){
		Log.d(TAG, "SMS entrando");
		RubensService.startService(ctx);
	}
	
}
