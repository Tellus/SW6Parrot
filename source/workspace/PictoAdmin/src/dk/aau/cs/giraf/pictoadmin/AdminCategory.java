package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.oasis.lib.controllers.ProfilesHelper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictoadmin.CreateDialogFragment.CreateDialogListener;
import dk.aau.cs.giraf.pictogram.Pictogram;


@SuppressLint("DefaultLocale")
public class AdminCategory extends Activity implements CreateDialogListener{
	private Profile child;
	private Bundle  extras;
	
	private PARROTCategory selectedCategory    = null;
	private PARROTCategory selectedSubCategory = null;
	private Pictogram 	   selectedPictogram   = null;
	private int			   selectedLocation;
	
	private ArrayList<PARROTCategory> categoryList    = new ArrayList<PARROTCategory>();
	private ArrayList<PARROTCategory> subcategoryList = new ArrayList<PARROTCategory>();
	private ArrayList<Pictogram> 	  pictograms	  = new ArrayList<Pictogram>();
	
	private GridView categoryGrid;
	private GridView subcategoryGrid;
	private GridView pictogramGrid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_category);
		
		extras = getIntent().getExtras();
		if(extras != null){
			getAllExtras();
		}
		
		CategoryHelper helpCat = new CategoryHelper(this);
		categoryList = (ArrayList<PARROTCategory>) helpCat.getTempCategoriesWithNewPictogram(child.getId());
		
		// Setup subcategory gridview
		subcategoryGrid = (GridView) findViewById(R.id.subcategory_gridview);
		subcategoryGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				updateSelected(v, position, 0);
				updateButtonVisibility(v);
			}
		});
		subcategoryGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				SettingDialogFragment settingDialog = new SettingDialogFragment(AdminCategory.this,
																				subcategoryList.get(position),
																				position, false);
				settingDialog.show(getFragmentManager(), "chooseSettings");
				return false;
			}
		});
		
		// Setup category gridview
		categoryGrid = (GridView) findViewById(R.id.category_gridview);
		categoryGrid.setAdapter(new PictoAdminCategoryAdapter(categoryList, this));
		categoryGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				updateSelected(v, position, 1);
				updateButtonVisibility(v);
			}
		});
		categoryGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				SettingDialogFragment settingDialog = new SettingDialogFragment(AdminCategory.this,
																			categoryList.get(position),
																			position, true);
				settingDialog.show(getFragmentManager(), "chooseSettings");
				return false;
			}
		});
		
		// Setup pictogram gridview
		pictogramGrid = (GridView) findViewById(R.id.pictogram_gridview);
		pictogramGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				updateSelected(v, position, 2);
				updateButtonVisibility(v);
			}
		});
		
		TextView currentChild = (TextView) findViewById(R.id.currentChildName);
		currentChild.setText(child.getFirstname()+ " " + child.getSurname());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_category, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/*
	 * The following methods handle the creation of new categories and sub-categories
	 * 
	 */
	private int newCategoryColor; // Hold the value set when creating a new category or sub-category
	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String titel, boolean isCategory) {
		MessageDialogFragment message;
		boolean legal = true;

		if(titel.isEmpty() == false && newCategoryColor != 0) {
			if(isCategory){
				for(PARROTCategory c : categoryList) {
					if(c.getCategoryName().equals(titel)){
						legal = false;
					}
				}
				if(legal){
					categoryList.add(new PARROTCategory(titel, newCategoryColor, categoryList.get(0).getIcon()));
					categoryGrid.setAdapter(new PictoAdminCategoryAdapter(categoryList, this));
				}
				else {
					message = new MessageDialogFragment("Den valgte titel er allerede anvendt af en anden kategori");
					message.show(getFragmentManager(), "usedTitle");
				}
			}
			else {
				for(PARROTCategory sc : subcategoryList) {
					if(sc.getCategoryName().equals(titel)){
						legal = false;
					}
				}
				if(legal){
					subcategoryList.add(new PARROTCategory(titel, newCategoryColor, categoryList.get(0).getIcon()));
					subcategoryGrid.setAdapter(new PictoAdminCategoryAdapter(subcategoryList, this));
				}
				else {
					message = new MessageDialogFragment("Den valgte titel er allerede anvendt af en anden kategori");
					message.show(getFragmentManager(), "usedTitle");
				}
			}
		}
		else {
			message = new MessageDialogFragment("Mangler titel og farve");
			message.show(getFragmentManager(), "message");
		}
		newCategoryColor = 0;
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// Do nothing
	}
	
	
	// Called when pressing the @id/colorButton and updates the newCategoryColor
	public void setNewCategoryColor(View view)
	{
		AmbilWarnaDialog colorDialog = new AmbilWarnaDialog(this, 0, new OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				newCategoryColor = color;
			}
			
			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				
			}
		});
		colorDialog.show();
	}
	
	/*
	 * The following methods handle updating of categories and sub-categories. This occurs when long-clicking either
	 * a category or sub-category. Depending on the setting parameter, individual methods for updating is called
	 */
	public void updateSettings(PARROTCategory category, int pos, boolean isCategory, String setting) {
		
		if(setting.toLowerCase().equals("title")){
			updateTitel(category, pos, isCategory);
		}
		else if(setting.toLowerCase().equals("color")){
			updateColor(category, pos, isCategory);
		}
		else if(setting.toLowerCase().equals("icon")){
			updateIcon(category, pos, isCategory);
		}
		else if(setting.toLowerCase().equals("delete")){
			if(isCategory){
				subcategoryList.removeAll(subcategoryList);
				pictograms.removeAll(pictograms);
				selectedCategory = null;
				categoryList.remove(pos);
			}
			else {
				pictograms.removeAll(pictograms);
				selectedSubCategory = null;
				subcategoryList.remove(pos);
			}
		}
		else if(setting.toLowerCase().equals("deletepictogram")){
			if(selectedSubCategory == null){
				selectedPictogram = null;
				selectedCategory.removePictogram(selectedLocation);
			}
		}
		
		if(isCategory){
			categoryGrid.setAdapter(new PictoAdminCategoryAdapter(categoryList, this));
		}
		subcategoryGrid.setAdapter(new PictoAdminCategoryAdapter(subcategoryList, this));
		pictogramGrid.setAdapter(new PictoAdapter(pictograms, this));
		updateButtonVisibility(categoryGrid);
		updateButtonVisibility(subcategoryGrid);
		updateButtonVisibility(pictogramGrid);
	}
	
	private void updateTitel(PARROTCategory tempCategory, int pos, boolean isCategory) {
		boolean legal = true;
		if(isCategory) {
			for(PARROTCategory c : categoryList){
				if(c.getCategoryName().equals(tempCategory.getCategoryName())) {
					legal = false;
					MessageDialogFragment message = new MessageDialogFragment("Navn er allerede brugt");
					message.show(getFragmentManager(), "invalidName");
					break;
				}
			}
			if(legal) {
				categoryList.get(pos).setCategoryName(tempCategory.getCategoryName());
			}
		}
		else {
			for(PARROTCategory sc : subcategoryList){
				if(sc.getCategoryName().equals(tempCategory.getCategoryName())){
					legal = false;
					MessageDialogFragment message = new MessageDialogFragment("Navn er allerede brugt");
					message.show(getFragmentManager(), "invalidName");
					break;
				}
			}
			if(legal){
				subcategoryList.get(pos).setCategoryName(tempCategory.getCategoryName());
			}
		}
	}
	
	private void updateColor(PARROTCategory category, int pos, boolean isCategory) {
		category.setChanged(true);
		
		if(isCategory){
			categoryList.set(pos, category);
		}
		else {
			subcategoryList.set(pos, category);
		}
	}
	
	private void updateIcon(PARROTCategory category, int pos, boolean isCategory) {
		category.setChanged(true);
		
		if(isCategory){
			categoryList.set(pos, category);
		}
		else {
			subcategoryList.set(pos, category);
		}
	}
	
	/*
	 * This method gets all extras in the extras bundle from the intent that started this activity
	 */
	private void getAllExtras() {
		ProfilesHelper help = new ProfilesHelper(this);
		if(getIntent().hasExtra("childId")){
			child = help.getProfileById(extras.getLong("childId"));
		}
	}

	/*
	 * The following methods handle menu pressing
	 */
	public void returnToCaller(MenuItem item) {
		finish();
	}

	/*
	 * The following method update the visibility of buttons. This depends on what is selected. This limits
	 * the buttons the user has access to, thereby limiting what the user can do (such as deletion and addition)
	 */
	public void updateButtonVisibility(View v) {
		ImageButton delcat = (ImageButton) findViewById(R.id.delete_selected_category_button);
		ImageButton delsub = (ImageButton) findViewById(R.id.delete_selected_subcategory_button);
		ImageButton delpic = (ImageButton) findViewById(R.id.delete_selected_picture_button);
		ImageButton addsub = (ImageButton) findViewById(R.id.add_new_subcategory_button);
		ImageButton addpic = (ImageButton) findViewById(R.id.add_new_picture_button);
		ImageButton accpic = (ImageButton) findViewById(R.id.access_pictocreator_button);
		
		if(selectedCategory != null) {	
			delcat.setVisibility(View.VISIBLE);
			addsub.setVisibility(View.VISIBLE);
			accpic.setVisibility(View.VISIBLE);
			addpic.setVisibility(View.VISIBLE);
		}
		else if(selectedCategory == null) {
			delcat.setVisibility(View.GONE);
		}
		if(selectedSubCategory != null) {
			delsub.setVisibility(View.VISIBLE);
		}
		else if(selectedSubCategory == null) {
			delsub.setVisibility(View.GONE);
		}
		if(selectedPictogram != null) {
			delpic.setVisibility(View.VISIBLE);
		}
		else if(selectedPictogram == null) {
			delpic.setVisibility(View.GONE);
		}
	}
	
	/*
	 * This method update what is currently selected (category, sub-category or pictogram)
	 */
	private void updateSelected(View view, int position, int id) {
		selectedLocation = position;
		if(id == 2) {
			selectedPictogram = pictograms.get(position);
		}
		if(id == 1) {
			selectedCategory = categoryList.get(position);
			selectedSubCategory = null;
			selectedPictogram = null;
			subcategoryList  = selectedCategory.getSubCategories();
			pictograms 		 = selectedCategory.getPictograms();
		    
			subcategoryGrid.setAdapter(new PictoAdminCategoryAdapter(subcategoryList, view.getContext()));
			pictogramGrid.setAdapter(new PictoAdapter(pictograms, true, view.getContext()));
		}
		else if(id == 0) {
			selectedSubCategory = subcategoryList.get(position);
			selectedPictogram = null;
			pictograms = subcategoryList.get(position).getPictograms();
			
			pictogramGrid.setAdapter(new PictoAdapter(pictograms, true, view.getContext()));
		}
	}
	
	/*
	 * The following methods handle the creation and deletion of categories and sub-categories
	 */
	public void createCategory(View view) {
		CreateDialogFragment createDialog = new CreateDialogFragment(true, "kategori");
		createDialog.show(getFragmentManager(), "dialog");
	}
	
	public void deleteCategory(View view) {
		DeleteDialogFragment deleteDialog = new DeleteDialogFragment(this, selectedCategory, selectedLocation, true);
		deleteDialog.show(getFragmentManager(), "deleteCategory?");
	}
	
	public void createSubCategory(View view) {
		CreateDialogFragment createDialog = new CreateDialogFragment(false, "under kategori");
		createDialog.show(getFragmentManager(), "dialog");
	}
	
	public void deleteSubCategory(View view) {
		DeleteDialogFragment deleteDialog = new DeleteDialogFragment(this, selectedSubCategory, selectedLocation, false);
		deleteDialog.show(getFragmentManager(), "deleteSubCategory?");
	}
	
	public void createPictogram(View view) {
		
	}
	
	public void deletePictogram(View view) {
		DeleteDialogFragment deleteDialog = new DeleteDialogFragment(this, selectedPictogram, selectedLocation, false);
		deleteDialog.show(getFragmentManager(), "deletePictogram?");
	}
	
	/*
	 * The following method handle how we access pictoCreator
	 */
	public void gotoPictoCreator(View view)
	{
		//TODO: Make this
	}
}
