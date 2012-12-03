package an.dpr.rubenslocatephone;

import an.dpr.rubenslocatephone.network.RubensService;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected static final String TAG = MainActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		refreshStatus();
	}

	@Override
	public void onResume() {
		super.onResume();
		refreshStatus();
	}

	private void refreshStatus() {
		TextView txtStatus = (TextView)findViewById(R.id.txtStatus);
		if (RubensService.isRunning()){
			txtStatus.setText(R.string.status_on);
		} else {
			txtStatus.setText(R.string.status_off);
		}
	}

	public void onClickRLP(View v) {
		final Location loc = RubensService.getLocation(MainActivity.this);
		AsyncTask<String, Integer, String> at = new AsyncTask<String, Integer, String>() {

			@Override
			protected String doInBackground(String... params) {
				String url = RubensService.URL_RUBENS_JSON;
				String retValue = RubensService.enviarPosicion(MainActivity.this,
						url, loc);
				return retValue;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.d(TAG, result);
			}

		};
		at.execute("epei");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean retValue = false;
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent locIntent = new Intent("an.dpr.accion.SETTINGS_LOCATE_PHONE");
			startActivity(locIntent);
			retValue = true;
			break;
		case R.id.menu_start_service:
			Log.i(TAG, "lanzamos servicio");
			RubensService.startService(this);
			break;
		case R.id.menu_stop_service:
			Log.i(TAG, "detenemos servicio");
			RubensService.stopService(this);
			break;
		case R.id.menu_registra_posicion:
			Log.i(TAG, "registra posicion");
			RubensService.registraPosicion(this);
			break;
		case R.id.menu_go_web:
			Log.i(TAG, "go to web");
			goToWeb();
			break;
		}
		refreshStatus();
		return retValue;
	}

	// Se lanza desde el boton goToWeb del main activity
	public void goToWeb(View v) {
		goToWeb();
	}

	public void goToWeb() {
		Intent go = new Intent(Intent.ACTION_VIEW);
		String url = "http://locatephone.rubenlor.es";
		go.setData(Uri.parse(url));
		startActivity(go);
	}
}
