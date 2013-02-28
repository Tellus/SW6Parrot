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
	public static int draggedItemIndex = -1;
	public static int catDragOwnerID =-1;
	public static int currentCategoryId = 0; //This is the currrent category that is chosen
	public static PARROTProfile profileBeingModified; 
	public static ArrayList<Pictogram> categories =  new ArrayList<Pictogram>();

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.parentActivity = activity;
		ManageCategoryFragment.profileBeingModified = PARROTActivity.getUser();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onResume() {

		super.onResume();
		// Change view to managecategory_layout
		parentActivity.setContentView(R.layout.managecategory_layout);
		
		// We start by instantiating the views
		Spinner profiles = (Spinner) parentActivity.findViewById(R.id.profiles);
		ListView categories = (ListView) parentActivity.findViewById(R.id.categories);
		GridView pictograms = (GridView) parentActivity.findViewById(R.id.pictograms);
		ImageView trash = (ImageView) parentActivity.findViewById(R.id.trash);
		//TextView categoryInfo = (TextView) parentActivity.findViewById(R.id.categoryinfo);
		EditText categoryInfo = (EditText) parentActivity.findViewById(R.id.categoryinfo);
		PARROTDataLoader dummyLoader = new PARROTDataLoader(parentActivity);
		ImageView categoryPic = (ImageView) parentActivity.findViewById(R.id.categorypic);
		Button createNewCategory = (Button) parentActivity.findViewById(R.id.createnewcategorybutton);
		Button changeCategoryColor = (Button) parentActivity.findViewById(R.id.changecategorycolorbutton);
		Button changeCategoryName = (Button) parentActivity.findViewById(R.id.changecategorynamebutton);
		Button copyThisCategoryToOtherProfile = (Button) parentActivity.findViewById(R.id.copythiscategorytootherprofilebutton);
		Button copyThisCategoryToOtherProfileCategory = (Button) parentActivity.findViewById(R.id.copythiscategorytootherprofilecategorybutton);


		categoryPic.setImageBitmap(profileBeingModified.getCategoryAt(currentCategoryId).getIcon().getBitmap()); //Loads the category's icon
		categoryInfo.setText(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryName());
		setPictogramsColour(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryColour());
		
		categories.setAdapter(new ListViewAdapter(parentActivity, R.layout.categoriesitem, profileBeingModified.getCategories())); //Adapter for the category gridview
		
		pictograms.setAdapter(new PictogramAdapter(profileBeingModified.getCategoryAt(currentCategoryId), parentActivity));

		//We set the onDragListeners for the views that implement drag and drop functionality
		parentActivity.findViewById(R.id.trash).setOnDragListener(new ManagementBoxDragListener(parentActivity));
		parentActivity.findViewById(R.id.categories).setOnDragListener(new ManagementBoxDragListener(parentActivity));
		parentActivity.findViewById(R.id.pictograms).setOnDragListener(new ManagementBoxDragListener(parentActivity));
		parentActivity.findViewById(R.id.categorypic).setOnDragListener(new ManagementBoxDragListener(parentActivity));


		profiles.setOnItemSelectedListener(new OnItemSelectedListener() //Here we chose what profile to show //TODO this method has yet to be implemented
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

		categories.setOnItemClickListener(new OnItemClickListener() //This is when we want to select a category
		{

			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) 
			{	
				//We save the position of the selected category in a public variable for outside access
				currentCategoryId = position;
				//We change the icon in the information view to that of the selected profile
				ImageView categoryPic = (ImageView) parentActivity.findViewById(R.id.categorypic);
				categoryPic.setImageBitmap(ManageCategoryFragment.profileBeingModified.getCategoryAt(currentCategoryId).getIcon().getBitmap());
				//We update the pictogram grid to show the pictograms of the selected category.
				GridView pictograms = (GridView) parentActivity.findViewById(R.id.pictograms);
				pictograms.setAdapter(new PictogramAdapter(profileBeingModified.getCategoryAt(currentCategoryId), parentActivity));
				//We show the name of the category.
				EditText categoryInfo = (EditText) parentActivity.findViewById(R.id.categoryinfo);
				categoryInfo.setText(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryName());
				//We set the colour of the pictogram grid.
				setPictogramsColour(profileBeingModified.getCategoryAt(currentCategoryId).getCategoryColour());
			}
		});

		categories.setOnItemLongClickListener(new OnItemLongClickListener() //This is when we want to move a category.
		{
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
			{
				draggedItemIndex = position;
				catDragOwnerID = R.id.categories;
				ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
				//This gives the dragged pictogram a transparentActivity, shadowy look.
				DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			}
		});


		pictograms.setOnItemLongClickListener(new OnItemLongClickListener()	//We want to drag a pictogram from the pictogram grid
		{

			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id)
			{
				draggedItemIndex = position;
				catDragOwnerID = R.id.pictograms;
				ClipData data = ClipData.newPlainText("label", "text"); //TODO Dummy. Pictogram information can be placed here instead.
				DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			}
		});

		//We are creating a new category here. We fill it with dummy data to start with. The empty picture, the name "Kategori navn" and the color red.
		createNewCategory.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				Pictogram pictogram = new Pictogram("#usynlig#", null, null, null, parentActivity);
				Category cat = new Category("Kategori Navn", 0xffff0000, pictogram);
				profileBeingModified.addCategory(cat);
				ListView categories = (ListView) parentActivity.findViewById(R.id.categories); //Redrawing the categories
				categories.setAdapter(new ListViewAdapter(parentActivity, R.layout.categoriesitem, profileBeingModified.getCategories())); //Adapter for the category gridview
			}
		});

		//We want to change the colour of the selected category
		changeCategoryColor.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), profileBeingModified.getCategoryAt(currentCategoryId).getCategoryColour(), new OnAmbilWarnaListener() 
				{
					public void onCancel(AmbilWarnaDialog dialog){}
					public void onOk(AmbilWarnaDialog dialog, int color) 
					{
						Category tempCat = profileBeingModified.getCategoryAt(currentCategoryId);
						tempCat.setCategoryColour(color);
						profileBeingModified.setCategoryAt(currentCategoryId, tempCat);
						
						setPictogramsColour(color);
					}
				});
				dialog.show();
			}
		});

		//FIXME This part of the code is currently not functioning
//		//Click this button to change the name of the category
//		changeCategoryName.setOnClickListener(new OnClickListener() 
//		{
//			public void onClick(View v) 
//			{
//				EditText categoryInfo = (EditText) parentActivity.findViewById(R.id.categoryinfo);
//				((InputMethodManager)parentActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(categoryInfo, InputMethodManager.SHOW_FORCED);
//				
//				String name = categoryInfo.getText().toString();
//				profileBeingModified.getCategoryAt(currentCategoryId).setCategoryName(name);
//			
//			}
//		});

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

	//TODO implement me
	//	public void onPause() 
	//	{
	//		saveProfileChanges(parentActivity, profileBeingModified);
	//	}

	//FIXME currently this causes the program to crash, fix this.
	public void saveProfileChanges(Activity parentActivity, PARROTProfile profileBeingModified)
	{
		PARROTDataLoader dataLoader = new PARROTDataLoader(parentActivity);
		dataLoader.saveProfile(profileBeingModified);
	}
	
	//This method sets the colour of the pictogram grid to the input colour
	private void setPictogramsColour(int colour)
	{
		GridView pictograms = (GridView) parentActivity.findViewById(R.id.pictograms);
		pictograms.setBackgroundColor(colour);
	}
}
