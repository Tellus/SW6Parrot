package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * 
 * @PARROT
 * This is the ManageCategoryFragment class. It is used to manage category settings.
 *
 */
public class ManageCategoryFragment extends Fragment {

	private Activity parentActivity;

	//Remembers the index of the item that is currently being dragged.
	public static int draggedItemIndex   = -1;
	public static int categoryDragownerID =-1;
	public static int currentCategoryId   = 0; //This is the current category that is chosen
	public static PARROTProfile profileBeingModified; 
	public static ArrayList<Pictogram> categories =  new ArrayList<Pictogram>();

	// Klim: What does this function do?
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		this.parentActivity = activity;
		ManageCategoryFragment.profileBeingModified = PARROTActivity.getUser();
	}

	// Set the color of the pictogram grid to the input color
	private void setPictogramsColor(int color)
	{
		GridView pictograms = (GridView) parentActivity.findViewById(R.id.pictograms);
		pictograms.setBackgroundColor(color);
	}
	
	//FIXME currently this causes the program to crash, fix this.
	/*public void saveProfileChanges(Activity parentActivity, PARROTProfile modifiedProfile)
	{
		PARROTDataLoader dataLoader = new PARROTDataLoader(parentActivity);
		dataLoader.saveProfile(modifiedProfile);
	}*/
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		
		// Change view to managecategory_layout
		parentActivity.setContentView(R.layout.managecategory_layout);
		
		// Setup the views, so they can be accessed directly
		Spinner profiles = (Spinner) parentActivity.findViewById(R.id.profiles);
		ListView categories   = (ListView)  parentActivity.findViewById(R.id.categories);
		GridView pictograms   = (GridView)  parentActivity.findViewById(R.id.pictograms);
		EditText categoryInfo = (EditText) parentActivity.findViewById(R.id.categoryinfo);
		ImageView categoryPic = (ImageView) parentActivity.findViewById(R.id.categoryPicture);
		
		Button createNewCategory = (Button) parentActivity.findViewById(R.id.createnewcategorybutton);
		Button changeCategoryColor = (Button) parentActivity.findViewById(R.id.changecategorycolorbutton);
		Button copyThisCategoryToOtherProfile = (Button) parentActivity.findViewById(R.id.copythiscategorytootherprofilebutton);
		Button copyThisCategoryToOtherProfileCategory = (Button) parentActivity.findViewById(R.id.copythiscategorytootherprofilecategorybutton);
		// TODO Implement button
		Button changeCategoryName = (Button) parentActivity.findViewById(R.id.changecategorynamebutton);
		
		// Unused variables from previous version
		ImageView trash = (ImageView) parentActivity.findViewById(R.id.trash); // Trash is unused variable
		PARROTDataLoader dummyLoader = new PARROTDataLoader(parentActivity);
		// End of setup

		categoryPic.setImageBitmap(profileBeingModified.getCategoryAt(currentCategoryId).getIcon().getBitmap()); //Loads the category's icon
		// Klim categoryInfo.setText(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryName());
		setPictogramsColor(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryColour());
		
		categories.setAdapter(new ListViewAdapter(parentActivity, R.layout.categoriesitem, profileBeingModified.getCategories())); //Adapter for the category gridview
		pictograms.setAdapter(new PictogramAdapter(profileBeingModified.getCategoryAt(currentCategoryId), parentActivity));

		//We set the onDragListeners for the views that implement drag and drop functionality
		parentActivity.findViewById(R.id.trash).setOnDragListener(new ManagementBoxDragListener(parentActivity));
		parentActivity.findViewById(R.id.categories).setOnDragListener(new ManagementBoxDragListener(parentActivity));
		parentActivity.findViewById(R.id.pictograms).setOnDragListener(new ManagementBoxDragListener(parentActivity));
		parentActivity.findViewById(R.id.categoryPicture).setOnDragListener(new ManagementBoxDragListener(parentActivity));

		//Here we chose what profile to show
		//TODO this method has yet to be implemented
		profiles.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) 
			{
				// TODO Auto-generated method stub
				//profileBeingModified = /* profil på position pladsen i arrayet givet til adapteren*/;
			}

			public void onNothingSelected(AdapterView<?> arg0) 
			{
				//Do nothing
			}
		});
		
		// Load selected category, when user press item
		categories.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) 
			{	
				// Save position for possible future reference
				currentCategoryId = position;

				// Match icon in information view with selected category
				ImageView categoryPic = (ImageView) parentActivity.findViewById(R.id.categoryPicture); 
				categoryPic.setImageBitmap(ManageCategoryFragment.profileBeingModified.getCategoryAt(currentCategoryId).getIcon().getBitmap());
				
				// Display pictograms in pictogram grid corresponding with the selected category.
				GridView pictograms = (GridView) parentActivity.findViewById(R.id.pictograms);
				pictograms.setAdapter(new PictogramAdapter(profileBeingModified.getCategoryAt(currentCategoryId), parentActivity));
				
				// Display the name of the category
				EditText categoryInfo = (EditText) parentActivity.findViewById(R.id.categoryinfo);
				categoryInfo.setText(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryName());
				
				// Match color of pictogram grid with selected category
				setPictogramsColor(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryColour());
			}
		});
		
		// This is when we want to move a category.
		categories.setOnItemLongClickListener(new OnItemLongClickListener() 
		{
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
			{
				// Save position for future reference
				draggedItemIndex = position;
				categoryDragownerID = R.id.categories;
				
				//TODO Dummy. Pictogram information can be placed here instead.
				ClipData data = ClipData.newPlainText("label", "text"); 
				
				// This gives the dragged category a transparentActivity, shadowy look.
				DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				
				return true;
			}
		});

		// Drag pictogram from pictogram grid
		pictograms.setOnItemLongClickListener(new OnItemLongClickListener()	
		{
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
			{
				ClipData data = ClipData.newPlainText("","");
				
				// This gives the dragged pictogram a transparentActivity, shadowy look.
				DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				
				return true;
			}
		});

		// Create new category button is pressed
		//The empty picture, the name "Kategori navn" and the color red.
		createNewCategory.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				// We fill it with dummy data to start with. 
				Pictogram pictogram = new Pictogram("#usynlig#", null, null, null, parentActivity);
				
				// Category name set to Kategori Navn, default green category color set
				PARROTCategory newCategory = new PARROTCategory("Kategori Navn", 0xffff0000, pictogram);
				
				// Add category to the current profile/user
				profileBeingModified.addCategory(newCategory);
				
				// Update the list of categories on the left side of the screen
				ListView categories = (ListView) parentActivity.findViewById(R.id.categories);
				
				// TODO Mangler bedre kommentar - Adapter for the category gridview
				categories.setAdapter(new ListViewAdapter(parentActivity, R.layout.categoriesitem, profileBeingModified.getCategories())); 
			}
		});

		// Change the color of selected category
		changeCategoryColor.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), profileBeingModified.getCategoryAt(currentCategoryId).getCategoryColour(), new OnAmbilWarnaListener() 
				{
					public void onCancel(AmbilWarnaDialog dialog){}
					public void onOk(AmbilWarnaDialog dialog, int color) 
					{
						// Assess currentCategory
						PARROTCategory currentCategory = profileBeingModified.getCategoryAt(currentCategoryId);
						currentCategory.setCategoryColour(color);
						profileBeingModified.setCategoryAt(currentCategoryId, currentCategory);
						// Method to change color
						setPictogramsColor(color);
					}
				});
				dialog.show();
			}
		});

		// FIXME Click this button to change the name of the category
		/*changeCategoryName.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				EditText categoryInfo = (EditText) parentActivity.findViewById(R.id.categoryinfo);
				((InputMethodManager)parentActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(categoryInfo, InputMethodManager.SHOW_FORCED);
				
				String name = categoryInfo.getText().toString();
				profileBeingModified.getCategoryAt(currentCategoryId).setCategoryName(name);
			}
		});*/

		copyThisCategoryToOtherProfile.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			}
		});

		copyThisCategoryToOtherProfileCategory.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onPause() 
	{
		super.onPause();
		// FIXME saveProfileChanges(parentActivity, profileBeingModified);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// FIXME saveProfileChanges(parentActivity, profileBeingModified);
		
	}

}
