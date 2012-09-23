package an.dpr.pruebasandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnList = (Button)findViewById(R.id.btnList);
        btnList.setOnClickListener( new OnClickListener(){

			public void onClick(View v) {
				mostrarListado();
			}

        	
        });
        
        Button btnNew = (Button)findViewById(R.id.btnNew);
        btnNew.setOnClickListener( new OnClickListener(){
        	

			public void onClick(View arg0) {
				mostrarFormulario();
			}
        	
        	
        });
    }

    public void mostrarFormulario() {
    	Intent intent = new Intent(this, BiciForm.class);
    	startActivity(intent);
    }
    
    private void mostrarListado() {
    	Intent intent = new Intent(this, ListBicis.class);
    	startActivity(intent);
    }

//    private void pruebaCursor() {
//    	TextView textView1 = (TextView) findViewById(R.id.textView1);
//        Cursor cursor=getContentResolver().query(PruebaCPContract.URI, null, null, null, null);
//        StringBuilder sb = new StringBuilder();
//        if (cursor.moveToFirst()){
//        	do{
//				sb.append(cursor.getString(cursor.getColumnIndex(PruebaCPContract.COLUMN_ID)));
//				sb.append("-");
//				sb.append(cursor.getString(cursor.getColumnIndex(PruebaCPContract.COLUMN_VALUE)));
//        	}while(cursor.moveToNext());
//        } else {
//        	sb.append("no localizado");
//        }
//        textView1.setText(sb.toString());		
//	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
}
