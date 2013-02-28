package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

/**
 * 
 * @Rasmus
 *The Category class is used to store a number of pictograms
 */
public class Category {
	private ArrayList<Pictogram> pictograms; 
	private int categoryColour;
	private Pictogram icon;
	private boolean newCategory;
	private boolean changed;
	private String categoryName;
	//This is the constructor method.
	public Category(int colour, Pictogram icon) //this is a constructer method that takes icon and color as input
	{
		pictograms = new ArrayList<Pictogram>();
		setCategoryColour(colour);
		setIcon(icon);
	}
	
	public Category(String categoryName,int colour,Pictogram icon)  //this is a constructer method that takes name, icon and color as input
	{
		pictograms = new ArrayList<Pictogram>();
		setCategoryColour(colour);
		setIcon(icon);
		setCategoryName(categoryName);
	}

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

	public int getCategoryColour() {
		return categoryColour;
	}

	public void setCategoryColour(int categoryColour) {
		this.categoryColour = categoryColour;
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

}
