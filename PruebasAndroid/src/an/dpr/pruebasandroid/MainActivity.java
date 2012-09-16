package an.dpr.pruebasandroid;

import an.dpr.pruebasandroid.content.PruebaCPContract;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        pruebaCursor();
    }

    private void pruebaCursor() {
    	TextView textView1 = (TextView) findViewById(R.id.textView1);
        Cursor cursor=getContentResolver().query(PruebaCPContract.URI, null, null, null, null);
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()){
        	do{
				sb.append(cursor.getString(cursor.getColumnIndex(PruebaCPContract.COLUMN_ID)));
				sb.append("-");
				sb.append(cursor.getString(cursor.getColumnIndex(PruebaCPContract.COLUMN_VALUE)));
        	}while(cursor.moveToNext());
        } else {
        	sb.append("no localizado");
        }
        textView1.setText(sb.toString());		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
}
