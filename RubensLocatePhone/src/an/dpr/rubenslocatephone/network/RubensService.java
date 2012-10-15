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

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

public class RubensService {

	private static final String TAG = RubensService.class.getName();
	public static final String URL_RUBENS_JSON = "http://locatephone.rubenlor.es/locatephone.php";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;

	public static String leerInfo(Context context, String url, Location loc){
		StringBuilder retValue = new StringBuilder();
		HttpClient hc = new DefaultHttpClient();
		HttpPost req = new HttpPost(url);

		try{
			Log.d(TAG, "rellenamos parametros");
			List<NameValuePair> params = addParametros(context,loc);
			
			req.setEntity(new UrlEncodedFormEntity(params));
			Log.d(TAG, "lanzamos request");
			HttpResponse response = hc.execute(req);
			Log.d(TAG, "recibimos response "+response);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String linea;
			while((linea = br.readLine()) != null){
				retValue.append(linea);
			}			
		} catch (ClientProtocolException e) {
			Log.e(TAG, "error obteniendo info", e);
		} catch(Exception e){
			Log.e(TAG, "error obteniendo info", e);
		}
		Log.d(TAG, "respuesta LocatePhone:"+retValue);
		return retValue.toString();
	}
	
	private static List<NameValuePair> addParametros(Context context, Location loc){
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String user = pref.getString("login", "def");
		Log.d(TAG, "user="+user);
        String pass = pref.getString("pass", "def");
        Log.d(TAG, "pass="+pass);
		String method = "insertlocatedevice";
        String codigo = pref.getString("code", "def");
        Log.d(TAG, "code="+codigo);
		String fecha = sdf.format(Calendar.getInstance().getTime());
		Log.d(TAG, "fecha="+fecha);
		byte[] lat  = doubleToByteArray(loc.getLatitude());// --> latitud actual del dispositivo
		Log.d(TAG, "lat="+loc.getLatitude());
        byte[] lng  = doubleToByteArray(loc.getLongitude());// --> longitud actual del dispositivo
        Log.d(TAG, "lng="+loc.getLongitude());

		//add values
//		params.add(new BasicNameValuePair("a",Base64.encodeToString(method.getBytes(), Base64.DEFAULT)));
//		params.add(new BasicNameValuePair("c",Base64.encodeToString(codigo.getBytes(), Base64.DEFAULT)));
//		params.add(new BasicNameValuePair("d",Base64.encodeToString(lat, Base64.DEFAULT)));
//		params.add(new BasicNameValuePair("e",Base64.encodeToString(lng, Base64.DEFAULT)));
//		params.add(new BasicNameValuePair("f",Base64.encodeToString(fecha.getBytes(), Base64.DEFAULT)));
//		params.add(new BasicNameValuePair("y",Base64.encodeToString(user.getBytes(), Base64.DEFAULT)));
//		params.add(new BasicNameValuePair("z",Base64.encodeToString(pass.getBytes(), Base64.DEFAULT)));

		params.add(new BasicNameValuePair("a",method));
		params.add(new BasicNameValuePair("c",codigo));
		params.add(new BasicNameValuePair("d",String.valueOf(loc.getLatitude())));
		params.add(new BasicNameValuePair("e",String.valueOf(loc.getLongitude())));
		params.add(new BasicNameValuePair("f",fecha));
		params.add(new BasicNameValuePair("y",user));
		params.add(new BasicNameValuePair("z",pass));
		params.add(new BasicNameValuePair("codificado","false"));
		
		return params;
	}
	
	private  static byte[] doubleToByteArray(double valor){
		byte[] retValue = new byte[8];
		ByteBuffer.wrap(retValue).putDouble(valor);
		return retValue;
	}
	
	
	public static Location getLocation(Context context){
		LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return loc;
	}

}
