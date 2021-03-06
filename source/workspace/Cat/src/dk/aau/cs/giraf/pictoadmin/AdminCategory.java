package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;



@SuppressLint("DefaultLocale")
public class AdminCategory extends Activity implements CreateDialogListener{
	private Profile child;
	private Profile guardian;
	private Bundle  extras;
	
	private PARROTCategory selectedCategory    = null;
	private PARROTCategory selectedSubCategory = null;
	private Pictogram 	   selectedPictogram   = null;
	
	private ArrayList<PARROTCategory> categoryList    = new ArrayList<PARROTCategory>();
	private ArrayList<PARROTCategory> subcategoryList = new ArrayList<PARROTCategory>();
	private ArrayList<Pictogram> 	  pictograms	  = new ArrayList<Pictogram>();
	
	private GridView categoryGrid;
	private GridView subcategoryGrid;
	private GridView pictogramGrid;
	
	private boolean somethingChanged = false; // If something is deleted, is has to be noted
	private int     selectedLocation;
	private int     newCategoryColor; // Hold the value set when creating a new category or sub-category
	
	private CategoryHelper catHelp;
	private ProfilesHelper proHelp;
	
	private MessageDialogFragment message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("klim", "created");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_category);
		catHelp =  new CategoryHelper(this);
		proHelp =  new ProfilesHelper(this);
		
		extras = getIntent().getExtras();
		if(extras != null){
			getAllExtras();
		}
		else{
			message = new MessageDialogFragment("No childId found. Loading default child");
			message.show(getFragmentManager(), "noChildFound");
			child = proHelp.getProfileById(12);
			guardian = proHelp.getProfileById(1);
		}
		
		categoryList = catHelp.getChildsCategories(child.getId());
		
		// Setup category gridview
		categoryGrid = (GridView) findViewById(R.id.category_gridview);
		categoryGrid.setAdapter(new PictoAdminCategoryAdapter(categoryList, this));
		categoryGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				categoryGrid.setBackgroundColor(categoryList.get(position).getCategoryColor());
				subcategoryGrid.setBackgroundColor(categoryList.get(position).getCategoryColor());
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
		
		// Setup subcategory gridview
		subcategoryGrid = (GridView) findViewById(R.id.subcategory_gridview);
		subcategoryGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				subcategoryGrid.setBackgroundColor(subcategoryList.get(position).getCategoryColor());
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
		getMenuInflater().inflate(R.menu.activity_admin_category, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		Log.v("klim", "destroyed");
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		Log.v("klim", "resumed");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.v("klim", "paused");
		super.onPause();
		for(PARROTCategory sc : subcategoryList){
			if(sc.isChanged()){
				sc.getSuperCategory().setChanged(true);
				Log.v("AdminCategory", "set changed to true");
			}
		}
		for(PARROTCategory c : categoryList){
			if(c.isChanged()){
				somethingChanged = true;
				catHelp.saveCategory(c);
			}
		}

		if(somethingChanged){
			catHelp.saveChangesToXML();
		}
	}
	
	/*
	 * The following methods handle the creation of new categories and sub-categories
	 */
	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String titel, boolean isCategory) {
		boolean legal = true;

		if(titel.isEmpty() == false) {
			if(isCategory){
				for(PARROTCategory c : categoryList) {
					if(c.getCategoryName().equals(titel)){
						legal = false;
					}
				}
				if(legal){
					categoryList.add(new PARROTCategory(titel, newCategoryColor, categoryList.get(0).getIcon()));
					categoryList.get(categoryList.size()-1).setChanged(true);
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
					subcategoryList.add(new PARROTCategory(titel, selectedCategory.getCategoryColor(), categoryList.get(0).getIcon()));
					selectedCategory.setChanged(true);
					subcategoryGrid.setAdapter(new PictoAdminCategoryAdapter(subcategoryList, this));
				}
				else {
					message = new MessageDialogFragment("Den valgte titel er allerede anvendt af en anden kategori");
					message.show(getFragmentManager(), "usedTitle");
				}
			}
		}
		else {
			message = new MessageDialogFragment("Mangler titel");
			message.show(getFragmentManager(), "missingTitle");
		}
		newCategoryColor = 0;
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// Do nothing
	}

	// DONE: Called when pressing the @id/colorButton and updates the newCategoryColor
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
				catHelp.deleteCategory(selectedCategory);
				categoryList.remove(pos);
				selectedCategory = null;
			}
			else {
				pictograms.removeAll(pictograms);
				subcategoryList.remove(pos);
				selectedCategory.setChanged(true);
				selectedSubCategory = null;
			}
			somethingChanged = true;
		}
		else if(setting.toLowerCase().equals("deletepictogram")){
			if(selectedSubCategory == null){
				selectedCategory.removePictogram(selectedLocation);
			}
			else{
				selectedSubCategory.removePictogram(selectedLocation);
			}
			selectedCategory.setChanged(true);
			selectedPictogram = null;
			somethingChanged = true;
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
	
	// DONE
	private void updateTitel(PARROTCategory tempCategory, int pos, boolean isCategory) {
		boolean legal = true;
		if(isCategory) {
			for(PARROTCategory c : categoryList){
				if(c.getCategoryName().equals(tempCategory.getCategoryName())) {
					legal = false;
					message = new MessageDialogFragment("Titlen er anvendt");
					message.show(getFragmentManager(), "invalidName");
					break;
				}
			}
			if(legal) {
				categoryList.get(pos).setCategoryName(tempCategory.getCategoryName());
				categoryList.get(pos).setChanged(true);
				Log.v("AdminCategory", "In updateTitel: set changed to true");
			}
		}
		else {
			for(PARROTCategory sc : subcategoryList){
				if(sc.getCategoryName().equals(tempCategory.getCategoryName())){
					legal = false;
					message = new MessageDialogFragment("Navn er allerede brugt");
					message.show(getFragmentManager(), "invalidName");
					break;
				}
			}
			if(legal){
				subcategoryList.get(pos).setCategoryName(tempCategory.getCategoryName());
				subcategoryList.get(pos).setChanged(true);
				Log.v("AdminCategory", "In updateTitel: set sub changed to true");
			}
		}
	}
	
	// DONE
	private void updateColor(PARROTCategory category, int pos, boolean isCategory) {
		category.setChanged(true);
		Log.v("AdminCategory", "In updateColor: set changed to true");
		
		if(isCategory){
			categoryList.set(pos, category);
		}
		else {
			subcategoryList.set(pos, category);
		}
	}
	
	// DONE
	private void updateIcon(PARROTCategory category, int pos, boolean isCategory) {
		category.setChanged(true);
		Log.v("AdminCategory", "In updateIcon: set changed to true");
		
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
		
		if(getIntent().hasExtra("currentChildID")){
			child = proHelp.getProfileById(extras.getLong("currentChildID"));
		}
		if(getIntent().hasExtra("currentGuardianID")){
			
			guardian = proHelp.getProfileById(extras.getLong("currentGuardianID"));
		}
	}

	/*
	 * DONE: The following methods handle menu pressing
	 */
	public void saveChanges(MenuItem item) {
		for(PARROTCategory sc : subcategoryList){
			if(sc.isChanged()){
				sc.getSuperCategory().setChanged(true);
				sc.setChanged(false);
			}
		}
		for(PARROTCategory c : categoryList){
			if(c.isChanged()){
				somethingChanged = true;
				catHelp.saveCategory(c);
				c.setChanged(false);
			}
		}

		if(somethingChanged){
			catHelp.saveChangesToXML();
		}
		somethingChanged = false;
	}
	
	public void returnToCaller(MenuItem item) {
		finish();
	}

	/*
	 * DONE: The following method update the visibility of buttons. This depends on what is selected. This limits
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
		else if(selectedCategory == null || categoryList.size() == 1) {
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
	 * DONE: This method update what is currently selected (category, sub-category or pictogram)
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
	 * DONE: The following methods handle the creation and deletion of categories and sub-categories
	 */
	public void createCategory(View view) {
		CreateDialogFragment createDialog = new CreateDialogFragment(true, "kategori");
		createDialog.show(getFragmentManager(), "dialog");
	}
	
	// DONE
	public void deleteCategory(View view) {
		DeleteDialogFragment deleteDialog = new DeleteDialogFragment(this, selectedCategory, selectedLocation, true);
		deleteDialog.show(getFragmentManager(), "deleteCategory?");
	}
	
	// DONE
	public void createSubCategory(View view) {
		CreateDialogFragment createDialog = new CreateDialogFragment(false, "under kategori");
		createDialog.show(getFragmentManager(), "dialog");
	}
	
	// DONE
	public void deleteSubCategory(View view) {
		DeleteDialogFragment deleteDialog = new DeleteDialogFragment(this, selectedSubCategory, selectedLocation, false);
		deleteDialog.show(getFragmentManager(), "deleteSubCategory?");
	}
	
	public void createPictogram(View view) {
		//TODO: implement
		Intent request = new Intent();
		request.setComponent(new ComponentName("dk.aau.cs.giraf.pictosearch", "dk.aau.cs.giraf.pictosearch.PictoAdminMain"));
		request.putExtra("purpose", "CAT");
		request.putExtra("currentChildID", child.getId());
		request.putExtra("currentGuardianID", guardian.getId());
		startActivityForResult(request, RESULT_FIRST_USER);
	}
	
	// DONE
	public void deletePictogram(View view) {
		DeleteDialogFragment deleteDialog = new DeleteDialogFragment(this, selectedPictogram, selectedLocation, false);
		deleteDialog.show(getFragmentManager(), "deletePictogram?");
	}
	
	/*
	 * The following method handle how we access pictoCreator
	 */
	public void gotoPictoCreator(View view)
	{
		try{
			Intent croc = new Intent();
			croc.setComponent(new ComponentName("dk.aau.cs.giraf.pictocreator", "dk.aau.cs.giraf.pictocreator.crocActivity"));
			startActivity(croc);
		}
		catch (Exception e) {
			message = new MessageDialogFragment("Croc not installed");
			message.show(getFragmentManager(), "missingApp");
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bundle extras = data.getExtras();
		if(data.hasExtra("checkoutIds")){
			Log.v("klim", "ids returned");
			long[] checkoutIds = new long[extras.getLongArray("checkoutIds").length];
			checkoutIds = extras.getLongArray("checkoutIds");
			boolean legal = true;
			// Add pictograms to selectedCategory if no subcategory is selected
			if(selectedSubCategory == null){
				for(long id : checkoutIds){
					for(Pictogram p : pictograms){
						if(p.getPictogramID() == id){
							legal = false;
						}
					}
					if(legal){
						selectedCategory.addPictogram(PictoFactory.getPictogram(this, id));
						selectedCategory.setChanged(true);
						pictograms = selectedCategory.getPictograms();
					}
					legal = true;
				}
			}
			else{
				for(long id : checkoutIds){
					for(Pictogram p : pictograms){
						if(p.getPictogramID() == id){
							legal = false;
							break;
						}
					}
					if(legal){
						selectedSubCategory.addPictogram(PictoFactory.getPictogram(this, id));
						selectedCategory.setChanged(true);
						pictograms = selectedSubCategory.getPictograms();
					}
					legal = true;
				}
			}
			pictogramGrid.setAdapter(new PictoAdapter(pictograms, this));
		}
		else{
			MessageDialogFragment message = new MessageDialogFragment("testing");
			message.show(getFragmentManager(), "");
		}
	}
}
