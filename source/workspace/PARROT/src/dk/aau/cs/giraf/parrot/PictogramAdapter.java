package dk.aau.cs.giraf.parrot;


import dk.aau.cs.giraf.categorylib.PARROTCategoryOLD;
import dk.aau.cs.giraf.categorylib.PictogramOLD;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @PARROT
 * This is the Pictogram Adapter class. It is used to import the pictograms into a gridview.
 */
public class PictogramAdapter extends BaseAdapter {

	private PARROTCategoryOLD cat;
	private Context context;

	public PictogramAdapter(PARROTCategoryOLD cat, Context c)
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
	//create an image view for each pictogram in the list.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView imageView;
		View view = convertView;
		TextView textView;
		PictogramOLD pct=cat.getPictogramAtIndex(position);

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.pictogramview, null);

		imageView = (ImageView) view.findViewById(R.id.pictogrambitmap); 
		imageView.setImageBitmap(pct.getBitmap());
		LinearLayout.LayoutParams layoutParams;
		if(PARROTActivity.getUser().getPictogramSize()== PARROTProfile.PictogramSize.LARGE)
		{
			layoutParams = new LinearLayout.LayoutParams(190, 190);
		}
		else
		{
			layoutParams = new LinearLayout.LayoutParams(145, 145);	
		}
		
		imageView.setLayoutParams(layoutParams);
		if(pct.isEmpty() == false && PARROTActivity.getUser().getShowText()==true)
		{
			textView = (TextView) view.findViewById(R.id.pictogramtext);
			textView.setTextSize(20);	//TODO this value should be customizable
			textView.setText(pct.getName());
		}
		view.setPadding(8, 8, 8, 8);

		return view;
	}
}
