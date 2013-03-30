package dk.aau.cs.giraf.parrot;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.categorylib.Pictogram;
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

		/*new settings*/
		int noOfBoxes = Integer.valueOf(profileSettings.get("SentenceboardSettings").get("NoOfBoxes"));
		boolean showText = Boolean.valueOf(profileSettings.get("PictogramSettings").get("ShowText"));
		String PictogramSize = String.valueOf(profileSettings.get("PictogramSettings").get("PictogramSize"));
		int sentenceColour = Integer.valueOf(profileSettings.get("SentenceboardSettings").get("Color"));	
		
		parrotUser.setSentenceBoardColor(sentenceColour);
		parrotUser.setNumberOfSentencePictograms(noOfBoxes);
		if(PictogramSize.equalsIgnoreCase("MEDIUM"))
		{ 
			parrotUser.setPictogramSize(PARROTProfile.PictogramSize.MEDIUM);
		}
		else if(PictogramSize.equalsIgnoreCase("LARGE"))
		{
			parrotUser.setPictogramSize(PARROTProfile.PictogramSize.LARGE);
		}
		
		if(showText)
		{
			parrotUser.setShowText(true);
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
	public void saveChanges(PARROTProfile user)
	{
		Profile prof = help.profilesHelper.getProfileById(user.getProfileID());
		Setting<String, String, String> profileSetting = new Setting<String, String, String>();
		profileSetting = help.appsHelper.getSettingByIds(app.getId(), prof.getId());
		
		profileSetting.remove("SentenceboardSettings");
		profileSetting.remove("PictogramSettings");
		
		//save profile settings
		profileSetting.addValue("SentenceboardSettings", "Color", String.valueOf(user.getSentenceBoardColor()));
		profileSetting.get("SentenceboardSettings").put("NoOfBoxes", String.valueOf(user.getNumberOfSentencePictograms()));
		profileSetting.addValue("PictogramSettings","PictogramSize", String.valueOf(user.getPictogramSize()));
		profileSetting.get("PictogramSettings").put("ShowText", String.valueOf(user.getShowText()));
		
		app.setSettings(profileSetting);
		help.appsHelper.modifyAppByProfile(app, prof);
		
	}

}
