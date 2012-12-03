package an.dpr.rubenslocatephone.broadcast;

import an.dpr.rubenslocatephone.network.RubensService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class LocationBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = LocationBroadcastReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		Log.i(TAG, "broadcast recibido!!");
		Location loc = extras != null ? (Location) extras
				.get(LocationManager.KEY_LOCATION_CHANGED) : null;
		if (loc == null) {
			Log.d(TAG, "No recibimos loc en los extras, obtenemos localizacion");
			loc = RubensService.getLocation(context);
		}
		String msj = "long:" + (loc != null ? loc.getLongitude() : "-")
				+ ", lat:" + (loc != null ? loc.getLatitude() : "-");
		Log.i(TAG, msj);
		lanzarRequest(context, loc);
	}

	/**
	 * Lanza la peticion de envio de posicion al locateDevice
	 * @param context
	 * @param loc
	 */
	private void lanzarRequest(final Context context, final Location loc) {
		AsyncTask<String, Integer, String> at = new AsyncTask<String, Integer, String>() {

			@Override
			protected String doInBackground(String... params) {
				String url = RubensService.URL_RUBENS_JSON;
				String retValue = RubensService.enviarPosicion(context, url, loc);
				return retValue;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.i(TAG, result);
			}

		};
		at.execute("epei");
	}

}

