package dk.aau.cs.giraf.parrot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.pictogram.Pictogram;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

public class TestData {
	private Activity parrent;
	private Helper help;
	private App app;
	private Context c;
	public TestData(Activity activity)
	{
		this.parrent = activity;
		help = PARROTActivity.getHelp();
		app = PARROTActivity.getApp();
		c = parrent.getApplicationContext();

	}
	
	public void TESTsaveTestProfile()
	{
		help = new Helper(parrent);
		app = help.appsHelper.getAppByPackageName();
		long profileId;		
		List<Profile> listOfChildren = help.profilesHelper.getChildren();
		
		File categoriesXML = new File(Environment.getExternalStorageDirectory().getPath() + "/Categories.xml");
        try{
        	categoriesXML.createNewFile();
        }catch(IOException e)
        {
            Log.e("IOException", "Exception in create new File(");
        }
        
		Log.v("MessageParrot","before for");
		for(Profile tempProf: listOfChildren)
		{
			/*ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
			List<Media> childMedia = help.mediaHelper.getMediaByProfile(tempProf);

			Log.v("MessageParrot","hentet childMedia");

			for(Media m : childMedia)
			{
				if(m.getMType().equalsIgnoreCase("image"))
				{
					Log.v("MessageParrot", "in IMAGE if");
					pictograms.add(loadPictogram(m.getId()));
					Log.v("MessageParrot", "efter indlæsning af media");
				}
			}
			if(pictograms.isEmpty())
			{
				Log.v("MessageParrot", "pictograms is empty");
			}
			Log.v("MessageParrot", "efter indlæsning af media");*/
			profileId = tempProf.getId();

			
			Setting<String, String, String> profileSetting = new Setting<String, String, String>();
			
			//START TEMP LINES
			Pictogram tempPic = new Pictogram(c, tempProf.getPicture(),tempProf.getFirstname(), "", -1);
			
	
			PARROTProfile testProfile = new PARROTProfile(tempProf.getFirstname(), tempPic);
			testProfile.setProfileID(profileId);
			Log.v("MessageParrot", "har indlæst test profil, til at lave kategori");
			

					
			testProfile.setNumberOfSentencePictograms(2);
			testProfile.setPictogramSize(PARROTProfile.PictogramSize.MEDIUM);
			testProfile.setSentenceBoardColor(0xffffffff);
			testProfile.setShowText(true);
			
			PARROTActivity.setUser(testProfile);
	
			Log.v("MessageParrot", "gem kategorier");
			
		/*	for(int i=0;i<testProfile.getCategories().size();i++)
			{
				profileSetting = saveCategory(testProfile.getCategoryAt(i), i, profileSetting);
			}*/
			Log.v("MessageParrot", "gem settings");
			
			profileSetting = saveSettings(profileSetting, testProfile);
			app.setSettings(profileSetting);
			
			
			help.appsHelper.modifyAppByProfile(app, tempProf);
			long appID=app.getId();
			Log.v("MessageParrot", "færdig");
		//	PARROTProfile parrot =loadProfile(profileId, appID);
		//	PARROTActivity.setUser(parrot);
			//END TEMP LINES
		}
		
		
	}
	
	public Setting<String, String, String> saveSettings(Setting<String, String, String> profileSettings, PARROTProfile user)
	{

		profileSettings.addValue("SentenceboardSettings", "Color", String.valueOf(user.getSentenceBoardColor()));
		profileSettings.get("SentenceboardSettings").put("NoOfBoxes", String.valueOf(user.getNumberOfSentencePictograms()));
		profileSettings.addValue("PictogramSettings","PictogramSize", String.valueOf(user.getPictogramSize()));
		profileSettings.get("PictogramSettings").put("ShowText", String.valueOf(user.getShowText()));
		//Now we return the settings so that they can be saved.
		return profileSettings;	
	}

	/*public Pictogram loadPictogram(long idPictogram)
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
	}*/

/*
	private Setting<String, String, String> saveCategory(PARROTCategory category, int categoryNumber, Setting<String, String, String> profileSetting ) {
		//first, we save the pictograms
		String pictogramString = "";
		for(int i=0;i<category.getPictograms().size();i++)
		{
			Pictogram pic = category.getPictogramAtIndex(i);

			pic = savePictogram(pic);

			//In any case, save the references
			pictogramString = pictogramString + pic.getImageID()+'#';

		}
		pictogramString += "$";
		pictogramString = pictogramString.replace("#$", "$");	//Here we make sure that the end is $, and not #$
		//now save the pictograms
		profileSetting.addValue("category"+categoryNumber, "pictograms", pictogramString);
		//And the name
		profileSetting.get("category"+categoryNumber).put("name", category.getCategoryName());
		//then we save the colour
		profileSetting.get("category"+categoryNumber).put("colour", String.valueOf(category.getCategoryColor()));
		//and then we save the icon
		Pictogram icon = category.getIcon();
		icon = savePictogram(icon);
		profileSetting.get("category"+categoryNumber).put("icon", String.valueOf(icon.getImageID()));

		return profileSetting;
	}*/

	/**
	 * 
	 * @PARROT
	 *This method is used to save completely new pictograms to the database, as well as modify existing ones.
	 */
	/*private Pictogram savePictogram(Pictogram pic)
	{
		Media imageMedia = null;
		Media soundMedia = null;
		Media wordMedia = null;
		if(pic.getImageID() == -1) //if the picture is new in the database
		{
			imageMedia = new Media(pic.getName(), pic.getImagePath(), true, "IMAGE", PARROTActivity.getUser().getProfileID());
			pic.setImageID(help.mediaHelper.insertMedia(imageMedia));
			imageMedia.setId(pic.getImageID());
		}
		else
		{
			imageMedia = new Media(pic.getName(),pic.getImagePath(),true,"IMAGE",PARROTActivity.getUser().getProfileID());
			imageMedia.setId(pic.getImageID());
			help.mediaHelper.modifyMedia(imageMedia);
		}

		if(pic.getWordID() == -1 && pic.getWordPath() != null) //if the word is not in the database
		{
			wordMedia = new Media(pic.getName(), pic.getWordPath(), true, "IMAGE", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
			pic.setWordID(help.mediaHelper.insertMedia(wordMedia));
			wordMedia.setId(pic.getWordID());
		}
		else if(pic.getWordPath() != null)
		{
			wordMedia = new Media(pic.getName(), pic.getWordPath(), true, "WORD", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
			wordMedia.setId(pic.getWordID());
			help.mediaHelper.modifyMedia(wordMedia);
		}

		if(pic.getSoundID() == -1 && pic.getSoundPath() != null) //if the sound is new in the database
		{
			soundMedia = new Media(pic.getName(), pic.getSoundPath(), true, "SOUND", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
			pic.setSoundID(help.mediaHelper.insertMedia(soundMedia));
			soundMedia.setId(pic.getSoundID());
		}
		else
		{
			if(pic.getSoundPath() != null)
			{
				soundMedia = new Media(pic.getName(), pic.getSoundPath(), true, "SOUND", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
				soundMedia.setId(pic.getSoundID());
				help.mediaHelper.modifyMedia(soundMedia);
			}
		}

		// save the submedia references for NEW pictograms
		if(pic.isNewPictogram() == true)
		{
			if(soundMedia != null)
			{
				help.mediaHelper.attachSubMediaToMedia(soundMedia, imageMedia);
			}
			if(wordMedia !=null)
			{
				help.mediaHelper.attachSubMediaToMedia(wordMedia, imageMedia);
			}
		}
		//save the submedia references for a MODIFIED pictogram
		else if(pic.isChanged() == true)
		{
			List<Media> mediasToRemove = help.mediaHelper.getSubMediaByMedia(imageMedia);
			for(int i = 0;i<mediasToRemove.size();i++)	//find all previous references, and remove them
			{
				help.mediaHelper.removeSubMediaAttachmentToMedia(mediasToRemove.get(i), imageMedia);
			}
			if(soundMedia != null)
			{
				help.mediaHelper.attachSubMediaToMedia(soundMedia, imageMedia);
			}
			if(wordMedia !=null)
			{
				help.mediaHelper.attachSubMediaToMedia(wordMedia, imageMedia);
			}

		}
		//Update the information about the pictogram so that it is nolonger new or changed from the version in the database.
		pic.setChanged(false);
		pic.setNewPictogram(false);
		return pic;

	}
*/


}
