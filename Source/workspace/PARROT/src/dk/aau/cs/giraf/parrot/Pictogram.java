package dk.aau.cs.giraf.parrot;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;


/**
 * 
 * @PARROT
 * The Pictogram class contains the pictures used in PARROT, as well as associated sounds and text.
 *
 */
public class Pictogram {
	private String name;              ///< Corresponding text.
	private String imagePath;               ///< Corresponding image.
	private String soundPath;             ///< Corresponding sound effect.
	private String wordPath; 		///< Corresponding pronounciation.

	private long imageID = -1;
	private long soundID = -1;
	private long wordID = -1;
	private boolean newPictogram =false;
	private boolean changed = false;
	private Activity parrent;
	private Bitmap bitmap;

	//This are the constructor for the Pictogram class
	public Pictogram(String name, String imagePath, String soundPath, String wordPath)
	{
		this.setName(name);
		this.setImagePath(imagePath);
		this.setSoundPath(soundPath);
		this.setWordPath(wordPath);
	}
	
	public Pictogram(String name, String imagePath, String soundPath, String wordPath, Activity parrent)
	{
		this.setName(name);
		this.setImagePath(imagePath);
		this.setSoundPath(soundPath);
		this.setWordPath(wordPath);
		this.parrent = parrent;
	}

	//TODO make methods to ensure that the constructor can not put illegal arguments as the path for images, sounds and words


	public Bitmap getBitmap()
	{
		if(bitmap == null)
		{
			if(isEmpty() == true)
			{
				Resources res = parrent.getResources();
				bitmap = BitmapFactory.decodeResource(res, R.drawable.usynlig);
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

	private void playItem(final String path) {
		// Running this in the background to keep UI reponsive and smooth
		new Thread(new Runnable() {
			public void run() {
				try {
					AudioPlayer.play(path, null);
				} catch (Exception e) {
				}
			}
		}).start();
		//TODO check that the thread is stopped again at some point.
	}

	public void playSound()
	{
		if(soundPath!=null && validPath(soundPath))
		{
			playItem(soundPath);
		}
	}

	public void playWord()
	{
		if(wordPath!=null &&validPath(wordPath))
		{
			playItem(wordPath);
		}
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
		if(this.name.equals("#usynlig#"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
