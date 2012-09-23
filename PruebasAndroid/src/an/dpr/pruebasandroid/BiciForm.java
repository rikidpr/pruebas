package an.dpr.pruebasandroid;

import an.dpr.pruebasandroid.sqlite.Bici;
import an.dpr.pruebasandroid.sqlite.BiciDAO;
import an.dpr.pruebasandroid.util.NotificationUtil;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BiciForm extends Activity {
	
	private static final String TAG = BiciForm.class.getName();
	private BiciDAO dao;
	private EditText txtMarca;
	private EditText txtModelo;
	private EditText txtGrupo;
	private EditText txtId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bici_form);
        
        txtId= (EditText) findViewById(R.id.txtId);
        txtGrupo = (EditText) findViewById(R.id.txtGrupo);
        txtMarca = (EditText) findViewById(R.id.txtMarca);
        txtModelo = (EditText) findViewById(R.id.txtModelo);
        dao = new BiciDAO(this);
        
        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				guardarInfo();
			}});
    }

    protected void guardarInfo() {
    	Bici bici = new Bici();
    	bici.setGrupo(txtGrupo.getText().toString());
    	bici.setMarca(txtMarca.getText().toString());
    	bici.setModelo(txtModelo.getText().toString());
    	Integer id = 0;
    	try {
    		id = Integer.valueOf(txtId.getText().toString());
    	} catch(NumberFormatException e){
    		Log.e(TAG, "id no numerico");
    	}
    	bici.setBiciId(id);
    	long ret = dao.persist(bici);
    	Log.d(TAG, "resultado de persist;"+ret);
    	NotificationUtil.setToastMsg(this, "Resulado save:"+ret, Toast.LENGTH_LONG);
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bici_form, menu);
        return true;
    }
}
