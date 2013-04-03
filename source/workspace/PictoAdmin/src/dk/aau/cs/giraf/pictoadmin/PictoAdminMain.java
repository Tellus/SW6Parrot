package dk.aau.cs.giraf.pictoadmin;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.categorylib.PARROTCategoryOLD;
import dk.aau.cs.giraf.categorylib.PictogramOLD;
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
	private Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
	public void returnToLastActivity(MenuItem item)
	{
		finish();
	}
	
	/**
	 * MenuItem: Sends items from selected gridview to appropriate receiver
	 * @param item: This must be included for the function to work 
	 */
	public void sendContent(MenuItem item)
	{
		ArrayList<ParcelablePictogram> PicPackage = getCheckoutList();
		
		Intent intent = new Intent(this, AdminCategory.class);
		intent.putParcelableArrayListExtra("Pictograms", PicPackage);
		intent.putExtra("myIntent", new Intent(this, PictoAdminMain.class));
		intent.putExtra("childId", 1523);
		
		startActivity(intent);
		
	}
	
	/**
	 * MenuItem: Goto admin_category
	 * @param item
	 */
	public void gotoAdminCategory(MenuItem item)
	{
		Intent intent = new Intent(this, AdminCategory.class);
		startActivity(intent);
	}
	
	
	/**
	 * Assess the checkout gridview and load the pictograms into an ArrayList
	 * @return ArrayList of checkout pictograms
	 */
	public ArrayList<ParcelablePictogram> getCheckoutList() {
		ArrayList<ParcelablePictogram> checkout = new ArrayList<ParcelablePictogram>();
		//TODO: Load pictograms from checkout
		
		/* Example how to add pictogram to checkout */
		ParcelablePictogram pictogram = new ParcelablePictogram("name", "imagepath", "soundpath", "wordpath");
		checkout.add(pictogram);
		
		return checkout;
	}

	public void klimTestMethod(View view) {
		GridView checkoutGrid = (GridView) findViewById(R.id.checkout);
		
		Helper help = new Helper(this);
		Profile child = help.profilesHelper.getProfileById(11);
		
		PARROTCategory    checkoutCategoryNew = new PARROTCategory("TestNew", 0xffa500, null);
		PARROTCategoryOLD checkoutCategoryOld = new PARROTCategoryOLD("TestOld", 0xff0000, null);
		
		// Test images added
		checkoutCategoryOld.addPictogram(new PictogramOLD("test1", "testImagePath", "testSoundPath", "testWordPath"));
		checkoutCategoryOld.addPictogram(new PictogramOLD("test2", "testImagePath", "testSoundPath", "testWordPath"));
		checkoutCategoryOld.addPictogram(new PictogramOLD("test3", "testImagePath", "testSoundPath", "testWordPath"));
		checkoutCategoryOld.addPictogram(new PictogramOLD("test4", "testImagePath", "testSoundPath", "testWordPath"));
		
		checkoutGrid.setAdapter(new KlimAdapter(checkoutCategoryOld, this));
	}
/*	@Override
	public void onClick(View v) {
			// what happens when we click "search"
			textinput = inputbox.getText().toString();
			
			pictograms.addAll(dbhandler.getPictograms(textinput));
			
			//disphandler.updatePictogramDisplayer(pictograms);
	}*/
}