package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import dk.aau.cs.giraf.oasis.lib.models.Media;


public class PictoAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Media> pictogramlist;
	
	
	public PictoAdapter(Context cont, ArrayList<Media> piclist) {
		this.context = cont;
		this.pictogramlist = piclist;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		
		View gridview;
		
		if(convertView == null) {
			gridview = new View(context);
			
			gridview = inflater.inflate(R.layout.activity_picto_admin_main, null);
			//ImageView imageview = (ImageView) gridview.findViewById(R.id.pict_disp_grid_item);
			
			//imageview.setImageBitmap(piclist);
		}
		
		else {
			gridview = (View) convertView;
		}
		
		return gridview;
	}
	
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
