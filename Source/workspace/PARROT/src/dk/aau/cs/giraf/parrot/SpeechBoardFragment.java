package dk.aau.cs.giraf.parrot;


import java.io.InputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

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
<<<<<<< HEAD
=======
	public static PARROTCategory speechBoardCategory = new PARROTCategory(0x00ff00,null);	//This category contains the pictograms on the sentenceboard
	public static PARROTCategory displayedCat = new PARROTCategory(PARROTActivity.getUser().getCategoryAt(0).getCategoryColour(),null);			//This category contains the pictograms displayed on the big board
	private PARROTProfile user = null;
>>>>>>> origin/Jacob-2
	
	//This category contains the pictograms on the sentenceboard
	public static Category speechBoardCategory = new Category(0x00ff00,null);	
	//This category contains the pictograms displayed on the big board
	public static Category displayedCategory = new Category(PARROTActivity.getUser().getCategoryAt(0).getCategoryColour(),null);
	
	private PARROTProfile user = null;
	private Pictogram emptyPictogram;  
	
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
		emptyPictogram = new Pictogram("#emptyPictogram#", null, null, null, parrent);

		user=PARROTActivity.getUser();	//FIXME this might mean that the user is not updated. It would be better to us the static user from PARROTActivity
		if(user.getCategoryAt(0)!=null)
		{
			displayedCategory = user.getCategoryAt(0); //TODO we might have to replace this.

			//Fills the sentenceboard with emptyPictogram pictograms
			while(speechBoardCategory.getPictograms().size() <PARROTActivity.getUser().getNumberOfSentencePictograms())
			{
				speechBoardCategory.addPictogram(emptyPictogram);
			}

			//Setup the view for the listing of pictograms
			GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
			pictogramGrid.setAdapter(new PictogramAdapter(displayedCategory, parrent));
			
			//Setup the view for the sentences
			GridView sentenceBoardGrid = (GridView) parrent.findViewById(R.id.sentenceboard);
			sentenceBoardGrid.setAdapter(new PictogramAdapter(speechBoardCategory, parrent));
			
			//Setup the view for the categories 
			GridView superCategoryGrid = (GridView) parrent.findViewById(R.id.supercategory);
			superCategoryGrid.setAdapter(new PARROTCategoryAdapter(user.getCategories(), parrent));
			
			//initialise the colours of the fragment
			setColours(parrent);
			
			//setup drag listeners for the views
			parrent.findViewById(R.id.pictogramgrid).setOnDragListener(new SpeechBoardBoxDragListener(parrent));
			parrent.findViewById(R.id.sentenceboard).setOnDragListener(new SpeechBoardBoxDragListener(parrent));
			parrent.findViewById(R.id.supercategory).setOnDragListener(new SpeechBoardBoxDragListener(parrent));

			//for dragging pictogram from the pictogramlisting view
			pictogramGrid.setOnItemLongClickListener(new OnItemLongClickListener()
			{

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

				public void onItemClick(AdapterView<?> arg0, View view,	int position, long id) {
					speechBoardCategory.getPictogramAtIndex(position).playWord();
				}
			});
			
			//Drag pictogram from the sentenceBoard, 
			sentenceBoardGrid.setOnItemLongClickListener(new OnItemLongClickListener()
			{

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

				public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
				{
					displayedCategory = user.getCategoryAt(position);
					GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
					pictogramGrid.setAdapter(new PictogramAdapter(displayedCategory, parrent));
					setColours(parrent);
					
				}
			});
		}
	}

	/**
	 * This function set the colors in the speechBoardFragment
	 * @param invoker 
	 */
	public void setColours(Activity invoker)
	{
		//setup colors of the sentenceBoard view
		LinearLayout sentenceBoardLayout = (LinearLayout) invoker.findViewById(R.id.sentenceboardlinearhelper);
		Drawable draw = parrent.getResources().getDrawable(R.drawable.sentenceboardlayout);
		draw.setColorFilter(PARROTActivity.getUser().getSentenceBoardColor(), PorterDuff.Mode.OVERLAY);
		sentenceBoardLayout.setBackgroundDrawable(draw);

		//setup colors of the catagory listnings view
		LinearLayout superCategoryLayout = (LinearLayout) invoker.findViewById(R.id.supercategory_layout);
		draw=parrent.getResources().getDrawable(R.drawable.catlayout);
		draw.setColorFilter(PARROTActivity.getUser().getCategoryColor(), PorterDuff.Mode.OVERLAY);
		superCategoryLayout.setBackgroundDrawable(draw);
		
		//setup colors of the pictogram listnings view
		LinearLayout pictogramLayout = (LinearLayout) invoker.findViewById(R.id.pictogramgrid_layout);
		draw = parrent.getResources().getDrawable(R.drawable.gridviewlayout);
		draw.setColorFilter(displayedCategory.getCategoryColour(),PorterDuff.Mode.OVERLAY);
		pictogramLayout.setBackgroundDrawable(draw);
		
	}
}

