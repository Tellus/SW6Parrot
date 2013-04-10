package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictogram.Pictogram;

public class PictoAdminMain extends Activity {
	Button searchbutton;
	DatabaseHandler dbhandler;
	//DisplayPictograms disphandler;
	String textinput;
	EditText inputbox;
	List<Media> pictograms;
	ArrayList<Pictogram> checkoutList = new ArrayList<Pictogram>();
	ArrayList<Pictogram> pictoList  = new ArrayList<Pictogram>();
	long[] output;
	GridView checkoutGrid;
	GridView pictoGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);
		
		checkoutGrid = (GridView) findViewById(R.id.checkout);
		checkoutGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				//checkoutList.removePictogram(position);
				checkoutList.remove(position);
				checkoutGrid.setAdapter(new PictoAdapter2(checkoutList, getApplicationContext()));
				return true;
			}
		});
		
		pictoGrid = (GridView) findViewById(R.id.pictogram_displayer);
		pictoGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				checkoutList.add(pictoList.get(position));
				checkoutGrid.setAdapter(new PictoAdapter2(checkoutList, getApplicationContext()));
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.picto_admin_main, menu);
		return true;
	}
	
	/**
	 * Called when pressing search_button
	 * Depending on search_field, search for pictograms in database
	 * @param view: This must be included for the function to work
	 */
	public void searchForPictogram(View view){
		Spinner searchField = (Spinner)findViewById(R.id.select_search_field);
		String  selectedTag =  searchField.getSelectedItem().toString();
		
		updateErrorMessage(null, 0); // Clear errorMessage
		
		loadPictoIntoGridView(selectedTag);
	}
	
	//TODO: Load pictograms depending on tag
	/**
	 * Loads the pictograms into the gridview depending on the search tag
	 * @param tag: String identifying whether the user searches for tags, name,
	 * category, subcategory or color
	 */
	private void loadPictoIntoGridView(String tag)
	{
		GridView picGrid = (GridView) findViewById(R.id.pictogram_displayer);
		
		//TODO: If no pictograms found call below method
		updateErrorMessage("No such picture in database", R.drawable.action_about);
	}
	
	/**
	 * Updates the errorMessage with appropriate error
	 * @param message: Message to be displayed, null = clear
	 * @param icon: get icon from R.drawable
	 */
	private void updateErrorMessage(String message, int icon)
	{
		TextView errorMessage = (TextView)findViewById(R.id.errorMessage);
		ImageView errorIcon = (ImageView)findViewById(R.id.errorIcon);
		
		errorMessage.setText(message);
		errorIcon.setImageResource(icon);
	}
	
	/**
	 * MenuItem: Exist the current activity and returns to the latest active activity
	 * @param item: This must be included for the function to work 
	 */
	public void returnToLastActivity(MenuItem item) {
		finish();
	}
	
	/**
	 * MenuItem: Sends items from selected gridview to appropriate receiver
	 * @param item: This must be included for the function to work 
	 */
	public void sendContent(MenuItem item) {
		output = getCheckoutList();
		
		Intent data = this.getIntent();
		data.putExtra("checkoutIds", output);
		if(getParent() == null) {
			setResult(Activity.RESULT_OK, data);
		}
		else {
			getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}
	
	/**
	 * MenuItem: Goto admin_category
	 * @param item
	 */
	public void gotoAdminCategory(MenuItem item) {
		Intent intent = new Intent(this, AdminCategory.class);
		startActivity(intent);
	}
	
	
	/**
	 * Assess the checkout gridview and load the pictograms into an ArrayList
	 * @return ArrayList of checkout pictograms
	 */
	public long[] getCheckoutList() {
		long[] checkout = new long[checkoutList.size()];
		int i = 0;
		for(Pictogram p : checkoutList){
			checkout[i] = p.getPictogramID();
			i++;
		}
		
		return checkout;
	}

	public void klimTestMethod(MenuItem item) {
		CategoryHelper helpCat = new CategoryHelper(this);
		Helper help = new Helper(this);
		Profile child = help.profilesHelper.getProfileById(11);
		List<PARROTCategory> list = new ArrayList<PARROTCategory>();
		list = helpCat.getTempCategoriesWithNewPictogram(child);
		
		pictoList = list.get(0).getPictograms();
		pictoGrid.setAdapter(new PictoAdapter2(pictoList, getApplicationContext()));
	}
	
	/**
	 * This should be done by the calling activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Define testList in outer scope
		long[] checkout;
		Bundle extras = data.getExtras();
		/*
		 * Add different cases for different resultCode
		 * Source: http://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
		 */
		if(resultCode == RESULT_OK){
			checkout = extras.getLongArray("checkoutIds");
		}
	}
}