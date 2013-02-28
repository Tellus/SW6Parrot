package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Fragment which is used for listing all guardians of a child in the system
 * 
 * @author Oasis
 *
 */
public class ChildGuardiansFrag extends ExpandableListFragment {

	Helper helper;
	List<Profile> list;
	TextView tvHeader;
	ChildListAdapter adapter;
	Profile guardProfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.children_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity().getApplicationContext());

		Button b = (Button) getView().findViewById(R.id.bAddProfile);
		b.setText("Tilføj Pædagog");
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				List<Profile> guardList = helper.profilesHelper.getGuardians();
				List<Profile> valueList = new ArrayList<Profile>();
				for (Profile pItem : guardList) {
					if (!list.contains(pItem)) {
						valueList.add(pItem);
					}
				}

				List<String> items = new ArrayList<String>();
				for (Profile p : valueList) {
					String name = ""+p.getId(); 
					name += " " + p.getFirstname();
					if (p.getMiddlename() != null) {
						name += " " + p.getMiddlename();
					}
					name += " " + p.getSurname();
					items.add(name);
				}

				final tmpListAdapter tmpAdapter = new tmpListAdapter(getActivity(), R.layout.children_list_childitem, items);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Vælg en Pædagog");
				builder.setAdapter(tmpAdapter,	new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						String tmpPath = tmpAdapter.getItem(item);
						String tmpA[] = tmpPath.split(" ");
						Profile newP = helper.profilesHelper.getProfileById(Long.parseLong(tmpA[0]));
						helper.profilesHelper.attachChildToGuardian(MainActivity.child, newP);

						updateList();
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("Cancel", null);
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		if (MainActivity.child != null) {
			updateList();
		} else {
			setListAdapter(null);
		}

		getExpandableListView().setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

				guardProfile = adapter.getChild(groupPosition, childPosition);

				String name = guardProfile.getFirstname();
				if (guardProfile.getMiddlename() != null) {
					name += " " + guardProfile.getMiddlename();
				}
				name += " " + guardProfile.getSurname();

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setCancelable(true);
				builder.setTitle("Profil");
				LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = mInflater.inflate(R.layout.profilepopup_view, null, false);
				TextView tName = (TextView) view.findViewById(R.id.tvName);
				tName.setText(name);
				TextView tPhone = (TextView) view.findViewById(R.id.tvPhone);
				tPhone.setText(""+guardProfile.getPhone());
				ImageView iProfileImg = (ImageView) view.findViewById(R.id.iImg);
				if (guardProfile.getPicture() != null) {
					Bitmap bm = BitmapFactory.decodeFile(guardProfile.getPicture());
					iProfileImg.setImageBitmap(bm);
				} else {
					iProfileImg.setImageResource(R.drawable.no_profile_pic);
				}
				builder.setView(view);
				builder.setNegativeButton("Tilbage", null);
				AlertDialog alert = builder.create();
				alert.show();

				return false;
			}
		});

		registerForContextMenu(getExpandableListView());

	}

	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		ExpandableListView.ExpandableListContextMenuInfo info =
				(ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
		int type =
				ExpandableListView.getPackedPositionType(info.packedPosition);
		int group =
				ExpandableListView.getPackedPositionGroup(info.packedPosition);
		int child =
				ExpandableListView.getPackedPositionChild(info.packedPosition);
		//Only create a context menu for child items
		if (type == 1) {
			guardProfile = adapter.getChild(group, child);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Fjern Pædagog");
			String gName = guardProfile.getFirstname();
			if (guardProfile.getMiddlename() != null) {
				gName += " " + guardProfile.getMiddlename();
			}
			gName += " " + guardProfile.getSurname();
			builder.setMessage("Sikker på at du vil fjerne " + gName + "?");
			builder.setPositiveButton("Ja", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					helper.profilesHelper.removeChildAttachmentToGuardian(MainActivity.child, guardProfile);
					updateList();
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("Nej", null);
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	private void updateList() {
		adapter = new ChildListAdapter(getActivity().getApplicationContext());

		list = helper.profilesHelper.getGuardiansByChild(MainActivity.child);

		List<Department> depList = helper.departmentsHelper.getDepartments();
		for(Department d : depList) {
			ArrayList<Profile> result = new ArrayList<Profile>();
			List<Profile> pList = helper.profilesHelper.getGuardiansByDepartment(d);		

			for(Profile p : pList) {
				for (Profile p2 : list) {
					if (p.getId() == p2.getId()) {
						result.add(p);
					}
				}
			}
			if (!result.isEmpty()) {
				adapter.AddGroup(d, result);
			}
		}

		ArrayList<Profile> noDepResult = new ArrayList<Profile>();
		List<Profile> noDep = helper.profilesHelper.getGuardiansWithNoDepartment();
		for (Profile p : noDep) {
			for (Profile p2 : list) {
				if (p.getId() == p2.getId()) {
					noDepResult.add(p);
				}
			}
		}
		if (!noDepResult.isEmpty()) {
			Department rogueDep = new Department();
			rogueDep.setName("Ingen Afdeling");
			adapter.AddGroup(rogueDep, noDepResult);
		}

		setListAdapter(adapter);
	}

}
