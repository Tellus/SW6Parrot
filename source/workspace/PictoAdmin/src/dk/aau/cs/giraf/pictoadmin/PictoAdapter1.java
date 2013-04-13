package dk.aau.cs.giraf.pictoadmin;


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
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.categorylib.PARROTCategoryOLD;
import dk.aau.cs.giraf.categorylib.PictogramOLD;
import dk.aau.cs.giraf.pictogram.Pictogram;

/**
 * 
 * @PARROT
 * This is the Pictogram Adapter class. It is used to import the pictograms into a gridview.
 */
public class PictoAdapter1 extends BaseAdapter {
	private PARROTCategory	  categoryNew = null;
	private Context context;
	
	public PictoAdapter1(PARROTCategory cat, Context c)
	{
		super();
		this.categoryNew = cat;
		context = c;
	}

	@Override
	public int getCount() {
		//return the number of pictograms
		return categoryNew.getPictograms().size();
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
		Pictogram pctNew = categoryNew.getPictogramAtIndex(position);
		Bitmap img = BitmapFactory.decodeFile(pctNew.getImagePath());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.pictogramview, null);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.pictogrambitmap); 
		imageView.setImageBitmap(img);
		imageView.setLayoutParams(layoutParams);

		TextView textView = (TextView) convertView.findViewById(R.id.pictogramtext);
		textView.setText(pctNew.getTextLabel());
		
		convertView.setPadding(20, 0, 0, 0);

		return convertView;
	}
}