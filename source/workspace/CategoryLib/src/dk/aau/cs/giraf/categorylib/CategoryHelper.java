package dk.aau.cs.giraf.categorylib;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;


public class CategoryHelper {

	
	Helper help=null;
	Activity activity=null;
	XMLCommunicater communicater= null;
	ArrayList<XMLProfile> xmlData= new ArrayList<XMLProfile>();
	PictoFactory factory= PictoFactory.INSTANCE;
	
	
	public CategoryHelper(Activity activity)
	{
		this.activity = activity;
		help = new Helper(activity); 
		communicater = new XMLCommunicater(); 
		//this should be an easy temperaty way to get static data into the xml
		if(XMLCommunicater.isNewFile==true)
		{
			XMLTESTER();
			XMLCommunicater.isNewFile=false;
		}
	}
	
	
	
	public void saveChangesToXML()
	{
		communicater.setXMLDataAndUpdate(xmlData);
	}
	
 	
	
	private XMLCategoryProfile transformToXMLCategoryProfile(PARROTCategory category)
	{
		Log.v("XMLTESTER","start transformToXMLCategoryProfile");
		XMLCategoryProfile xmlCategory = new XMLCategoryProfile();
		
		xmlCategory.setColor(category.getCategoryColor());
		xmlCategory.setIconID(category.getIcon().getPictogramID());
		xmlCategory.setName(category.getCategoryName());
		
		for(Pictogram p: category.getPictograms())
		{
			xmlCategory.addPictogramId(Long.valueOf(p.getPictogramID()));
		}
		for(PARROTCategory subCategory : category.getSubCategories())
		{
			xmlCategory.addSubcategory(transformToXMLCategoryProfile(subCategory));
		
		}
		
		return xmlCategory;
		
	}
	
	//used when saving or adding a category to child 
	public void saveCategory(PARROTCategory category,long childId)
	{
		Log.v("XMLTESTER","Start saveCategory");
		XMLCategoryProfile categoryProfile = transformToXMLCategoryProfile(category);
		Log.v("XMLTESTER", "_color "+ categoryProfile.getColor() + " _name: "+ categoryProfile.getName());
		ArrayList<XMLCategoryProfile> categoryProfileList = null; 
		
		boolean childExist = false;
		if(!xmlData.isEmpty())
		{
			for(XMLProfile x: xmlData)
			{
				
				if(x.getChildID()==childId)
				{
					categoryProfileList = x.getCategories();
					childExist=true;
					break;
				}
			}
		}
		if(!childExist)
		{
			
			XMLProfile prof= new XMLProfile();
			prof.setChildID(childId);
			categoryProfileList = prof.getCategories();
			xmlData.add(prof);
		}
		else
		{
			
			deleteCategory(category, childId, categoryProfileList);
		}
		//if the category exist then delete
		
		categoryProfileList.add(categoryProfile);
		Log.v("XMLTESTER","done in saveCategory");

		
	}
	
	private void deleteCategory(PARROTCategory category, long childId, ArrayList<XMLCategoryProfile> categoryProfileList)
	{	 
		
		for(XMLCategoryProfile cp : categoryProfileList)
		{
			if(cp.getName()==category.getCategoryName())
			{
				categoryProfileList.remove(cp);

			}
		}
		
	}
	
	public void deleteCategory(PARROTCategory category, long childId)
	{
		XMLProfile childProfile=null;
		ArrayList<XMLCategoryProfile> categoryProfileList = null; 
		for(XMLProfile prof: xmlData)
		{
			if(prof.getChildID()==childId)
			{
				childProfile=prof;
				categoryProfileList = prof.getCategories();
				break;
			}
		}
		deleteCategory(category, childId, categoryProfileList);
	/*	for(XMLCategoryProfile cp : categoryProfileList)
		{
			if(cp.getName()==category.getCategoryName())
			{
				categoryProfileList.remove(cp);

			}
		}*/
		if(categoryProfileList.isEmpty())
		{
			xmlData.remove(childProfile);
		}
		
	
	}

	
	//from here from xml to parrotcategories 
	private PARROTCategory transformToPARROTCategory(XMLCategoryProfile categoryProfile)
	{	
		Log.v("MessageXML","transformToPARROTCategory begin");
		Pictogram icon = factory.getPictogram(activity.getApplicationContext(), categoryProfile.getIconID());
		PARROTCategory category = new PARROTCategory(categoryProfile.getName(), categoryProfile.getColor(), icon);
		
		for(Long pictogramId : categoryProfile.getPictogramsID())
		{
			//Log.v("MessageXML","pic begin");
			Pictogram pictoId = factory.getPictogram(activity.getApplicationContext(), pictogramId);
			category.addPictogram(pictoId);	
			//Log.v("MessageXML","pic end");
		}
		Log.v("MessageXML","pic final end");
		Log.v("MessageXML","catego final begin");
		Log.v("","sub/category not empty");
		for(XMLCategoryProfile cp : categoryProfile.getSubcategories())
		{
			Log.v("MessageXML","sub/cate begin");
			category.addSubCategory(transformToPARROTCategory(cp));
			Log.v("MessageXML","sub/cate end");
		}
		Log.v("MessageXML","transformToPARROTCategory end");
		return category;
	}
	
	public ArrayList<PARROTCategory> getChildsCategories(long childId)
	{
		xmlData = communicater.getXMLData();
		Log.v("XMLTESTER", "after getXMLData");
		ArrayList<PARROTCategory> categories=null;
		XMLProfile xmlChild = null;
		for(XMLProfile profile: xmlData)
		{
			if(profile.getChildID()==childId)
			{
				
				categories = new ArrayList<PARROTCategory>();
				xmlChild=profile;
				break;
			}
		}		
		
		if(xmlChild==null)
		{
			Log.v("MessageXML", "xmlChild does not exist return null");return null;}
		
		for(XMLCategoryProfile cp : xmlChild.getCategories())
		{
			Log.v("MessageXML", "category found begin");
			PARROTCategory category= transformToPARROTCategory(cp);
			Log.v("MessageXML","category: " + category.getCategoryName());
			categories.add(category);
			Log.v("MessageXML", "category found end");
			
		}

		return categories;
	}
	
	
	/** temp file **/
	public ArrayList<PARROTCategory> getTempCategoriesWithNewPictogram(long childid)
	{
		
		Profile childProfile= help.profilesHelper.getProfileById(childid);
		ArrayList<PARROTCategory> categories= new ArrayList<PARROTCategory>();
		
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		ArrayList<Media> childMedia = (ArrayList<Media>)help.mediaHelper.getMediaByProfile(childProfile);

		for(Media m : childMedia)
		{
			if(m.getMType().equalsIgnoreCase("image"))
			{
				
				Pictogram pic = PictoFactory.INSTANCE.getPictogram(activity.getApplicationContext(), m.getId());
				pictograms.add(pic);
				
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
	
	public void XMLTESTER()
	{
		Log.v("PARROTmessage","start xmltester");
		List<PARROTCategory> categories = getTempCategoriesWithNewPictogram(11);
		
		Log.v("PARROTmessage","save categories");
		List<Profile> children = help.profilesHelper.getChildren();
		for(Profile child : children)
		{
			Log.v("PARROTmessage","child; " + child.getId());
			for(PARROTCategory category : categories)
			{
				Log.v("PARROTmessage","child; " + category.getCategoryName());
				saveCategory(category, child.getId());	
			}
		}
		
		saveChangesToXML();
		
		Log.v("PARROTmessage","done xmltester");
	}
	
	public ArrayList<PARROTCategory> getTempCategoriesWithNewPictogram(Profile childProfile)
	{
		
		ArrayList<PARROTCategory> categories= new ArrayList<PARROTCategory>();
		
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		ArrayList<Media> childMedia =(ArrayList<Media>) help.mediaHelper.getMediaByProfile(childProfile);



		for(Media m : childMedia)
		{
			if(m.getMType().equalsIgnoreCase("image"))
			{

				Pictogram pic = PictoFactory.INSTANCE.getPictogram(activity.getApplicationContext(), m.getId());
				pictograms.add(pic);
				//pictograms.add(loadPictogram(m.getId()));
			
			}
		}
		
		
		PARROTCategory tempCat3 = new PARROTCategory("Kategori 1", 0xff05ff12, pictograms.get(0));
		PARROTCategory tempCatSUB1 = new PARROTCategory("SUB1",0xff05ff12, pictograms.get(0));
		
		/*int count=1;
		for(Pictogram p : pictograms)
		{
			tempCatSUB1.addPictogram(p);
			
			if(count%4==0)
			{
				tempCat3.addPictogram(p);
			}
			count++;
		}*/

		

		PARROTCategory tempCat4 = new PARROTCategory("Kategori 2", 0xffff0000, pictograms.get(10));
		/*tempCat4.addPictogram(pictograms.get(10));
		tempCat4.addPictogram(pictograms.get(13));
		tempCat4.addPictogram(pictograms.get(12));
		tempCat4.addPictogram(pictograms.get(11));*/

		PARROTCategory tempCatSUB2 = new PARROTCategory("SUB2", 0xffff0000, pictograms.get(10));
		/*tempCatSUB2.addPictogram(pictograms.get(3));
		tempCatSUB2.addPictogram(pictograms.get(5));
		tempCatSUB2.addPictogram(pictograms.get(9));
		tempCatSUB2.addPictogram(pictograms.get(1));	*/
		
		tempCat3.addSubCategory(tempCatSUB1);
		tempCat4.addSubCategory(tempCatSUB2);
				
		categories.add(tempCat3);
		categories.add(tempCat4);
		
		return categories;
	}
	
	

}
