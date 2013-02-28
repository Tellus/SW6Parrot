package dk.aau.cs.giraf.parrot;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @PARROT
 * This is the Pictogram Adapter class. It is used to import the pictograms into a gridview.
 */
public class PictogramAdapter extends BaseAdapter {

	private Category cat;
	private Context context;

	public PictogramAdapter(Category cat, Context c)
	{
		super();
		this.cat=cat;
		context = c;
	}

	public int getCount() {
		//return the number of pictograms
		return cat.getPictograms().size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	//create an image view for each pictogram in the list.
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView imageView;
		View view = convertView;
		TextView textView;
		Pictogram pct=cat.getPictogramAtIndex(position);

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.pictogramview, null);

		imageView = (ImageView) view.findViewById(R.id.pictogrambitmap); 
		imageView.setImageBitmap(pct.getBitmap());

		textView = (TextView) view.findViewById(R.id.pictogramtext);
		textView.setTextSize(20);	//TODO this value should be customizable
		if(pct.isEmpty() == false)
		{

			textView.setText(pct.getName());
		}
		else
		{
			textView.setText("");
		}

		view.setPadding(8, 8, 8, 8);
		return view;
	}

}
