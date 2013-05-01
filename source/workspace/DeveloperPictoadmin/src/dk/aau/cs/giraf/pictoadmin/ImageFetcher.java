package dk.aau.cs.giraf.pictoadmin;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import dk.aau.cs.giraf.pictogram.Pictogram;

/* * The following class will be responsible creating threaded tasks that gets images and adds them
   * to an imageview, why is this class and function so small? 
   * Because it will be invoked from the UI thread! */

public class ImageFetcher {
	public void fetchBitmap(String imagepath, ImageView imageview) {
		BitmapWorker task = new BitmapWorker(imageview); // Create an image view so there's space, buddy
		task.execute(imagepath); // Launch the task with the pictogram's imagepath, buddy
	}
	
	static Bitmap downloadBitmap(String path) {
		Bitmap bmp = BitmapFactory.decodeFile(path);
		
		// Check if there actually was an image on the decoded path and return it if true
		if(bmp != null) {
			return bmp;
		}
		
		else {
			return null;
		}
	}
	
	
	class BitmapWorker extends AsyncTask<String, Void, Bitmap> {
		// En weak reference gør den "flagged" som "garbage collectable" :)
		private final WeakReference<ImageView> imageview;
		//private final WeakReference<TextView> textview;
		private Pictogram pictogram;
		private Context context;
		//WeakReference<TextView> textview; 
		//TextView text, Context con
		ImageFetcher imgfetcher;
		
		public BitmapWorker(ImageView img) {
			imageview = new WeakReference<ImageView>(img);
			//this.textview = new WeakReference<TextView>(text);
			//this.context = con;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			//pictogram = (Pictogram) params[0];
			//Bitmap bmp = null;
			
			// Gammel og virkende kode below
			/*if(pictogram.getPictogramID() == -1) {
				bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.usynlig);
			} else {
				bmp = BitmapFactory.decodeFile(pictogram.getImagePath());
			}*/
			
			return downloadBitmap(params[0]);
			//return downloadBitmap(params[0]);
			//return bmp;
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
}