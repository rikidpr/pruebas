package an.dpr.enbizzi;

import java.util.Calendar;
import java.util.List;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.bean.CyclingType;
import an.dpr.enbizzi.calendar.bean.Difficulty;
import an.dpr.enbizzi.calendar.contentprovider.BikeCalendarContract;
import an.dpr.enbizzi.calendar.network.CalendarService;
import an.dpr.enbizzi.calendar.xml.XMLCalendarConverter;
import an.dpr.enbizzi.util.NotificationUtil;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button) findViewById(R.id.btnPrueba);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prueba();
			}
		});

		Button btnXml = (Button) findViewById(R.id.btnXml);
		btnXml.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pruebaXml();
			}
		});

	 	Button btnNet = (Button) findViewById(R.id.btnNet);
	 	btnNet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pruebaNet();
			}
		});

	 	Button btnList= (Button) findViewById(R.id.btnList);
	 	btnList.setOnClickListener(new OnClickListener() {
	 		
	 		@Override
	 		public void onClick(View v) {
	 			launchList();
	 		}
	 	});

	 	Button btnInfo= (Button) findViewById(R.id.btnInfo);
	 	btnInfo.setOnClickListener(new OnClickListener() {
	 		
	 		@Override
	 		public void onClick(View v) {
	 			launchInfo();
	 		}
	 	});
	
	 	Button btnDel =(Button) findViewById(R.id.btnDel);
	 	btnDel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				launchDel();
			}});
	}

	protected void launchDel(){
		int i = getContentResolver().delete(BikeCalendarContract.CONTENT_URI, "", null);
		NotificationUtil.setToastMsg(this, "borrados: "+i, Toast.LENGTH_SHORT);
	}
	
	protected void launchInfo() {
		Cursor c = getContentResolver().query(
				BikeCalendarContract.CONTENT_URI, 
				BikeCalendarContract.COLUMN_NAMES,
				"", null, null);
		int cont = 0;
		c.moveToFirst();
		do{
			cont++;
		} while(c.moveToNext());
		NotificationUtil.setToastMsg(this, "resultados:"+cont, Toast.LENGTH_LONG);
	}

	protected void launchList() {
		Intent i = new Intent(this, CalendarList.class);
		startActivity(i);
	}

	protected void pruebaNet() {
		final MainActivity clase = this;
		final ProgressBar progressBar = new ProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, Gravity.CENTER));
		progressBar.setIndeterminate(true);

		// Must add the progress bar to the root of the layout
		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		root.addView(progressBar);
		
		AsyncTask<String, String, String> at = new AsyncTask<String, String, String>(){

			@Override
			protected String doInBackground(String... params) {
				Log.d(TAG, "doInBackground");
				return CalendarService.getCalendarXML();
			}
		@Override
			protected void onPostExecute(String result) {
				Log.d(TAG, result);
	            progressBar.setVisibility(ProgressBar.GONE);
//				NotificationUtil.setToastMsg(clase, result, Toast.LENGTH_LONG);
				tratarXmlNet(result);
			}	
		};
		at.execute("prueba");
	}
	
	private void tratarXmlNet(String xmlText){
		try {
			List<BikeCalendar> list = XMLCalendarConverter
					.getCalendarViaNewPullParser(xmlText);
			StringBuilder sb = new StringBuilder();
			for(BikeCalendar cal: list){
				ContentValues values = BikeCalendarContract.getValues(cal);
				Uri callUri = ContentUris.withAppendedId(BikeCalendarContract.CONTENT_URI,1);
				Uri retUri = getContentResolver().insert(callUri, values);
				sb.append(retUri.toString());
			}
			NotificationUtil.setToastMsg(this, sb.toString(), Toast.LENGTH_LONG);
			Log.d(TAG, sb.toString());
		} catch (Exception e) {
			Log.e(TAG, "Error!!", e);
		}
	}

	protected void pruebaXml() {
		try {
			List<BikeCalendar> list = XMLCalendarConverter
					.getCalendarViaNewPullParser(XMLCalendarConverter.xmlText);
			StringBuilder sb = new StringBuilder();
			for(BikeCalendar cal: list){
				ContentValues values = BikeCalendarContract.getValues(cal);
				Uri callUri = ContentUris.withAppendedId(BikeCalendarContract.CONTENT_URI,1);
				Uri retUri = getContentResolver().insert(callUri, values);
				sb.append(retUri.toString());
			}
			NotificationUtil.setToastMsg(this, sb.toString(), Toast.LENGTH_LONG);
			Log.d(TAG, sb.toString());
		} catch (Exception e) {
			Log.e(TAG, "Error!!", e);
		}
	}

	private void prueba() {
		BikeCalendar bikeCalendar = new BikeCalendar();
		bikeCalendar.setDate(Calendar.getInstance().getTime());
		bikeCalendar.setDifficulty(Difficulty.EASY);
		bikeCalendar.setKm((float) 78.8);
		bikeCalendar.setReturnRoute("Muel");
		bikeCalendar.setRoute("Villanueva de Huerva");
		bikeCalendar.setStop("Villaueva de Huerva");
		bikeCalendar.setType(CyclingType.ROAD);

		ContentValues values = BikeCalendarContract.getValues(bikeCalendar);
		Uri callUri = ContentUris.withAppendedId(BikeCalendarContract.CONTENT_URI,1);
		Uri retUri = getContentResolver().insert(callUri, values);
		Log.d(TAG, retUri.toString());
		Cursor rc = getContentResolver().query(retUri,
				BikeCalendarContract.COLUMN_NAMES, null, null, null);
		StringBuilder result = new StringBuilder();
		if (rc.moveToFirst()) {
			result.append(rc.getInt(rc
					.getColumnIndex(BikeCalendarContract.COL_ID)));
			result.append(", ").append(BikeCalendarContract.COL_DATE)
					.append(":");
			result.append(rc.getString(rc
					.getColumnIndex(BikeCalendarContract.COL_DATE)));
			result.append(", ").append(BikeCalendarContract.COL_DIFFICULTY)
					.append(":");
			result.append(rc.getString(rc
					.getColumnIndex(BikeCalendarContract.COL_DIFFICULTY)));
			result.append(", ").append(BikeCalendarContract.COL_KM).append(":");
			result.append(rc.getFloat(rc
					.getColumnIndex(BikeCalendarContract.COL_KM)));
			result.append(", ").append(BikeCalendarContract.COL_RETURN_ROUTE)
					.append(":");
			result.append(rc.getString(rc
					.getColumnIndex(BikeCalendarContract.COL_RETURN_ROUTE)));
			result.append(", ").append(BikeCalendarContract.COL_ROUTE)
					.append(":");
			result.append(rc.getString(rc
					.getColumnIndex(BikeCalendarContract.COL_ROUTE)));
			result.append(", ").append(BikeCalendarContract.COL_STOP)
					.append(":");
			result.append(rc.getString(rc
					.getColumnIndex(BikeCalendarContract.COL_STOP)));
			result.append(", ").append(BikeCalendarContract.COL_TYPE)
					.append(":");
			result.append(rc.getString(rc
					.getColumnIndex(BikeCalendarContract.COL_TYPE)));
			result.append(", ").append(BikeCalendarContract.COL_ELEVATION_GAIN)
					.append(":");
			result.append(rc.getString(rc
					.getColumnIndex(BikeCalendarContract.COL_ELEVATION_GAIN)));

		} else {
			result.append("no hay tutia");
		}
		NotificationUtil
				.setToastMsg(this, result.toString(), Toast.LENGTH_LONG);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
