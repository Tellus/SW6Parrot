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

public class TestData {
	private Activity parrent;
	private Helper help;
	private App app;
	
	public TestData(Activity activity)
	{
		this.parrent = activity;
		help = PARROTActivity.getHelp();
		app = PARROTActivity.getApp();

	}
	
	public void TESTsaveTestProfile()
	{
		help = new Helper(parrent);
		app = help.appsHelper.getAppByPackageName();
		long profileId;

		/*
		Pictogram badePic = new Pictogram("Bade", "/sdcard/Pictogram/Bade.png", null, "/sdcard/Pictogram/bade.wma");
		badePic.setNewPictogram(true);
		Pictogram børsteTænderPic = new Pictogram("Børste Tænder", "/sdcard/Pictogram/Børste_Tænder.png", null, "/sdcard/Pictogram/børste_tænder.wma");
		børsteTænderPic.setNewPictogram(true);
		Pictogram drikkePic = new Pictogram("Drikke", "/sdcard/Pictogram/Drikke.png", null, "/sdcard/Pictogram/drikke.wma");
		drikkePic.setNewPictogram(true);
		Pictogram duPic = new Pictogram("Du", "/sdcard/Pictogram/Du.png", null, "/sdcard/Pictogram/du.wma");
		duPic.setNewPictogram(true);
		Pictogram filmPic = new Pictogram("Film", "/sdcard/Pictogram/Film.png", null, "/sdcard/Pictogram/film.wma");
		filmPic.setNewPictogram(true);
		Pictogram forHøjtPic = new Pictogram("For Højt", "/sdcard/Pictogram/For_Højt.png", null, "/sdcard/Pictogram/for_højt.wma");
		forHøjtPic.setNewPictogram(true);
		Pictogram gåPic = new Pictogram("Gå", "/sdcard/Pictogram/Gå.png", null, "/sdcard/Pictogram/gå.wma");
		gåPic.setNewPictogram(true);
		Pictogram jaPic = new Pictogram("Ja", "/sdcard/Pictogram/Ja.png", null, "/sdcard/Pictogram/ja.wma");
		jaPic.setNewPictogram(true);
		Pictogram kørePic = new Pictogram("Køre", "/sdcard/Pictogram/Køre.png", null, "/sdcard/Pictogram/køre.wma");
		kørePic.setNewPictogram(true);
		Pictogram laveMadPic = new Pictogram("Lave Mad", "/sdcard/Pictogram/Lave_Mad.png", null, "/sdcard/Pictogram/lave_mad.wma");
		laveMadPic.setNewPictogram(true);
		Pictogram legePic = new Pictogram("Lege", "/sdcard/Pictogram/Lege.png", null, "/sdcard/Pictogram/lege.wma");
		legePic.setNewPictogram(true);
		Pictogram migPic = new Pictogram("Mig", "/sdcard/Pictogram/Mig.png", null, "/sdcard/Pictogram/mig.wma");
		migPic.setNewPictogram(true);
		Pictogram morgenRoutinePic = new Pictogram("Morgen Routine", "/sdcard/Pictogram/Morgen_Routine.png", null, "/sdcard/Pictogram/morgen_routine.wma");
		morgenRoutinePic.setNewPictogram(true);
		Pictogram nejPic = new Pictogram("Nej", "/sdcard/Pictogram/Nej.png", null, "/sdcard/Pictogram/nej.wma");
		nejPic.setNewPictogram(true);
		Pictogram sePic = new Pictogram("Se", "/sdcard/Pictogram/Se.png", null, "/sdcard/Pictogram/se.wma");
		sePic.setNewPictogram(true);
		Pictogram sideNedPic = new Pictogram("Side Ned", "/sdcard/Pictogram/Side_Ned.png", null, "/sdcard/Pictogram/side_ned.wma");
		sideNedPic.setNewPictogram(true);
		Pictogram spilleComputerPic = new Pictogram("Spille Computer", "/sdcard/Pictogram/Spille_Computer.png", null, "/sdcard/Pictogram/spille_computer.wma");
		spilleComputerPic.setNewPictogram(true);
		Pictogram stopPic = new Pictogram("Stop", "/sdcard/Pictogram/Stop.png", null, "/sdcard/Pictogram/stop.wma");
		stopPic.setNewPictogram(true);
		Pictogram sultenPic = new Pictogram("Sulten", "/sdcard/Pictogram/Sulten.png", null, "/sdcard/Pictogram/sulten.wma");
		sultenPic.setNewPictogram(true);
		Pictogram søvnigPic = new Pictogram("Søvnig", "/sdcard/Pictogram/Søvnig.png", null, "/sdcard/Pictogram/søvnig.wma");
		søvnigPic.setNewPictogram(true);
		Pictogram taleSammenPic = new Pictogram("Tale Sammen", "/sdcard/Pictogram/Tale_Sammen.png", null, "/sdcard/Pictogram/tale_sammen.wma");
		taleSammenPic.setNewPictogram(true);
		Pictogram tørstigPic = new Pictogram("Tørstig", "/sdcard/Pictogram/Tørstig.png", null, "/sdcard/Pictogram/tørstig.wma");
		tørstigPic.setNewPictogram(true);
		Pictogram væreStillePic = new Pictogram("Være Stille", "/sdcard/Pictogram/Være_Stille.png", null, "/sdcard/Pictogram/være_stille.wma");
		væreStillePic.setNewPictogram(true);*/

		
		List<Profile> listOfChildren = help.profilesHelper.getChildren();

		
		Log.v("MessageParrot","before for");
		for(Profile tempProf: listOfChildren)
		{
			ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
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
			Log.v("MessageParrot", "efter indlæsning af media");
			
			profileId = tempProf.getId();
			
			Setting<String, String, String> profileSetting = new Setting<String, String, String>();
			
			//START TEMP LINES
			Pictogram tempPic = new Pictogram(tempProf.getFirstname(),tempProf.getPicture(),null, null);
	
			PARROTProfile testProfile = new PARROTProfile(tempProf.getFirstname(), tempPic);
			testProfile.setProfileID(profileId);
			Log.v("MessageParrot", "har indlæst test profil, til at lave kategori");
			

			PARROTCategory tempCat3 = new PARROTCategory("Kategori 1", 0xff05ff12, pictograms.get(0));
	
			for(Pictogram p : pictograms)
			{
				tempCat3.addPictogram(p);
			}
			/*tempCat3.addPictogram(badePic);
			tempCat3.addPictogram(børsteTænderPic);
			tempCat3.addPictogram(drikkePic);
			tempCat3.addPictogram(duPic);
			tempCat3.addPictogram(filmPic);
			tempCat3.addPictogram(forHøjtPic);
			tempCat3.addPictogram(gåPic);
			tempCat3.addPictogram(jaPic);
			tempCat3.addPictogram(kørePic);
			tempCat3.addPictogram(laveMadPic);
			tempCat3.addPictogram(legePic);
			tempCat3.addPictogram(migPic);
			tempCat3.addPictogram(morgenRoutinePic);
			tempCat3.addPictogram(nejPic);
			tempCat3.addPictogram(sePic);
			tempCat3.addPictogram(sideNedPic);
			tempCat3.addPictogram(spilleComputerPic);
			tempCat3.addPictogram(stopPic);
			tempCat3.addPictogram(sultenPic);
			tempCat3.addPictogram(søvnigPic);
			tempCat3.addPictogram(taleSammenPic);
			tempCat3.addPictogram(tørstigPic);
			tempCat3.addPictogram(væreStillePic);*/
		
			testProfile.addCategory(tempCat3);
	
			PARROTCategory tempCat4 = new PARROTCategory("Kategori 2", 0xffff0000, pictograms.get(10));
			tempCat4.addPictogram(pictograms.get(10));
			tempCat4.addPictogram(pictograms.get(13));
			tempCat4.addPictogram(pictograms.get(12));
			tempCat4.addPictogram(pictograms.get(11));
			
			Log.v("MessageParrot", "settings bliver sat");
			
			testProfile.setRights(0, true);
			testProfile.setRights(1, true);
			testProfile.setRights(2, true);
			testProfile.addCategory(tempCat4);
			testProfile.setCategoryColor(0xff23ff12);
			testProfile.setSentenceBoardColor(0xffffffff);
			PARROTActivity.setUser(testProfile);
	
			Log.v("MessageParrot", "gem kategorier");
			
			for(int i=0;i<testProfile.getCategories().size();i++)
			{
				profileSetting = saveCategory(testProfile.getCategoryAt(i), i, profileSetting);
			}
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

	
	
	public Setting<String, String, String> saveSettings(Setting<String, String, String> profileSettings, PARROTProfile user)
	{
		//First, we save the colour settings
		profileSettings.addValue("ColourSettings", "SuperCategory", String.valueOf(user.getCategoryColor()));
		profileSettings.get("ColourSettings").put("SentenceBoard", String.valueOf(user.getSentenceBoardColor()));

		//Then we save the rights, which are the available tabs for the user.
		profileSettings.addValue("Rights", "tab0", String.valueOf(user.getRights(0)));
		profileSettings.get("Rights").put("tab1", String.valueOf(user.getRights(1)));
		profileSettings.get("Rights").put("tab2", String.valueOf(user.getRights(2)));

		//Now we return the settings so that they can be saved.
		return profileSettings;	
	}


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
		profileSetting.get("category"+categoryNumber).put("colour", String.valueOf(category.getCategoryColour()));
		//and then we save the icon
		Pictogram icon = category.getIcon();
		icon = savePictogram(icon);
		profileSetting.get("category"+categoryNumber).put("icon", String.valueOf(icon.getImageID()));

		return profileSetting;
	}

	/**
	 * 
	 * @PARROT
	 *This method is used to save completely new pictograms to the database, as well as modify existing ones.
	 */
	private Pictogram savePictogram(Pictogram pic)
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



}
