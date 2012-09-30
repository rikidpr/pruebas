package an.dpr.enbizzi.calendar.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.contentprovider.BikeCalendarContract;
import an.dpr.enbizzi.calendar.xml.XMLCalendarConverter;
import an.dpr.enbizzi.util.NotificationUtil;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class CalendarService {

	public static String getCalendarXML() {
		StringBuilder xml = new StringBuilder();
		HttpClient hc = new DefaultHttpClient();
		HttpPost postR = new HttpPost("https://sites.google.com/site/bikermente/calendarioSalidas.xml");
		try {
			HttpResponse response = hc.execute(postR);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line;
			while((line= br.readLine()) != null){
				xml.append(line.trim());
			}
			br.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xml.toString();
	}
	
}
