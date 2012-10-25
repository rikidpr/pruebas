package an.dpr.enbizzi;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarTabs extends FragmentActivity implements ActionBar.TabListener {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final String TAB_SALIDAS="TAB_SALIDAS";
    private static final String TAB_PROXIMAS="TAB_PROXIMAS";
    private static final String TAB_MARCHAS="TAB_MARCHAS";
    
    private Fragment salidasFr;
    private Fragment proximasFr;
    private Fragment marchasFr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_tabs);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        ActionBar.Tab tab = actionBar.newTab();
        tab.setTag(TAB_SALIDAS);
        tab.setTabListener(this);
        actionBar.addTab(tab);
        
        tab = actionBar.newTab();
        tab.setTag(TAB_PROXIMAS);
        tab.setTabListener(this);
        actionBar.addTab(tab);
        
        tab = actionBar.newTab();
        tab.setTag(TAB_MARCHAS);
        tab.setTabListener(this);
        actionBar.addTab(tab);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calendar_tabs, menu);
        return true;
    }

    

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, show the tab contents in the container
    	if (TAB_SALIDAS.equals(tab.getTag())){
    		fragmentTransaction.attach(getSalidasFr());
    	} else {
    		Toast.makeText(this, "por implementar", Toast.LENGTH_SHORT).show();
//	        Fragment fragment = new DummySectionFragment();
//	        Bundle args = new Bundle();
//	        args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, tab.getPosition() + 1);
//	        fragment.setArguments(args);
//	        getSupportFragmentManager().beginTransaction()
//	                .replace(R.id.container, fragment)
//	                .commit();
    	}
    }

    private Fragment getSalidasFr() {
		if (salidasFr==null){
			 salidasFr = Fragment.instantiate(this, CalendarGridFragment.class.getName());
		}
		return salidasFr;
	}

	@Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        public DummySectionFragment() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            Bundle args = getArguments();
            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
            return textView;
        }
    }
}
