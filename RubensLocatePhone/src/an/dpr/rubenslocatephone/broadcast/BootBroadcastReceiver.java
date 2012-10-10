package an.dpr.rubenslocatephone.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = BootBroadcastReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "este si lo rulamooooos");
		
	}

}
