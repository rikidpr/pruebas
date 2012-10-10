package an.dpr.rubenslocatephone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;

@SuppressLint("NewApi")
public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedStateInstance){
		super.onCreate(savedStateInstance);
		addPreferencesFromResource(R.xml.preferences);
	}
}
