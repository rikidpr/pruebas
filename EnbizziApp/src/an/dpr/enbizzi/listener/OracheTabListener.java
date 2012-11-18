package an.dpr.enbizzi.listener;

import java.util.Calendar;
import java.util.Date;

import an.dpr.enbizzi.OracheSalidaAuxFragment;
import an.dpr.enbizzi.OracheSalidaFragment;
import an.dpr.enbizzi.R;
import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.bean.PrediccionAemet;
import an.dpr.enbizzi.calendar.util.DetalleSalidaUtil;
import an.dpr.enbizzi.util.EnbizziContract;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class OracheTabListener implements ActionBar.TabListener {

	private static final String TAG = TabListener.class.getName();
	private String tTag;
	private Activity tActivity;
	private OracheSalidaFragment oracheF;
	private OracheSalidaAuxFragment auxF;
	private Fragment actualF;
	private PrediccionAemet aemetInfo;

	public OracheTabListener(Activity act, String tag) {
		tTag = tag;
		tActivity = act;
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		Log.d(TAG, "detach fragment "+tab.getTag());
		fragmentTransaction.detach(actualF);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		Log.d(TAG, "onTabSelected "+tab.getTag());
		if (actualF == null) {
			Log.d(TAG, "new fragment "+tab.getTag());
			auxF = (OracheSalidaAuxFragment) Fragment.instantiate(tActivity,
					OracheSalidaAuxFragment.class.getName());
			auxF.setTextInfo(tActivity.getString(R.string.cargando_info_aemet));
			actualF = auxF;
			fragmentTransaction.add(android.R.id.content, actualF, tTag);
			cargarPrediccion();
		} else {
			Log.d(TAG, "attach exist fragment "+tab.getTag());
			fragmentTransaction.attach(actualF);
		}
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		Log.d(TAG, "reselect tab " + tab.getTag());
	}
	
	
	//metodos
	private void cargarPrediccion(){
		Bundle extras = tActivity.getIntent().getExtras();
		if (extras.containsKey(EnbizziContract.DATOS_SALIDA)) {
			BikeCalendar salida = (BikeCalendar) extras.get(EnbizziContract.DATOS_SALIDA);
			if (salida != null) {
				AsyncTask<Object, Integer, PrediccionAemet> at = 
						new AsyncTask<Object, Integer, PrediccionAemet>(){
					
					@Override
					protected PrediccionAemet doInBackground(Object... params){
						Log.d(TAG, "doInBackground:"+params);
						Date fecha = (Date)params[0];
//						Date fecha = getFechaPruebas();//(Date)params[0];
						Integer aemetCode = (Integer)params[1];
						PrediccionAemet pred = null;
						try{
							pred= DetalleSalidaUtil.getPrediccion(aemetCode, fecha);
						} catch(Exception e){
							Log.e(TAG,"Error obteniendo prediccion", e);
						}
						return pred;
					}
					
					@Override
					protected void onProgressUpdate(Integer... progress){
						Log.d(TAG, "progreso:"+progress);
					}
					
					@Override
					protected void onPostExecute(PrediccionAemet prediccion){
						Log.d(TAG, "onPostExecute:"+prediccion);
						setPrediccionOnView(prediccion);
					}
				};
				at.execute(new Object[]{salida.getDate(), salida.getAemetStart()});
			}
		}
	}
	
	private Date getFechaPruebas(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.DAY_OF_MONTH, 20);
		return cal.getTime();
	}

	private void setPrediccionOnView(PrediccionAemet prediccion){
		this.aemetInfo = prediccion;
		if (aemetInfo != null){
		FragmentManager fm = tActivity.getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.detach(actualF);
			oracheF = OracheSalidaFragment.newInstance(aemetInfo);
			actualF = oracheF;
			ft.replace(R.id.fragment_container, actualF);
//			ft.addToBackStack(null);
			ft.commit();
		} else {
			auxF.setTextInfo(tActivity.getString(R.string.no_obtenida_info_aemet));
		}
	}
}
