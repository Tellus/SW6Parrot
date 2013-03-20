package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Adapter, which is used for creating profile lists in the system
 * 
 * @author Oasis
 *
 */
public class ChildListAdapter extends BaseExpandableListAdapter {


	private final HashMap<Department, ArrayList<Profile>> myData = new HashMap<Department, ArrayList<Profile>>();
	private final HashMap<Integer, Department> lookUp = new HashMap<Integer, Department>();
	private final Context context;
	private LayoutInflater mInflater;
	
	public ChildListAdapter(final Context con)
	{
		context = con;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public boolean AddGroup(final Department groupName, final ArrayList<Profile> list)
	{
		final ArrayList<Profile> prev = myData.put(groupName, list);

		if (prev != null)
			return false;

		lookUp.put(myData.size() - 1, groupName);

		notifyDataSetChanged();
		return true;
	}

	@Override
	public Profile getChild(int groupPos, int childPos) 
	{
		if (lookUp.containsKey(groupPos))
		{
			final Department m = lookUp.get(groupPos);
			final ArrayList<Profile> data = myData.get(m);

			return data.get(childPos);
		}

		return null;
	}

	@Override
	public long getChildId(int groupPos, int childPos) 
	{
		return 0;
	}

	@Override
	public View getChildView(int groupPos, int childPos, boolean isLastChild, 
			View convertView, ViewGroup parent) 
	{
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.children_list_childitem, null);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvItemName);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Profile p = getChild(groupPos, childPos);
		String name = p.getFirstname();
		if (p.getMiddlename() != null) {
			name += " " + p.getMiddlename();
		}
		name += " " + p.getSurname();
		
		holder.tvName.setText("\t\t\t\t\t" + name);

		return convertView;
	}

	static class ViewHolder {
		TextView tvChildHeader, tvName;
	}

	@Override
	public int getChildrenCount(int groupPos) 
	{
		if (lookUp.containsKey(groupPos))
			return myData.get(lookUp.get(groupPos)).size();

		return 0;
	}

	@Override
	public Department getGroup(int groupPos) 
	{
		if (lookUp.containsKey(groupPos))
			//			return myData.get(lookUp.get(groupPos));
			return lookUp.get(groupPos);

		return null;
	}

	@Override
	public int getGroupCount() 
	{
		return myData.size();
	}

	@Override
	public long getGroupId(int groupPos) 
	{
		return 0;
	}

	@Override
	public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.children_list_groupitem, null);

			holder = new ViewHolder();
			holder.tvChildHeader = (TextView) convertView.findViewById(R.id.tvGroupItem);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvChildHeader.setText("\t\t\t" + getGroup(groupPos).getName());

		return convertView;
	}

	@Override
	public boolean hasStableIds() 
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPos, int childPos) 
	{
		return true;
	}
}
