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

	private Activity parrent;
	private Pictogram draggedPictogram = null;
	ListView categories;
	GridView pictograms;
	boolean insideOfMe = false;
	
	public ManagementBoxDragListener(Activity active) {
		parrent = active;
		categories = (ListView) parrent.findViewById(R.id.categories);
		pictograms = (GridView) parrent.findViewById(R.id.pictograms);
	}
	
	public boolean onDrag(View self, DragEvent event) {
		if (event.getAction() == DragEvent.ACTION_DRAG_STARTED)
		{
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
					PARROTCategory temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					temp.removePictogram(ManageCategoryFragment.draggedItemIndex);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, temp);
					
					pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parrent));
				}
				
				else if(self.getId()==R.id.categories && ManageCategoryFragment.catDragOwnerID == R.id.pictograms) //We are to copy a pictogram into another category
				{
					
					draggedPictogram = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex); 
							
					ListView categories = (ListView) parrent.findViewById(R.id.categories);
					int x = (int)event.getX();
					int y = (int)event.getY();
					int index = categories.pointToPosition(x, y);
					
					PARROTCategory temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(index);
					temp.addPictogram(draggedPictogram);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(index, temp);
										
				}
				else if(self.getId()==R.id.pictograms && ManageCategoryFragment.catDragOwnerID == R.id.categories) //We are to copy a category into another category
				{	
					
					PARROTCategory categoryCopiedFrom = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.draggedItemIndex); 
					
					PARROTCategory temp = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					
					for(int i = 0; i < categoryCopiedFrom.getPictograms().size(); i++)
					{
						temp.addPictogram(categoryCopiedFrom.getPictogramAtIndex(i)); 
					}
					
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, temp);
					pictograms.setAdapter(new PictogramAdapter(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId), parrent));
				}
				else if(self.getId()==R.id.trash && ManageCategoryFragment.catDragOwnerID == R.id.categories) //We are to delete a category
				{	
					ManageCategoryFragment.profileBeingModified.removeCategory(ManageCategoryFragment.draggedItemIndex);
					categories.setAdapter(new ListViewAdapter(parrent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
				}
				else if(self.getId()==R.id.categorypic && ManageCategoryFragment.catDragOwnerID == R.id.pictograms) //We are to change the icon of the category
				{
					draggedPictogram = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getPictogramAtIndex(ManageCategoryFragment.draggedItemIndex);
					PARROTCategory tempCat = ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId);
					tempCat.setIcon(draggedPictogram);
					ManageCategoryFragment.profileBeingModified.setCategoryAt(ManageCategoryFragment.currentCategoryId, tempCat);
					ImageView icon = (ImageView) parrent.findViewById(R.id.categorypic);
					icon.setImageBitmap(ManageCategoryFragment.profileBeingModified.getCategoryAt(ManageCategoryFragment.currentCategoryId).getIcon().getBitmap());
					ListView list = (ListView) parrent.findViewById(R.id.categories);
					list.setAdapter(new ListViewAdapter(parrent, R.layout.categoriesitem, ManageCategoryFragment.profileBeingModified.getCategories()));
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
		}
		return true;
	}
}


