package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Adapter, which is used for creating media lists in the system
 * 
 * @author Oasis
 *
 */
public class MediaListAdapter extends BaseAdapter {

	private List<Media> values;
	private LayoutInflater mInflater;
	private Context c;

	public MediaListAdapter(Context context, List<Media> results) {
		values = results;
		mInflater = LayoutInflater.from(context);
		c = context;
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
			convertView = mInflater.inflate(R.layout.media_list_item, null);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvItemMediaName);
			holder.tvPath = (TextView) convertView.findViewById(R.id.tvItemMediaPath);
			holder.tvPublic = (TextView) convertView.findViewById(R.id.tvItemMediaPublic);
			holder.tvType = (TextView) convertView.findViewById(R.id.tvItemMediaType);
			holder.tvOwner = (TextView) convertView.findViewById(R.id.tvItemMediaOwner);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvName.setText(values.get(position).getName());
		holder.tvPath.setText(values.get(position).getMPath());
		if (values.get(position).isMPublic()) {
			holder.tvPublic.setText("Sandt");
		} else {
			holder.tvPublic.setText("Falsk");
		}
		holder.tvType.setText(values.get(position).getMType());
		Helper helper = new Helper(c);
		Profile owner = helper.profilesHelper.getProfileById(values.get(position).getOwnerId());
		String oName = owner.getFirstname();
		if (owner.getMiddlename() != null) {
			oName += " " + owner.getMiddlename();
		}
		oName += " " + owner.getSurname();
		holder.tvOwner.setText(oName);

		return convertView;
	}

	static class ViewHolder {
		TextView tvName, tvPath, tvPublic, tvOwner, tvType;
	}
}
