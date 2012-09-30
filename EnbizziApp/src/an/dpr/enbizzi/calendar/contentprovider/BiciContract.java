package an.dpr.enbizzi.calendar.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class BiciContract {

	public static final String CONTENT_AUTHORITY = "an.dpr.android.content";
	public static final String PATH = "bici";
	public static final Uri CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY+"/"+PATH);
	public static final String MIME_ITEM = "vnd.android.cursor.item/"
			+ BiciContract.CONTENT_AUTHORITY + "." + BiciContract.PATH;
	public static final String MIME_DIR = "vnd.android.cursor.dir/"
			+ BiciContract.CONTENT_AUTHORITY + "." + BiciContract.PATH;

	public static final String TABLE_NAME = "BICIS";
	public static final String COLUMN_ID = BaseColumns._ID;
	public static final String COLUMN_MARCA = "MARCA";
	public static final String COLUMN_MODELO = "MODELO";
	public static final String COLUMN_GRUPO = "GRUPO";
	public static final String[] COLUMN_NAMES = { COLUMN_ID, COLUMN_MARCA, COLUMN_MODELO, COLUMN_GRUPO };

	public static final String FIND_BY_ID_SELECTION = COLUMN_ID+"=?";
	
	//MIME
	private static final String MIME_BICI = "vnd.an.dpr.bici";
	public static final String MIME_SINGLE = "vnd.android.cursor.item"+"/"+MIME_BICI;
	public static final String MIME_GROUP = "vnd.android.cursor.dir"+"/"+MIME_BICI;
	
	
	public static Uri getContentUriId(String id){
		return Uri.parse("content://"+CONTENT_AUTHORITY+"/"+PATH+"/"+id);
	}

}
