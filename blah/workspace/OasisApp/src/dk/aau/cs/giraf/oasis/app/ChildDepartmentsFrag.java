package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Department;

/**
 * Fragment which is used for listing all departments of a child in the system
 * 
 * @author Oasis
 *
 */
public class ChildDepartmentsFrag extends ListFragment {

	Helper helper;
	List<Department> list;
	Department d;

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

		if (MainActivity.child != null) {
			list = helper.departmentsHelper.getDepartmentsByProfile(MainActivity.child);
			setListAdapter(new DepartmentListAdapter(getActivity().getApplicationContext(), list));
		} else {
			setListAdapter(null);
		}

		Button bAdd = (Button) getView().findViewById(R.id.bAddDepartment);
		bAdd.setText("Tilføj Afdeling");
		bAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				List<Department> depList = helper.departmentsHelper.getDepartments();
				List<Department> valueList = new ArrayList<Department>();
				for (Department dItem : depList) {
					if (!list.contains(dItem)) {
						valueList.add(dItem);
					}
				}
				
				List<String> items = new ArrayList<String>();
				for (Department d : valueList) {
					String name = ""+d.getId(); 
					name += " " + d.getName();
					items.add(name);
				}

				final tmpListAdapter adapter = new tmpListAdapter(getActivity(), R.layout.children_list_childitem, items);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Vælg en Afdeling");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						String tmpPath = adapter.getItem(item);
						String tmpA[] = tmpPath.split(" ");
						Department newDep = helper.departmentsHelper.getDepartmentById(Long.parseLong(tmpA[0]));
						helper.departmentsHelper.attachProfileToDepartment(MainActivity.child, newDep);

						list = helper.departmentsHelper.getDepartmentsByProfile(MainActivity.child);
						setListAdapter(new DepartmentListAdapter(getActivity().getApplicationContext(), list));

						dialog.dismiss();
					}
				});
				builder.setNegativeButton("Cancel", null);
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
				builder.setMessage("Sikker på at du vil fjerne " + d.getName() + "?");
				builder.setPositiveButton("Ja", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						helper.departmentsHelper.removeProfileAttachmentToDepartment(MainActivity.child, d);

						list = helper.departmentsHelper.getDepartmentsByProfile(MainActivity.child);
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
