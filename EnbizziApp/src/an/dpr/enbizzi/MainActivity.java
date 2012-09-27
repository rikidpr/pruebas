package an.dpr.enbizzi;

import java.util.Calendar;
import java.util.List;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.bean.CyclingType;
import an.dpr.enbizzi.calendar.bean.Difficulty;
import an.dpr.enbizzi.calendar.contentprovider.BikeCalendarContract;
import an.dpr.enbizzi.calendar.xml.XMLCalendarConverter;
import an.dpr.enbizzi.util.NotificationUtil;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	}

	protected void pruebaXml() {
		try {
			List<BikeCalendar> list = XMLCalendarConverter
					.getCalendarViaNewPullParser(null);
			StringBuilder sb = new StringBuilder();
			for(BikeCalendar cal: list){
				sb.append(cal);
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
		Uri retUri = getContentResolver().insert(
				BikeCalendarContract.CONTENT_URI, values);

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
