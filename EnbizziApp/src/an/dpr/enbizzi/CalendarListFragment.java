package an.dpr.enbizzi;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.contentprovider.BikeCalendarContract;
import an.dpr.enbizzi.util.EnbizziContract;
import an.dpr.enbizzi.util.UtilFecha;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CalendarListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String TAG = CalendarListFragment.class.getName();
	private SimpleCursorAdapter adapter;
	private static final String[] PROJECTION = new String[] {
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
		adapter.setViewBinder(new ViewBinder());
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
		Intent i = new Intent(EnbizziContract.ACCION_DETALLE_SALIDA);
		i.putExtra(EnbizziContract.DATOS_SALIDA, bean);
		startActivity(i);
	}
	

	/**
	 * Bind para la vista de los detalles del listado
	 * @author rsaez
	 *
	 */
	private class ViewBinder implements SimpleCursorAdapter.ViewBinder {
		@Override
		public boolean setViewValue(View view, Cursor cursor, int index) {
			if (index == cursor.getColumnIndex(BikeCalendarContract.COL_DATE)) {
				// get a locale based string for the date
				DateFormat formatter = android.text.format.DateFormat
						.getDateFormat(getActivity().getApplicationContext());
				String sDate = cursor.getString((index));
				try {
					Log.d(TAG, "fDate:"+sDate);
					Date fDate = formatter.parse(sDate);
					sDate = UtilFecha.formatFecha(fDate);
				} catch (ParseException e) {
					Log.e(TAG,"", e);
				}
				((TextView) view).setText(sDate);
				return true;
			} else {
				return false;
			}
		}
	}

}