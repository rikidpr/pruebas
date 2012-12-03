package an.dpr.rubenslocatephone.config;

import an.dpr.rubenslocatephone.util.LocatePhoneContract;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Settings {

	private static final String TAG = Settings.class.getName();

	public static final SharedPreferences getPreferences(Context ctx) {
		SharedPreferences pref = ctx.getSharedPreferences("preferences",
				Context.MODE_PRIVATE);
		return pref;
	}

	public static int getMinTime(Context ctx) {
		String minTime = getPreferences(ctx).getString("minTime",String.valueOf(LocatePhoneContract.MIN_TIME_DEFAULT));
		int retValue = 0;
		try{
			retValue = Integer.valueOf(minTime);
		} catch(NumberFormatException e){
			retValue = LocatePhoneContract.MIN_TIME_DEFAULT;
		}
		Log.i(TAG, "tiempo minimo:" + retValue +" minutos");
		retValue = retValue * 60 * 1000; // minutos a milisec
		return 20000;
	}

	public static int getMinDistance(Context ctx) {
		String minTime = getPreferences(ctx).getString("minDistance",String.valueOf(LocatePhoneContract.MIN_DISTANCE_DEFAULT));
		int retValue = 0;
		try{
			retValue = Integer.valueOf(minTime);
		} catch(NumberFormatException e){
			retValue = LocatePhoneContract.MIN_DISTANCE_DEFAULT;
		}
		Log.i(TAG, "metros minimo:" + retValue);
		return 1;
	}
}
