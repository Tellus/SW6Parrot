package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_category);
		
		extras = getIntent().getExtras();
		if(extras != null){
			getAllExtras();
		}
		
		CategoryHelper helpCat = new CategoryHelper(this);
		Helper help = new Helper(this);
		Profile child = help.profilesHelper.getProfileById(11);
		final ArrayList<PARROTCategory> categoryList = (ArrayList<PARROTCategory>) helpCat.getTempCategoriesWithNewPictogram(child);
		final ArrayList<PARROTCategory> subcategoryList = new ArrayList<PARROTCategory>();
		final ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();
		final GridView subcategoryGrid = (GridView) findViewById(R.id.subcategory_gridview);
		subcategoryGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				selectedSubCategory = (PARROTCategory) subcategoryList.get(position);
				ArrayList<Pictogram> pictograms = selectedSubCategory.getPictograms();
				GridView pictoGrid = (GridView) findViewById(R.id.pictogram_gridview);
				pictoGrid.setAdapter(new PictoAdapter2(pictograms, getApplicationContext()));
				// TODO Auto-generated method stub
			}
		});
		subcategoryGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		GridView categoryGrid = (GridView) findViewById(R.id.category_gridview);
		categoryGrid.setAdapter(new PARROTCategoryAdapter(categoryList, this));
		categoryGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				selectedCategory = (PARROTCategory) categoryList.get(position);
				ArrayList<PARROTCategory> subcategoryList = selectedCategory.getSubCategories();
				subcategoryGrid.setAdapter(new PARROTCategoryAdapter(subcategoryList, getApplicationContext()));
				ImageButton addnewsub = (ImageButton) findViewById(R.id.add_new_subcategory_button);
							addnewsub.setClickable(true);
				
				ImageButton delsubcat = (ImageButton) findViewById(R.id.delete_selected_subcategory_button);
							delsubcat.setClickable(false);
			}
		});
		categoryGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				SettingDialogFragment testDialog = new SettingDialogFragment();
				testDialog.show(getFragmentManager(), "test");
				return false;
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
		if(selectedCategory != null) {
			ImageButton delcat = (ImageButton) findViewById(R.id.delete_selected_category_button);
			ImageButton addsub = (ImageButton) findViewById(R.id.add_new_subcategory_button);
			ImageButton accpic = (ImageButton) findViewById(R.id.access_pictocreator_button);
			ImageButton addpic = (ImageButton) findViewById(R.id.add_new_picture_button);
						
			delcat.setClickable(true);
			addsub.setClickable(true);
			accpic.setClickable(true);
			addpic.setClickable(true);
		}
		if(selectedSubCategory != null) {
			ImageButton delsub = (ImageButton) findViewById(R.id.delete_selected_subcategory_button);
						delsub.setClickable(true);
		}
		if(selectedPictogram != null) {
			ImageButton delpic = (ImageButton) findViewById(R.id.delete_selected_picture_button);
						delpic.setClickable(true);
		}
	}
	
	private void getAllExtras() {
		if(getIntent().hasExtra("childId")){
			childName = extras.get("childId").toString();
		}
		if(getIntent().hasExtra("guardianId")){
			guardianName = extras.get("guardianId").toString();
		}
	}
	
	public void returnToCaller(MenuItem item) {
		Intent data = this.getIntent();
		if(getParent() == null){
			setResult(Activity.RESULT_OK, data);
		}
		else{
			getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}

	public void createCategory(View view)
	{
		
	}
	
	public void deleteCategory(View view)
	{
		
	}
	
	public void createSubCategory(View view)
	{
		
	}
	
	public void deleteSubCategory(View view)
	{
		
	}
	
	public void gotoPictoCreator(View view)
	{
		
	}
}
