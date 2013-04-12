package dk.aau.cs.giraf.parrot;
import java.lang.ref.WeakReference;

import dk.aau.cs.giraf.parrot.R;
import dk.aau.cs.giraf.pictogram.Pictogram;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


class LoadImage extends AsyncTask<Object, Void, Bitmap>{

		private final WeakReference<ImageView> imageView;
        private String path;
        private Context context;

        public LoadImage(ImageView imv,Context context) {
        	Log.v("LoadImage;Message","begin LoadImage");
        	 imageView = new WeakReference<ImageView>(imv);
             this.context= context;
             Log.v("LoadImage;Message","end LoadImage");
             
        }

    @Override
    protected Bitmap doInBackground(Object... params) {
    	Log.v("LoadImage;Message","begin doInBackground");
    	Pictogram result = (Pictogram) params[0];
        Bitmap bitmap = null;
        
        if(result.getPictogramID() == -1)
		{
        	Log.v("LoadImage;Message","doInBackground usynlig");
        	bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.usynlig);
			
		}
        else	
    	{
        	Log.v("LoadImage;Message","doInBackground path" + result.getImagePath());
        	bitmap = BitmapFactory.decodeFile(result.getImagePath());
    	}
        Log.v("LoadImage;Message","end doInBackground");
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
    	Log.v("LoadImage;Message","begin onPostExecute");
        if(result != null && imageView != null){
        	 final ImageView imageView2 = imageView.get();
             if (imageView2 != null) {
                 imageView2.setImageBitmap(result);
             }
        }
        Log.v("LoadImage;Message","end onPostExecute");
    }

}
