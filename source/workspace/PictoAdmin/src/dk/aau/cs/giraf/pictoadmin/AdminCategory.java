package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class AdminCategory extends Activity {
	private String childName = "defaultChild";
	private String guardianName = "defaultGuardian";
	private Bundle extras;
	//private ArrayList<ParcelablePictogram> pictograms;
	private ArrayList<KlimPictogram> pictograms;
	private PARROTCategory selectedCategory = null;
	private PARROTCategory selectedSubCategory = null;
	
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
		ArrayList<PARROTCategory> list = new ArrayList<PARROTCategory>();
		list = (ArrayList<PARROTCategory>) helpCat.getTempCategoriesWithNewPictogram(child);
		
		TextView displayChild = (TextView) findViewById(R.id.currentChildName);
		displayChild.setText(childName);
		
		final GridView categoryGrid = (GridView) findViewById(R.id.category_gridview);
		categoryGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				selectedCategory = (PARROTCategory) categoryGrid.getItemAtPosition(position);
				//Populate subcategory gridview by using adapter
			}
		});
		PARROTCategoryAdapter test = new PARROTCategoryAdapter(list, this);
		categoryGrid.setAdapter(test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_category, menu);
		return true;
	}
	
	private void getAllExtras() {
		if(getIntent().hasExtra("childId")){
			childName = extras.get("childId").toString();
		}
		if(getIntent().hasExtra("guardianId")){
			guardianName = extras.get("guardianId").toString();
		}
		if(getIntent().hasExtra("checkoutList")){
			pictograms = getIntent().getParcelableArrayListExtra("checkoutList");
		}
	}
	
	public void returnToCaller(MenuItem item) {
		Intent data = this.getIntent();
		data.putParcelableArrayListExtra("testParcel", pictograms);
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
