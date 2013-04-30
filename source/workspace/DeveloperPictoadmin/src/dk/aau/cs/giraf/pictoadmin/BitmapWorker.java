package dk.aau.cs.giraf.pictoadmin;

import java.lang.ref.WeakReference;

import dk.aau.cs.giraf.pictogram.Pictogram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

public class BitmapWorker extends AsyncTask<Object, Integer, Bitmap> {
	// En weak reference gør den "flagged" som "garbage collectable" :)
	private final WeakReference<ImageView> imageview;
	//private final WeakReference<TextView> textview;
	private Pictogram pictogram;
	private Context context;
	//WeakReference<TextView> txtview, 
	
	public BitmapWorker(ImageView img, Context con) {
		imageview = new WeakReference<ImageView>(img);
		//this.textview = txtview;
		this.context = con;
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		pictogram = (Pictogram) params[0];
		Bitmap bmp = null;
		
		if(pictogram.getPictogramID() == -1) {
			bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.usynlig);
		} else {
			bmp = BitmapFactory.decodeFile(pictogram.getImagePath());
		}
		
		return bmp;
}
	
	protected void onPostExecute(Bitmap result) {
		if(result != null && imageview != null) {
			final ImageView imgview = imageview.get();
			
			imgview.setImageBitmap(result);
		}
	}
}
