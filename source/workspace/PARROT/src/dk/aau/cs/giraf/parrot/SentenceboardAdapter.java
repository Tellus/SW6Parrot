	package dk.aau.cs.giraf.parrot;
	
	import dk.aau.cs.giraf.categorylib.PARROTCategory;
	import dk.aau.cs.giraf.pictogram.Pictogram;
	import android.content.Context;
	import android.graphics.Bitmap;
	import android.graphics.BitmapFactory;
	import android.util.Log;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.BaseAdapter;
	import android.widget.ImageView;
	import android.widget.LinearLayout;
	import android.widget.TextView;

public class SentenceboardAdapter extends BaseAdapter {


	private PARROTCategory cat;
	private Context context;

	public SentenceboardAdapter(PARROTCategory cat, Context c)
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
			
			Pictogram pct=cat.getPictogramAtIndex(position);

			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.pictogramview, null);

			imageView = (ImageView) view.findViewById(R.id.pictogrambitmap); 
			textView = (TextView) view.findViewById(R.id.pictogramtext);
			//ordi: imageView.setImageBitmap(BitmapFactory.decodeFile(pct.getImagePath()));
			
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
			//Log.v("message", "show text: " + PARROTActivity.getUser().getShowText());
			if(PARROTActivity.getUser().getShowText()==true)//pct.getPictogramID() != -1 && PARROTActivity.getUser().getShowText()==true)
			{
				
				textView.setTextSize(20);	//TODO this value should be customizable
				textView.setText(pct.getTextLabel());
				
			}
			Bitmap bitmap;
			if(pct.getPictogramID() == -1)
			{
	        	bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.usynlig);
				
			}
			else
			{
				bitmap = BitmapFactory.decodeFile(pct.getImagePath());
			}
			imageView.setImageBitmap(bitmap);
			view.setPadding(8, 8, 8, 8);
			

			return view;
		}

}
