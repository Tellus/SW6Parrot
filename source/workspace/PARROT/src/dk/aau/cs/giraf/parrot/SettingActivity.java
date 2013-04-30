package dk.aau.cs.giraf.parrot;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import dk.aau.cs.giraf.parrot.PARROTProfile.PictogramSize;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @author SW605f13-PARROT
 * In this Activity class the changeable settings of the PARROTActivity can be changed.
 */
public class SettingActivity extends Activity  {
	private PARROTProfile user;
	private PARROTDataLoader dataloader;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_setting);
		Log.v("SettingActivity","in settings oncreate");
		user = PARROTActivity.getUser();
		Log.v("SettingActivity","in settings user read");
		dataloader = new PARROTDataLoader(this, false);
		Log.v("SettingActivity","in settings oncreate done");
		        
	}
	
	/**
	 * A menu is created upon creation
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_setting, menu);
				
		return true;
	}
	
	/**
	 * Selector for what happens when a menu Item is clicked
	 */
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		switch(item.getItemId()){
		case R.id.goToParrot:
			
			returnToParrot();
			break;
		}
		return true;
	}
	
	/**
	 * finish this activity and return to PARROTActivity
	 */
	public void returnToParrot()
	{
		finish();
	}
	
	/**
	 * This is called when exitting the activity 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		dataloader.saveChanges(user);
		PARROTActivity.setUser(user);
		
	}
		
	/**
	 * This is called when upon returning to the activity or after onCreate.
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		//Setup of the spinner with is the selector of how many of boxes the child can handle in the sentenceboard
		Spinner spinner = (Spinner) findViewById(R.id.spinnerNoOfsentence);
		// Create an ArrayAdapter using the string array and a default spinner layout
		Integer[] items = new Integer[]{1,2,3,4,5,6};
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		//get the current Settings
        readTheCurrentData();
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
			public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
                user.setNumberOfSentencePictograms((Integer)parent.getItemAtPosition(pos));
                
            }

            @Override
			public void onNothingSelected(AdapterView<?> parent) {
                // do nothing   
            }        
        }); 
    }
	
	/**
	 * get the current Settings and show it in the UI
	 */
	private void readTheCurrentData() {
		
		int noOfPlacesInSentenceboard = user.getNumberOfSentencePictograms();
		boolean showText = user.getShowText();
		PARROTProfile.PictogramSize pictogramSize = user.getPictogramSize();
		
		if(pictogramSize == PARROTProfile.PictogramSize.MEDIUM)
		{ 
			RadioButton radioB = (RadioButton) findViewById(R.id.mediumPicRadioButton);
			radioB.setChecked(true);
		}
		else if(pictogramSize == PARROTProfile.PictogramSize.LARGE)
		{
			RadioButton radioB = (RadioButton) findViewById(R.id.largePicRadioButton);
			radioB.setChecked(true);
		}

		Spinner spinner = (Spinner) findViewById(R.id.spinnerNoOfsentence);
		spinner.setSelection(noOfPlacesInSentenceboard-1,true);
		
		CheckBox checkBox  = (CheckBox) findViewById(R.id.checkBoxShowText);
		if(showText)
		{
			
			checkBox.setChecked(true);
		}
		else
		{
			checkBox.setChecked(false);
		}
	}
	
	/**
	 * When buttonChangeSentenceColor is clicked this happens, change the color of the sentenceboard
	 * @param view, the buttonChangeSentenceColor
	 */
	public void onSentenceboardColorChanged(View view)
	{
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, 
				user.getSentenceBoardColor(),
				new OnAmbilWarnaListener() {
			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
			}

			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				user.setSentenceBoardColor(color);
				Log.v("MessageParrot", "color: " + color);
			}
		});
		dialog.show();

	}
	
	/**
	 * When mediumPicRadioButton or largePicRadioButton is clicked, this happens. 
	 * Change pictogram size.
	 * @param view, mediumPicRadioButton or largePicRadioButton
	 */
	public void onSizePictogramChanged(View view)
	{
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.mediumPicRadioButton:
	            if (checked)
	                user.setPictogramSize(PictogramSize.MEDIUM);
	            break;
	        case R.id.largePicRadioButton:
	            if (checked)
	            	user.setPictogramSize(PictogramSize.LARGE);
	            break;
	    }
	}
	
	/**
	 * When checkBoxShowText is clicked, this happens.
	 * change whether a child can handle text or not. 
	 * @param view
	 */
	public void onShowTextChanged(View view)
	{
		 // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    if (checked)
	    {
	    	user.setShowText(true);
	    }         
	    else
	    {
	    	user.setShowText(false);
	    }
	}


}
