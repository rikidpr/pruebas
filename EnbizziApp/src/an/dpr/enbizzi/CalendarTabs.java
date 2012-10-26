package an.dpr.enbizzi;

import an.dpr.enbizzi.listener.TabListener;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CalendarTabs extends FragmentActivity {

	private static final String TAG = CalendarTabs.class.getName();

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final String TAB_CALENDARIO = "TAB_CALENDARIO";
	private static final String TAB_PROXIMAS = "TAB_PROXIMAS";
	private static final String TAB_MARCHAS = "TAB_MARCHAS";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_tabs);
		Log.d(TAG, "creado layout");

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Log.d(TAG, "navegacion tab");
		actionBar.setHomeButtonEnabled(true);

		// For each of the sections in the app, add a tab to the action bar.
		ActionBar.Tab tab = actionBar.newTab();
		tab.setTag(TAB_CALENDARIO);
		tab.setText(R.string.tab_calendario);
		tab.setTabListener(new TabListener<CalendarGridForTab>(this,
				TAB_CALENDARIO, CalendarGridForTab.class));
		actionBar.addTab(tab);
		Log.d(TAG, "add tab calendario");

		tab = actionBar.newTab();
		tab.setTag(TAB_PROXIMAS);
		tab.setText(R.string.tab_proximas_salidas);
		tab.setTabListener(new TabListener<CalendarListFragment>(this,
				TAB_PROXIMAS, CalendarListFragment.class));
		actionBar.addTab(tab);
		Log.d(TAG, "add tab salidas");

		tab = actionBar.newTab();
		tab.setTag(TAB_MARCHAS);
		tab.setText(R.string.tab_marchas);
		tab.setTabListener(new TabListener<MarchasFragment>(this, TAB_MARCHAS,
				MarchasFragment.class));
		actionBar.addTab(tab);
		Log.d(TAG, "add tab marchas");
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_calendar_tabs, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = false;
		Log.d(TAG, String.valueOf(item.getItemId()));
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent("an.dpr.enbizzi.MAIN");
			//borramos el historial al volver a home
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			ret = true;
		default:
			ret = super.onOptionsItemSelected(item);
		}
		return ret;
	}
}
