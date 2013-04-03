package dk.aau.cs.giraf.parrot;


import dk.aau.cs.giraf.categorylib.PictogramOLD;
import android.app.Activity;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.GridView;

/**
 * 
 * @author PARROT, spring 2012
 * This is the BoxDragListener class.
 * It handles drag and drop functionality with objects in the SpeechboardFragment.
 *
 */
public class SpeechBoardBoxDragListener implements OnDragListener 
{
	private Activity parrent;
	private PictogramOLD draggedPictogram = null;
	private PARROTProfile profile = PARROTActivity.getUser();
	int numberOfSentencePictograms = profile.getNumberOfSentencePictograms();
	boolean insideOfMe = false;

	/**
	 * @param active
	 */
	public SpeechBoardBoxDragListener(Activity active) {
		parrent = active;
	}

	/**
	 * it handles drag and drop functionality with objects in the SpeechboardFragment
	 * @param self
	 * @param event
	 */
	@Override
	public boolean onDrag(View self, DragEvent event) {
		if (event.getAction() == DragEvent.ACTION_DRAG_STARTED){
			//When pictogram is dragged from sentenceboard
			if(self.getId() == R.id.sentenceboard && SpeechBoardFragment.dragOwnerID == R.id.sentenceboard)
			{
				draggedPictogram = SpeechBoardFragment.speechBoardCategory.getPictogramAtIndex(SpeechBoardFragment.draggedPictogramIndex);
				//Do not allow dragging empty pictograms, show do nothing
				if(draggedPictogram.isEmpty()==true)
				{
				}
				else
				{
					GridView speech = (GridView) parrent.findViewById(R.id.sentenceboard);
					
					//remove pictogram from sentenceboard and add an empty pictogram 
					SpeechBoardFragment.speechBoardCategory.removePictogram(SpeechBoardFragment.draggedPictogramIndex);	
					SpeechBoardFragment.speechBoardCategory.addPictogram(new PictogramOLD("#emptyPictogram#", null, null, null, parrent));
					
					speech.setAdapter(new PictogramAdapter(SpeechBoardFragment.speechBoardCategory, parrent));
				}
			}
		} else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED){ 
			insideOfMe = true;
		} else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED){
			insideOfMe = false;
		} else if (event.getAction() == DragEvent.ACTION_DROP){
			if (insideOfMe){

				//We want to drop a view into the sentenceboard
				if( self.getId() == R.id.sentenceboard && SpeechBoardFragment.dragOwnerID != R.id.sentenceboard)	
				{
					GridView speech = (GridView) parrent.findViewById(R.id.sentenceboard);
					int x = (int)event.getX();
					int y = (int)event.getY();
					int index = speech.pointToPosition(x, y);
					if(index <0)	//If the pictorgram is dropped at an illegal index
					{
						//Do nothing
						//TODO improve this situation.
					}
					else
					{
						draggedPictogram = SpeechBoardFragment.displayedCategory.getPictogramAtIndex(SpeechBoardFragment.draggedPictogramIndex);


						//Replaces a pictogram already in the sentencebord
						if(SpeechBoardFragment.speechBoardCategory.getPictogramAtIndex(index).isEmpty() == false) 
						{
							SpeechBoardFragment.speechBoardCategory.removePictogram(index); //Removes the pictogram at the specific index
							SpeechBoardFragment.speechBoardCategory.addPictogramAtIndex(draggedPictogram, index); //add the pictogram at the specific position
						}
						//place the dragged pictogram into an empty filled
						else 
						{
							int count = 0;
							//place the new pictogram in the first empty filled
							while (count < numberOfSentencePictograms) 
							{
								if (SpeechBoardFragment.speechBoardCategory.getPictogramAtIndex(count).isEmpty() == true) 
								{
									SpeechBoardFragment.speechBoardCategory.removePictogram(count); //Removes the pictogram at the specific index
									SpeechBoardFragment.speechBoardCategory.addPictogramAtIndex(draggedPictogram, count); //add the pictogram at the specific position
									break;
								} 
								count++;
							}
						}


						speech.setAdapter(new PictogramAdapter(SpeechBoardFragment.speechBoardCategory, parrent));
						speech.invalidate();
					}
				}
				if(self.getId() == R.id.sentenceboard && SpeechBoardFragment.dragOwnerID == R.id.sentenceboard) //We are rearanging the position of pictograms on the sentenceboard
				{

					GridView speech = (GridView) parrent.findViewById(R.id.sentenceboard);
					int x = (int)event.getX();
					int y = (int)event.getY();
					int index = speech.pointToPosition(x, y);
					if(index <0)//if the pictogram is dropped at an illegal position
					{
						//do nothing, let the pictogram be removed
						//TODO improve this

					}
					else
					{
						if(SpeechBoardFragment.speechBoardCategory.getPictogramAtIndex(index).isEmpty() == true)
						{
							//if it is empty, there might be empty spaces to the left of it too
							int count = 0;
							while (count < numberOfSentencePictograms) 
							{

								if (SpeechBoardFragment.speechBoardCategory.getPictogramAtIndex(count).isEmpty() == true) 
								{
									SpeechBoardFragment.speechBoardCategory.removePictogram(count); //Removes the pictogram at the specific index
									SpeechBoardFragment.speechBoardCategory.addPictogramAtIndex(draggedPictogram, count); //add the pictogram at the specific position
									break;
								} 
								count++;
							}
						}
						else
						{
							SpeechBoardFragment.speechBoardCategory.addPictogramAtIndex(draggedPictogram, index);
						}


						speech.setAdapter(new PictogramAdapter(SpeechBoardFragment.speechBoardCategory, parrent));
						speech.invalidate();
						draggedPictogram = null;
					}

					while(SpeechBoardFragment.speechBoardCategory.getPictograms().size() > profile.getNumberOfSentencePictograms())
					{
						SpeechBoardFragment.speechBoardCategory.removePictogram(SpeechBoardFragment.speechBoardCategory.getPictograms().size()-1);
					}

				}
				if(self.getId() != R.id.sentenceboard && SpeechBoardFragment.dragOwnerID == R.id.sentenceboard) //If we drag something from the sentenceboard to somewhere else
				{

					GridView speech = (GridView) parrent.findViewById(R.id.sentenceboard);
					speech.setAdapter(new PictogramAdapter(SpeechBoardFragment.speechBoardCategory, parrent));
					speech.invalidate();

				}
				/*
				//The folowing do not work at the moment. It is mean to let the child be able to move pictograms from one category into another if this functionality is given to the child. 
				//This functionality is also handled in the ManageCategoryFragment.java.
				if( self.getId() == R.id.supercategory && SpeechBoardFragment.dragOwnerID == R.id.pictogramgrid)	//We are about to copy a pictogram from one category to another
				{
					//TODO Create a OPTIONS boolean to insures that this can be turned on or off.
					GridView gridCat = (GridView) parrent.findViewById(R.id.supercategory);
					int x = (int)event.getX();
					int y = (int)event.getY();
					int index = gridCat.pointToPosition(x, y);

					Category categoryDroppedIn = profile.getCategoryAt(index);
					categoryDroppedIn.addPictogram(draggedPictogram);
				}
				 */


			}
			//To ensure that no wrong references will be made, the index is reset to -1
			SpeechBoardFragment.draggedPictogramIndex = -1;
		} else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED){
			//Dummy				
		}
		return true;
	}
}


