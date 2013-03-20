package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Department;

/**
 * Fragment which is used for listing all the departments in the system
 * 
 * @author Oasis
 *
 */
public class DepartmentsFrag extends ListFragment {

	Helper helper;
	List<Department> list;
	Department d;
	String chosen;
	EditText tName, tAddress, tEmail, tPhone;
	CheckBox cb;
	TextView tSuper;
	Button bDep;
	boolean cbChecked = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.departments_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity().getApplicationContext());

		if (MainActivity.guardian != null) {
			list = helper.departmentsHelper.getDepartments();
			setListAdapter(new DepartmentListAdapter(getActivity().getApplicationContext(), list));
		} else {
			setListAdapter(null);
		}

		Button bAdd = (Button) getView().findViewById(R.id.bAddDepartment);
		bAdd.setText("Opret ny Afdeling");
		bAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setCancelable(true);
				builder.setTitle("Tilføj Ny Afdeling");
				LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = mInflater.inflate(R.layout.createdepartment_view, null, false);
				tName = (EditText) view.findViewById(R.id.etCreateDepName);
				tAddress = (EditText) view.findViewById(R.id.etCreateDepAddress);
				tEmail = (EditText) view.findViewById(R.id.etCreateDepEmail);
				tPhone = (EditText) view.findViewById(R.id.etCreateDepPhone);
				cb = (CheckBox) view.findViewById(R.id.cbCreateDepSuperDep);
				tSuper = (TextView) view.findViewById(R.id.tvCreateDepSuperDep);
				bDep = (Button) view.findViewById(R.id.bCreateDepSuperDep);
				tSuper.setEnabled(false);
				bDep.setClickable(false);
				bDep.setEnabled(false);
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener()	{
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if ( isChecked ) {
							cbChecked = true;
							tSuper.setEnabled(true);
							bDep.setClickable(true);
							bDep.setEnabled(true);
						} else {
							cbChecked = false;
							tSuper.setEnabled(false);
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
								chosen = tAdapter.getItem(item);
								if (chosen != null) {
									tSuper.setText(chosen);
								}
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
						if (tName.getText() != null && tAddress.getText() != null && tPhone.getText() != null && tEmail.getText() != null) {
						String newName = tName.getText().toString();
						String newAddress = tAddress.getText().toString();
						String newPhone = tPhone.getText().toString();
						String newEmail = tEmail.getText().toString();
						Department newDep = new Department(newName, newAddress, Long.parseLong(newPhone), newEmail);
						long id = helper.departmentsHelper.insertDepartment(newDep);
						newDep.setId(id);

						if (cbChecked && tSuper.getText() != null) {
							String sDep = tSuper.getText().toString();
							String sTmp[] = sDep.split(" ");
							Department supDep = helper.departmentsHelper.getDepartmentById(Long.parseLong(sTmp[0]));
							helper.departmentsHelper.attachSubDepartmentToDepartment(supDep, newDep);
						}

						list = helper.departmentsHelper.getDepartments();
						setListAdapter(new DepartmentListAdapter(getActivity().getApplicationContext(), list));
						dialog.dismiss();
						}
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

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
				d = (Department) getListAdapter().getItem(position);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Fjern Afdeling");
				builder.setMessage("Sikker på at du vil fjerne " + d.getName() +"?");
				builder.setPositiveButton("Ja", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						helper.departmentsHelper.removeDepartment(d);

						list = helper.departmentsHelper.getDepartments();
						setListAdapter(new DepartmentListAdapter(getActivity().getApplicationContext(), list));

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
