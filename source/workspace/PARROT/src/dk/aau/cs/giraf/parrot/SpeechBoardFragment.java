package dk.aau.cs.giraf.parrot;


import java.util.ArrayList;

import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.pictogram.Pictogram;
import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

/**
 * @author PARROT spring 2012
 * This class handles the views and actions of the speechLearning "Tale" function
 */
public class SpeechBoardFragment extends Fragment
{

	private Activity parrent;
	
	//Remembers the index of the pictogram that is currently being dragged.
	public static int draggedPictogramIndex = -1;
	public static int dragOwnerID =-1;
	//Serves as the back-end storage for the visual speechboard
	public static ArrayList<Pictogram> speechboardPictograms = new ArrayList<Pictogram>();
	
	//This category contains the pictograms on the sentenceboard
	public static PARROTCategory speechBoardCategory = new PARROTCategory(0xffffff,null);	
	//This category contains the pictograms displayed on the big board
	public static PARROTCategory displayedCategory = new PARROTCategory(PARROTActivity.getUser().getCategoryAt(0).getCategoryColor(),null);
	
	private PARROTProfile user = null;
	private static Pictogram emptyPictogram =null;  
	
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.parrent = activity;
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		parrent.setContentView(R.layout.speechboard_layout);
		
		user=PARROTActivity.getUser();	//FIXME this might mean that the user is not updated. It would be better to us the static user from PARROTActivity
		
		if(user.getCategoryAt(0)!=null)
		{
			displayedCategory = user.getCategoryAt(0);
			clearSentenceboard(parrent);
			

			
			//Setup the view for the listing of pictograms
			GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
			pictogramGrid.setAdapter(new PictogramAdapter(displayedCategory, parrent.getApplicationContext()));
			
			if(PARROTProfile.PictogramSize.MEDIUM == user.getPictogramSize())
			{
				pictogramGrid.setNumColumns(7);
			}
			//Setup the view for the sentences
			GridView sentenceBoardGrid = (GridView) parrent.findViewById(R.id.sentenceboard);
			sentenceBoardGrid.setAdapter(new PictogramAdapter(speechBoardCategory, parrent.getApplicationContext()));
			sentenceBoardGrid.setNumColumns(PARROTActivity.getUser().getNumberOfSentencePictograms());
			
			//Setup the view for the categories 
			GridView superCategoryGrid = (GridView) parrent.findViewById(R.id.supercategory);
			superCategoryGrid.setAdapter(new PARROTCategoryAdapter(user.getCategories(), parrent.getApplicationContext()));
		
			//initialise the colors of the fragment
			setColours();
			
			//setup drag listeners for the views
			parrent.findViewById(R.id.pictogramgrid).setOnDragListener(new SpeechBoardBoxDragListener(parrent));
			parrent.findViewById(R.id.sentenceboard).setOnDragListener(new SpeechBoardBoxDragListener(parrent));
			parrent.findViewById(R.id.supercategory).setOnDragListener(new SpeechBoardBoxDragListener(parrent));
			parrent.findViewById(R.id.subcategory).setOnDragListener(new SpeechBoardBoxDragListener(parrent));

			//for dragging pictogram from the pictogramlisting view
			pictogramGrid.setOnItemLongClickListener(new OnItemLongClickListener()
			{

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
				{
					draggedPictogramIndex = position; 
					dragOwnerID = R.id.pictogramgrid;
					ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
					DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
					view.startDrag(data, shadowBuilder, view, 0);
					return true;
				}

			});
			
			//Play sound, when click on a pictogram in the sentence board
			sentenceBoardGrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,	int position, long id) {
					speechBoardCategory.getPictogramAtIndex(position).playAudio();
				}
			});
			
			//Drag pictogram from the sentenceBoard, 
			sentenceBoardGrid.setOnItemLongClickListener(new OnItemLongClickListener()
			{

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
				{
					draggedPictogramIndex = position; 
					dragOwnerID = R.id.sentenceboard;			
					ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
					DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
					view.startDrag(data, shadowBuilder, view, 0);
					return true;
				}

			});
			
			
			//change category that is to be shown 
			superCategoryGrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
				{
					displayedCategory = user.getCategoryAt(position);
					GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
					pictogramGrid.setAdapter(new PictogramAdapter(displayedCategory, parrent.getApplicationContext()));
					//Setup the view for the categories 
					GridView subCategoryGrid = (GridView) parrent.findViewById(R.id.subcategory);
					subCategoryGrid.setAdapter(new PARROTCategoryAdapter(displayedCategory.getSubCategories(), parrent.getApplicationContext()));
					setPictogramGridColor();					
					
					subCategoryGrid.setOnItemClickListener(new OnItemClickListener() 
					{
						@Override
						public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
						{
							displayedCategory = displayedCategory.getSubCategoryAtIndex(position);
							GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
							pictogramGrid.setAdapter(new PictogramAdapter(displayedCategory, parrent.getApplicationContext()));
						}
					});
				}
			});
		}
	}

	public static void clearSentenceboard(Activity activity)
	{
		//(Context context, final String image, final String text, final String audio, final long id)
			emptyPictogram = new Pictogram(activity.getApplicationContext(),"#emptyPictogram#", "", "", -1);
			int count = speechBoardCategory.getPictograms().size()-1;
			while(speechBoardCategory.getPictograms().size()!= 0)
			{
				speechBoardCategory.removePictogram(count);
				count--;
			}
				
			//Fills the sentenceboard with emptyPictogram pictograms
			while(speechBoardCategory.getPictograms().size() <PARROTActivity.getUser().getNumberOfSentencePictograms())
			{
				speechBoardCategory.addPictogram(emptyPictogram);
			}
			GridView sentenceBoardGrid = (GridView) activity.findViewById(R.id.sentenceboard);
			sentenceBoardGrid.setAdapter(new PictogramAdapter(speechBoardCategory, activity.getApplicationContext()));
	}

	/**
	 * This function set the colors in the speechBoardFragment
	 * 
	 */
	private void setColours()
	{
		//setup colors of the sentenceBoard view	
		GridView sentenceBoardGrid = (GridView) parrent.findViewById(R.id.sentenceboard);
		Drawable draw = parrent.getResources().getDrawable(R.drawable.sentenceboardlayout);
		draw.setColorFilter(user.getSentenceBoardColor(), PorterDuff.Mode.OVERLAY);
		sentenceBoardGrid.setBackgroundDrawable(draw);
		
		//setup colors of the catagory listnings view
		GridView superCategoryGrid = (GridView) parrent.findViewById(R.id.supercategory);
		draw=parrent.getResources().getDrawable(R.drawable.catlayout);
		draw.setColorFilter(Color.DKGRAY, PorterDuff.Mode.OVERLAY);
		superCategoryGrid.setBackgroundDrawable(draw);
		
		GridView subCategoryGrid = (GridView) parrent.findViewById(R.id.subcategory);
		draw=parrent.getResources().getDrawable(R.drawable.catlayout);
		draw.setColorFilter(Color.DKGRAY, PorterDuff.Mode.OVERLAY);
		subCategoryGrid.setBackgroundDrawable(draw);
		setPictogramGridColor();
		
	}
	private void setPictogramGridColor()
	{
		//setup colors of the pictogram listnings view
				GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
				Drawable draw = parrent.getResources().getDrawable(R.drawable.gridviewlayout);
				draw.setColorFilter(displayedCategory.getCategoryColor(),PorterDuff.Mode.OVERLAY);
				pictogramGrid.setBackgroundDrawable(draw);
				
	}
}

