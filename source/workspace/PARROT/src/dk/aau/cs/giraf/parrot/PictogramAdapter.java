package dk.aau.cs.giraf.parrot;


import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.pictogram.Pictogram;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author PARROT spring 2012 and adapted by sw605f13-PARROT
 * This is the Pictogram Adapter class. It is used to import the pictograms into a the GridView pictogramgrid.
 */
public class PictogramAdapter extends BaseAdapter {

	private PARROTCategory cat;
	private Context context;
	
	/**
	 * 
	 * @param cat, a PARROTCategory
	 * @param c, the applications context
	 */
	public PictogramAdapter(PARROTCategory cat, Context c)
	{
		super();
		this.cat=cat;
		context = c;
	}

	@Override
	public int getCount() {
		//return the number of pictograms
		return cat.getPictograms().size();
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
	/**
	 * create an image view for each pictogram in the pictogram list from the PARROTCategory.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		ImageView imageView;
		View view = convertView;
		TextView textView;
		
		Pictogram pct=cat.getPictogramAtIndex(position);

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.pictogramview, null);

		//setup views
		imageView = (ImageView) view.findViewById(R.id.pictogrambitmap); 
		textView = (TextView) view.findViewById(R.id.pictogramtext);
		
		//setup layout for imageView
		LinearLayout.LayoutParams layoutParams;
		if(PARROTActivity.getUser().getPictogramSize()== PARROTProfile.PictogramSize.LARGE)
		{
			layoutParams = new LinearLayout.LayoutParams(180, 180);
		}
		else
		{
			layoutParams = new LinearLayout.LayoutParams(145, 145);	
		}
		
		imageView.setLayoutParams(layoutParams);

		//load the Bitmap and set the setImageBitmap
		LoadImage task = new LoadImage(imageView,textView, context);
	    task.execute(pct);

	    view.setPadding(8, 8, 8, 8);
		

		return view;
	}
}
