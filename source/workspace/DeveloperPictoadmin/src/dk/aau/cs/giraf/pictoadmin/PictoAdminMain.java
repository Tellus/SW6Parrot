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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;


public class PictoAdminMain extends Activity {
	private DatabaseHandler dbhandler;
	private String textinput;
	private EditText inputbox;

	private ArrayList<Pictogram> checkoutList = new ArrayList<Pictogram>();
	private ArrayList<Pictogram> pictoList  = new ArrayList<Pictogram>();
	private ArrayList<Pictogram> searchlist = new ArrayList<Pictogram>();

	private long childId;
	private long guardianId;
	private Profile child;
	private Profile guardian;
	private ArrayList<Pictogram> pictograms; // Den egentlige liste af pictogrammer vi ønsker at bruge

	long[] output;
	private GridView checkoutGrid;
	private GridView pictoGrid;

	private ImageButton sendButton;
	private ImageButton searchbutton;
	
	private CheckoutGridHandler cgHandler;
	
	private Bundle extras;
	/*
	 *  Request from another group. It should be possible to only send one pictogram,
	 *  and therefore only display one pictogram in the checkout list. isSingle is used
	 *  to store information. Default = false, so multiple pictograms are possible.
	 *  If the intent that started the search contain the extra "single", isSingle is set
	 *  to true
	 */
	private boolean isSingle = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);
		
		extras = getIntent().getExtras();
		if(extras != null){
			getProfile();
			getPurpose();
		}
		else{
			childId = 12;
			guardianId = 1;
		}
		
		Helper help = new Helper(this);
		
		child    = help.profilesHelper.getProfileById(childId);
		guardian = help.profilesHelper.getProfileById(guardianId);
		
		getAllPictograms();
		
		checkoutGrid = (GridView) findViewById(R.id.checkout);
		checkoutGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				checkoutList.remove(position);
				checkoutGrid.setAdapter(new PictoAdapter(checkoutList, getApplicationContext()));
				return true;
			}
		});
		
		pictoGrid = (GridView) findViewById(R.id.pictogram_displayer);
		pictoGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				// if single pictogram requested, only one pictogram is displayed in checkout
				if(isSingle){
					checkoutList.clear();
				}
				checkoutList.add(searchlist.get(position));
				checkoutGrid.setAdapter(new PictoAdapter(checkoutList, getApplicationContext()));
			}
		});
		
		cgHandler = new CheckoutGridHandler(checkoutList);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.picto_admin_main, menu);
		return true;
	}
	
	private void getPurpose() {
		if(getIntent().hasExtra("purpose")){
			if(getIntent().getStringExtra("purpose").equals("single")){
				isSingle = true;
				MessageDialogFragment message = new MessageDialogFragment("Vælg et pictogram og afslut med Send.");
				message.show(getFragmentManager(), "choosingMessage");
			}
			else if(getIntent().getStringExtra("purpose").equals("multi")){
				isSingle = false;
				MessageDialogFragment message = new MessageDialogFragment("Vælg et eller flere pictogrammer og afslut med Send.");
				message.show(getFragmentManager(), "choosingMessage");
			}
			else if(getIntent().getStringExtra("purpose").equals("CAT")){
				MessageDialogFragment message = new MessageDialogFragment("Vælg pictogrammer, som skal tilføjes til kategori og afslut med Send.");
				message.show(getFragmentManager(), "CATaccess");
			}
		}
	}
	
	public void getProfile() {
		childId = extras.getLong("currentChildID");
		guardianId = extras.getLong("currentGuardianID");
	}
	
	@SuppressWarnings("static-access")
	public void getAllPictograms() {
		List<Pictogram> pictotemp = PictoFactory.INSTANCE.getAllPictograms(getApplicationContext());
		pictograms = new ArrayList<Pictogram>();
		
		for (Pictogram p : pictotemp) {
			pictograms.add(p);
		}
	}
	
	/**
	 * Loads the pictograms into the gridview depending on the search tag
	 * @param tag: String identifying whether the user searches for tags, name,
	 * category, subcategory or color
	 */
	private void loadPictoIntoGridView(String tag)
	{	
		GridView picgrid = (GridView) findViewById(R.id.pictogram_displayer);		
		EditText searchterm = (EditText) findViewById(R.id.text_input);
		searchlist.clear();
		
		if(tag.equals("Alt")){
			String pictoname;
			String input = searchterm.getText().toString();
			
			for(Pictogram p : pictograms){
				pictoname = p.getTextLabel();
				if(pictoname.equals(input) || searchMatcher(pictoname, input)) {
					searchlist.add(p);
				}
			}
		}
		else if(tag.equals("Tags")) {
			// TODO: tags not implemented yet
			updateErrorMessage("You cannot search for tags yet", R.drawable.action_about);
		}
		else if(tag.equals("Navn")) {
			String pictoname;
			String input = searchterm.getText().toString();
			
			for (Pictogram p : pictograms) {
				pictoname = p.getTextLabel();
				if(pictoname.equals(input) || searchMatcher(pictoname, input)) {
					searchlist.add(p);
				}
			}
		}
		//searchlist = sortList(searchlist, input);
		
		PictoAdapter picto = new PictoAdapter(searchlist, this);
		picgrid.setAdapter(picto);
		
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
			j = 0;
			letters = p.getTextLabel().toCharArray();
			
			for (char c : letters) {
				if(searchterm.indexOf(c) != -1) { // Hvis bogstavet findes i søgestrengen skal vi gemme indexet og øge vægtningen
					
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
	
	public void clearSearchField(View view) {
		EditText searchField = (EditText) findViewById(R.id.text_input);
		searchField.setText(null);
	}
	
	public void clearCheckoutList(View view) {
		checkoutList.clear();
		checkoutGrid.setAdapter(new PictoAdapter(checkoutList, this));
	}
	
	/**
	 * MenuItem: Sends items from selected gridview to appropriate receiver
	 * @param item: This must be included for the function to work 
	 */	
	public void sendContent(View view) {
		output = cgHandler.getCheckoutList();
		
		Intent data = this.getIntent();
		if(isSingle && output.length > 0){
			data.putExtra("checkoutId", output[0]);
		}
		else{
			data.putExtra("checkoutIds", output);
		}
		
		if(getParent() == null) {
			setResult(Activity.RESULT_OK, data);
		}
		else {
			getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}
	
	public void callAndersSupport(MenuItem item) {
		MessageDialogFragment message = new MessageDialogFragment("Call: +45 24 26 93 98 for tech support");
		message.show(getFragmentManager(), "callTechSupport");
	}
}