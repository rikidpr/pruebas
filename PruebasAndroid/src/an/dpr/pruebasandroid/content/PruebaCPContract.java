package an.dpr.pruebasandroid.content;

import android.net.Uri;

public final class PruebaCPContract {

	public static final String CONTENT_AUTHORITY = "an.dpr.android.content";
	public static final String PATH = "prueba";
	public static final Uri URI = Uri.parse("content://"+CONTENT_AUTHORITY+"/"+PATH);
	public static final String MIME_ITEM = "vnd.android.cursor.item/"
			+ PruebaCPContract.CONTENT_AUTHORITY + "." + PruebaCPContract.PATH;
	public static final String MIME_DIR = "vnd.android.cursor.dir/"
			+ PruebaCPContract.CONTENT_AUTHORITY + "." + PruebaCPContract.PATH;

	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_VALUE = "VALUE";
	public static final String[] columnNames = { COLUMN_ID, COLUMN_VALUE };
}
