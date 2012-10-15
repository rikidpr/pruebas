package an.dpr.rubenslocatephone.broadcast;

import an.dpr.rubenslocatephone.network.RubensService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = LocationBroadcastReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		Log.d(TAG, "recibido!!");
		if (extras != null
			&& extras.containsKey(Context.LOCATION_SERVICE)) {
			Location loc = (Location)extras.get(Context.LOCATION_SERVICE);
			//TODO lanzar RubensService
			String msj = "long:"+(loc!=null ? loc.getLongitude(): "-")+", lat:"+(loc!=null ? loc.getLatitude(): "-");
			Log.d(TAG, msj);
			Toast.makeText(context, msj, Toast.LENGTH_LONG).show();
			lanzarRequest(context, loc);
		} else {
			String msj = "no hay tema";
			Toast.makeText(context, msj, Toast.LENGTH_LONG).show();
		}

	}

	private void lanzarRequest(final Context context, final Location loc) {
		AsyncTask<String, Integer, String> at = new AsyncTask<String, Integer, String>(){

			@Override
			protected String doInBackground(String... params) {
				String url = RubensService.URL_RUBENS_JSON;
				String retValue = RubensService.leerInfo(context, url, loc);
				return retValue;
			}
			
			@Override
			protected void onPostExecute(String result){
				Log.d(TAG, result);
			}
    		
    	};
    	at.execute("epei");
	}

}


/*new method: 
1-broadcastReceiver BOOT que lance un service
2-El service genera un pendingIntent con la accion "pillarGPSLocate" que lanza un broadcastreceiver
3.-Segun hayamos configurado, nuestro broadcastreceiver recibira la info y la mandara pa la weba

Manifest:
<receiver android:name="claseReceiverBootQueLanzaElTema">
	<intent-filter>
		<action = accion de boot>
	</intent-filter>
</receiver>

<receiver android:name="RubensLocationBR" android:enable=true>
	<intent-filter>
		<action android:name="an.dpr.accion.rubenslocatephone">
	</intent-filter>
</receiver>
*/
//public class LocationBroadcastReceiver extends BroadcastReceiver {
//
//	private static final String TAG = LocationBroadcastReceiver.class.getName();
//
//	@Override
//	public void onReceive(Context ctx, Intent intent){
//		Log.d(TAG, "pillamos el boot");
//		//sacar esto a un servicio para no torpedear el mainThread
//		Intent locIntent = new Intent("an.dpr.accion.rubenslocatephone");
//		PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, locIntent, 0/*PendingIntent.FLAGS?*/);
//		LocationManager locManager = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
//		SharedPreferences pref = ctx.getSharedPreferences("myPrefs", 0);
//		String provider = "GPS_PROVIDER";//"NETWORK_PROVIDER";
//		//esto nos lanzara el broadcast con extras key KEY_LOCATION_CHANGED and value LOCATION
//		locManager.requestLocationUpdates(provider, getMinTime(pref), getMinDistance(pref), pi);
//	}
//	
//	private int getMinTime(SharedPreferences pref){
//		int retValue = pref.getInt("minTime", LocatePhoneContract.MIN_TIME_DEFAULT);
//		retValue = retValue * 60 * 1000; //minutos a milisec
//		return retValue;
//	}
//	
//	
//	private int getMinDistance(SharedPreferences pref){
//		int retValue = pref.getInt("minDistance", LocatePhoneContract.MIN_DISTANCE_DEFAULT);
//		return retValue;
//	}
//}
//

