package dk.aau.cs.giraf.categorylib;


import java.util.ArrayList;


/**
 * 
 * @Rasmus
 *The Category class is used to store a number of pictograms
 */
public class PARROTCategory 
{	
	private ArrayList<PictogramOLD> pictograms; 
	private int categoryColor;
	private PictogramOLD icon;
	private boolean newCategory;
	private boolean changed;
	private String categoryName;
	private ArrayList<PARROTCategory> subCategories;
	public PARROTCategory parent = null;
	
	//This is the constructor method.
	public PARROTCategory(int color, PictogramOLD icon) //this is a constructor method that takes icon and color as input
	{
		pictograms = new ArrayList<PictogramOLD>();
		setCategoryColor(color);
		setIcon(icon);
	}
	
	public PARROTCategory(String categoryName,int color,PictogramOLD icon)  //this is a constructor method that takes name, icon and color as input
	{
		pictograms = new ArrayList<PictogramOLD>();
		subCategories = new ArrayList<PARROTCategory>();
		setCategoryColor(color);
		setIcon(icon);
		setCategoryName(categoryName);
	}
	/*-------------  Pictograms  -----------------------*/
	public ArrayList<PictogramOLD> getPictograms() { 
		return pictograms;
	}
	
	public PictogramOLD getPictogramAtIndex(int i)
	{
		return pictograms.get(i);
	}
	
	public void addPictogram(PictogramOLD pic)
	{
		pictograms.add(pic);
	}
	
	public void addPictogramAtIndex(PictogramOLD pic, int index)
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

	public PictogramOLD getIcon() {
		return icon;
	}

	public void setIcon(PictogramOLD icon) {
		this.icon = icon;
	}

	public boolean isNewCategory() {
		return newCategory;
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
