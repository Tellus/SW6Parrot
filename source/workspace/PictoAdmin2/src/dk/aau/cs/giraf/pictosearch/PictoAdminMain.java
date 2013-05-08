package dk.aau.cs.giraf.pictosearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import dk.aau.cs.giraf.categorylib.CategoryHelper;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;

/**
 * 
 * @author Christian Klim
 *
 */
public class PictoAdminMain extends Activity {
	private long    childId = 12;

	private ArrayList<Pictogram> checkoutList = new ArrayList<Pictogram>();
	private ArrayList<Pictogram> pictoList    = new ArrayList<Pictogram>();
	private ArrayList<Pictogram> searchlist   = new ArrayList<Pictogram>();
	
	private GridView checkoutGrid;
	private GridView pictoGrid;
	
	private CategoryHelper      catHelp;
	
	/*
	 *  Request from another group. It should be possible to only send one pictogram,
	 *  and therefore only display one pictogram in the checkout list. isSingle is used
	 *  to store information. Default = false, so multiple pictoList are possible.
	 *  If the intent that started the search contain the extra "single", isSingle is set
	 *  to true
	 */
	private boolean isSingle = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);
		
		catHelp = new CategoryHelper(this);
		
		getProfile();
		getPurpose();
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.picto_admin_main, menu);
		return true;
	}
	
	/**
	 * Makes sure, that when the application is suspended it is instead destroyed
	 */
	@Override
	protected void onPause() {
		super.onDestroy();
	}
	
	/**
	 * Override the function of the back button. Does the same as sendContent
	 */
	@Override
	public void onBackPressed() {
		sendContent(getCurrentFocus());
	}
	
	/**
	 * Get the current child id if information is send by calling application
	 * Otherwise the standard value of childId is 12
	 */
	public void getProfile() {
		if(getIntent().hasExtra("currentChildID")){
			childId = getIntent().getLongExtra("currentChildID", -1);
		}
	}
	
	/**
	 * Get the purpose from the calling application and displays a message to the user
	 * describing what to do in the application and how to finish
	 */
	private void getPurpose() {
		if(getIntent().hasExtra("purpose")){
			if(getIntent().getStringExtra("purpose").equals("single")){
				isSingle = true;
				MessageDialogFragment message = new MessageDialogFragment("V�lg et pictogram og afslut med Send.");
				message.show(getFragmentManager(), "choosingMessageSingle");
				updateErrorMessage("V�lg et pictogram og afslut med Send", 0);
			}
			else if(getIntent().getStringExtra("purpose").equals("multi")){
				isSingle = false;
				MessageDialogFragment message = new MessageDialogFragment("V�lg pictogrammer og afslut med Send.");
				message.show(getFragmentManager(), "choosingMessageMulti");
				updateErrorMessage("V�lg pictogrammer og afslut med Send", 0);
			}
			else if(getIntent().getStringExtra("purpose").equals("CAT")){
				MessageDialogFragment message = new MessageDialogFragment("V�lg pictogrammer, som skal tilf�jes til kategori og afslut med Send.");
				message.show(getFragmentManager(), "CATaccess");
				updateErrorMessage("V�lg pictogrammer, som skal tilf�jes til kategori og afslut med Send", 0);
			}
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	public void getAllPictograms() {
		List<Pictogram> pictotemp = PictoFactory.INSTANCE.getAllPictograms(getApplicationContext());
		pictoList = new ArrayList<Pictogram>();
		
		for (Pictogram p : pictotemp) {
			pictoList.add(p);
		}
	}
	
	/**
	 * Called when pressing search_button
	 * Depending on search_field, search for pictoList in database
	 * @param view: This must be included for the function to work
	 */
	public void searchForPictogram(View view){
		Spinner searchField = (Spinner)findViewById(R.id.select_search_field);
		String  selectedTag =  searchField.getSelectedItem().toString();
		
		updateErrorMessage(null, 0); // Clear errorMessage
		
		loadPictogramIntoGridView(selectedTag);
	}
	
	/**
	 * Updates the errorMessage with appropriate error
	 * @param message: Message to be displayed, null = clear
	 * @param icon: get icon from R.drawable
	 */
	private void updateErrorMessage(String message, int icon)
	{
		TextView  errorMessage = (TextView)  findViewById(R.id.errorMessage);
		ImageView errorIcon    = (ImageView) findViewById(R.id.errorIcon);
		
		errorMessage.setText(message);
		errorIcon.setImageResource(icon);
	}
	
	/**
	 * Loads the pictoList into the gridview depending on the search tag
	 * @param tag: String identifying whether the user searches for tags, name,
	 * category, sub-category or color
	 */
	private void loadPictogramIntoGridView(String tag)
	{	
		GridView picgrid = (GridView) findViewById(R.id.pictogram_displayer);		
		EditText searchterm = (EditText) findViewById(R.id.text_input);
		searchlist.clear();
		String[] splitinput = searchterm.getText().toString().replaceAll("\\s", "").split(",");
		
		if(tag.equals("Alt")) {
			String pictoname;
			
			for (Pictogram p : pictoList){
				pictoname = p.getTextLabel().toLowerCase().replace("\\s", "");
				
				for(int i = 0; i < splitinput.length; i++ ){
					if(searchMatcher(pictoname, splitinput[i])){
						searchlist.add(p);
					}
				}
			}
		}
		else if(tag.equals("Tags")) {
			// TODO: tags not implemented yet
			updateErrorMessage("You cannot search for tags yet", 0);
		}
		else if(tag.equals("Navn")) 
		{
			String pictoname;
			
			for (Pictogram p : pictoList) {
				pictoname = p.getTextLabel().toLowerCase().replace("\\s", "");
				
				for(int i = 0; i < splitinput.length; i++ ){
					if(searchMatcher(pictoname, splitinput[i])){
						searchlist.add(p);
					}
				}
			}
		}
		else if(tag.equals("Kategori"))
		{
			ArrayList<PARROTCategory> childcat = catHelp.getChildsCategories(childId);
			
			for(PARROTCategory pc : childcat){
				for(int i = 0; i < splitinput.length; i++){
					if(searchMatcher(pc.getCategoryName().toLowerCase().replaceAll("\\s", ""),splitinput[i])){
						searchlist.add(pc.getIcon());
					}
				}
			}
		}
		else if(tag.equals("Under kategori"))
		{
			ArrayList<PARROTCategory> childcat = catHelp.getChildsCategories(childId);
			
			for(PARROTCategory pc : childcat){
				ArrayList<PARROTCategory> catsubcat = pc.getSubCategories();
				
				for(PARROTCategory subc : catsubcat){
					for(int i = 0; i < splitinput.length; i++){
						if(searchMatcher(subc.getCategoryName().toLowerCase().replaceAll("\\s", ""),splitinput[i])){
							searchlist.add(subc.getIcon());
						}
					}
				}
			}
		}
		
		ArrayList<SearchNode> sortedsearchlist = new ArrayList<SearchNode>();
		
		int value;
		for(Pictogram p : searchlist){
			value = calculateValueOfPictogram(p, splitinput);
			SearchNode sn = new SearchNode(p, value);
			sortedsearchlist.add(sn);
		}
		
		Collections.sort(sortedsearchlist, new Comparator<SearchNode>() {
			@Override
	        public int compare(SearchNode o1, SearchNode o2) 
	        {
				if(o1.searchvalue > o2.searchvalue){
					return 1;
				}
				else if(o1.searchvalue == o2.searchvalue){
					return 0;
				}
				else{
					return -1;
				}
	        }
	    });
		
		searchlist.clear();
		
		for(SearchNode sn : sortedsearchlist){
			searchlist.add(sn.pic);
		}
		
		if(searchlist.size() > 0){
			picgrid.setAdapter(new PictoAdapter(searchlist, this));
		}
		else{
			updateErrorMessage("No such picture in database", R.drawable.action_about);
		}
	}
	
	private boolean searchMatcher(String pictoname, String searchinput) {
		// Mulighed for at g�re s�gefunktionen endnu mere intelligent
		// Made so that it is possible to make search function more intelligent
		
		if(pictoname.contains(searchinput)) {
			return true;
		} 
		else {
			return false;
		}
	}
	
	// Used in loadPictogramIntoGridview to
	//TODO: INSERT description Jacob
	private static int calculateValueOfPictogram(Pictogram p, String[] searchterm)
    {
    	int searchvalue = 0;
    	
    	for(String s : searchterm){
    		s.toLowerCase().replaceAll("\\s", "");
    		
    		if(p.getTextLabel().toLowerCase().replaceAll("\\s", "").equals(s)){
    			searchvalue = 100;
    		}
    		
    		String temps = s;
    		
    		for(int i = 0; i < s.length(); i++){
    			if(p.getTextLabel().toLowerCase().replaceAll("\\s", "").contains(temps) || temps.contains(p.getTextLabel().toLowerCase().replaceAll("\\s", ""))){
    				searchvalue++;
    			}
    			
    			temps.substring(0, temps.length() - 1);
    		}
    	}
    	
    	return searchvalue;
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
	
	public void clearSearchField(View view) {
		EditText searchField = (EditText) findViewById(R.id.text_input);
		searchField.setText(null);
	}
	
	public void clearCheckoutList(View view) {
		checkoutList.clear();
		checkoutGrid.setAdapter(new PictoAdapter(checkoutList, this));
	}
	
	/**
	 * MenuItem: Sends pictogram ids from checkoutlist to appropriate calling application 
	 */	
	public void sendContent(View view) {
		long[] output = getCheckoutList();
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
	
	public void callAndersSupport(MenuItem item) {
		MessageDialogFragment message = new MessageDialogFragment("Call: +45 24 26 93 98 for tech support");
		message.show(getFragmentManager(), "callTechSupport");
	}
}