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
	ListView categories;
	GridView pictograms;
	boolean insideOfMe = false;
	
	public ManagementBoxDragListener(Activity active) {
		parent = active;
		categories = (ListView) parent.findViewById(R.id.categories);
		pictograms = (GridView) parent.findViewById(R.id.pictograms);
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


