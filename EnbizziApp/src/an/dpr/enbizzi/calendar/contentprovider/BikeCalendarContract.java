package an.dpr.enbizzi.calendar.contentprovider;

import java.text.SimpleDateFormat;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.bean.CyclingType;
import an.dpr.enbizzi.calendar.bean.Difficulty;
import android.content.ContentValues;
import android.database.Cursor;
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
	public static final String COL_AEMET_START = "AEMET_START";
	public static final String COL_AEMET_STOP = "AEMET_STOP";
	
	// COLUMNS
	public static final String[] COLUMN_NAMES = { COL_ID, COL_DATE,
			COL_DIFFICULTY, COL_KM, COL_RETURN_ROUTE, COL_ROUTE, COL_STOP,
			COL_TYPE, COL_ELEVATION_GAIN, COL_AEMET_START, COL_AEMET_STOP };

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
		
		rv.put(COL_ID, bean.getId()>0 ? bean.getId() : null);
		rv.put(COL_DATE, sdf.format(bean.getDate()));
		rv.put(COL_DIFFICULTY, bean.getDifficulty()!=null 
				? bean.getDifficulty().name(): 
				Difficulty.MEDIUM.name());
		rv.put(COL_KM, bean.getKm());
		rv.put(COL_RETURN_ROUTE, bean.getReturnRoute());
		rv.put(COL_ROUTE, bean.getRoute());
		rv.put(COL_STOP, bean.getStop());
		rv.put(COL_TYPE, bean.getType()!=null 
				? bean.getType().name() 
				: CyclingType.ROAD.name());
		rv.put(COL_ELEVATION_GAIN, bean.getElevationGain());
		rv.put(COL_AEMET_START, bean.getAemetStart());
		rv.put(COL_AEMET_STOP, bean.getAemetStop());
		
		return rv;
	}
	
	public static Uri getQueryUri(long id){
		StringBuilder uriText = new StringBuilder();
		uriText.append("content://").append(CONTENT_AUTHORITY)
			.append("/").append(PATH).append("/").append(id);
		Uri uri = Uri.parse(uriText.toString());
		return uri;
	}

	public static BikeCalendar getBikeCalendar(Cursor c) {
		BikeCalendar bean = new BikeCalendar();
		bean.setReturnRoute(c.getString(c.getColumnIndex(COL_RETURN_ROUTE)));
		bean.setRoute(c.getString(c.getColumnIndex(COL_ROUTE)));
		bean.setStop(c.getString(c.getColumnIndex(COL_STOP)));
		bean.setDifficulty(c.getString(c.getColumnIndex(COL_DIFFICULTY)));
		bean.setType(c.getString(c.getColumnIndex(COL_TYPE)));
		bean.setDate(c.getString(c.getColumnIndex(COL_DATE)));
		bean.setElevationGain(c.getInt(c.getColumnIndex(COL_ELEVATION_GAIN)));
		bean.setId(c.getInt(c.getColumnIndex(COL_ID)));
		bean.setKm(c.getFloat(c.getColumnIndex(COL_KM)));
		bean.setAemetStart(c.getInt(c.getColumnIndex(COL_AEMET_START)));
		bean.setAemetStop(c.getInt(c.getColumnIndex(COL_AEMET_STOP)));
		return bean;
	}

}
