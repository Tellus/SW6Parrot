package dk.aau.cs.giraf.pictoadmin;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import dk.aau.cs.giraf.oasis.lib.models.Media;

public class PictoAdminMain extends Activity {
	Button searchbutton;
	DatabaseHandler dbhandler;
	//DisplayPictograms disphandler;
	String textinput;
	EditText inputbox;
	List<Media> pictograms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);
		
		inputbox = (EditText) findViewById(R.id.text_input);
		
		//Setting up the button such that it may be clicked
		searchbutton.findViewById(R.id.search_button);
		searchbutton.setOnClickListener(searchButtonHandler);
		
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picto_admin_main, menu);
		
		//Setting up the button such that it may be clicked
		searchButton.findViewById(R.id.search_button);
		searchButton.setOnClickListener(searchButtonHandler);
		
		return true;
	}*/
	
	
	// Button event handlers
	View.OnClickListener searchButtonHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// what happens when we click "search"
			textinput = inputbox.getText().toString();
			
			pictograms.addAll(dbhandler.getPictograms(textinput));
			
			//disphandler.updatePictogramDisplayer(pictograms);
		}
	};
}
