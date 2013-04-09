package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Loader.ForceLoadContentObserver;
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
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;


public class PictoAdminMain extends Activity {
	Button searchbutton;
	DatabaseHandler dbhandler;
	//DisplayPictograms disphandler;
	String textinput;
	EditText inputbox;

	private Bundle extras;
	
	public long childid;
	public long guardianid;
	private Intent girafIntent;
	private Profile profile;
	public List<Pictogram> pictograms;

	PARROTCategory checkoutList;
	long[] output;
	GridView checkout;
	GridView picto_display;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		girafIntent = getIntent();
		
		/* Dette outcommented segment er det korrekte at bruge men vi mangle en ordentligt tablet til at køre det på 
		*  guardianid = girafIntent.getLongExtra("currentGuardianID", -1); 
		*  childid = girafIntent.getLongExtra("currentChildID", -1); */
		
		childid = 12;
		guardianid = 1;
		
		Helper help = new Helper(this);
		profile = help.profilesHelper.getProfileById(childid);
	
		pictograms = PictoFactory.INSTANCE.getAllPictograms(getApplicationContext());
		
		
		setContentView(R.layout.activity_picto_admin_main);
		
		checkout = (GridView) findViewById(R.id.checkout);
		checkout.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				checkoutList.removePictogram(position);
				checkout.setAdapter(new PictoAdapter1(checkoutList, getApplicationContext()));
				return true;
			}
		});
		
		picto_display = (GridView) findViewById(R.id.pictogram_displayer);
		picto_display.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				//Add selected pictogram to checkoutList
				//Update checkout view, by setting adapter
			}
		});
		
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
		EditText textinput = (EditText) findViewById(R.id.text_input);
		String searchterm = textinput.getText().toString();
		
		if(tag.equals("Tags")) {
			// TODO: tags not implemented yet
			updateErrorMessage("You cannot search for tags yet", R.drawable.action_about);
		}
		
		else if(tag.equals("Navn")) {
			
			
		}
		
		//TODO: If no pictograms found call below method
		//updateErrorMessage("No such picture in database", R.drawable.action_about);
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
		output = getCheckoutList();
		TextView display = (TextView) findViewById(R.id.textView1);
		display.setText(checkoutList.getPictogramAtIndex(0).getTextLabel());
		/*ArrayList<KlimPictogram> test = new ArrayList<KlimPictogram>();
		KlimPictogram test1 = new KlimPictogram(getApplicationContext(), "imagepath", "textlabel", "audiopath", 11);
		test.add(test1);*/
		
		Intent data = this.getIntent();
		Intent tester = new Intent(this, AdminCategory.class);
		tester.putExtra("checkoutIds", output);
		data.putExtra("checkoutIds", output);
		//data.putParcelableArrayListExtra("checkoutList", test);
		if(getParent() == null) {
			setResult(Activity.RESULT_OK, data);
		}
		else {
			getParent().setResult(Activity.RESULT_OK, data);
		}
		startActivity(tester);
		//finish();
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
	public long[] getCheckoutList() {
		long[] checkout = new long[checkoutList.getPictograms().size()];
		
		for (int i = 0; i < 2; i++) { 
			checkout[i] = checkoutList.getPictogramAtIndex(i).getPictogramID();
		}
		/* Example how to add pictogram to checkout */
		//ParcelablePictogram pictogram = new ParcelablePictogram("name", "imagepath", "soundpath", "wordpath");
		//checkout.add(pictogram);
		
		return checkout;
	}

	public void klimTestMethod(MenuItem item) {
		checkout = (GridView) findViewById(R.id.checkout);
		
		CategoryHelper helpCat = new CategoryHelper(this);
		Helper help = new Helper(this);
		Profile child = help.profilesHelper.getProfileById(11);
		List<PARROTCategory> list = new ArrayList<PARROTCategory>();
		list = helpCat.getTempCategoriesWithNewPictogram(child);
		
		checkoutList = list.get(0);

		checkout.setAdapter(new PictoAdapter1(checkoutList, this));
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