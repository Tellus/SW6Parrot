package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.util.Log;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

/**
 * 
 * @PARROT
 * The PARROT Data Loader is used for interacting with the admin functionality of the GIRAF Project.
 *
 */
public class PARROTDataLoader {

	private Activity parrent;
	private Helper help;
	private App app;

	public PARROTDataLoader(Activity activity)
	{
		this.parrent = activity;
		help = new Helper(parrent); 
		app = PARROTActivity.getApp();

	}


	/**
	 * 
	 * @return
	 * An ArrayList of all the children asociated with the guardian who is currently using the system.
	 */
	public ArrayList<PARROTProfile> getChildrenFromCurrentGuardian()
	{
		ArrayList<PARROTProfile> parrotChildren = new ArrayList<PARROTProfile>();
		Profile guardian = help.profilesHelper.getProfileById(PARROTActivity.getGuardianID());
		List<Profile> children = help.profilesHelper.getChildrenByGuardian(guardian);
		
		for(int i = 0;i<children.size();i++)
		{
			parrotChildren.add(loadProfile(children.get(i).getId(), app.getId()));
		}
		return parrotChildren;
	}


	/**
	 * @param childId
	 * The ID of the child using the app.
	 * @param appId
	 * The ID of the app.
	 * @return
	 * This methods loads a specific PARROTProfile.
	 */
	public PARROTProfile loadProfile(long childId,long appId)	
	{
		Profile prof =null;

		
		if(childId != -1 && appId >=0)
		 {
				
			prof = help.profilesHelper.getProfileById(childId);	//It used to be "currentProfileId"
		
			Pictogram pic = new Pictogram(prof.getFirstname(), prof.getPicture(), null, null);	//TODO discuss whether this image might be changed
			PARROTProfile parrotUser = new PARROTProfile(prof.getFirstname(), pic);
			
			parrotUser.setProfileID(prof.getId());
			Setting<String, String, String> specialSettings = help.appsHelper.getSettingByIds(appId, childId);
					//app.getSettings();//This object might be null
			
			try
			{
					
				//Load the settings
				parrotUser = loadSettings(parrotUser, specialSettings);
				//Add all of the categories to the profile
				String categoryString = null;
				for(int number = 0; specialSettings.get("category"+number) != null; number++)
				{
					categoryString = specialSettings.get("category"+number).get("pictograms");
					String colourString = specialSettings.get("category"+number).get("colour");
					int col=Integer.valueOf(colourString);
					String iconString = specialSettings.get("category"+number).get("icon");
					String catName = specialSettings.get("category"+number).get("name");
					parrotUser.addCategory(loadCategory(catName,categoryString,col,iconString));					
				}
				return parrotUser;
			}
			
			catch(NullPointerException e)
			{
				//TODO make a exception that can be catched later.
				return null;
			}
		}
		//If an error has happened, return null
		return null;
	}

	private PARROTProfile loadSettings(PARROTProfile parrotUser, Setting<String, String, String> profileSettings) {
		
		
		int sentenceColour=0xaaafff;
		int catColour=0xbbbfffff;
		//First we load the colour settings
		catColour = Integer.valueOf(profileSettings.get("ColourSettings").get("SuperCategory"));
		sentenceColour = Integer.valueOf(profileSettings.get("ColourSettings").get("SentenceBoard"));
		Log.v("MessageParrot","I loadSettings: efter get colour Settings");
		parrotUser.setCategoryColor(catColour);
		parrotUser.setSentenceBoardColor(sentenceColour);
		Log.v("MessageParrot","I loadSettings: efter set colour i parrotUser");
		//Then we load the tab settings
		for(int i = 0; i<profileSettings.get("Rights").size(); i++)
		{
			parrotUser.setRights(i, Boolean.valueOf(profileSettings.get("Rights").get("tab" + i)));
		}
		
		return parrotUser;
	}
	//This method loads category
	public PARROTCategory loadCategory(String catName, String pictureIDs,int colour,String iconString)
	{
		Long iconId = Long.valueOf(iconString);
		PARROTCategory cat = new PARROTCategory(catName, colour, loadPictogram(iconId));
		ArrayList<Long> listIDs = getIDsFromString(pictureIDs);
		for(int i = 0; i<listIDs.size();i++)
		{
			cat.addPictogram(loadPictogram(listIDs.get(i)));
		}
		return cat;
	}

	public Pictogram loadPictogram(long idPictogram)
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

	//This function takes a string consisting of IDs, and returns a list of integer IDs instead
	public ArrayList<Long> getIDsFromString(String IDstring)
	{
		ArrayList<Long> listOfID = new ArrayList<Long>();

		if(IDstring !=null && IDstring.charAt(0)!='$' && IDstring.charAt(0)!='#')
		{
			String temp = String.valueOf(IDstring.charAt(0));
			for(int w = 1; IDstring.charAt(w)!='$'; w++)
			{
				if(IDstring.charAt(w)!='#')
				{
					temp = temp+ IDstring.charAt(w);
				}
				else if(IDstring.charAt(w)=='$')
				{
					break;
				}
				else
				{
					listOfID.add(Long.valueOf(temp));
					w++;
					temp = String.valueOf(IDstring.charAt(w));
				}
			}
		}

		return listOfID;
	}

	public void saveProfile(PARROTProfile user)
	{
		Log.v("MessageParrot", "Begin saving in save Profil");
		Setting<String, String, String> profileSetting = new Setting<String, String, String>();
		//save profile settings
		saveSettings(profileSetting, user);

/*		for(int i=0;i<user.getCategories().size();i++)
		{
			profileSetting = saveCategory(user.getCategoryAt(i), i, profileSetting);
		}*/
		//after all the changes are made, we save the settings to the database
		app.setSettings(profileSetting);
		Profile prof = help.profilesHelper.getProfileById(user.getProfileID());
		Log.v("MessageParrot", "before saving in db");
		help.appsHelper.modifyAppByProfile(app, prof);
		Log.v("MessageParrot", "End saving in save Profil");
	}

	public Setting<String, String, String> saveSettings(Setting<String, String, String> profileSettings, PARROTProfile user)
	{
		Log.v("MessageParrot", "Begin saving in save saveSettings");
		//First, we save the colour settings
		profileSettings.addValue("ColourSettings", "SuperCategory", String.valueOf(user.getCategoryColor()));
		profileSettings.get("ColourSettings").put("SentenceBoard", String.valueOf(user.getSentenceBoardColor()));
		Log.v("MessageParrot", "saveSetting: save colour");
		//Then we save the rights, which are the available tabs for the user.
		profileSettings.addValue("Rights", "tab0", String.valueOf(user.getRights(0)));
		profileSettings.get("Rights").put("tab1", String.valueOf(user.getRights(1)));
		profileSettings.get("Rights").put("tab2", String.valueOf(user.getRights(2)));
		Log.v("MessageParrot", "end saveSettings");
		//Now we return the settings so that they can be saved.
		return profileSettings;	
	}

	/*private Setting<String, String, String> saveCategory(PARROTCategory category, int categoryNumber, Setting<String, String, String> profileSetting ) {
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
		profileSetting.get("category"+categoryNumber).put("colour", String.valueOf(category.getCategoryColour()));
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
			wordMedia = new Media(pic.getName(), pic.getWordPath(), true, "WORD", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
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
		else if(pic.getSoundPath() != null)
		{
			soundMedia = new Media(pic.getName(), pic.getSoundPath(), true, "SOUND", PARROTActivity.getUser().getProfileID());	//TODO we might want to set the booleans to false
			soundMedia.setId(pic.getSoundID());
			help.mediaHelper.modifyMedia(soundMedia);
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
		//Update the information about the pictogram so that it is no longer new or changed from the version in the database.
		pic.setChanged(false);
		pic.setNewPictogram(false);
		return pic;

	}*/

}
