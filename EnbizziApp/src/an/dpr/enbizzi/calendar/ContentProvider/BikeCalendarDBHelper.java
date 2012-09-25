package an.dpr.enbizzi.calendar.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BikeCalendarDBHelper extends SQLiteOpenHelper{
	
	private static final String TAG = BikeCalendarDBHelper.class.getName();
	
	private static final String DB_CREATE = 
			"CREATE TABLE "+BikeCalendarContract.TABLE_NAME+"(" +
					BikeCalendarContract.COL_ID +" integer primary key," +
					BikeCalendarContract.COL_DATE +" text," +
					BikeCalendarContract.COL_DIFFICULTY +" text," +
					BikeCalendarContract.COL_KM +" real," +
					BikeCalendarContract.COL_RETURN_ROUTE +" text," +
					BikeCalendarContract.COL_ROUTE +" text," +
					BikeCalendarContract.COL_STOP +" text," +
					BikeCalendarContract.COL_TYPE +" text" +
					")";

	private static final String DB_DROP_FOR_UPDATE = 
			"DROP TABLE IF EXISTS " + BikeCalendarContract.TABLE_NAME;

	
	public BikeCalendarDBHelper(Context context){
		super(context, BikeCalendarContract.TABLE_NAME+".db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Creando tabla "+BikeCalendarContract.TABLE_NAME);
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Actualizando tabla "+BikeCalendarContract.TABLE_NAME+", old:"+oldVersion+", new:"+newVersion);
		db.execSQL(DB_DROP_FOR_UPDATE);
		onCreate(db);
	}

}
