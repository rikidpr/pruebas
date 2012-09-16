package an.dpr.pruebasandroid;

import android.app.Activity;
import android.os.Bundle;

public class ContentProviderActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.contentProviderTest);
		//add a boton evento para llamar a content provider
	}
	
	private String infoCalendar(){
		String uri = null;
//		uri = CalendarContract.Events.CONTENT_URI;
		return uri;
	}
}
