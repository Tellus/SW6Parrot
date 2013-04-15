package dk.aau.cs.giraf.pictoadmin;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import dk.aau.cs.giraf.pictogram.Pictogram;

/**
 * 
 * @PARROT
 * This is the Pictogram Adapter class. It is used to import the pictograms into a gridview.
 */
public class PictoAdapter2 extends BaseAdapter {
	private Context context;
	private ArrayList<Pictogram> pictograms;
	
	public PictoAdapter2(ArrayList<Pictogram> p, Context c)
	{
		super();
		this.pictograms = p;
		context = c;
	}

	@Override
	public int getCount() {
		//return the number of pictograms
		return pictograms.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	//create an image view for each pictogram in the list.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Pictogram pctNew = pictograms.get(position);
		
		Bitmap img = BitmapFactory.decodeFile(pctNew.getImagePath());
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.pictogramview, null);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.pictogrambitmap); 
		imageView.setImageBitmap(img);
		imageView.setLayoutParams(layoutParams);

		TextView textView = (TextView) convertView.findViewById(R.id.pictogramtext);
		textView.setText(pctNew.getTextLabel());
		
		convertView.setPadding(5, 5, 5, 5);

		return convertView;
	}
}
