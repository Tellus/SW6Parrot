package dk.aau.cs.giraf.pictoadmin;


import android.content.Context;
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
public class KlimAdapter extends BaseAdapter {

	private PARROTCategoryOLD categoryOld;
	private PARROTCategory	  categoryNew;
	private Context context;

	public KlimAdapter(PARROTCategoryOLD cat, Context c)
	{
		super();
		this.categoryOld = cat;
		context = c;
	}
	
	public KlimAdapter(PARROTCategory cat, Context c)
	{
		super();
		this.categoryNew = cat;
		context = c;
	}

	@Override
	public int getCount() {
		//return the number of pictograms
		return categoryOld.getPictograms().size();
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
		LinearLayout.LayoutParams layoutParams;
		ImageView imageView;
		View view = convertView;
		TextView textView;
		PictogramOLD pctOld = categoryOld.getPictogramAtIndex(position);
		//TODO: Pictogram	 pctNew = categoryNew.getPictogramAtIndex(position);

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.pictogramview, null);

		imageView = (ImageView) view.findViewById(R.id.pictogrambitmap); 
		imageView.setImageBitmap(pctOld.getBitmap());
		//TODO: imageView.setImageBitmap(pctNew.getBitmap());
		
		layoutParams = new LinearLayout.LayoutParams(145, 145);

		imageView.setLayoutParams(layoutParams);

		textView = (TextView) view.findViewById(R.id.pictogramtext);
		textView.setTextSize(20);
		textView.setText(pctOld.getName());
		//TODO: textView.setText(pctNew.getName());
		
		view.setPadding(8, 8, 8, 8);

		return view;
	}
}
