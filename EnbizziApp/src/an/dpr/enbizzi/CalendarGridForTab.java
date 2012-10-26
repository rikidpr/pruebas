package an.dpr.enbizzi;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class CalendarGridForTab extends Fragment {

	private static final String TAG = CalendarGridForTab.class.getName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View ret = inflater.inflate(R.layout.calendar_grid, container, false);
		GridView gv = (GridView) ret.findViewById(R.id.calendarGW);
		gv.setAdapter(new CalendarImageAdapter(this.getActivity()));

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				goToMonthStages(pos, id);
			}
		});
		return ret;
	}

	protected void goToMonthStages(int pos, long id) {
		StringBuilder text = new StringBuilder("mes ").append(pos)
				.append("/ id").append(id);
		Log.d(TAG, text.toString());
		Toast.makeText(this.getActivity(), text.toString(), Toast.LENGTH_SHORT)
				.show();
	}

	static class CalendarImageAdapter extends BaseAdapter {

		private static final int[] meses = new int[] { R.drawable.enero,
				R.drawable.febrero, R.drawable.marzo, R.drawable.abril,
				R.drawable.mayo, R.drawable.junio, R.drawable.julio,
				R.drawable.agosto, R.drawable.septiembre, R.drawable.octubre,
				R.drawable.noviembre, R.drawable.diciembre, };
		private Context context;

		public CalendarImageAdapter(Context ctx) {
			context = ctx;
		}

		@Override
		public int getCount() {
			return meses.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) { // if it's not recycled, initialize some
										// attributes
				imageView = new ImageView(context);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(meses[position]);
			return imageView;
		}
	}
}