package dk.aau.cs.giraf.pictoadmin;

import java.lang.ref.WeakReference;

import dk.aau.cs.giraf.pictogram.Pictogram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class BitmapWorker extends AsyncTask<String, Void, Bitmap> {
	// En weak reference gør den "flagged" som "garbage collectable" :)
	private final WeakReference<ImageView> imageview;
	//private final WeakReference<TextView> textview;
	private Pictogram pictogram;
	private Context context;
	//WeakReference<TextView> textview; 
	//TextView text, Context con
	
	public BitmapWorker(ImageView img) {
		imageview = new WeakReference<ImageView>(img);
		//this.textview = new WeakReference<TextView>(text);
		//this.context = con;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		pictogram = (Pictogram) params[0];
		Bitmap bmp = null;
		
		// Gammel og virkende kode below
		/*if(pictogram.getPictogramID() == -1) {
			bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.usynlig);
		} else {
			bmp = BitmapFactory.decodeFile(pictogram.getImagePath());
			Log.v("doInBackground", "Found image at: "+pictogram.getImagePath().toString());
		}*/
		
		return bmp;
	}
	
	protected void onPostExecute(Bitmap result) {
		if(result != null && imageview != null) {
			final ImageView imgview = imageview.get();
			//final TextView txtview = textview.get();
			
			if(imgview != null) {
				imgview.setImageBitmap(result);
			}
		}
	}
}
