package an.dpr.enbizzi.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class TuitsEnbizzi {
	
	private static final String TAG =TuitsEnbizzi.class.getName();
	private static final String URL = "http://search.twitter.com/search.json?q=enbizzi";

	public static void leerTuitsEnbizzi() {
		AsyncTask<String, String, String> tarea = new AsyncTask<String, String, String>() {
			@Override
			protected String doInBackground(String... param){
				String result = readTwitterFeed(param[0]);
				return result;
			}
			
			@Override
			protected void onPostExecute(String result){
				mostrarMensajesToast(result);
			}
		};
		tarea.execute(URL);
	}
	
	static void mostrarMensajesToast(String jsonTxt){
		try{
			JSONObject json = new JSONObject(jsonTxt);
			JSONArray result = json.getJSONArray("results");
			for (int i = 0; i<result.length(); i++){
				StringBuilder tuit = new StringBuilder();
				JSONObject res = result.getJSONObject(i);
				tuit.append("@");
				tuit.append(res.getString("from_user"));
				tuit.append(":");
				tuit.append(res.getString("text"));
				Log.i(TAG, tuit.toString());
			}
//			JSONArray results = json.getJSONArray();
//			JSONArray jsonArray = new JSONArray(result);
//			Log.i(TuitsEnbizzi.class.getName(), "Number of entries "
//					+ jsonArray.length());
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jsonObject = jsonArray.getJSONObject(i);
//				Log.i(TuitsEnbizzi.class.getName(),
//						jsonObject.getString("text"));
//			}
		} catch (Exception e){
			Log.e(TAG, "cagada en tuitsenbizzi", e);
		}
	}
	
	static String readTwitterFeed(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(TuitsEnbizzi.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
