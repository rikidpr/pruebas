package an.dpr.enbizzi.calendar.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class BikeCalendarCP extends ContentProvider {

	private static final String TAG = BikeCalendarCP.class.getName();

	private static final int ITEM_TYPE = 1;
	private static final int DIR_TYPE = 2;
	private UriMatcher um;
	private BikeCalendarDBHelper dbHelper;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d(TAG, "delete");
		int rv = 0;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		StringBuilder where = new StringBuilder();
		String[] whereArgs = null;
		switch (um.match(uri)) {
		case ITEM_TYPE:
			// borramos el susodicho
			String id = uri.getLastPathSegment();
			where.append(BikeCalendarContract.COL_ID).append("=").append(id);
			break;
		case DIR_TYPE:
			// borramos todos
			where.append(selection);
			whereArgs = selectionArgs;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		rv = db.delete(BikeCalendarContract.TABLE_NAME, where.toString(),
				whereArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return rv;
	}

	@Override
	public String getType(Uri uri) {
		Log.d(TAG, "getType");
		String retValue = null;
		switch (um.match(uri)) {
		case ITEM_TYPE:
			retValue = BikeCalendarContract.MIME_ITEM;
			break;
		case DIR_TYPE:
			retValue = BikeCalendarContract.MIME_DIR;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		return retValue;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d(TAG, "insert");
		Uri rv = null;

		if (um.match(uri) != ITEM_TYPE) {
			final String msg = "Unknown URI " + uri;
			Log.e(TAG, msg);
			throw new IllegalArgumentException(msg);
		}
		if (values == null) {
			final String msg = "No se pueden insertar un set de valores nulo";
			Log.e(TAG, msg);
			throw new IllegalArgumentException(msg);
		}

		SQLiteDatabase con = dbHelper.getWritableDatabase();
		long id = con.insert(BikeCalendarContract.TABLE_NAME, null, values);
		if (id > 0) {
			rv = ContentUris.withAppendedId(BikeCalendarContract.CONTENT_URI,
					id);
			getContext().getContentResolver().notifyChange(rv, null);
		} else {
			throw new SQLException("Failed to insert row into " + uri);
		}

		return rv;
	}

	@Override
	public boolean onCreate() {
		Log.d(TAG, "onCreate");
		buildUriMatcher();
		dbHelper = new BikeCalendarDBHelper(getContext());
		return true;
	}

	private void buildUriMatcher() {
		um = new UriMatcher(UriMatcher.NO_MATCH);
		um.addURI(BikeCalendarContract.CONTENT_AUTHORITY,BikeCalendarContract.PATH, DIR_TYPE);
		um.addURI(BikeCalendarContract.CONTENT_AUTHORITY,BikeCalendarContract.PATH + "/#", ITEM_TYPE);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "query");
		Cursor rv = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder where = new StringBuilder();
		String[] whereArgs = null;

		switch (um.match(uri)) {
		case ITEM_TYPE:
			where.append(BikeCalendarContract.COL_ID).append("=?");
			whereArgs = new String[] { uri.getLastPathSegment() };
			break;
		case DIR_TYPE:
			where.append(selection);
			whereArgs = selectionArgs;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		rv = db.query(BikeCalendarContract.TABLE_NAME, projection,
				where.toString(), whereArgs, null, null, sortOrder, null);

		return rv;
	}

	@Override
	public int update(Uri uri, ContentValues values, String sel,
			String[] selArgs) {
		Log.d(TAG, "update");
		int rv = 0;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		StringBuilder where = new StringBuilder();
		String[] whereArgs = null;

		switch (um.match(uri)) {
		case DIR_TYPE:
			where.append(sel);
			whereArgs = selArgs;
			break;
		case ITEM_TYPE:
			where.append(BikeCalendarContract.COL_ID).append("=")
					.append(uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		rv = db.update(BikeCalendarContract.TABLE_NAME, values,
				where.toString(), whereArgs);
		return rv;
	}

}
