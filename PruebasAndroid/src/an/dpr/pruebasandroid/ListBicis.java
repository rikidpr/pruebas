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
        //oldVersionList();
        loadersVersionList(); //con loaders, api 16 en adelante
    }
	
	private void loadersVersionList(){
		getActionBar().setDisplayHomeAsUpEnabled(true);

        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);
		
		//columnas y vistas para meter la info
		String[] fromColumns = new String[]{BiciContract.COLUMN_MARCA, BiciContract.COLUMN_MODELO};
		int[] toViews = {R.id.text_marca, R.id.text_modelo}
        
        mAdapter = new SimpleCursorAdapter(this, R.layout.bici_item, null, fromColumns, toViews, 0);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
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
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(this, BiciContract.URI,
                PROJECTION, WHERE, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
	}
}
