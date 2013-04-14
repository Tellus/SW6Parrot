package dk.aau.cs.giraf.categorylib;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;

/**
 * 
 * @author SW605-PARROT
 * This is a temporary class that handles the xml communication to get the categories.
 */
//TODO This class should be changed when the categories tables in the local database are included

public class CategoryHelper {

	
	Helper help=null;
	Activity activity=null;
	XMLCommunicater communicater= null;
	XMLProfile xmlChild = null;
	PictoFactory factory= PictoFactory.INSTANCE;
	
	/**
	 * setup the CategoryHelper and connect to XMLCommunicater
	 * @param activity
	 */
	public CategoryHelper(Activity activity)
	{
		this.activity = activity;
		help = new Helper(activity); 
		communicater = new XMLCommunicater(); 
		//this should be an easy temperaty way to get static data into the xml 
		//TODO it deleted at some point
		if(XMLCommunicater.isNewFile==true)
		{
			XMLTESTER();
			XMLCommunicater.isNewFile=false;
		}
	}
	
	
	/**
	 * get the XMLCommunicater to save all changes to the xml-file
	 */
	public void saveChangesToXML()
	{
		communicater.setXMLDataAndUpdate(xmlChild);
	}
	
 	
	/**
	 * transform a PARROTCategory into XMLCategoryProfile
	 * @param category, a PARROTCategory
	 * @return XMLCategoryProfile
	 */
	private XMLCategoryProfile transformToXMLCategoryProfile(PARROTCategory category)
	{
		//Log.v("MessageXML","start transformToXMLCategoryProfile");
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
		//Log.v("MessageXML","end transformToXMLCategoryProfile");
		return xmlCategory;
		
	}
	
	/**
	 * used when saving an existing or adding a new category to child 
	 * @param category, PARROTCategory
	 * @param childId, child ProfileId
	 */
	public void saveCategory(PARROTCategory category,long childId)
	{
		//Log.v("MessageXML","Start saveCategory");
		XMLCategoryProfile categoryProfile = transformToXMLCategoryProfile(category);
		ArrayList<XMLCategoryProfile> categoryProfileList = null; 
		
		if(xmlChild != null)
		{
			categoryProfileList = xmlChild.getCategories();
			deleteCategory(category, childId, categoryProfileList);
		}
		else
		{
			xmlChild= new XMLProfile();
			xmlChild.setChildID(childId);
			categoryProfileList = xmlChild.getCategories();
			
		}
		
		//if the category exist then delete
		categoryProfileList.add(categoryProfile);
		//Log.v("MessageXML","done in saveCategory");
						
	}
	
	/**
	 * Finds the current version of a PARROTCategory  in XMLCategoryProfile and delete it from the list.
	 * @param category, PARROTCategory
	 * @param childId, child ProfileID
	 * @param categoryProfileList, ArrayList of XMLCategoryProfile
	 */
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
	
	/**
	 * Finds the current version of a PARROTCategory  in XMLCategoryProfile and delete it from the list.
	 * @param category, PARROTCategory
	 * @param childId, child ProfileID
	 */
	public void deleteCategory(PARROTCategory category, long childId)
	{
		
		ArrayList<XMLCategoryProfile> categoryProfileList = null; 
		if(xmlChild != null)
		{
			categoryProfileList = xmlChild.getCategories();
			deleteCategory(category, childId, categoryProfileList);

		}
	
	}

	
	/**
	 * transform a XMLCategoryProfile into PARROTCategory
	 * @param categoryProfile,XMLCategoryProfile
	 * @return PARROTCategory
	 */
	private PARROTCategory transformToPARROTCategory(XMLCategoryProfile categoryProfile)
	{	
		//Log.v("MessageXML","transformToPARROTCategory begin");
		Pictogram icon = factory.getPictogram(activity.getApplicationContext(), categoryProfile.getIconID());
		PARROTCategory category = new PARROTCategory(categoryProfile.getName(), categoryProfile.getColor(), icon);
		
		for(Long pictogramId : categoryProfile.getPictogramsID())
		{
			Pictogram pictoId = factory.getPictogram(activity.getApplicationContext(), pictogramId);
			category.addPictogram(pictoId);	
		}

		for(XMLCategoryProfile cp : categoryProfile.getSubcategories())
		{
			category.addSubCategory(transformToPARROTCategory(cp));
		}
		//Log.v("MessageXML","transformToPARROTCategory end");
		return category;
	}
	
	/**
	 * get a child's categories from xml data
	 * @param childId, a child profileID
	 * @return ArrayList<PARROTCategory>
	 */
	public ArrayList<PARROTCategory> getChildsCategories(long childId)
	{
		//Log.v("CategoryHelperMessage"," start getChildsCategories");
		ArrayList<PARROTCategory> categories=new ArrayList<PARROTCategory>();
		xmlChild = communicater.getChildXMLData(childId);
		if(xmlChild==null)
		{
			//Log.v("CategoryHelperMessage"," end getChildsCategories");
			
			return null;
		}

		for(XMLCategoryProfile cp : xmlChild.getCategories())
		{
			PARROTCategory category= transformToPARROTCategory(cp);
			categories.add(category);
			
		}
		//Log.v("CategoryHelperMessage"," end getChildsCategories");
		
		return categories;
	}
	
	
	/**---------------- temp file ---------------- **/
	/**
	 * Tempfiles TO be deleted at somepoint
	 */
	public ArrayList<PARROTCategory> getTempCategoriesWithNewPictogram(long childid)
	{
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		pictograms = (ArrayList<Pictogram>) factory.getAllPictograms(activity);
		ArrayList<PARROTCategory> categories= new ArrayList<PARROTCategory>();
		
		/*Profile childProfile= help.profilesHelper.getProfileById(childid);
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		ArrayList<Media> childMedia = (ArrayList<Media>)help.mediaHelper.getMediaByProfile(childProfile);
		int index =0;
		for(Media m : childMedia)
		{
			if(m.getMType().equalsIgnoreCase("image"))
			{
				
				Pictogram pic = PictoFactory.INSTANCE.getPictogram(activity.getApplicationContext(), m.getId());
				pictograms.add(index,pic);
				index++;
				
			}
		}*/
		
		//color = grey 'not in super'
		PARROTCategory tempCat1 = new PARROTCategory("Categori 1", Color.parseColor("#626262"), pictograms.get(0));
		PARROTCategory tempCatSUB1 = new PARROTCategory("SUBCategori 1",Color.parseColor("#626262"), pictograms.get(0));
		
		int count=1;
		for(Pictogram p : pictograms)
		{
			tempCatSUB1.addPictogram(p);
			
			if(count%4==0)
			{
				tempCat1.addPictogram(p);
			}
			count++;
		}
		tempCat1.addSubCategory(tempCatSUB1);
		categories.add(tempCat1);
		
		//color= special blue 'not in super'
		PARROTCategory tempCat2 = new PARROTCategory("Categori 2", Color.parseColor("#00d5f2"), pictograms.get(10));
		tempCat2.addPictogram(pictograms.get(10));
		tempCat2.addPictogram(pictograms.get(13));
		tempCat2.addPictogram(pictograms.get(12));
		tempCat2.addPictogram(pictograms.get(11));

		PARROTCategory tempCatSUB2 = new PARROTCategory("SUBCategori 2", Color.parseColor("#00d5f2"), pictograms.get(10));
		tempCatSUB2.addPictogram(pictograms.get(3));
		tempCatSUB2.addPictogram(pictograms.get(5));
		tempCatSUB2.addPictogram(pictograms.get(9));
		tempCatSUB2.addPictogram(pictograms.get(1));	
		
		tempCat2.addSubCategory(tempCatSUB2);
		categories.add(tempCat2);
		
		/*fillings catgories*/
		//color= ugly yelllow 'not in super'
		PARROTCategory tempCat3 = new PARROTCategory("Categori 3", Color.parseColor("#f6ff60"), pictograms.get(3));
		PARROTCategory tempCatSUB30 = new PARROTCategory("SUBCategori 30", Color.parseColor("#f6ff60"), pictograms.get(1));
		PARROTCategory tempCatSUB31 = new PARROTCategory("SUBCategori 31", Color.parseColor("#f6ff60"), pictograms.get(2));
		PARROTCategory tempCatSUB32 = new PARROTCategory("SUBCategori 32", Color.parseColor("#f6ff60"), pictograms.get(3));
		PARROTCategory tempCatSUB33 = new PARROTCategory("SUBCategori 33", Color.parseColor("#f6ff60"), pictograms.get(4));
		PARROTCategory tempCatSUB34 = new PARROTCategory("SUBCategori 34", Color.parseColor("#f6ff60"), pictograms.get(5));
		PARROTCategory tempCatSUB35 = new PARROTCategory("SUBCategori 35", Color.parseColor("#f6ff60"), pictograms.get(6));
		PARROTCategory tempCatSUB36 = new PARROTCategory("SUBCategori 36", Color.parseColor("#f6ff60"), pictograms.get(7));
		PARROTCategory tempCatSUB37 = new PARROTCategory("SUBCategori 37", Color.parseColor("#f6ff60"), pictograms.get(8));
		PARROTCategory tempCatSUB38 = new PARROTCategory("SUBCategori 38", Color.parseColor("#f6ff60"), pictograms.get(9));
		PARROTCategory tempCatSUB39 = new PARROTCategory("SUBCategori 39", Color.parseColor("#f6ff60"), pictograms.get(10));
		PARROTCategory tempCatSUB310 = new PARROTCategory("SUBCategori 310", Color.parseColor("#f6ff60"), pictograms.get(11));
		PARROTCategory tempCatSUB311 = new PARROTCategory("SUBCategori 311", Color.parseColor("#f6ff60"), pictograms.get(12));
	
		
		
		tempCat3.addSubCategory(tempCatSUB30);
		tempCat3.addSubCategory(tempCatSUB31);
		tempCat3.addSubCategory(tempCatSUB32);
		tempCat3.addSubCategory(tempCatSUB33);
		tempCat3.addSubCategory(tempCatSUB34);
		tempCat3.addSubCategory(tempCatSUB35);
		tempCat3.addSubCategory(tempCatSUB36);
		tempCat3.addSubCategory(tempCatSUB37);
		tempCat3.addSubCategory(tempCatSUB38);
		tempCat3.addSubCategory(tempCatSUB39);
		tempCat3.addSubCategory(tempCatSUB310);
		tempCat3.addSubCategory(tempCatSUB311);
		
		categories.add(tempCat3);
		
		
		// color = puple ' in not super'
		PARROTCategory tempCat4 = new PARROTCategory("Categori 4", Color.parseColor("#b536da"), pictograms.get(4));
		categories.add(tempCat4);
		// color =blue(ish) ok
		PARROTCategory tempCat5 = new PARROTCategory("Categori 5", Color.parseColor("#2fb1d6"), pictograms.get(5));
		categories.add(tempCat5);
		// color = green ok
		PARROTCategory tempCat6 = new PARROTCategory("Categori 6", Color.parseColor("#4ac925"), pictograms.get(6));
		categories.add(tempCat6);
		// color = red 'not in super'
		PARROTCategory tempCat7 = new PARROTCategory("Categori 7", Color.parseColor("#e00707"), pictograms.get(7));
		categories.add(tempCat7);
		// color = orange 'not in super' 
		PARROTCategory tempCat8 = new PARROTCategory("Categori 8", Color.parseColor("#f2a400"), pictograms.get(8));
		categories.add(tempCat8);
		// color = white ´not in super´
		PARROTCategory tempCat9 = new PARROTCategory("Categori 9", Color.parseColor("#ffffff"), pictograms.get(9));
		categories.add(tempCat9);
		// color = dark blue 'not in super'
		PARROTCategory tempCat10 = new PARROTCategory("Categori 10", Color.parseColor("#000056"), pictograms.get(10));
		categories.add(tempCat10);
		// color = army green 'not in super'
		PARROTCategory tempCat11 = new PARROTCategory("Categori 11", Color.parseColor("#658200"), pictograms.get(11));
		categories.add(tempCat11);
		

		return categories;
	}
	
	/**
	 * Tempfiles TO be deleted at somepoint
	 */
	public void XMLTESTER()
	{
		//Log.v("PARROTmessage","start xmltester");
		List<PARROTCategory> categories = getTempCategoriesWithNewPictogram(11);
		
		//Log.v("PARROTmessage","save categories");
		List<Profile> children = help.profilesHelper.getChildren();
		
		for(Profile child : children)
		{
			//Log.v("PARROTmessage","child; " + child.getId());
			for(PARROTCategory category : categories)
			{
				//Log.v("PARROTmessage","child; " + category.getCategoryName());
				saveCategory(category, child.getId());	
			}
			XMLCommunicater.xmlData.add(xmlChild);
			xmlChild=null;
		}
		
		
		
		
		saveChangesToXML();
		
		
		//Log.v("PARROTmessage","done xmltester");
	}
	
	/**
	 * Tempfiles TO be deleted at somepoint
	 */
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
	
	

}
