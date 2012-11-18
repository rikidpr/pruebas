package an.dpr.enbizzi;

import java.util.Map;

import an.dpr.enbizzi.calendar.bean.AemetPeriodo;
import an.dpr.enbizzi.calendar.bean.AemetViento;
import an.dpr.enbizzi.calendar.bean.PrediccionAemet;
import an.dpr.enbizzi.util.UtilFecha;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OracheSalidaFragment extends Fragment {

	private static final String TAG = OracheSalidaFragment.class.getName();
	
	private static String LOCALIDAD = "LOCALIDAD";
	private static String FECHA = "FECHA";
	private static String MIN_TEMP="MIN_TEMP";
	private static String MAX_TEMP = "MAX_TEMP";
	private static String VIENTO_MANYANA = "VIENTO_MANYANA";
	private static String VIENTO_TARDE = "VIENTO_TARDE";
	private static String PRECIPITACION_MANYANA = "PRECIPITACION_MANYANA";
	private static String PRECIPITACION_TARDE = "PRECIPITACION_TARDE";
	
	private TextView txtLocalidad;
	private TextView txtFecha;
	private TextView txtMinTemp;
	private TextView txtMaxTemp;
	private TextView txtMnyViento;
	private TextView txtTardeViento;
	private TextView txtMnyProbPre;
	private TextView txtTardeProbPre;

	
	// lifecycle
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		super.onCreateView(inflater, container, savedInstanceState);
		View view =  inflater.inflate(R.layout.orache_salida_fragment, container,
				false);
		
		txtLocalidad=(TextView)view.findViewById(R.id.txtLocalidad);
		txtFecha=(TextView)view.findViewById(R.id.txtFecha);
		txtMinTemp=(TextView)view.findViewById(R.id.txtMinTemp);
		txtMaxTemp=(TextView)view.findViewById(R.id.txtMaxTemp);
		txtMnyViento=(TextView)view.findViewById(R.id.txtMnyViento);
		txtTardeViento=(TextView)view.findViewById(R.id.txtTardeViento);
		txtMnyProbPre=(TextView)view.findViewById(R.id.txtMnyProbPre);
		txtTardeProbPre=(TextView)view.findViewById(R.id.txtTardeProbPre);
		
		//ahora seteamos la info en las vistas
		Bundle aemetInfo = null;
		if (savedInstanceState!=null){
			aemetInfo = savedInstanceState;
		} else {
			aemetInfo = getArguments();
		}
		setAemetInfo(aemetInfo);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.d(TAG, "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.d(TAG, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.d(TAG, "onDetach");
		super.onDetach();
	}
	
	
	
	//metodos funcionales
	public static OracheSalidaFragment newInstance(PrediccionAemet prediccion){
		OracheSalidaFragment salida = new OracheSalidaFragment();
		Bundle args = new Bundle();
		args.putString(LOCALIDAD, prediccion.getLocalidad());
		args.putString(FECHA, UtilFecha.formatFecha(prediccion.getDia()));
		args.putString(MIN_TEMP, String.valueOf(prediccion.getMinTemperatura()));
		args.putString(MAX_TEMP, String.valueOf(prediccion.getMaxTemperatura()));
		args = putViento(args, prediccion);
		args = putProbPrecipitacion(args, prediccion);
		salida.setArguments(args);
		return salida;
	}
	
	
	private static Bundle putViento(Bundle args, PrediccionAemet prediccion){
		StringBuilder sb;
		for (AemetViento av : prediccion.getViento()){
			switch(av.getPeriodo()){
			case p0012:
				sb = new StringBuilder();
				sb.append(av.getDireccion()).append(" ")
					.append(av.getVelocidad());
				args.putString(VIENTO_MANYANA, sb.toString());
				
				break;
			case p1224:
				sb = new StringBuilder();
				sb.append(av.getDireccion()).append(" ")
					.append(av.getVelocidad());
				args.putString(VIENTO_TARDE, sb.toString());
				break;
			default:
				break;
			}
		}
		return args;
	}
	
	private static Bundle putProbPrecipitacion(Bundle args, PrediccionAemet prediccion){
		StringBuilder sb;
		Map<AemetPeriodo, Integer> mapa = prediccion.getProbPrecipitacion();
		for (AemetPeriodo per : mapa.keySet()){
			switch(per){
			case p0012:
				args.putString(PRECIPITACION_MANYANA,
						String.valueOf(mapa.get(per)));
				break;
			case p1224:
				args.putString(PRECIPITACION_TARDE, 
				        String.valueOf(mapa.get(per)));
				break;
			}
		}
		return args;
	}

	private void setAemetInfo(Bundle args) {
		if (args != null) {
			txtLocalidad.setText(fuckNull(args.getString(LOCALIDAD)));
			txtFecha.setText(fuckNull(args.getString(FECHA)));
			txtMinTemp.setText(fuckNull(args.getString(MIN_TEMP))
					+ getString(R.string.grados_temp));
			txtMaxTemp.setText(fuckNull(args.getString(MAX_TEMP))
					+ getString(R.string.grados_temp));
			txtMnyViento.setText(fuckNull(args.getString(VIENTO_MANYANA))
					+ getString(R.string.kmh));
			txtTardeViento.setText(fuckNull(args.getString(VIENTO_TARDE))
					+ getString(R.string.kmh));
			txtMnyProbPre.setText(fuckNull(args
					.getString(PRECIPITACION_MANYANA))
					+ getString(R.string.percent));
			txtTardeProbPre.setText(fuckNull(args
					.getString(PRECIPITACION_TARDE))
					+ getString(R.string.percent));
		}
	}

	private String fuckNull(String cadena){
		return cadena!=null ? cadena :"";
	}
	
}



