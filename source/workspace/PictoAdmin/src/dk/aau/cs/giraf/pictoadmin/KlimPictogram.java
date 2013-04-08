package dk.aau.cs.giraf.pictoadmin;

import dk.aau.cs.giraf.pictogram.AudioPlayer;
import dk.aau.cs.giraf.pictogram.IPictogram;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//TODO: Make custom ImageView and TextView with predefined "niceness"
public class KlimPictogram extends FrameLayout implements IPictogram, Parcelable {
    private static final String TAG = "Pictogram";

    private final String imagePath;
    private final String textLabel;
    private final String audioPath;
    private final long pictogramID;
    
    private static Context context;

    private Gravity textGravity;

    //Main constructor (no XML)
    public KlimPictogram(Context context, final String image,
                     final String text, final String audio,
                     final long id) {

        super(context);
        this.context = context;
        this.imagePath = image;
        this.textLabel = text;
        this.audioPath = audio;
        this.pictogramID = id;
    }

	public KlimPictogram(Parcel in)
	{
		super(context);
		this.imagePath = in.readString();
		this.textLabel = in.readString();
		this.audioPath = in.readString();
		this.pictogramID = in.readLong();
	}

	@Override
    public void renderAll() {
        renderImage();
        renderText();
    }

	@Override
    public void renderText() {
        TextView text = new TextView(getContext());
        text.setText(textLabel);
        text.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        this.addView(text);
    }

    public void renderText(int gravity) {
        TextView text = new TextView(getContext());
        text.setText(textLabel);
        text.setPadding(10, 10, 10, 10);
        text.setGravity(Gravity.CENTER_HORIZONTAL | gravity);
        this.addView(text);
    }

	@Override
    public void renderImage() {
        Bitmap img = BitmapFactory.decodeFile(imagePath);
        ImageView image = new ImageView(getContext());
        String msg = imagePath + " found, making bitmap.";
        Log.d(TAG, msg);
        image.setImageBitmap(img);
        this.addView(image);
    }

    public boolean hasAudio() {
        return audioPath != null;
    }


	@Override
    public void playAudio() {
        playAudio(null);
    }

    public void playAudio(final OnCompletionListener listener){
        if(hasAudio()){
            new Thread(new Runnable(){
                    public void run(){
                        AudioPlayer.INSTANCE.play(audioPath, listener);
                    }
                }).start();

            String msg = "Played audio: " + textLabel;
            Toast.makeText(super.getContext(), msg, Toast.LENGTH_SHORT).show();
            //TODO check that the thread is stopped again at some point. [OLD PARROT TODO]
        } else {
            Log.d(TAG, "No sound attatched: " + pictogramID + "\n\tOn:" + textLabel);
        }
    }

	@Override
    public String[] getTags() {
        return null;
    }

	@Override
    public String getImageData() {
        return null;
    }

	@Override
    public String getAudioData() {
        return null;
    }


	@Override
    public String getTextData() {
        return null;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTextLabel() {
        return textLabel;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public long getPictogramID() {
        return pictogramID;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.imagePath);
		dest.writeString(this.textLabel);
		dest.writeString(this.audioPath);
		dest.writeLong(this.pictogramID);
	}
	
	public static final Parcelable.Creator<KlimPictogram> CREATOR= new Parcelable.Creator<KlimPictogram>() {
		 
		@Override
		public KlimPictogram createFromParcel(Parcel source) {
			return new KlimPictogram(source);  //using parcelable constructor
		}
		 
		@Override
		public KlimPictogram[] newArray(int size) {
			return new KlimPictogram[size];
		}
	};
}
