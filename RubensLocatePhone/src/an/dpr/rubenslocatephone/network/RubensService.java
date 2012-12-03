package an.dpr.rubenslocatephone.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import an.dpr.rubenslocatephone.config.Settings;
import an.dpr.rubenslocatephone.util.DateTimeUtil;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class RubensService {

	private static boolean serviceRunning = false; 
	private static final String TAG = RubensService.class.getName();
	public static final String URL_RUBENS_JSON = "http://locatephone.rubenlor.es/locatephone.php";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");;

	public static String enviarPosicion(Context context, String url, Location loc) {
		StringBuilder retValue = new StringBuilder();
		HttpClient hc = new DefaultHttpClient();
		HttpPost req = new HttpPost(url);

		try {
			Log.i(TAG, "rellenamos parametros");
			List<NameValuePair> params = addParametros(context, loc);

			req.setEntity(new UrlEncodedFormEntity(params));
			Log.i(TAG, "lanzamos request "+req.getURI());
			HttpResponse response = hc.execute(req);
			Log.i(TAG, "recibimos response " + response);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String linea;
			while ((linea = br.readLine()) != null) {
				retValue.append(linea);
			}
		} catch (ClientProtocolException e) {
			Log.e(TAG, "error obteniendo info", e);
		} catch (Exception e) {
			Log.e(TAG, "error obteniendo info", e);
		}
		Log.d(TAG, "respuesta LocatePhone:" + retValue);
		return retValue.toString();
	}

	private static List<NameValuePair> addParametros(Context context,
			Location loc) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		String user = pref.getString("login", "def");
		String pass = pref.getString("pass", "def");
		String method = "insertlocatedevice";
		String codigo = pref.getString("code", "def");
		String fecha = sdf.format(Calendar.getInstance().getTime());
		int difGTM = DateTimeUtil.diferenciaGTM();
		Log.d(TAG, "DiferenciaGTM:" + difGTM);

		// add values
		params.add(new BasicNameValuePair("a", Base64.encodeToString(
				method.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("c", Base64.encodeToString(
				codigo.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("d", Base64.encodeToString(
				String.valueOf(loc.getLongitude()).getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("e", Base64.encodeToString(
				String.valueOf(loc.getLatitude()).getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("f", Base64.encodeToString(
				fecha.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("y", Base64.encodeToString(
				user.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("z", Base64.encodeToString(
				pass.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("offset", Base64.encodeToString(
				String.valueOf(difGTM).getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("codificado", "true"));

		Log.i(TAG, params.toString());
		return params;
	}

	public static Location getLocation(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Location loc = null;
		
		Criteria fine = new Criteria();
		fine.setAccuracy(Criteria.ACCURACY_FINE);
		Criteria coarse = new Criteria();
		coarse.setAccuracy(Criteria.ACCURACY_COARSE);

		loc = lm.getLastKnownLocation(lm.getBestProvider(fine, false));
		if(loc == null){
			loc = lm.getLastKnownLocation(lm.getBestProvider(coarse, false));
		}
		Log.d(TAG, "location:"+loc);
		return loc;
	}

	public static void stopService(Context ctx) {
		// ver si con esto basta (que sea la misma accion) o tiene que ser
		// obejto, entonces creariamos variable static
		Intent locIntent = new Intent("an.dpr.accion.rubenslocatephone");
		PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, locIntent, 0);
		LocationManager locManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		locManager.removeUpdates(pi);
		serviceRunning = false;
	}

	public static void startService(Context ctx) {
		Log.i(TAG, "arrancando servicio de registro");
		Intent locIntent = new Intent("an.dpr.accion.rubenslocatephone");
		PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, locIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		LocationManager locManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
		if (pref.getBoolean("gpsProvider", false)) {
			String provider = LocationManager.GPS_PROVIDER;
			locManager.requestLocationUpdates(provider,Settings.getMinTime(ctx), Settings.getMinDistance(ctx), pi);
			Log.i(TAG, "GPS adherido");
		}

		if (pref.getBoolean("networkProvider", false)) {
			String provider = LocationManager.NETWORK_PROVIDER;
			locManager.requestLocationUpdates(provider,Settings.getMinTime(ctx), Settings.getMinDistance(ctx), pi);
			Log.i(TAG, "Network adherido");
		}
		serviceRunning = true;
	}

	public static void registraPosicion(final Context ctx) {
		final Location loc = getLocation(ctx);
		AsyncTask<String, Integer, String> at = new AsyncTask<String, Integer, String>() {

			@Override
			protected String doInBackground(String... params) {
				String url = RubensService.URL_RUBENS_JSON;
				String retValue = RubensService.enviarPosicion(ctx, url, loc);
				return retValue;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.d(TAG, result);
				Log.i(TAG, "posicion Registrada "+result);
				Toast.makeText(ctx, "Posicion registrada", Toast.LENGTH_LONG).show();
			}

		};
		at.execute("lanza");
	}

	public static boolean isRunning(){
		return serviceRunning;
	}
}
