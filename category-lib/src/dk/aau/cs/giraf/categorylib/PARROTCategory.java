package dk.aau.cs.giraf.categorylib;

import java.util.ArrayList;

import dk.aau.cs.giraf.pictogram.Pictogram;



/**
 * @author PARROT and edited by SW605f13-PARROT
 *The Category class is used to store a number of pictograms
 */
public class PARROTCategory 
{	
	private ArrayList<Pictogram> pictograms; 
	private int id=-1;
	private int categoryColor;
	private Pictogram icon;
	private boolean newCategory;
	private boolean changed;
	private String categoryName;
	private ArrayList<PARROTCategory> subCategories;
	public PARROTCategory parent = null;
	
	//This is the constructor method.
	public PARROTCategory(int color, Pictogram icon) 
	{
		subCategories = new ArrayList<PARROTCategory>();
		pictograms = new ArrayList<Pictogram>();
		setCategoryColor(color);
		setIcon(icon);
	}
	
	public PARROTCategory(String categoryName,int color,Pictogram icon)  
	{
		pictograms = new ArrayList<Pictogram>();
		subCategories = new ArrayList<PARROTCategory>();
		setCategoryColor(color);
		setIcon(icon);
		setCategoryName(categoryName);
	}
	/*-------------  Pictograms  -----------------------*/
	public ArrayList<Pictogram> getPictograms() { 
		return pictograms;
	}
	
	public Pictogram getPictogramAtIndex(int i)
	{
		return pictograms.get(i);
	}
	
	public void addPictogram(Pictogram pic)
	{
		pictograms.add(pic);
	}
	
	public void addPictogramAtIndex(Pictogram pic, int index)
	{
		pictograms.add(index, pic);
	}
	
	public void removePictogram(int i)
	{
		pictograms.remove(i);
	}
/*----------SUB categories------------*/
	public ArrayList<PARROTCategory> getSubCategories() { 
		return subCategories;
	}
	
	public PARROTCategory getSubCategoryAtIndex(int i)
	{
		return subCategories.get(i);
	}
	
	public void addSubCategory(PARROTCategory category)
	{
		subCategories.add(category);
	}
	
	public void addSubCategoryAtIndex(PARROTCategory category, int index)
	{
		subCategories.add(index, category);
	}
	
	public void removeSubCategory(int i)
	{
		subCategories.remove(i);
	}
	/*--------- others -----------*/
	public int getCategoryColor() {
		return categoryColor;
	}

	public void setCategoryColor(int categoryColor) {
		this.categoryColor = categoryColor;
	}

	public Pictogram getIcon() {
		return icon;
	}

	public void setIcon(Pictogram icon) {
		this.icon = icon;
	}

	public boolean isNewCategory() {
		return newCategory;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setNewCategory(boolean newCategory) {
		this.newCategory = newCategory;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void setSuperCategory(PARROTCategory superCategory)
	{
		parent = superCategory;
	}
	public PARROTCategory getSuperCategory()
	{
		return parent;
	}
	
}
