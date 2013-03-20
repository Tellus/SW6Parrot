package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Fragment which is used for listing all the Profiles in the system
 * 
 * @author Oasis
 *
 */
public class ProfilesFrag extends ExpandableListFragment {

	private Helper helper;
	private List<Profile> list;
	private ChildListAdapter adapter;
	private Button bAdd, bDep;
	private String tmpPath;
	private EditText tName, tPhone;
	private TextView tvDep;
	private ImageView iProfileImg;
	private CheckBox cbGuard, cbDep;
	private Profile newProfile, viewProfile, delProfile;
	private boolean cbDepChecked;


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

		updateList();

		bAdd = (Button) getView().findViewById(R.id.bAddProfile);
		bAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newProfile = new Profile();
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setCancelable(true);
				builder.setTitle(R.string.addProfile);
				LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = mInflater.inflate(R.layout.createprofile_view, null, false);
				tName = (EditText) view.findViewById(R.id.etCreateProfileName);
				tPhone = (EditText) view.findViewById(R.id.etCreateProfilePhone);
				iProfileImg = (ImageView) view.findViewById(R.id.iCreateProfileImg);
				iProfileImg.setImageResource(R.drawable.no_profile_pic);
				Button bImg = (Button) view.findViewById(R.id.bCreateProfileImg);
				bImg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						List<Media> valueList = helper.mediaHelper.getMedia();
						List<String> items = new ArrayList<String>();
						for (Media m : valueList) {
							items.add(m.getMPath());
						}

						final tmpListAdapter tAdapter = new tmpListAdapter(getActivity(), R.layout.children_list_childitem, items);

						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("Vælg et Profil Billede");
						builder.setAdapter(tAdapter,
								new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int item) {
								tmpPath = tAdapter.getItem(item);
								if (tmpPath != null) {
									Bitmap bm = BitmapFactory.decodeFile(tmpPath);
									iProfileImg.setImageBitmap(bm);
								} else {
									iProfileImg.setImageResource(R.drawable.no_profile_pic);
								}

								dialog.dismiss();
							}
						});
						builder.setNegativeButton("Afbryd", null);
						AlertDialog alert = builder.create();
						alert.show();
					}
				});
				cbGuard = (CheckBox) view.findViewById(R.id.cbCreateProfileIsGuardian);
				newProfile.setPRole(Profile.pRoles.CHILD.ordinal());
				cbGuard.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

						if ( isChecked ) {
							newProfile.setPRole(Profile.pRoles.GUARDIAN.ordinal());
						} else {
							newProfile.setPRole(Profile.pRoles.CHILD.ordinal());
						}

					}
				});
				tvDep = (TextView) view.findViewById(R.id.tvCreateProfileDep);
				bDep = (Button) view.findViewById(R.id.bCreateProfileDep);
				tvDep.setEnabled(false);
				bDep.setClickable(false);
				bDep.setEnabled(false);
				cbDep = (CheckBox) view.findViewById(R.id.cbCreateProfileDep);
				cbDep.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

						if ( isChecked ) {
							cbDepChecked = true;
							tvDep.setEnabled(true);
							bDep.setClickable(true);
							bDep.setEnabled(true);
						} else {
							cbDepChecked = false;
							tvDep.setEnabled(false);
							bDep.setClickable(false);
							bDep.setEnabled(false);
						}

					}
				});
				bDep.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						List<Department> valueList = helper.departmentsHelper.getDepartments();
						List<String> items = new ArrayList<String>();
						for (Department d : valueList) {
							items.add(d.getId() + " " + d.getName());
						}

						final tmpListAdapter tAdapter = new tmpListAdapter(getActivity(), R.layout.children_list_childitem, items);

						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("Vælg Over-Afdeling");
						builder.setAdapter(tAdapter, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int item) {
								tvDep.setText(tAdapter.getItem(item));
								dialog.dismiss();
							}
						});
						builder.setNegativeButton("Tilbage", null);
						AlertDialog alert = builder.create();
						alert.show();
					}
				});

				builder.setView(view);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String newName = tName.getText().toString();
						String tmp[] = newName.split(" ");
						String first = tmp[0];
						String middle = "";
						for (int i = 1; i < tmp.length-1; i++) {
							middle += tmp[i] + " ";
						}
						if (middle.equals("")) {
							middle = null;
						}
						String last = "";
						if (tmp.length > 1) {
							last = tmp[tmp.length-1];
						}
						String newPhone = tPhone.getText().toString();
						newProfile.setFirstname(first);
						newProfile.setSurname(last);
						newProfile.setMiddlename(middle);
						newProfile.setPhone(Long.parseLong(newPhone));
						newProfile.setPicture(tmpPath);
						newProfile.setId(helper.profilesHelper.insertProfile(newProfile));

						if (cbDepChecked && tvDep.getText() != null) {
							String sDep = tvDep.getText().toString();
							String sTmp[] = sDep.split(" ");
							Department aDep = helper.departmentsHelper.getDepartmentById(Long.parseLong(sTmp[0]));
							helper.departmentsHelper.attachProfileToDepartment(newProfile, aDep);
						}
						updateList();
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("Afbryd", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		getExpandableListView().setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

				viewProfile = adapter.getChild(groupPosition, childPosition);

				String name = viewProfile.getFirstname();
				if (viewProfile.getMiddlename() != null) {
					name += " " + viewProfile.getMiddlename();
				}
				name += " " + viewProfile.getSurname();

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setCancelable(true);
				builder.setTitle("Profil");
				LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = mInflater.inflate(R.layout.profilepopup_view, null, false);
				TextView tName = (TextView) view.findViewById(R.id.tvName);
				tName.setText(name);
				TextView tPhone = (TextView) view.findViewById(R.id.tvPhone);
				tPhone.setText(""+MainActivity.guardian.getPhone());
				ImageView iProfileImg = (ImageView) view.findViewById(R.id.iImg);
				if (viewProfile.getPicture() != null) {
					Bitmap bm = BitmapFactory.decodeFile(viewProfile.getPicture());
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
			delProfile = adapter.getChild(group, child);
			AlertDialog.Builder delbuilder = new AlertDialog.Builder(getActivity());
			if (MainActivity.guardian == null || delProfile.getId() != MainActivity.guardian.getId()) {
				delbuilder.setTitle("Fjern Profil");
				String dName = delProfile.getFirstname();
				if (delProfile.getMiddlename() != null) {
				dName += delProfile.getMiddlename();
				}
				dName += delProfile.getSurname();
				delbuilder.setMessage("Sikker på at du vil fjerne " + dName + "?");
				delbuilder.setPositiveButton("Ja", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						helper.profilesHelper.removeProfile(delProfile);
						updateList();
						dialog.dismiss();
					}
				});
				delbuilder.setNegativeButton("Nej", null);
			} else {
				delbuilder.setTitle("Fejl");
				delbuilder.setMessage("Du kan ikke slette dig selv!");
				delbuilder.setNegativeButton("Tilbage", null);
			}
			AlertDialog delalert = delbuilder.create();
			delalert.show();

		}
	}

	private void updateList() {
		adapter = new ChildListAdapter(getActivity().getApplicationContext());

		list = helper.profilesHelper.getProfiles();

		List<Department> depList = helper.departmentsHelper.getDepartments();

		for(Department d : depList) {
			ArrayList<Profile> result = new ArrayList<Profile>();
			List<Profile> pList = helper.profilesHelper.getProfilesByDepartment(d);		

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
		noDep.addAll(helper.profilesHelper.getChildrenWithNoDepartment());
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
