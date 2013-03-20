package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.models.Department;

/**
 * Adapter, which is used for creating department lists in the system
 * 
 * @author Oasis
 *
 */
public class DepartmentListAdapter extends BaseAdapter {

	private static List<Department> values;
	private LayoutInflater mInflater;

	public DepartmentListAdapter(Context context, List<Department> results) {
		values = results;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(int arg0) {
		return values.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.department_list_item, null);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvItemDepName);
			holder.tvAddress = (TextView) convertView.findViewById(R.id.tvItemDepAddress);
			holder.tvContacts = (TextView) convertView.findViewById(R.id.tvItemDepContacts);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(values.get(position).getName());
		holder.tvAddress.setText(values.get(position).getAddress());
		holder.tvContacts.setText(values.get(position).getEmail() + "\n" + values.get(position).getPhone());

		return convertView;
	}

	static class ViewHolder {
		TextView tvName, tvAddress, tvContacts;
	}
}
