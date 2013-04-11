package dk.aau.cs.giraf.pictoadmin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
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

	public long childid;
	public long guardianid;
	private Profile profile;
	public List<Pictogram> pictotemp; // Vi bruger denne kun til at gette elementer fra pictofactory som returner list
	public ArrayList<Pictogram> pictograms; // Den egentlige liste af pictogrammer vi ønsker at bruge

	PARROTCategory checkoutList;
	long[] output;
	GridView checkout;
	GridView picto_display;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);

		//girafIntent = getIntent();
		getAllPictograms();
		getProfile();
		
		
		checkout = (GridView) findViewById(R.id.checkout);
		checkout.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				checkoutList.removePictogram(position);
				checkout.setAdapter(new PictoAdapter2(checkoutList.getPictograms(), getApplicationContext()));
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
	
	public void getProfile() {
		/* Dette outcommented segment er det korrekte at bruge men vi mangle en ordentligt tablet til at køre det på 
		*  guardianid = girafIntent.getLongExtra("currentGuardianID", -1); 
		*  childid = girafIntent.getLongExtra("currentChildID", -1); 
		*  Indtil da bliver børn og guardians ID hardcoded */
		
		childid = 12;
		guardianid = 1;

		Helper help = new Helper(this);
		profile = help.profilesHelper.getProfileById(childid);
	}
	
	public void getAllPictograms() {
		pictotemp = PictoFactory.INSTANCE.getAllPictograms(getApplicationContext());
		pictograms = new ArrayList<Pictogram>();
		
		//Manuel casting fra  list til ArrayList
		for (Pictogram p : pictotemp) {
			pictograms.add(p);
		}
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
		GridView picgrid = (GridView) findViewById(R.id.pictogram_displayer);		
		EditText searchterm = (EditText) findViewById(R.id.text_input);
		
		if(tag.equals("Tags")) {
			// TODO: tags not implemented yet
			updateErrorMessage("You cannot search for tags yet", R.drawable.action_about);
		}
		
		else if(tag.equals("Navn")) {
			ArrayList<Pictogram> searchlist = new ArrayList<Pictogram>();
			
			String pictoname;
			String input = searchterm.getText().toString();
			
			for (Pictogram p : pictograms) {
				pictoname = p.getTextLabel();
				if(pictoname.equals(input) || searchMatcher(pictoname, input)) {
					searchlist.add(p);
				}
			}
			
			//searchlist = sortList(searchlist, input);
			
			PictoAdapter2 picto = new PictoAdapter2(searchlist, this);
			picgrid.setAdapter(picto);
		}
		
		//TODO: If no pictograms found call below method
		//updateErrorMessage("No such picture in database", R.drawable.action_about);
	}
	
	
	private boolean searchMatcher(String pictoname, String searchinput) {
		// Mulighed for at gøre søgefunktionen endnu mere intelligent.
		Boolean result = true;
		
		if(pictoname.contains(searchinput)) {
			result = true;
		} else {
			result = false;
		}
		
		
		return result;
	}
	
	private ArrayList<Pictogram> sortList(ArrayList<Pictogram> listtobesorted, String searchterm) {
		ArrayList<Pictogram> sortedlist = new ArrayList<Pictogram>();
		
		char [] letters;
		
		int [] weight = null; // Bruges til at gemme vægten af alle strenge i listen
		int [][] index = null; // Bruges til at gemme indexet for læste karakterer for den relative streng
		int i = 0; // Counter til vægtningen
		int j = 0; // Counter til at huske index for sidste læste karakter relativt til dens streng
		
		for (Pictogram p : listtobesorted) {

			letters = p.getTextLabel().toCharArray();
			
			for (char c : letters) {
				if(searchterm.indexOf(c) != -1) { // Hvis bogstavet findes i søgestrengen skal vi gemme indexet og øge vægtningen
					weight[i] += 1;
					index[i][j] = c;
					j++;
				}
			}
			
			i++;
		}
		
		
		/*else if (letters.equals(searchterm)) { // Hvis ordet matcher lige præcist søgetermen så får den en giga-weight.
			weight[i] += 50;
			break;
		}*/
		
		return listtobesorted;
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
	public void gotoAdminCategory(MenuItem item) {
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

		checkout.setAdapter(new PictoAdapter2(checkoutList.getPictograms(), this));
	}
	
	/**
	 * This should be done by the calling activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bundle extras = data.getExtras();
		/*
		 * Add different cases for different resultCode
		 * Source: http://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
		 */
		if(resultCode == RESULT_OK){
			extras.getLongArray("checkoutIds");
		}
	}
}