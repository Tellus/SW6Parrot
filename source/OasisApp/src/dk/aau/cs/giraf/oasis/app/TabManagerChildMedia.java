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
 * Fragment which is used for managing: ChildMediaFrag, ChildAllMediaFrag, and ChildPrivateMediaFrag
 * 
 * @author Oasis
 *
 */
public class TabManagerChildMedia extends Fragment implements OnTabChangeListener {

	public static final String TAB_CHILDMEDIA = "ChildMedia";
	public static final String TAB_CHILDPRIVATEMEDIA = "ChildPrivateMedia";
	public static final String TAB_CHILDPUBLICMEDIA = "ChildPublicMedia";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.threetabsview, null);
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

		updateTab(TAB_CHILDMEDIA, R.id.threetab_1);

		Button b = (Button) getView().findViewById(R.id.b3Tab);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragParentTab.switchTab(FragParentTab.TABCHILD);
			}
		});
	}

	private void setupTabs() {
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_CHILDMEDIA, R.string.tab_childmedia, R.id.threetab_1));
		mTabHost.addTab(newTab(TAB_CHILDPRIVATEMEDIA, R.string.tab_privatemedia, R.id.threetab_2));
		mTabHost.addTab(newTab(TAB_CHILDPUBLICMEDIA, R.string.tab_publicmedia, R.id.threetab_3));
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
		if (TAB_CHILDMEDIA.equals(tabId)) {
			updateTab(tabId, R.id.threetab_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_CHILDPRIVATEMEDIA.equals(tabId)) {
			updateTab(tabId, R.id.threetab_2);
			mCurrentTab = 1;
			return;
		}
		if (TAB_CHILDPUBLICMEDIA.equals(tabId)) {
			updateTab(tabId, R.id.threetab_3);
			mCurrentTab = 2;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();

		if (TAB_CHILDMEDIA.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new ChildMediaFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_CHILDPRIVATEMEDIA.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new ChildPrivateMediaFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_CHILDPUBLICMEDIA.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new ChildAllMediaFrag(), tabId)
			.commit();
			return;
		}
	}
}