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
import android.widget.LinearLayout;
import android.widget.TextView;


class LoadImage extends AsyncTask<Object, Void, Bitmap>{

		private final WeakReference<ImageView> imageView;
        private String path;
        private Context context;
        private final WeakReference<TextView>text;
        private Pictogram pictogram;

        public LoadImage(ImageView imv, TextView text, Context context) {
        	//Log.v("LoadImage;Message","begin LoadImage");
        	 imageView = new WeakReference<ImageView>(imv);
        	 this.text = new WeakReference<TextView>(text);
             this.context= context;
             //Log.v("LoadImage;Message","end LoadImage");
             
        }

    @Override
    protected Bitmap doInBackground(Object... params) {
    	//Log.v("LoadImage;Message","begin doInBackground");
    	pictogram = (Pictogram) params[0];
        Bitmap bitmap = null;
        
        if(pictogram.getPictogramID() == -1)
		{
        	//Log.v("LoadImage;Message","doInBackground usynlig");
        	bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.usynlig);
			
		}
        else	
    	{
        	//Log.v("LoadImage;Message","doInBackground path" + pictogram.getImagePath());
        	bitmap = BitmapFactory.decodeFile(pictogram.getImagePath());
    	}
        //Log.v("LoadImage;Message","end doInBackground");
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
    	//Log.v("LoadImage;Message","begin onPostExecute");
        if(result != null && imageView != null){
    	     final ImageView imageView2 = imageView.get();
             if (imageView2 != null) {
                 imageView2.setImageBitmap(result);
             }
        }
        //Log.v("LoadImage;Message","end onPostExecute");
    }

}
