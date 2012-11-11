package an.dpr.enbizzi;

import java.util.Date;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.bean.PrediccionAemet;
import an.dpr.enbizzi.calendar.util.DetalleSalidaUtil;
import an.dpr.enbizzi.util.EnbizziContract;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OracheSalidaFragment extends Fragment {

	private static final String TAG = OracheSalidaFragment.class.getName();


	// lifecycle
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach");
		//FIXME no se en que metodo es el primero en que se tiene acceso a los extras!!
		cargarPrediccion();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.orache_salida_fragment, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated");
	}

	@Override
	public void onStart() {
		Log.d(TAG, "onStart");
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
	}

	@Override
	public void onStop() {
		Log.d(TAG, "onStop");
	}

	@Override
	public void onDestroyView() {
		Log.d(TAG, "onDestroyView");
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onDetach() {
		Log.d(TAG, "onDetach");
	}
	
	//metodos
	private void cargarPrediccion(){
		Bundle extras = getActivity().getIntent().getExtras();
		if (extras.containsKey(EnbizziContract.DATOS_SALIDA)) {
			BikeCalendar salida = (BikeCalendar) extras.get(EnbizziContract.DATOS_SALIDA);
			if (salida != null) {
				AsyncTask<Object, Integer, PrediccionAemet> at = 
						new AsyncTask<Object, Integer, PrediccionAemet>(){
					
					@Override
					protected PrediccionAemet doInBackground(Object... params){
						Log.d(TAG, "doInBackground:"+params);
						Date fecha = (Date)params[0];
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
	
	private void setPrediccionOnView(PrediccionAemet prediccion){
		
	}
	
}

