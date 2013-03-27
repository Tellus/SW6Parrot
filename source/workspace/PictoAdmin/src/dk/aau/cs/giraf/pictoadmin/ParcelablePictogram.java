package dk.aau.cs.giraf.pictoadmin;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * 
 * @PARROT
 * The Pictogram class contains the pictures used in PARROT, as well as associated sounds and text.
 *
 */
public class ParcelablePictogram implements Parcelable {
	private String name;              ///< Corresponding text.
	private String imagePath;               ///< Corresponding image.
	private String soundPath;             ///< Corresponding sound effect.
	private String wordPath; 		///< Corresponding pronounciation.

	private long imageID = -1;
	private long soundID = -1;
	private long wordID = -1;
	private boolean newPictogram = false;
	private boolean changed = false;
	private Activity parent;
	private Bitmap bitmap;

	//This are the constructor for the Pictogram class
	public ParcelablePictogram(String name, String imagePath, String soundPath, String wordPath)
	{
		this.setName(name);
		this.setImagePath(imagePath);
		this.setSoundPath(soundPath);
		this.setWordPath(wordPath);
	}
	
	public ParcelablePictogram(String name, String imagePath, String soundPath, String wordPath, Activity parent)
	{
		this.setName(name);
		this.setImagePath(imagePath);
		this.setSoundPath(soundPath);
		this.setWordPath(wordPath);
		this.parent = parent;
	}

	public ParcelablePictogram(Parcel in)
	{
		String[] data= new String[4];
		 
		in.readStringArray(data);
		this.name	   = data[0];
		this.imagePath = data[1];
		this.soundPath = data[2];
		this.wordPath  = data[3];
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[]{this.name, this.imagePath, this.soundPath, this.wordPath});
	}
	
	public static final Parcelable.Creator<ParcelablePictogram> CREATOR= new Parcelable.Creator<ParcelablePictogram>() {
		 
		@Override
		public ParcelablePictogram createFromParcel(Parcel source) {
			return new ParcelablePictogram(source);  //using parcelable constructor
		}
		 
		@Override
		public ParcelablePictogram[] newArray(int size) {
			return new ParcelablePictogram[size];
		}
	};
	
	//TODO make methods to ensure that the constructor can not put illegal arguments as the path for images, sounds and words


	public Bitmap getBitmap()
	{
		if(bitmap == null)
		{
			if(isEmpty() == true)
			{
				Resources res = parent.getResources();
				bitmap = BitmapFactory.decodeResource(res, 0);
			}
			else
			{
				bitmap = BitmapFactory.decodeFile(imagePath);
			}
		}
		return bitmap;
	
	}


	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSoundPath() {
		return soundPath;
	}

	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}

	public String getWordPath() {
		return wordPath;
	}

	public void setWordPath(String wordPath) {
		this.wordPath = wordPath;
	}

	private boolean validPath(String path)
	{
		//this method checks if a given path is valid
		//TODO write me...
		return true; //change me
	}


	public long getImageID() {
		return imageID;
	}

	public void setImageID(long id) {
		this.imageID = id;
	}

	public long getSoundID() {
		return soundID;
	}

	public void setSoundID(long soundID) {
		this.soundID = soundID;
	}

	public long getWordID() {
		return wordID;
	}

	public void setWordID(long wordID) {
		this.wordID = wordID;
	}

	public boolean isNewPictogram() {
		return newPictogram;
	}

	public void setNewPictogram(boolean newPictogram) {
		this.newPictogram = newPictogram;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean isEmpty() {
		if(this.name.equals("#emptyPictogram#")) return true;
		else return false;
	}
}
