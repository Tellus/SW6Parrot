package dk.aau.cs.giraf.pictoadmin;

import java.lang.ref.WeakReference;

import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class BitmapWorker extends AsyncTask<Object, Void, Bitmap> {
	// En weak reference g�r den "flagged" som "garbage collectable" :)
	private final WeakReference<ImageView> imageview;

	private Pictogram pictogram;
	private Context context;
	
	public BitmapWorker(ImageView img) {
		imageview = new WeakReference<ImageView>(img);
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		pictogram = (Pictogram) params[0];
		Bitmap bmp = null;
		

		if(pictogram.getPictogramID() == -1) {
			bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.usynlig);
		} else {
			bmp = BitmapFactory.decodeFile(pictogram.getImagePath());
			Log.v("Klim", "Working on: " + pictogram.getImagePath());
			Log.v("Klim", "Working on: " + pictogram.getPictogramID());
		}
		
		return bmp;
	}
	
	protected void onPostExecute(Bitmap result) {
		if(result != null && imageview != null) {
			final ImageView imgview = imageview.get();
			
			if(imgview != null) {
				imgview.setImageBitmap(result);
			}
		}
	}
}
