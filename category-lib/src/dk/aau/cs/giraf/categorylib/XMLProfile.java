package dk.aau.cs.giraf.categorylib;

import java.util.ArrayList;
/**
 * @author SW605f13-PARROT
 * used to store data from the XML file
 */
public class XMLProfile {
	//tags
	public static final String PICTOGRAM = "Pictogram"; 
	public static final String CHILDID = "ChildId";
	public static final String CATEGORY = "Category";
	public static final String SUBCATEGORY = "SubCategory";
	//attributes
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String COLOR = "color";
	public static final String ICON = "icon";
	
	
	
	private long childID;
	private ArrayList<XMLCategoryProfile> categories = null;
	
	public XMLProfile()
	{
		categories = new ArrayList<XMLCategoryProfile>();
	}
	public XMLProfile(long childID,ArrayList<XMLCategoryProfile> categories)
	{
		this.categories = categories;
		this.childID = childID;
	}	
	
	public void setChildID(long childId)
	{
		this.childID= childId;
	}
	public void setCategories(ArrayList<XMLCategoryProfile> categories)
	{
		this.categories= categories;
	}
	public long getChildID()
	{
		return childID;
		}
	public ArrayList<XMLCategoryProfile> getCategories()
	{
		return categories;
	}
}
