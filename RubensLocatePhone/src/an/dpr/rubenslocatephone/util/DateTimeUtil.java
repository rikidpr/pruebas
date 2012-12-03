package an.dpr.rubenslocatephone.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	
	/**
	 * return int diferencia entre el uso horario de la jvm actual
	 * y el GTM
	 */
	public static int diferenciaGTM(){
		Date currentLocalTime = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("Z");  
		String text = df.format(currentLocalTime);
		int difGtm = Integer.valueOf(text.replace("+","")).intValue()/100;
		return difGtm;
	}
}
