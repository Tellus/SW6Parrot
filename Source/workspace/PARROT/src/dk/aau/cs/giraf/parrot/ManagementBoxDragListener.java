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
	
	// Klim 
	private void deletePictogram()
	{
		PARROTCategory tempCategory = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
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
		PARROTCategory categoryCopiedTo = ManageCategoryFragment.profileBeingModified.getCategoryAt(pos);
		categories = (ListView) parent.findViewById(R.id.categories);
		
		categoryCopiedTo.addPictogram(draggedPictogram);
		ManageCategoryFragment.profileBeingModified.setCategoryAt(pos, categoryCopiedTo);			
	}
	
	// Klim
	private void copyCategoryToCategory(DragEvent event)
	{
		PARROTCategory categoryCopiedFrom = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.draggedItemIndex); 
		PARROTCategory categoryCopiedTo   = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
		
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
		PARROTCategory tempCategory = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
		
		// Set new icon
		tempCategory.setIcon(draggedPictogram);
		
		ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCategory);
		
		ImageView icon = (ImageView) parent.findViewById(R.id.categoryPicture);
		icon.setImageBitmap(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getIcon().getBitmap());
		
		ListView list = (ListView) parent.findViewById(R.id.categories);
		list.setAdapter(new ListViewAdapter(parent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
	}
	
	public boolean onDrag(View self, DragEvent event) {
		switch (event.getAction()) {
		// Drag started
		case DragEvent.ACTION_DRAG_STARTED:
			// Dummy
			break;
		// Drag ended
		case DragEvent.ACTION_DRAG_ENDED:
			// Dummy
			break;
		// Drag entered
		case DragEvent.ACTION_DRAG_ENTERED:
			insideOfMe = true;
			break;
		// Drag exited
		case DragEvent.ACTION_DRAG_EXITED:
			insideOfMe = false;
			break;
		// Drop dragged item
		case DragEvent.ACTION_DROP:
			if (insideOfMe)
			{
				switch(ManageCategoryFragment.categoryDragownerID){
				// Drag pictogram to..
				case R.id.pictograms:
					if(self.getId() == R.id.trash){
						deletePictogram(); 				// Delete pictogram from category
					}
					else if(self.getId() == R.id.categories){
						copyPictogramToCategory(event); // Copy a pictogram into another category
					}
					else if(self.getId() == R.id.categoryPicture){
						changeCategoryIcon(event); 		// Change category icon
					}
					break;
				// Drag category to..
				case R.id.categories:
					if(self.getId() == R.id.trash){
						// Delete category
						ManageCategoryFragment.profileBeingModified.removeCategory(ManageCategoryFragment.draggedItemIndex);
						categories.setAdapter(new ListViewAdapter(parent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
					}
					if(self.getId() == R.id.pictograms){
						copyCategoryToCategory(event);	// Copy a category into another category
					}
					break;
				default:
					break;
				}
			}
			break;
		default:
			break;
		}
		return true;
	}
}


