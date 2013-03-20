package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter, which is used when simple string lists is created in the system
 * 
 * @author Oasis
 *
 */
public class tmpListAdapter extends ArrayAdapter<String> {

	ViewHolder holder;
	Drawable icon;
	Context c;
	List<String> items;
	int layout;

	public tmpListAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		this.c = context;
		this.items = objects;
		this.layout = textViewResourceId;
	}

	class ViewHolder {
		TextView title;
	}

	public View getView(int position, View convertView,
			ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(layout, null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.tvItemName);
			convertView.setTag(holder);
		} else {
			// view already defined, retrieve view holder
			holder = (ViewHolder) convertView.getTag();
		}       

		holder.title.setText(items.get(position));

		return convertView;
	}
}
