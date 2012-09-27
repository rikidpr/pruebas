package an.dpr.enbizzi.calendar.contentprovider;

import java.text.SimpleDateFormat;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;

public class BikeCalendarContract {

	// TABLE
	public static final String TABLE_NAME = "BIKE_CALENDARS";
	public static final String COL_ID = BaseColumns._ID;
	public static final String COL_DATE = "DATE";
	public static final String COL_DIFFICULTY = "DIFFICULTY";
	public static final String COL_KM = "KM";
	public static final String COL_RETURN_ROUTE = "RETURN_ROUTE";
	public static final String COL_ROUTE = "ROUTE";
	public static final String COL_STOP = "STOP";
	public static final String COL_TYPE = "TYPE";
	public static final String COL_ELEVATION_GAIN = "ELEVATION_GAIN";

	// COLUMNS
	public static final String[] COLUMN_NAMES = { COL_ID, COL_DATE,
			COL_DIFFICULTY, COL_KM, COL_RETURN_ROUTE, COL_ROUTE, COL_STOP,
			COL_TYPE, COL_ELEVATION_GAIN };

	// URI
	public static final String CONTENT_AUTHORITY = "an.dpr.enbizzi";
	public static final String PATH = "calendar";
	public static final Uri CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY+"/"+PATH);
	
	//MIME
	private static final String MIME_BIKE_CALENDAR = "vnd.an.dpr.enbizzi.calendar";
	public static final String MIME_ITEM = "vnd.android.cursor.item/"+MIME_BIKE_CALENDAR;
	public static final String MIME_DIR = "vnd.android.cursor.dir/"+MIME_BIKE_CALENDAR;
	
	public static ContentValues getValues(BikeCalendar bean) {
		ContentValues rv = new ContentValues();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		rv.put(COL_ID, bean.getId());
		rv.put(COL_DATE, sdf.format(bean.getDate()));
		rv.put(COL_DIFFICULTY, bean.getDifficulty().name());
		rv.put(COL_KM, bean.getKm());
		rv.put(COL_RETURN_ROUTE, bean.getReturnRoute());
		rv.put(COL_ROUTE, bean.getRoute());
		rv.put(COL_STOP, bean.getStop());
		rv.put(COL_TYPE, bean.getType().name());
		
		return rv;
	}

}
