package dk.aau.cs.giraf.categorylib;

import java.util.ArrayList;

public class XMLCategoryProfile {

		private int color; 	
		private String name;
		private long iconID;
		private ArrayList<Long> pictogramsID = null;
		private ArrayList<XMLCategoryProfile> subcategories= null;
		
		XMLCategoryProfile()
		{
			pictogramsID = new ArrayList<Long>();
			subcategories = new ArrayList<XMLCategoryProfile>();
		}
		
		XMLCategoryProfile(int color, String name, long iconID,	ArrayList<Long> pictogramsID, ArrayList<XMLCategoryProfile> subcategories)
		{
			this.color=color; 	
			this.name=name;
			this.iconID= iconID;
			this.pictogramsID = pictogramsID;
			this.subcategories = subcategories;
		}
		
		
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getIconID() {
			return iconID;
		}
		public void setIconID(long iconID) {
			this.iconID = iconID;
		}
		public ArrayList<Long> getPictogramsID() {
			return pictogramsID;
		}
		public void setPictogramsID(ArrayList<Long> pictogramsID) {
			this.pictogramsID = pictogramsID;
		}
		public ArrayList<XMLCategoryProfile> getSubcategories() {
			return subcategories;
		}
		public void setSubcategories(ArrayList<XMLCategoryProfile> subcategories) {
			this.subcategories = subcategories;
		}
		
		public void addSubcategory(XMLCategoryProfile categoryProfile)
		{
			subcategories.add(categoryProfile);
		}
		public void addPictogramId(Long id)
		{
			pictogramsID.add(id);
		}

		
		
		
}