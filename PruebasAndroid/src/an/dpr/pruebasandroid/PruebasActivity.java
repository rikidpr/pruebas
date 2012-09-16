//package an.dpr.pruebasandroid;
//
//import an.dpr.pruebasandroid.content.PruebaCPContract;
//import android.app.Activity;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.TextView;
//
//public class PruebasActivity extends Activity {
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        
//        TextView textView1 = (TextView) findViewById(R.id.textView2);
//        Cursor cursor=getContentResolver().query(PruebaCPContract.URI, null, null, null, null);
//        textView1.setText(cursor.getCount());
//    }
//}