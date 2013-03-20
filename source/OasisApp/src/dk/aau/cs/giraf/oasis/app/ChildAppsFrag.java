package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;

/**
 * Fragment which is used for listing all the applications a child has on its list in the system
 * 
 * @author Oasis
 *
 */
public class ChildAppsFrag extends ListFragment {

	Helper helper;
	List<App> list;
	App app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.apps_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity().getApplicationContext());

		if (MainActivity.child != null) {
			list = helper.appsHelper.getAppsByProfile(MainActivity.child);
			setListAdapter(new AppListAdapter(getActivity().getApplicationContext(), list));
		} else {
			setListAdapter(null);
		}
		
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
				app = (App) getListAdapter().getItem(position);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Fjern App");
				builder.setMessage("Sikker på at du vil fjerne " + app.getName() +"?");
				builder.setPositiveButton("Ja", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						helper.appsHelper.removeAppAttachmentToProfile(app, MainActivity.child);
						list = helper.appsHelper.getAppsByProfile(MainActivity.child);
						setListAdapter(new AppListAdapter(getActivity().getApplicationContext(), list));
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("Nej", null);
				AlertDialog alert = builder.create();
				alert.show();
				
				return false;
			}
		});
	}

}
