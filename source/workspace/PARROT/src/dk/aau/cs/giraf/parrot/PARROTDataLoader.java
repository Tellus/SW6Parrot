package dk.aau.cs.giraf.parrot;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import dk.aau.cs.giraf.pictogram.Pictogram;


/**
 * 
 * @PARROT
 * The PARROT Data Loader is used for interacting with the admin functionality of the GIRAF Project.
 *
 */
public class PARROTDataLoader {

	private Activity parent;
	private Helper help;
	private App app;
	private CategoryHelper categoryHelper= null;



	public PARROTDataLoader(Activity activity)
	{
		this.parent = activity;
		help = new Helper(parent); 
		app = help.appsHelper.getAppById(PARROTActivity.getApp().getId()); 
		categoryHelper= new CategoryHelper(parent);

	}


	/**
	 * 
	 * @return
	 * An ArrayList of all the children asociated with the guardian who is currently using the system.
	 */
	public ArrayList<PARROTProfile> getChildrenFromGuardian(long guardianID)
	{
		ArrayList<PARROTProfile> parrotChildren = new ArrayList<PARROTProfile>();
		Profile guardian = help.profilesHelper.getProfileById(guardianID);
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
			//(Context context, final String image, final String text, final String audio, final long id)
			Pictogram pic = new Pictogram(parent.getApplicationContext(), "","", null,-1);
					//new Pictogram(parent.getApplicationContext(),prof.getFirstname(), prof.getPicture(), null, null);	//TODO discuss whether this image might be changed
			PARROTProfile parrotUser = new PARROTProfile(prof.getFirstname(), pic);
			
			parrotUser.setProfileID(prof.getId());
			Setting<String, String, String> specialSettings = help.appsHelper.getSettingByIds(appId, childId);

			//Load the settings
			parrotUser = loadSettings(parrotUser, specialSettings);

			//Add all of the categories to the profile
			String categoryString = null;
			Log.v("MessageParrot", "before categori");
			
			
			//this return null if the child does not exist
			List<PARROTCategory> categories = categoryHelper.getChildsCategories(prof.getId());
			
			if(categories ==null)
			{
				categories = categoryHelper.getTempCategoriesWithNewPictogram(prof.getId());
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(parent);
		
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage(R.string.dialog_message)
		       .setTitle(R.string.dialog_title)
		       .setPositiveButton( R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // User clicked OK, so save the mSelectedItems results somewhere
	                   // or return them to the component that opened the dialog
	                   
	               }
		       })
		       .setNegativeButton(R.string.returnItem, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // User clicked OK, so save the mSelectedItems results somewhere
	                   // or return them to the component that opened the dialog
	            	   parent.finish();
	               }
		       });
				// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
			}
			
			Log.v("MessageXML", "xmlChild does exist");
			for(PARROTCategory c : categories)
			{
				parrotUser.addCategory(c);
			}
			
			return parrotUser;
				
		}

		
		return null;
	}

	private PARROTProfile loadSettings(PARROTProfile parrotUser, Setting<String, String, String> profileSettings) {
		/*new settings*/
		//if(profileSettings.containsKey(SentenceboardSettings))
		Log.v("PARROTMessAGE", "BEFORE get from settings");
		try{
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
		}
		catch(Exception e)
		{
			
		}
		
		return parrotUser;
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
	public void XMLTESTER()
	{
		Log.v("PARROTmessage","start xmltester");
		long childid=11;
		CategoryHelper helper = new CategoryHelper(parent);
		List<PARROTCategory> categories = helper.getTempCategoriesWithNewPictogram(childid);
		
		Log.v("PARROTmessage","save categories");
		for(PARROTCategory category : categories)
		{
			helper.saveCategory(category, childid);	
		}
		helper.saveChangesToXML();
		Log.v("PARROTmessage","done xmltester");
	}

}
