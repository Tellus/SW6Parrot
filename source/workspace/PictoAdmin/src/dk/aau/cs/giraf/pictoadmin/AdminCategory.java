package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictogram.Pictogram;


public class AdminCategory extends Activity {
	private String childName = "";
	private String guardianName = "";
	private Bundle extras;
	
	private PARROTCategory selectedCategory = null;
	private PARROTCategory selectedSubCategory = null;
	private Pictogram 	   selectedPictogram = null;
	
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
		categoryList = (ArrayList<PARROTCategory>) helpCat.getTempCategoriesWithNewPictogram(11);
		
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
				SettingDialogFragment testDialog = new SettingDialogFragment();
				testDialog.show(getFragmentManager(), "chooseSettings");
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
	
	private void getAllExtras() {
		if(getIntent().hasExtra("childId")){
			childName = extras.get("childId").toString();
		}
		if(getIntent().hasExtra("guardianId")){
			guardianName = extras.get("guardianId").toString();
		}
	}
	
	public void updateSelected(View view, int position, int id) {
		if(id == 2) {
			selectedPictogram = pictograms.get(position);
			selectedCategory = null;
			selectedSubCategory = null;
		}
		if(id == 1) {
			selectedCategory = categoryList.get(position);
			selectedSubCategory = null;
			selectedPictogram = null;
			subcategoryList  = selectedCategory.getSubCategories();
			pictograms 		 = selectedCategory.getPictograms();
		    
			//updateGrid(subcategoryGrid, new PictoAdminCategoryAdapter(subcategoryList, view.getContext()));
			subcategoryGrid.setAdapter(new PictoAdminCategoryAdapter(subcategoryList, view.getContext()));
			
			pictogramGrid.setAdapter(new PictoAdapter2(pictograms, view.getContext()));
		}
		else if(id == 0) {
			selectedSubCategory = subcategoryList.get(position);
			selectedCategory = null;
			selectedPictogram = null;
			pictograms = subcategoryList.get(position).getPictograms();
			
			pictogramGrid.setAdapter(new PictoAdapter2(pictograms, view.getContext()));
		}
	}
	
	public void returnToCaller(MenuItem item) {
		finish();
	}

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
	
	public void validTest(String input) {
		TextView testView = (TextView) findViewById(R.id.currentChildName);
		testView.setText(input);
	}
	
	public void createCategory(View view)
	{
		
	}
	
	public void deleteCategory(View view)
	{
		DeleteDialogFragment option = new DeleteDialogFragment();
		option.show(getFragmentManager(), "deleteCategory?");
	}
	
	public void createSubCategory(View view)
	{
		
	}
	
	public void deleteSubCategory(View view)
	{
		DeleteDialogFragment option = new DeleteDialogFragment();
		option.show(getFragmentManager(), "deleteSubCategory?");
	}
	
	public void gotoPictoCreator(View view)
	{
		
	}
	
	public void createPictogram(View view)
	{
		
	}
	
	public void deletePictogram(View view)
	{
		DeleteDialogFragment option = new DeleteDialogFragment();
		option.show(getFragmentManager(), "deletePictogram?");
	}
}
