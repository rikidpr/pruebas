package an.dpr.enbizzi;

import java.text.SimpleDateFormat;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.util.EnbizziContract;
import an.dpr.enbizzi.util.UtilFecha;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DatosSalidaFragment extends Fragment {

	private static final String TAG = DatosSalidaFragment.class.getName();

	private TextView txtFecha;
	private TextView txtHora;
	private TextView txtAlt;
	private TextView txtDif;
	private TextView txtKm;
	private TextView txtParada;
	private TextView txtRuta;
	private TextView txtRutaRet;
	private TextView txtTipo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.datos_salida_fragment, container,
				false);
		txtFecha = (TextView) view.findViewById(R.id.txtFecha);
		txtHora = (TextView) view.findViewById(R.id.txtHora);
		txtAlt= (TextView) view.findViewById(R.id.txtAltitudAcumulada);
		txtDif = (TextView) view.findViewById(R.id.txtDificultad);
		txtKm = (TextView) view.findViewById(R.id.txtKm);
		txtParada = (TextView) view.findViewById(R.id.txtParada);
		txtRuta = (TextView) view.findViewById(R.id.txtRuta);
		txtRutaRet = (TextView) view.findViewById(R.id.txtRutaRetorno);
		txtTipo = (TextView) view.findViewById(R.id.txtTipo);

		fillData(view);

		return view;
	}

	private void fillData(View view) {
		Bundle extras = getActivity().getIntent().getExtras();
		if (extras.containsKey(EnbizziContract.DATOS_SALIDA)) {
			BikeCalendar salida = (BikeCalendar) extras.get(EnbizziContract.DATOS_SALIDA);
			if (salida != null) {
				txtFecha.setText(UtilFecha.formatFecha(salida.getDate()));
				txtHora.setText(UtilFecha.verHora(salida.getDate()));
				txtAlt.setText(salida.getElevationGain()+"m");
				txtDif.setText(getString(salida.getDifficulty().getKeyString()));
				txtKm.setText(salida.getKm()+" km");
				txtParada.setText(salida.getStop());
				txtRuta.setText(salida.getRoute());
				txtRutaRet.setText(salida.getReturnRoute());
				txtTipo.setText(getString(salida.getType().getKeyString()));
			}
		}
	}
}
