package an.dpr.rubenslocatephone.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = LocationBroadcastReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		Log.d(TAG, "recivido!!");
		if (extras != null){
			Location loc = (Location)extras.get(LocationManager.KEY_LOCATION_CHANGED);
			String msj = "long:"+loc.getLongitude()+", lat:"+loc.getLatitude();
			Log.d(TAG, msj);
			Toast.makeText(context, msj, Toast.LENGTH_LONG).show();
		} else {
			String msj = "no hay tema";
			Toast.makeText(context, msj, Toast.LENGTH_LONG).show();
		}

	}

}
