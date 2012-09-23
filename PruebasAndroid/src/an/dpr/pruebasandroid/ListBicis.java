package an.dpr.pruebasandroid;

import java.util.ArrayList;

import an.dpr.pruebasandroid.content.BiciContract;
import an.dpr.pruebasandroid.sqlite.Bici;
import an.dpr.pruebasandroid.sqlite.BiciDAO;
import android.app.ListActivity;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListBicis extends ListActivity {//implements LoaderCallbacks<Cursor>{

	private SimpleCursorAdapter mAdapter;
	private String[] PROJECTION = new String[]{
			BiciContract.COLUMN_ID,
			BiciContract.COLUMN_MARCA,
			BiciContract.COLUMN_MODELO,
			BiciContract.COLUMN_GRUPO,
	};
	private String WHERE = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bicis);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

//        // Create a progress bar to display while the list loads
//        ProgressBar progressBar = new ProgressBar(this);
//        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//        progressBar.setIndeterminate(true);
//        getListView().setEmptyView(progressBar);
//
//        // Must add the progress bar to the root of the layout
//        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//        root.addView(progressBar);
        
//        mAdapter = new SimpleCur
        oldVersionList();
    }
    
    

    private void oldVersionList() {
    	ListView listView = getListView();
    	listView.setAdapter(new ArrayAdapter<Bici>(this, R.layout.list_item_bici, getBicis()));
	}


	private ArrayList<Bici> getBicis() {
		BiciDAO dao = new BiciDAO(this);
		return dao.getList();
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_bicis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
