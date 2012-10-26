package an.dpr.enbizzi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarGridFragment extends Fragment {
	
	private static final String TAG = CalendarGridFragment.class.getName();

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 super.onCreateView(inflater, container, savedInstanceState);
		 return inflater.inflate(R.layout.calendar_grid, container, false);
	 }
}