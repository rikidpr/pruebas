package an.dpr.pruebasandroid.content;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class BiciCP extends ContentProvider {
	
	private static String TAG = BiciCP.class.getName();
	private static UriMatcher um;
	
	private static final int GROUP_CODE = 1;
	private static final int SINGLE_CODE = 2;
	
	private static final UriMatcher buildUriMatcher(){
    	um = new UriMatcher(UriMatcher.NO_MATCH);
    	um.addURI(BiciContract.CONTENT_AUTHORITY, BiciContract.PATH, GROUP_CODE);
    	um.addURI(BiciContract.CONTENT_AUTHORITY, BiciContract.PATH+"/#", SINGLE_CODE);
    	return um;
    }

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		Log.d(TAG, "string borrando con parametros "+arg1+","+arg2);
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		Log.d(TAG, "inicio");
		String type = null;
		switch(um.match(uri)){
		case GROUP_CODE:
			type = BiciContract.MIME_GROUP;
			break;
		case SINGLE_CODE:
			type = BiciContract.MIME_SINGLE;
			break;
		}
		return type;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d(TAG, "inicio");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		Log.d(TAG, "inicio");
		buildUriMatcher();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "inicio");
		MatrixCursor c = new MatrixCursor(BiciContract.columnNames);
		switch(um.match(uri)){
		case GROUP_CODE:
			c.addRow(new String[]{"1","dateiros"});
			c.addRow(new String[]{"2","daticos"});
			break;
		case SINGLE_CODE:
			c.addRow(new String[]{"3","onlyOneRow"});
		}
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.d(TAG, "inicio");
		// TODO Auto-generated method stub
		return 0;
	}

}
