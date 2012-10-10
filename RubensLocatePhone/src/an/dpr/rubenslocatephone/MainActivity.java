package an.dpr.rubenslocatephone;

import an.dpr.rubenslocatephone.location.LocationPruebas;
import an.dpr.rubenslocatephone.network.RubensService;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    protected static final String TAG = MainActivity.class.getName();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnRLP = (Button)findViewById(R.id.btnRLP);
        btnRLP.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				rubensLocatePhone();
			}
        	
        });

        Button btnLoc = (Button)findViewById(R.id.btnLoc);
        btnLoc.setOnClickListener(new OnClickListener(){
        	
        	@Override
        	public void onClick(View v) {
        		pruebaLocate();
        	}
        });
    }

    protected void pruebaLocate() {
		Location loc = RubensService.getLocation(MainActivity.this);
		StringBuilder text = new StringBuilder();
		if (loc!=null){
			text.append(loc.getLatitude())
				.append(" / ")
				.append(loc.getLongitude());
		} else {
			text.append("kk, nulo!!");
		}
		Toast.makeText(this, text.toString(), Toast.LENGTH_LONG).show();
		LocationPruebas lp = new LocationPruebas();
		lp.gpsPrueba(this);
	}

	protected void rubensLocatePhone() {
    	
    	AsyncTask<String, Integer, String> at = new AsyncTask<String, Integer, String>(){

			@Override
			protected String doInBackground(String... params) {
				String url = RubensService.URL_RUBENS_JSON;
				String retValue = RubensService.leerInfo(MainActivity.this, url);
				return retValue;
			}
			
			@Override
			protected void onPostExecute(String result){
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
	public boolean onOptionsItemSelected(MenuItem item){
		boolean retValue = false;
		switch(item.getItemId()){
		case R.id.menu_settings:
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			retValue = true;
			break;
		}
		return retValue;
	}
}
