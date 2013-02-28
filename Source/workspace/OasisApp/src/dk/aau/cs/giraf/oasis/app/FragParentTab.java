package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Manages all the TabManager fragments
 * 
 * @author Oasis
 *
 */
public class FragParentTab extends Activity {

	private int tabView;
	public final static int TABPROFILE = 0;
	public final static int TABAPP = 1;
	public final static int TABMEDIA = 2;
	public final static int TABALLPROFILES = 3;
	public final static int TABALLDEPARTMENTS = 4;
	public final static int TABCHILD = 5;
	public final static int TABCHILDAPP = 6;
	public final static int TABCHILDMEDIA = 7;
	static FragmentManager t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View main_layout = findViewById(android.R.id.content).getRootView();
		main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			tabView = extras.getInt("tabView");
		} else {
			tabView = -1;
		}

		setContentView(R.layout.fragments_view);
		
		findViewById(R.id.fragUpperLayout).setBackgroundColor(MainActivity.color);

		t = getFragmentManager();

		switch(tabView) {
		case TABPROFILE:
			t.beginTransaction().add(R.id.fDetails, new TabManagerProfile()).commit();
			break;
		case TABALLPROFILES:
			t.beginTransaction().add(R.id.fDetails, new TabManagerAllProfiles()).commit();
			break;
		case TABALLDEPARTMENTS:
			t.beginTransaction().add(R.id.fDetails, new TabManagerAllDepartments()).commit();
			break;
		case TABCHILD:
			t.beginTransaction().add(R.id.fDetails, new TabManagerChild()).commit();
		}
	}

	public static void switchTab(int tabViewId) {

		switch(tabViewId) {
		case TABPROFILE:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerProfile()).commit();
			break;
		case TABMEDIA:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerMedia()).commit();
			break;
		case TABAPP:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerApp()).commit();
			break;
		case TABCHILD:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerChild()).commit();
			break;
		case TABCHILDMEDIA:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerChildMedia()).commit();
			break;
		case TABCHILDAPP:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerChildApp()).commit();
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		t = getFragmentManager();
	}
}
