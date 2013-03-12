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
 * This is the ManagementBoxDragListener 
 * The basic function of this class is like that of BoxDragListener
 * It works with the ManageCategoryFragment and handles drag-drop functionality
 */
public class ManagementBoxDragListener implements OnDragListener
{
	private Activity parent;
	private Pictogram draggedPictogram = null;
	private ListView categories;
	private GridView pictograms;
	private boolean insideOfMe = false;
	
	// Constructor
	public ManagementBoxDragListener(Activity active)
	{
		parent = active;
		categories = (ListView) parent.findViewById(R.id.categories);
		pictograms = (GridView) parent.findViewById(R.id.pictograms);
	}
	
	private void deletePictogram()
	{
		PARROTCategory tempCategory = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
		tempCategory.removePictogram(ManageCategoryFragment.draggedItemIndex);
		
		ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCategory);
		pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
	}
	
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
	
	private void copyCategoryToCategory(DragEvent event)
	{
		PARROTCategory categoryCopiedFrom = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.draggedItemIndex); 
		PARROTCategory categoryCopiedTo   = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
		
		// Add all pictogram from categoryCopiedFrom to categoryCopiedTo
		for(int picIndex = 0; picIndex < categoryCopiedFrom.getPictograms().size(); picIndex++)
		{
			categoryCopiedTo.addPictogram(categoryCopiedFrom.getPictogramAtIndex(picIndex)); 
		}
		
		ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, categoryCopiedTo);
		pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parent));
	}
	
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
	
	/**
	 * This method is the primary event handler, when dragging items in the administration
	 * It handles the administration of categories by drag-drop functionality
	 * Each action (except delete category) has its own method declared above
	 * The functionality of each method should be self-explanatory
	 */
	public boolean onDrag(View self, DragEvent event)
	{
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:	// Drag started
			// Dummy
			break;
		case DragEvent.ACTION_DRAG_ENDED:	// Drag ended
			// Dummy
			break;
		case DragEvent.ACTION_DRAG_ENTERED:	// Drag entered
			insideOfMe = true;
			break;
		case DragEvent.ACTION_DRAG_EXITED:	// Drag exited
			insideOfMe = false;
			break;
		case DragEvent.ACTION_DROP:			// Drop dragged item
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


