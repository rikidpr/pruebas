package an.dpr.pruebasandroid.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class NotificationUtil {

	/**
	 * 
	 * @param msg
	 * @param duration
	 *            Toast.LENGTH_SHORT, Toast.LENGTH_LONG
	 */
	public static void setToastMsg(Activity activity, String msg, int duration) {
		Context context = activity.getApplicationContext();
		CharSequence text = msg;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
