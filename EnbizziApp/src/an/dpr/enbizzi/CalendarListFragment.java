package an.dpr.enbizzi;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.contentprovider.BikeCalendarContract;
import an.dpr.enbizzi.util.NotificationUtil;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CalendarListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String TAG = CalendarListFragment.class.getName();
	private SimpleCursorAdapter adapter;
	private static final String[] PROJECTION = new String[] {
			// BikeCalendarContract.COL_ID,
			BikeCalendarContract.COL_DATE, BikeCalendarContract.COL_ROUTE };

	private String where = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		ProgressBar pb = new ProgressBar(this.getActivity());
//		pb.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT));
//		pb.setIndeterminate(true);
//		getListView().setEmptyView(pb);

		int[] toView = new int[] { R.id.txtDate, R.id.txtRoute };

		adapter = new SimpleCursorAdapter(this.getActivity(),
				R.layout.calendar_item, null, PROJECTION, toView, 0);
		setListAdapter(adapter);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Log.d(TAG, "empieza onCreateLoader");
		return new CursorLoader(this.getActivity(),
				BikeCalendarContract.CONTENT_URI,
				BikeCalendarContract.COLUMN_NAMES, where, null, "date asc");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.d(TAG, "empieza onLoadFinished");
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		Log.d(TAG, "empieza onLoadReset");
		adapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something when a list item is clicked
		Log.d(TAG, "inicio onListItemClick-" + position + "-" + id);
		Cursor c = null;
		BikeCalendar bean = null;
		try {
			c = this.getActivity()
					.getContentResolver()
					.query(BikeCalendarContract.getQueryUri(id),
							BikeCalendarContract.COLUMN_NAMES, null, null, null);
			if (c.moveToFirst()) {
				bean = BikeCalendarContract.getBikeCalendar(c);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error onListItemClick", e);
		} finally {
			c.close();
		}
		DialogFragment df = new DialogFragment();
		NotificationUtil.setToastMsg(this.getActivity(),
				bean != null ? bean.toString() : "no localizado " + id,
				Toast.LENGTH_LONG);
	}
	
}
