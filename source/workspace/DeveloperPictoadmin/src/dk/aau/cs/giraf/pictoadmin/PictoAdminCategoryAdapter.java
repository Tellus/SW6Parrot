package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;
<<<<<<< HEAD

=======
>>>>>>> origin/SubSprint4
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.pictogram.Pictogram;

/**
 * 
 * @author PARROT
 *This is the CategoryAdapter class. 
 * This class takes a list of categories and loads them into a GridView.
 */

public class PictoAdminCategoryAdapter extends BaseAdapter{

	private ArrayList<PARROTCategory> catList;
	private Context context;

	//Constructor taking List of PARROTCategories, and a Context.
	public PictoAdminCategoryAdapter(ArrayList<PARROTCategory> catList, Context c)
	{
		this.catList=catList;
		context = c;
	}

	@Override
	public int getCount() {
		//return the number of categories
		return catList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	//create an image view for each icon of the categories in the list.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		Pictogram pct=catList.get(position).getIcon();
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} 
		
		else {
			imageView = (ImageView) convertView;
		}
		
		//we then set the imageview to the icon of the category
		imageView.setImageBitmap(BitmapFactory.decodeFile(pct.getImagePath()));
		
		return imageView;
	}

}
