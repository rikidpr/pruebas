package an.dpr.rubenslocatephone.broadcast;

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
		//sacar esto a un servicio para no torpedear el mainThread
		Intent locIntent = new Intent("an.dpr.accion.rubenslocatephone");
		PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, locIntent, 0/*PendingIntent.FLAGS?*/);
		LocationManager locManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
		SharedPreferences pref = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE);
		String provider = LocationManager.GPS_PROVIDER;//"NETWORK_PROVIDER";
		//esto nos lanzara el broadcast con extras key KEY_LOCATION_CHANGED and value LOCATION
		locManager.requestLocationUpdates(provider, getMinTime(pref), getMinDistance(pref), pi);
//		//tambien para el network provider
//		provider = LocationManager.NETWORK_PROVIDER;//"NETWORK_PROVIDER";
//		locManager.requestLocationUpdates(provider, getMinTime(pref), getMinDistance(pref), pi);
	}
	
	private int getMinTime(SharedPreferences pref){
		int retValue = pref.getInt("minTime", LocatePhoneContract.MIN_TIME_DEFAULT);
		retValue = retValue * 60 * 1000; //minutos a milisec
		Log.d(TAG, "tiempo minimo:"+retValue);
		return 1000;//retValue;
	}
	
	
	private int getMinDistance(SharedPreferences pref){
		int retValue = pref.getInt("minDistance", LocatePhoneContract.MIN_DISTANCE_DEFAULT);
		Log.d(TAG, "metros minimo:"+retValue);
		return retValue;
	}

}
