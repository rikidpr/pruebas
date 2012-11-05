package an.dpr.enbizzi;

import an.dpr.enbizzi.listener.TabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DetalleSalida extends Activity {
	
	private static final String TAG = DetalleSalida.class.getName();
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	public static final String TAB_DATOS = "TAB_DATOS";
	public static final String TAB_MAPA = "TAB_MAPA";
	public static final String TAB_ORACHE  = "TAB_ORACHE";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_detalle_salida);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		
		ActionBar.Tab tab = actionBar.newTab();
		tab.setTag(TAB_DATOS);
		tab.setText(R.string.tab_datos);
		tab.setTabListener(new TabListener<DatosSalidaFragment>(this,
				TAB_DATOS, DatosSalidaFragment.class));
		actionBar.addTab(tab);

//		tab = actionBar.newTab();
//		tab.setTag(TAB_MAPA);
//		tab.setText(R.string.tab_mapa);
//		tab.setTabListener(new TabListener<DatosSalidaFragment>(this,
//				TAB_DATOS, DatosSalidaFragment.class));
//		actionBar.addTab(tab);
//		
		tab = actionBar.newTab();
		tab.setTag(TAB_ORACHE);
		tab.setText(R.string.tab_orache);
		tab.setTabListener(new TabListener<OracheSalidaFragment>(this,
				TAB_ORACHE, OracheSalidaFragment.class));
		actionBar.addTab(tab);
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
		getMenuInflater().inflate(R.menu.activity_detalle_salida, menu);
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
