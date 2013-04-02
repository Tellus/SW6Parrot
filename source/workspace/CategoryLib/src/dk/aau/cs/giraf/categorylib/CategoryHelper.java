package dk.aau.cs.giraf.categorylib;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;


public class CategoryHelper {

	Helper help=null;
	Activity parent=null;
	
	
	public CategoryHelper(Activity activity)
	{
		this.parent = activity;
		help = new Helper(parent); 
	}
	
	public List<PARROTCategory> getTempCategories(Profile childProfile)
	{
		List<PARROTCategory> categories= new ArrayList<PARROTCategory>();
		
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		List<Media> childMedia = help.mediaHelper.getMediaByProfile(childProfile);

		Log.v("MessageParrot","hentet childMedia");

		for(Media m : childMedia)
		{
			if(m.getMType().equalsIgnoreCase("image"))
			{
				Log.v("MessageParrot", "in IMAGE if");
				pictograms.add(loadPictogram(m.getId()));
				Log.v("MessageParrot", "efter indlęsning af media");
			}
		}
		if(pictograms.isEmpty())
		{
			Log.v("MessageParrot", "pictograms is empty");
		}
		Log.v("MessageParrot", "efter indlęsning af media");
		
		PARROTCategory tempCat3 = new PARROTCategory("Kategori 1", 0xff05ff12, pictograms.get(0));
		PARROTCategory tempCatSUB1 = new PARROTCategory("SUB1",0xff05ff12, pictograms.get(0));
		

		for(Pictogram p : pictograms)
		{
			tempCat3.addPictogram(p);
			tempCatSUB1.addPictogram(p);
		}

		tempCat3.addSubCategory(tempCatSUB1);

		PARROTCategory tempCat4 = new PARROTCategory("Kategori 2", 0xffff0000, pictograms.get(10));
		tempCat4.addPictogram(pictograms.get(10));
		tempCat4.addPictogram(pictograms.get(13));
		tempCat4.addPictogram(pictograms.get(12));
		tempCat4.addPictogram(pictograms.get(11));

		PARROTCategory tempCatSUB2 = new PARROTCategory("SUB2", 0xffff0000, pictograms.get(10));
		tempCatSUB2.addPictogram(pictograms.get(3));
		tempCatSUB2.addPictogram(pictograms.get(5));
		tempCatSUB2.addPictogram(pictograms.get(9));
		tempCatSUB2.addPictogram(pictograms.get(1));	
		
		tempCat4.addSubCategory(tempCatSUB2);
		
		categories.add(tempCat3);
		categories.add(tempCat4);
		
		return categories;
	}
	
	private Pictogram loadPictogram(long idPictogram)
	{
		Pictogram pic = null;
		Media media=help.mediaHelper.getSingleMediaById(idPictogram); //This is the image media //TODO check type

		List<Media> subMedias =	help.mediaHelper.getSubMediaByMedia(media); 
		String soundPath = null;
		String wordPath = null;
		long soundID = -1; //If this value is still -1 when we save a media, it is because the pictogram has no sound.
		long wordID = -1;
		
		if(subMedias != null)	//Media files can have a link to a sub-media file, check if this one does.
		{	
			Media investigatedMedia;
			for(int i = 0; i<subMedias.size();i++) 		
			{
				investigatedMedia = subMedias.get(i);
				if(investigatedMedia.getMType().equals("SOUND"))
				{
					soundPath = investigatedMedia.getMPath();
					soundID= investigatedMedia.getId();
				}
				else if(investigatedMedia.getMType().equals("WORD"))
				{
					wordPath = investigatedMedia.getMPath();
					wordID = investigatedMedia.getId();
				}
			}
		}
		pic = new Pictogram(media.getName(), media.getMPath(), soundPath, wordPath);
		//set the different ID's
		pic.setImageID(idPictogram);
		pic.setSoundID(soundID);
		pic.setWordID(wordID);

		return pic;
	}

}
