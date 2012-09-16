package an.dpr.pruebasandroid.sqlite;

import an.dpr.pruebasandroid.content.BiciContract;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BiciDBHelper extends SQLiteOpenHelper {
	
	private static final String TAG = BiciDBHelper.class.getName();

	private static final String DB_CREATE = 
			"create table "+BiciContract.TABLE_NAME+"("
			+ BiciContract.COLUMN_ID + " integer primary key,"
			+ BiciContract.COLUMN_MARCA + " text," 
			+ BiciContract.COLUMN_MODELO+ " modelo text," 
			+ BiciContract.COLUMN_GRUPO + " text);";
	
	private static final String DB_DROP_FOR_UPDATE = 
			"DROP TABLE IF EXISTS " + BiciContract.TABLE_NAME;

	public BiciDBHelper(Context context) {
		super(context, BiciContract.TABLE_NAME+".db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "creando tabla");
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "actualizando tabla");
		db.execSQL(DB_DROP_FOR_UPDATE);
		onCreate(db);
	}

}
