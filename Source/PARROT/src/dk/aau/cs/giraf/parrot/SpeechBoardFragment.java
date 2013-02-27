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

public class SpeechBoardFragment extends Fragment
{

	private Activity parrent;
	
	//Remembers the index of the pictogram that is currently being dragged.
	public static int draggedPictogramIndex = -1;
	public static int dragOwnerID =-1;
	//Serves as the back-end storage for the visual speechboard
	public static ArrayList<Pictogram> speechboardPictograms = new ArrayList<Pictogram>();
	public static Category speechBoardCategory = new Category(0x00ff00,null);	//This category contains the pictograms on the sentenceboard
	public static Category displayedCat = new Category(PARROTActivity.getUser().getCategoryAt(0).getCategoryColour(),null);			//This category contains the pictograms displayed on the big board
	private PARROTProfile user = null;
	

	private Pictogram empty;  
	
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
		empty = new Pictogram("#usynlig#", null, null, null, parrent);

		user=PARROTActivity.getUser();	//FIXME this might mean that the user is not updated. It would be better to us the static user from PARROTActivity
		if(user.getCategoryAt(0)!=null)
		{
			displayedCat = user.getCategoryAt(0); //TODO we might have to replace this.

			//Fills the sentenceboard with empty pictograms
			while(speechBoardCategory.getPictograms().size() <PARROTActivity.getUser().getNumberOfSentencePictograms())
			{
				speechBoardCategory.addPictogram(empty);
			}

			GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
			pictogramGrid.setAdapter(new PictogramAdapter(displayedCat, parrent));
			
			
			GridView sentenceBoardGrid = (GridView) parrent.findViewById(R.id.sentenceboard);
			sentenceBoardGrid.setAdapter(new PictogramAdapter(speechBoardCategory, parrent));
			
			GridView superCategoryGrid = (GridView) parrent.findViewById(R.id.supercategory);
			superCategoryGrid.setAdapter(new CategoryAdapter(user.getCategories(), parrent));
			
			//initialise the colours of the fragment
			setColours(parrent);
			
			parrent.findViewById(R.id.pictogramgrid).setOnDragListener(new BoxDragListener(parrent));
			parrent.findViewById(R.id.sentenceboard).setOnDragListener(new BoxDragListener(parrent));
			parrent.findViewById(R.id.supercategory).setOnDragListener(new BoxDragListener(parrent));


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
			//Play sound
			sentenceBoardGrid.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View view,	int position, long id) {
					speechBoardCategory.getPictogramAtIndex(position).playWord();
				}
			});
			
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
			
			
			
			superCategoryGrid.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
				{
					displayedCat = user.getCategoryAt(position);
					GridView pictogramGrid = (GridView) parrent.findViewById(R.id.pictogramgrid);
					pictogramGrid.setAdapter(new PictogramAdapter(displayedCat, parrent));
					setColours(parrent);
					
				}
			});
			
			

		}



	}

	
	public void setColours(Activity invoker)
	{
		LinearLayout sentenceBoardLayout = (LinearLayout) invoker.findViewById(R.id.sentenceboardlinearhelper);
		Drawable draw = parrent.getResources().getDrawable(R.drawable.sentenceboardlayout);
		draw.setColorFilter(PARROTActivity.getUser().getSentenceBoardColor(), PorterDuff.Mode.OVERLAY);
		sentenceBoardLayout.setBackgroundDrawable(draw);

		LinearLayout superCategoryLayout = (LinearLayout) invoker.findViewById(R.id.supercategory_layout);
		draw=parrent.getResources().getDrawable(R.drawable.catlayout);
		draw.setColorFilter(PARROTActivity.getUser().getCategoryColor(), PorterDuff.Mode.OVERLAY);
		superCategoryLayout.setBackgroundDrawable(draw);
		
		LinearLayout pictogramLayout = (LinearLayout) invoker.findViewById(R.id.pictogramgrid_layout);
		draw = parrent.getResources().getDrawable(R.drawable.gridviewlayout);
		draw.setColorFilter(displayedCat.getCategoryColour(),PorterDuff.Mode.OVERLAY);
		pictogramLayout.setBackgroundDrawable(draw);
		
	}
}

