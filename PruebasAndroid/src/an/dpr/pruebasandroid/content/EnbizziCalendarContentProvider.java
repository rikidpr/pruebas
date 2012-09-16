package an.dpr.pruebasandroid.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import static an.dpr.pruebasandroid.content.EnbizziCalendarContract.*;

public class EnbizziCalendarContentProvider extends ContentProvider{
	
	public static final int TYPE_SALIDAS = 301;
	public static final int TYPE_SALIDA_ID = 302;
	public static final int TYPE_SALIDA_FECHA = 303;
    private static final UriMatcher mUriMatcher = buildUriMatcher();

    private static final UriMatcher buildUriMatcher(){
    	UriMatcher um = new UriMatcher(UriMatcher.NO_MATCH);
    	um.addURI(CONTENT_AUTHORITY, ENBIZZI_CALENDAR_PATH, TYPE_SALIDAS);
    	um.addURI(CONTENT_AUTHORITY, ENBIZZI_CALENDAR_PATH, TYPE_SALIDA_ID);
    	um.addURI(CONTENT_AUTHORITY, ENBIZZI_CALENDAR_PATH, TYPE_SALIDA_FECHA);
    	return um;
    }
	
	@Override
	public String getType(Uri uri) {
		switch(mUriMatcher.match(uri)){
		case TYPE_SALIDAS:
			return EnbizziCalendar.CONTENT_TYPE_CALENDAR;
		case TYPE_SALIDA_ID:
			return EnbizziCalendar.CONTENT_TYPE_CALENDAR_ITEM;
		case TYPE_SALIDA_FECHA:
			return EnbizziCalendar.CONTENT_TYPE_CALENDAR_ITEM;
		default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
