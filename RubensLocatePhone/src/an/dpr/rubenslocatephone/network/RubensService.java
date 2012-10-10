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
import android.location.Location;
import android.location.LocationManager;
import android.util.Base64;
import android.util.Log;

public class RubensService {

	private static final String TAG = RubensService.class.getName();
	public static final String URL_RUBENS_JSON = "http://locatephone.rubenlor.es/locatephone.php";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;

	public static String leerInfo(Context context, String url){
		StringBuilder retValue = new StringBuilder();
		HttpClient hc = new DefaultHttpClient();
		HttpPost req = new HttpPost(url);

		try{
			List<NameValuePair> params = addParametros(context);
			
			req.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = hc.execute(req);
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
		
		return retValue.toString();
	}
	
	private static List<NameValuePair> addParametros(Context context){
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		String user = "riker";//accontdefault.WOSuser;
        String pass = "passRiker1"; //accontdefault.WOSpass;  --> registrate en locatephone.rubenlor.es y es el mismo usuario
		String method = "insertlocatedevice";
        String codigo = "50432a8cf21f7"; //  --> este código te lo da si creas un dispositivo en la web
		String fecha = sdf.format(Calendar.getInstance().getTime());
		Location location = getLocation(context);
		byte[] lat  = doubleToByteArray(40.415670);//location.getLatitude());// --> latitud actual del dispositivo
        byte[] lng  = doubleToByteArray(-3.694701);// --> longitud actual del dispositivo

		//add values
		params.add(new BasicNameValuePair("a",Base64.encodeToString(method.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("c",Base64.encodeToString(codigo.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("d",Base64.encodeToString(lat, Base64.DEFAULT)));
		params.add(new BasicNameValuePair("e",Base64.encodeToString(lng, Base64.DEFAULT)));
		params.add(new BasicNameValuePair("f",Base64.encodeToString(fecha.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("y",Base64.encodeToString(user.getBytes(), Base64.DEFAULT)));
		params.add(new BasicNameValuePair("z",Base64.encodeToString(pass.getBytes(), Base64.DEFAULT)));
		
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
