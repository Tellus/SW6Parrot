package dk.aau.cs.giraf.parrot;

import android.app.Activity;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
/**
 * 
 * @PARROT
 * This is the ManagementBoxDragListener, like the BoxDragListener, but for the ManageCategoryFragment instead.
 */
public class ManagementBoxDragListener implements OnDragListener
{
	private Activity parent;
	private Pictogram draggedPictogram = null;
	private ListView categories;
	private GridView pictograms;
	private boolean insideOfMe = false;
	
	// Constructor
	public ManagementBoxDragListener(Activity active) {
		parent = active;
		categories = (ListView) parent.findViewById(R.id.categories);
		pictograms = (GridView) parent.findViewById(R.id.pictograms);
	}
	
	private void deletePictogram()
	{
		Category tempCategory = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
		tempCategory.removePictogram(ManageCategoryFragment.draggedItemIndex);
		
		ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCategory);
		pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
	}
	
	// Klim
	private void copyPictogramToCategory(DragEvent event)
	{
		int x = (int)event.getX(),
			y = (int)event.getY();
		
		int pos = categories.pointToPosition(x, y);
		
		draggedPictogram = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex); 
		Category categoryCopiedTo = ManageCategoryFragment.profileBeingModified.getCategoryAt(pos);
		categories = (ListView) parent.findViewById(R.id.categories);
		
		categoryCopiedTo.addPictogram(draggedPictogram);
		ManageCategoryFragment.profileBeingModified.setCategoryAt(pos, categoryCopiedTo);			
	}
	
	// Klim
	private void copyCategoryToCategory(DragEvent event)
	{
		Category categoryCopiedFrom = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.draggedItemIndex); 
		Category categoryCopiedTo   = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
		
		// Add all pictogram from categoryCopiedFrom to categoryCopiedTo
		// i = numberOfPictograms
		for(int i = 0; i < categoryCopiedFrom.getPictograms().size(); i++)
		{
			categoryCopiedTo.addPictogram(categoryCopiedFrom.getPictogramAtIndex(i)); 
		}
		
		ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, categoryCopiedTo);
		pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
	}
	
	// Klim
	private void changeCategoryIcon(DragEvent event)
	{
		draggedPictogram      = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex);
		Category tempCategory = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
		
		// Set new icon
		tempCategory.setIcon(draggedPictogram);
		
		ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCategory);
		
		ImageView icon = (ImageView) parent.findViewById(R.id.categorypic);
		icon.setImageBitmap(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getIcon().getBitmap());
		
		ListView list = (ListView) parent.findViewById(R.id.categories);
		list.setAdapter(new ListViewAdapter(parent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
	}
	
	public boolean onDrag(View self, DragEvent event) {
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:
			// Dummy
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			insideOfMe = true;
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			insideOfMe = false;
			break;
		case DragEvent.ACTION_DROP:
			if (insideOfMe)
			{
				// Delete pictogram from category
				if(self.getId() == R.id.trash && ManageCategoryFragment.catDragOwnerID == R.id.pictograms)
				{
					// Klim: deletePictogram();
					Category tempCategory = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					tempCategory.removePictogram(ManageCategoryFragment.draggedItemIndex);
					
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCategory);
					pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
				}
				// Delete category
				else if(self.getId() == R.id.trash && ManageCategoryFragment.catDragOwnerID == R.id.categories)
				{	
					ManageCategoryFragment.profileBeingModified.removeCategory(ManageCategoryFragment.draggedItemIndex);
					categories.setAdapter(new ListViewAdapter(parent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
				}
				// Copy a pictogram into another category
				else if(self.getId() == R.id.categories && ManageCategoryFragment.catDragOwnerID == R.id.pictograms) 
				{
					//Klim: copyPictogramToCategory(event);
					draggedPictogram = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex); 
							
					ListView categories = (ListView) parent.findViewById(R.id.categories);
					int x = (int)event.getX();
					int y = (int)event.getY();
					int index = categories.pointToPosition(x, y);
					
					Category temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(index);
					temp.addPictogram(draggedPictogram);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(index, temp);			
				}
				// Copy a category into another category
				else if(self.getId() == R.id.pictograms && ManageCategoryFragment.catDragOwnerID == R.id.categories) 
				{	
					// Klim: copyCategoryToCategory(event);
					Category categoryCopiedFrom = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.draggedItemIndex); 
					
					Category temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					
					for(int i = 0; i < categoryCopiedFrom.getPictograms().size(); i++)
					{
						temp.addPictogram(categoryCopiedFrom.getPictogramAtIndex(i)); 
					}
					
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, temp);
					pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
				}
				// Change category icon
				else if(self.getId() == R.id.categorypic && ManageCategoryFragment.catDragOwnerID == R.id.pictograms) 
				{
					// Klim: changeCategoryIcon(event);
					draggedPictogram = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex);
					Category tempCat = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					tempCat.setIcon(draggedPictogram);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCat);
					ImageView icon = (ImageView) parent.findViewById(R.id.categorypic);
					icon.setImageBitmap(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getIcon().getBitmap());
					ListView list = (ListView) parent.findViewById(R.id.categories);
					list.setAdapter(new ListViewAdapter(parent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
				}
				else
				{
					//TODO check that nothing is done. 
				}
			}
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			// Dummy
			break;
		default:
			break;
		}
		/*if (event.getAction() == DragEvent.ACTION_DRAG_STARTED){
		 //Dummy
		 } 
		 
		else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED)
		{ 
			insideOfMe = true;
		} 
		else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED)
		{
			insideOfMe = false;
		} 
		else if (event.getAction() == DragEvent.ACTION_DROP)
		{
			if (insideOfMe)
			{
				if(self.getId()==R.id.trash && ManageCategoryFragment.catDragOwnerID == R.id.pictograms) //We are to delete a pictogram from a category
				{
					Category temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					temp.removePictogram(ManageCategoryFragment.draggedItemIndex);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, temp);
					
					pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
				}
				
				else if(self.getId()==R.id.categories && ManageCategoryFragment.catDragOwnerID == R.id.pictograms) //We are to copy a pictogram into another category
				{
					
					draggedPictogram = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex); 
							
					ListView categories = (ListView) parent.findViewById(R.id.categories);
					int x = (int)event.getX();
					int y = (int)event.getY();
					int index = categories.pointToPosition(x, y);
					
					Category temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(index);
					temp.addPictogram(draggedPictogram);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(index, temp);
										
				}
				else if(self.getId()==R.id.pictograms && ManageCategoryFragment.catDragOwnerID == R.id.categories) //We are to copy a category into another category
				{	
					
					Category categoryCopiedFrom = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.draggedItemIndex); 
					
					Category temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					
					for(int i = 0; i < categoryCopiedFrom.getPictograms().size(); i++)
					{
						temp.addPictogram(categoryCopiedFrom.getPictogramAtIndex(i)); 
					}
					
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, temp);
					pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
				}
				else if(self.getId()==R.id.trash && ManageCategoryFragment.catDragOwnerID == R.id.categories) //We are to delete a category
				{	
					ManageCategoryFragment.profileBeingModified.removeCategory(ManageCategoryFragment.draggedItemIndex);
					categories.setAdapter(new ListViewAdapter(parent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
				}
				else if(self.getId()==R.id.categorypic && ManageCategoryFragment.catDragOwnerID == R.id.pictograms) //We are to change the icon of the category
				{
					draggedPictogram = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex);
					Category tempCat = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					tempCat.setIcon(draggedPictogram);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCat);
					ImageView icon = (ImageView) parent.findViewById(R.id.categorypic);
					icon.setImageBitmap(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getIcon().getBitmap());
					ListView list = (ListView) parent.findViewById(R.id.categories);
					list.setAdapter(new ListViewAdapter(parent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
				}
				else
				{
					//TODO check that nothing is done. 
				}
			}
			//To ensure that no wrong references will be made, the index is reset to -1
			ManageCategoryFragment.draggedItemIndex = -1;
		} else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED){
			//Dummy				
		}*/
		return true;
	}
}


