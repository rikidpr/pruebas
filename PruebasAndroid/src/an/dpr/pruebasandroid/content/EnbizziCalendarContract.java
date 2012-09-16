package an.dpr.pruebasandroid.content;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.provider.BaseColumns;

public class EnbizziCalendarContract {

	public static final String CONTENT_AUTHORITY = "an.dpr.android.enbizzi.calendar";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY); 
	public static final String ENBIZZI_CALENDAR_PATH = "enbizziCalendar"; 
	
	/**
	 * Columnas de informacion del ContentProvider
	 * @author rsaez
	 *
	 */
	public interface EnbizziCalendarColumns extends BaseColumns{
		public static final String FECHA = "fecha";
		public static final String DESTINO = "destino";
	}
	
	/**
	 * Tipos mime y UriMatcher
	 * @author rsaez
	 *
	 */
	public static final class EnbizziCalendar implements EnbizziCalendarColumns{
		public static final String CONTENT_TYPE_CALENDAR = "vnd.android.cursor.dir/"+CONTENT_AUTHORITY;
		public static final String CONTENT_TYPE_CALENDAR_ITEM = "vnd.android.cursor.item/"+CONTENT_AUTHORITY;
		
		private EnbizziCalendar(){}
		
		public Uri getUriMatcher(){
			return BASE_CONTENT_URI.buildUpon().
					appendPath(ENBIZZI_CALENDAR_PATH).build();
		}
		
		public Uri getUriMatcher(long id){
			String sid = new StringBuilder().append(id).toString();
			return BASE_CONTENT_URI.buildUpon().
					appendPath(ENBIZZI_CALENDAR_PATH).
					appendPath(sid).build();
		}
		
		public Uri getUriMatcher(Date fecha){
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			String sfecha = sdf.format(fecha);
			return BASE_CONTENT_URI.buildUpon().
					appendPath(ENBIZZI_CALENDAR_PATH).
					appendPath(sfecha).build();
		}
	}
	
}
