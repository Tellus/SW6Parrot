package dk.aau.cs.giraf.categorylib;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import dk.aau.cs.giraf.oasis.lib.Helper;
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
		Log.v("CategoryHelper", "Before: saving to xml file");
		communicater.setXMLDataAndUpdate(xmlChild);
		Log.v("CategoryHelper", "After: saving to xml file");
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
		if(category.getId()!=-1)
		{
			xmlCategory.setId(category.getId());
		}
		
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
	public void saveCategory(PARROTCategory category)
	{
		Log.v("MessageXML------------------","Start saveCategory");
		XMLCategoryProfile categoryProfile = transformToXMLCategoryProfile(category);
		ArrayList<XMLCategoryProfile> categoryProfileList = null; 
		
		if(xmlChild != null)
		{
			boolean newCategory = true;
			Log.v("saveCategory", "In if");
			categoryProfileList = xmlChild.getCategories();
			for(XMLCategoryProfile cp : categoryProfileList)
			{
				Log.v("deleteCategory", cp.getId() + " and " + category.getId());
				if(cp.getId()==category.getId())
				{
					newCategory = false;
					Log.v("deleteCategory", "in if to delete");
					int postion = categoryProfileList.indexOf(cp);
					Log.v("XML tester","deleteCategory that exist "+ postion);
					categoryProfileList.set(postion, categoryProfile);
					break;
				}
			}
			if(newCategory)
			{
				categoryProfileList.add(categoryProfile);
			}
			
		}
		else
		{
			Log.v("saveCategory", "In else");
			xmlChild= new XMLProfile();
			xmlChild.setChildID(xmlChild.getChildID());
			categoryProfileList = xmlChild.getCategories();
			categoryProfileList.add(categoryProfile);
			
		}
		
		//if the category exist then delete
		Log.v("MessageXML-----------------","done in saveCategory");
						
	}

	
	/**
	 * Finds the current version of a PARROTCategory  in XMLCategoryProfile and delete it from the list.
	 * @param category, PARROTCategory
	 * @param childId, child ProfileID
	 */
	public void deleteCategory(PARROTCategory category)
	{
		
		ArrayList<XMLCategoryProfile> categoryProfileList = null; 
		if(xmlChild != null)
		{
			categoryProfileList = xmlChild.getCategories();
			for(XMLCategoryProfile cp : categoryProfileList)
			{
				Log.v("deleteCategory", cp.getId() + " and " + category.getId());
				if(cp.getId()==category.getId())
				{
					Log.v("deleteCategory", "in if to delete");
					int postion = categoryProfileList.indexOf(cp);
					Log.v("XML tester","deleteCategory that exist "+ postion);
					categoryProfileList.remove(postion);
					break;
				}
			}

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
		Pictogram icon = PictoFactory.getPictogram(activity.getApplicationContext(), categoryProfile.getIconID());
		PARROTCategory category = new PARROTCategory(categoryProfile.getName(), categoryProfile.getColor(), icon);
		if(categoryProfile.getId()!=-1){	
			category.setId(categoryProfile.getId());
		}
		for(Long pictogramId : categoryProfile.getPictogramsID())
		{
			Pictogram pictoId = PictoFactory.getPictogram(activity.getApplicationContext(), pictogramId);
			category.addPictogram(pictoId);	
		}

		for(XMLCategoryProfile cp : categoryProfile.getSubcategories())
		{
			PARROTCategory subc = transformToPARROTCategory(cp);
			subc.setSuperCategory(category);
			category.addSubCategory(subc);
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
	public ArrayList<PARROTCategory> getTempCategoriesWithNewPictogram()
	{
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		pictograms = (ArrayList<Pictogram>) PictoFactory.getAllPictograms(activity);
		ArrayList<PARROTCategory> categories= new ArrayList<PARROTCategory>();
		
		int id =1;
		//color = grey 'not in super'
		PARROTCategory tempCat1 = new PARROTCategory("Categori 1", Color.parseColor("#626262"), pictograms.get(0));
		tempCat1.setId(id);
		id++;
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
		tempCat2.setId(id);
		id++;
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
		tempCat3.setId(id);
		id++;
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
		tempCat4.setId(id);
		id++;
		categories.add(tempCat4);
		// color =blue(ish) ok
		PARROTCategory tempCat5 = new PARROTCategory("Categori 5", Color.parseColor("#2fb1d6"), pictograms.get(5));
		tempCat5.setId(id);
		id++;
		categories.add(tempCat5);
		// color = green ok
		PARROTCategory tempCat6 = new PARROTCategory("Categori 6", Color.parseColor("#4ac925"), pictograms.get(6));
		tempCat6.setId(id);
		id++;
		categories.add(tempCat6);
		// color = red 'not in super'
		PARROTCategory tempCat7 = new PARROTCategory("Categori 7", Color.parseColor("#e00707"), pictograms.get(7));
		tempCat7.setId(id);
		id++;
		categories.add(tempCat7);
		// color = orange 'not in super' 
		PARROTCategory tempCat8 = new PARROTCategory("Categori 8", Color.parseColor("#f2a400"), pictograms.get(8));
		tempCat8.setId(id);
		id++;
		categories.add(tempCat8);
		// color = white ´not in super´
		PARROTCategory tempCat9 = new PARROTCategory("Categori 9", Color.parseColor("#ffffff"), pictograms.get(9));
		tempCat9.setId(id);
		id++;
		categories.add(tempCat9);
		// color = dark blue 'not in super'
		PARROTCategory tempCat10 = new PARROTCategory("Categori 10", Color.parseColor("#000056"), pictograms.get(10));
		tempCat10.setId(id);
		id++;
		categories.add(tempCat10);
		// color = army green 'not in super'
		PARROTCategory tempCat11 = new PARROTCategory("Categori 11", Color.parseColor("#658200"), pictograms.get(11));
		tempCat11.setId(id);
		id++;
		categories.add(tempCat11);
		

		return categories;
	}
	
	/**
	 * Tempfiles TO be deleted at somepoint
	 */
	public void XMLTESTER()
	{
		//Log.v("PARROTmessage","start xmltester");
		List<PARROTCategory> categories = getTempCategoriesWithNewPictogram();
		
		//Log.v("PARROTmessage","Before for save categories");
		List<Profile> children = help.profilesHelper.getChildren();
		
		for(Profile child : children)
		{
			xmlChild= new XMLProfile();
			xmlChild.setChildID(child.getId());
			//Log.v("PARROT........","child; " + child.getId());
			for(PARROTCategory category : categories)
			{
				Log.v("PARROTmessage","category; " + category.getCategoryName());
				saveCategory(category);	
			}
			communicater.xmlData.add(xmlChild);
			xmlChild=null;
		}
		
		
		
		
		saveChangesToXML();
		
		
		//Log.v("PARROTmessage","done xmltester");
	}
	
}
