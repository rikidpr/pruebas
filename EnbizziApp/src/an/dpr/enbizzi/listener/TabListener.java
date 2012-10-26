package an.dpr.enbizzi.listener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {

	private static final String TAG = TabListener.class.getName();
	private String tTag;
	private Activity tActivity;
	private Fragment tFragment;
	private Class<T> fClass;

	public TabListener(Activity act, String tag, Class<T> cls) {
		tTag = tag;
		tActivity = act;
		fClass = cls;
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		Log.d(TAG, "detach fragment "+tab.getTag());
		fragmentTransaction.detach(tFragment);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		Log.d(TAG, "onTabSelected "+tab.getTag());
		if (tFragment == null) {
			Log.d(TAG, "new fragment "+tab.getTag());
			tFragment = Fragment.instantiate(tActivity, fClass.getName());
			fragmentTransaction.add(android.R.id.content, tFragment, tTag);
		} else {
			Log.d(TAG, "attach exist fragment "+tab.getTag());
			fragmentTransaction.attach(tFragment);
		}
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		Log.d(TAG, "reselect tab " + tab.getTag());
	}

}
