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
 * Fragment which is used for managing: ChildAppsFrag, and ChildAllAppsFrag.
 * 
 * @author Oasis
 *
 */
public class TabManagerChildApp extends Fragment implements OnTabChangeListener {

	public static final String TAB_CHILDAPPS = "ChildApps";
	public static final String TAB_ALLAPPS = "AllChildApps";

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

		updateTab(TAB_CHILDAPPS, R.id.twotab_1);

		Button b = (Button) getView().findViewById(R.id.b2Tab);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragParentTab.switchTab(FragParentTab.TABCHILD);
			}
		});
	}

	private void setupTabs() {
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_CHILDAPPS, R.string.tab_childapps, R.id.twotab_1));
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
		if (TAB_CHILDAPPS.equals(tabId)) {
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

		if (TAB_CHILDAPPS.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new ChildAppsFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_ALLAPPS.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new ChildAllAppsFrag(), tabId)
			.commit();
			return;
		}
	}
}