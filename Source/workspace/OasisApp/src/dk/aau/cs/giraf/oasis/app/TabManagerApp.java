package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * Fragment which is used for managing: MyAppsFrag, and AllAppsFrag
 * 
 * @author Oasis
 *
 */
public class TabManagerApp extends Fragment implements OnTabChangeListener {

	public static final String TAB_MYAPPS = "MyApps";
	public static final String TAB_ALLAPPS = "AllApps";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.twotabsview, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		setupTabs();
		return mRoot;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);

		updateTab(TAB_MYAPPS, R.id.twotab_1);

		Button b = (Button) getView().findViewById(R.id.b2Tab);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragParentTab.switchTab(FragParentTab.TABPROFILE);
			}
		});
	}

	private void setupTabs() {
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_MYAPPS, R.string.tab_myapps, R.id.twotab_1));
		mTabHost.addTab(newTab(TAB_ALLAPPS, R.string.tab_allapps, R.id.twotab_2));
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {

		View indicator = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab, (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.tvtab)).setText(labelId);

		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		if (TAB_MYAPPS.equals(tabId)) {
			updateTab(tabId, R.id.twotab_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_ALLAPPS.equals(tabId)) {
			updateTab(tabId, R.id.twotab_2);
			mCurrentTab = 1;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();

		if (TAB_MYAPPS.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyAppsFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_ALLAPPS.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new AllAppsFrag(), tabId)
			.commit();
			return;
		}
	}
}