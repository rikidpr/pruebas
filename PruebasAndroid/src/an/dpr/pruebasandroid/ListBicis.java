package an.dpr.pruebasandroid;

import java.util.ArrayList;

import an.dpr.pruebasandroid.content.BiciContract;
import an.dpr.pruebasandroid.sqlite.Bici;
import an.dpr.pruebasandroid.sqlite.BiciDAO;
import android.app.ActionBar.LayoutParams;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

public class ListBicis extends ListActivity implements LoaderCallbacks<Cursor> {

	private static final String TAG = ListBicis.class.getName();
	private SimpleCursorAdapter mAdapter;
	private String[] PROJECTION = new String[] { BiciContract.COLUMN_ID,
			BiciContract.COLUMN_MARCA, BiciContract.COLUMN_MODELO,
			BiciContract.COLUMN_GRUPO, };
	private String WHERE = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "inicio onCreate");
		setContentView(R.layout.activity_list_bicis);
		// oldVersionList();
		loadersVersionList(); // con loaders, api 16 en adelante
	}

	private void loadersVersionList() {
		Log.d(TAG, "inicio loadersVersionList");

		// boton de volver a principal en logo app
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

		// columnas y vistas para meter la info
		String[] fromColumns = new String[] { BiciContract.COLUMN_MARCA,
				BiciContract.COLUMN_MODELO };
		int[] toViews = { R.id.listTxtMarca, R.id.listTxtModelo };

		mAdapter = new SimpleCursorAdapter(this, R.layout.bici_item_v4, null,
				fromColumns, toViews, 0);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
	}

	private void oldVersionList() {
		Log.d(TAG, "inicio oldVersionList");
		ListView listView = getListView();
		listView.setAdapter(new ArrayAdapter<Bici>(this,
				R.layout.list_item_bici, getBicis()));
	}

	private ArrayList<Bici> getBicis() {
		Log.d(TAG, "inicio getBicis()");
		BiciDAO dao = new BiciDAO(this);
		return dao.getList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "inicio onCreateOptionsMenu");
		getMenuInflater().inflate(R.menu.activity_list_bicis, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "inicio onOptionsItemSelected");
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
		Log.d(TAG, "inicio onCreateLoader");
		return new CursorLoader(this, BiciContract.CONTENT_URI, PROJECTION,
				WHERE, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.d(TAG, "inicio onLoadFinished");
		// Swap the new cursor in. (The framework will take care of closing the
		// old cursor once we return.)
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		Log.d(TAG, "inicio onLoaderReset");
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed. We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something when a list item is clicked
		Log.d(TAG, "inicio onListItemClick-"+position+"-"+id);
		Intent form = new Intent(this, BiciForm.class);
		form.putExtra(BiciContract.COLUMN_ID, id);
		startActivity(form);
	}
}
