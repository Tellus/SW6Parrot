package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
 * @author PARROT
 *This is the CategoryAdapter class. 
 * This class takes a list of categories and loads them into a GridView.
 */

public class CategoryAdapter extends BaseAdapter{

	private ArrayList<Category> catList;
	private Context context;

	public CategoryAdapter(ArrayList<Category> catList, Context c)
	{
		this.catList=catList;
		context = c;
	}

	public int getCount() {
		//return the number of categories
		return catList.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	//create an image view for each icon of the categories in the list.
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		Pictogram pct=catList.get(position).getIcon();
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;

		}

		//we then set the imageview to the icon of the category
		imageView.setImageBitmap(pct.getBitmap());
		return imageView;
	}

}
