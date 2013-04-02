package dk.aau.cs.giraf.parrot;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import dk.aau.cs.giraf.parrot.PARROTProfile.PictogramSize;
import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData.Item;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SettingActivity extends Activity  {
	private PARROTProfile user;
	private PARROTDataLoader dataloader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_setting);
		
		user = PARROTActivity.getUser();
		dataloader = new PARROTDataLoader(this);

		        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_setting, menu);
				
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		switch(item.getItemId()){
		case R.id.goToParrot:
			returnToParrot();
			break;
		}
		return true;
	}
	public void returnToParrot()
	{
		finish();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		dataloader.saveChanges(user);
		
	}
		

	@Override
	protected void onResume() {
		super.onResume();
		Spinner spinner = (Spinner) findViewById(R.id.spinnerNoOfsentence);
		// Create an ArrayAdapter using the string array and a default spinner layout
		Integer[] items = new Integer[]{1,2,3,4,5,6};
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        readTheCurrentData();

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
                user.setNumberOfSentencePictograms((Integer)parent.getItemAtPosition(pos));
                
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing   
            }        
        }); 
    }
	
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
		
		if(showText)
		{
			CheckBox checkBox  = (CheckBox) findViewById(R.id.checkBoxShowText);
			checkBox.setChecked(true);
		}
	}
	
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
