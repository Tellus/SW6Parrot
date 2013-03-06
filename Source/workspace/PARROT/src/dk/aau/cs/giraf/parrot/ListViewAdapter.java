package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * 
 * @author PARROT
 * This is the ListViewAdapter class.
 * This class takes a list of categories and loads them into a ListView, displaying the icon as well as name. 
 */

public class ListViewAdapter extends ArrayAdapter<PARROTCategory> 
{
	private ArrayList<PARROTCategory> items;

	public ListViewAdapter(Context context, int textViewResourceId, ArrayList<PARROTCategory> items)
	{
		super(context, textViewResourceId, items);
		this.items = items;
	}


	@Override
	//This is the getView method, all adapters must have one. 
	//It takes the category corresponding to the current item in the ListView and transforms it into a View.  
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//We inflate a categoriesitem and prepare it for data 
			view = layoutInflater.inflate(R.layout.categoriesitem, null); 
		}
		//we get the current category.  
		PARROTCategory category = items.get(position);
		if (category != null) {
			//We find the imageView and the textView of the categoriesitem from before.
			ImageView imageView = (ImageView) view.findViewById(R.id.catpic); 
			TextView textView = (TextView) view.findViewById(R.id.catname);
				
			if (imageView != null) {
				imageView.setImageBitmap(items.get(position).getIcon().getBitmap());
			}
			if (textView != null) {

				textView.setText(category.getCategoryName());

			}

		}

		return view;
	}

}
