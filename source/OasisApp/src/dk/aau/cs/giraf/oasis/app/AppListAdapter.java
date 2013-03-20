package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.models.App;

/**
 * Adapter, which is used for create application lists in the system
 * 
 * @author Oasis
 *
 */
public class AppListAdapter extends BaseAdapter {

	private static List<App> values;
	private LayoutInflater mInflater;
//	private Context c;

	public AppListAdapter(Context context, List<App> results) {
		values = results;
		mInflater = LayoutInflater.from(context);
//		c = context;
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
			convertView = mInflater.inflate(R.layout.apps_list_item, null);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvItemAppName);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(values.get(position).getName());

		return convertView;
	}

	static class ViewHolder {
		TextView tvName;
	}
}
