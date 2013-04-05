package dk.aau.cs.giraf.categorylib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;


public class CategoryHelper {

	Helper help=null;
	Activity activity=null;
	File categoryXmlData;
	
	public CategoryHelper(Activity activity)
	{
		this.activity = activity;
		help = new Helper(activity); 
		

		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		}  else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		Log.v("MessagePARROT","mExternalStorageAvailable: " +mExternalStorageAvailable + " _mExternalStorageWriteable: " +mExternalStorageWriteable );
		
		
		
		
		XmlSerializer xmlSerializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	 
	    
	    try {
			xmlSerializer.setOutput(writer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("CategoryHelper","setOutput error");
		} 

	    categoryXmlData = new File(Environment.getExternalStorageDirectory().getPath() +"/Pictures/giraf/categoryData.xml");
		if(!categoryXmlData.exists())
		{
		    try{
		    	Log.v("MessagePARROT","no xml exist, creating xml");
		    	categoryXmlData.createNewFile();
		    	
		    
		    }catch(IOException e)
		    {
		        Log.e("IOException", "Exception in create new File(");
		    }
		}
		else
		{
			Log.v("MessagePARROT", "xml exist");
			
		}
		
	}
	
	public List<PARROTCategory> getChildsCategories(long childId)
	{
		List<PARROTCategory> cat=null;
		return cat;
	}
	
	public void saveChangesInCategory(PARROTCategory category,long childId)
	{
		
	}
	
	public void deleteCategory(PARROTCategory category, long childId)
	{
		
	}
	
	public void addNewCategory(PARROTCategory category,long childId)
	{
		
		FileOutputStream fileos = null;       	
        try{
        	fileos = new FileOutputStream(categoryXmlData);
        }catch(FileNotFoundException e){
        	Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        XmlSerializer serializer = Xml.newSerializer();

        try {
        	//we set the FileOutputStream as output for the serializer, using UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			//Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null) 
			serializer.startDocument(null, Boolean.valueOf(true)); 
			//start a tag called "root"
			serializer.startTag(null, "ChildId");
			Log.v("PARROTMESSAGE","ChildId");
			serializer.attribute(null, "id", Long.toString(childId));
			//i indent code just to have a view similar to xml-tree
				serializer.startTag(null, "Category");
				Log.v("PARROTMESSAGE","Category");
					serializer.attribute(null, "name", category.getCategoryName());
					serializer.attribute(null, "color", Integer.toString(category.getCategoryColor()));
					serializer.attribute(null, "icon", Long.toString(category.getIcon().getPictogramID()));
					for(PARROTCategory c : category.getSubCategories())
					{
						Log.v("PARROTMESSAGE","SubCategory");
						serializer.startTag(null, "SubCategory");
						serializer.attribute(null, "name", c.getCategoryName());
						serializer.attribute(null, "color", Integer.toString(category.getCategoryColor()));
						serializer.attribute(null, "icon", Long.toString(c.getIcon().getPictogramID()));
						for(Pictogram p : c.getPictograms())
						{
							Log.v("PARROTMESSAGE","Pictogram");
							serializer.startTag(null, "Pictogram");
							serializer.attribute(null, "id", Long.toString(p.getPictogramID()));
							serializer.endTag(null, "Pictogram");
							Log.v("PARROTMESSAGE","Pictogram end");
						}
						serializer.endTag(null, "SubCategory");
						Log.v("PARROTMESSAGE","SubCategory end");
					}
					
				serializer.endTag(null, "Category");
				Log.v("PARROTMESSAGE","category end");
				
				
				
			serializer.endTag(null, "ChildId");
			Log.v("PARROTMESSAGE","ChildId end");
			Log.v("PARROTMESSAGE","in xml");
			serializer.endDocument();
			Log.v("PARROTMESSAGE","end doc");
			//write xml data into the FileOutputStream
			serializer.flush();
			Log.v("PARROTMESSAGE","end flush");
			//finally we close the file stream
			fileos.close();
			Log.v("PARROTMESSAGE","writting to xml");
			
	       
		} catch (Exception e) {
			Log.v("PARROTMESSAGE","error occurred while creating xml file");
		}
	}

	public List<PARROTCategory> getTempCategoriesWithNewPictogram(long childid)
	{
		Profile childProfile= help.profilesHelper.getProfileById(childid);
		List<PARROTCategory> categories= new ArrayList<PARROTCategory>();
		
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		List<Media> childMedia = help.mediaHelper.getMediaByProfile(childProfile);

		Log.v("MessageParrot","hentet childMedia");

		for(Media m : childMedia)
		{
			if(m.getMType().equalsIgnoreCase("image"))
			{
				Log.v("MessageParrot", "in IMAGE if");
				Pictogram pic = PictoFactory.INSTANCE.getPictogram(activity.getApplicationContext(), m.getId());
				pictograms.add(pic);
				//pictograms.add(loadPictogram(m.getId()));
				Log.v("MessageParrot", "efter indlæsning af media");
			}
		}
		
		
		PARROTCategory tempCat3 = new PARROTCategory("Kategori 1", 0xff05ff12, pictograms.get(0));
		PARROTCategory tempCatSUB1 = new PARROTCategory("SUB1",0xff05ff12, pictograms.get(0));
		
		int count=1;
		for(Pictogram p : pictograms)
		{
			tempCatSUB1.addPictogram(p);
			
			if(count%4==0)
			{
				tempCat3.addPictogram(p);
			}
			count++;
		}

		

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
		
		tempCat3.addSubCategory(tempCatSUB1);
		tempCat4.addSubCategory(tempCatSUB2);
				
		categories.add(tempCat3);
		categories.add(tempCat4);
		
		addNewCategory(categories.get(0), childid);
		
		return categories;
	}
	
	
	public List<PARROTCategory> getTempCategoriesWithNewPictogram(Profile childProfile)
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
				Pictogram pic = PictoFactory.INSTANCE.getPictogram(activity.getApplicationContext(), m.getId());
				pictograms.add(pic);
				//pictograms.add(loadPictogram(m.getId()));
				Log.v("MessageParrot", "efter indlæsning af media");
			}
		}
		
		
		PARROTCategory tempCat3 = new PARROTCategory("Kategori 1", 0xff05ff12, pictograms.get(0));
		PARROTCategory tempCatSUB1 = new PARROTCategory("SUB1",0xff05ff12, pictograms.get(0));
		
		int count=1;
		for(Pictogram p : pictograms)
		{
			tempCatSUB1.addPictogram(p);
			
			if(count%4==0)
			{
				tempCat3.addPictogram(p);
			}
			count++;
		}

		

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
		
		tempCat3.addSubCategory(tempCatSUB1);
		tempCat4.addSubCategory(tempCatSUB2);
				
		categories.add(tempCat3);
		categories.add(tempCat4);
		
		return categories;
	}
	
	
	public List<PARROTCategoryOLD> getTempCategoriesOldPictograms(Profile childProfile)
	{
		List<PARROTCategoryOLD> categories= new ArrayList<PARROTCategoryOLD>();
		
		ArrayList<PictogramOLD> pictograms = new ArrayList<PictogramOLD>();
		List<Media> childMedia = help.mediaHelper.getMediaByProfile(childProfile);

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
		
		PARROTCategoryOLD tempCat3 = new PARROTCategoryOLD("Kategori 1", 0xff05ff12, pictograms.get(0));
		PARROTCategoryOLD tempCatSUB1 = new PARROTCategoryOLD("SUB1",0xff05ff12, pictograms.get(0));
		
		int count=1;
		for(PictogramOLD p : pictograms)
		{
			tempCatSUB1.addPictogram(p);
			
			if(count%4==0)
			{
				tempCat3.addPictogram(p);
			}
			count++;
		}

		

		PARROTCategoryOLD tempCat4 = new PARROTCategoryOLD("Kategori 2", 0xffff0000, pictograms.get(10));
		tempCat4.addPictogram(pictograms.get(10));
		tempCat4.addPictogram(pictograms.get(13));
		tempCat4.addPictogram(pictograms.get(12));
		tempCat4.addPictogram(pictograms.get(11));

		PARROTCategoryOLD tempCatSUB2 = new PARROTCategoryOLD("SUB2", 0xffff0000, pictograms.get(10));
		tempCatSUB2.addPictogram(pictograms.get(3));
		tempCatSUB2.addPictogram(pictograms.get(5));
		tempCatSUB2.addPictogram(pictograms.get(9));
		tempCatSUB2.addPictogram(pictograms.get(1));	
		
		tempCat3.addSubCategory(tempCatSUB1);
		tempCat4.addSubCategory(tempCatSUB2);
				
		categories.add(tempCat3);
		categories.add(tempCat4);
		
		return categories;
	}
	
	private PictogramOLD loadPictogram(long idPictogram)
	{
		PictogramOLD pic = null;
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
		pic = new PictogramOLD(media.getName(), media.getMPath(), soundPath, wordPath);
		//set the different ID's
		pic.setImageID(idPictogram);
		pic.setSoundID(soundID);
		pic.setWordID(wordID);

		return pic;
	}

}
