package dk.aau.cs.giraf.pictoadmin;

import dk.aau.cs.giraf.pictoadmin.PlaceHolder;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class PictoAdminMain extends Activity {
	
	Button searchButton;
	Button placeHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picto_admin_main, menu);
		
		//Setting up the button such that it may be clicked
		searchButton.findViewById(R.id.search_button);
		searchButton.setOnClickListener(searchButtonHandler);
		
		placeHolder.findViewById(R.id.random_placeholder);
		placeHolder.setOnClickListener(placeButtonHolderHandler);
		
		return true;
	}
	
	
	// Button event handlers
	View.OnClickListener searchButtonHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
	
	// Placeholder handler som bare smidder tomme objekter afsted til den activity der kaldte den.
	View.OnClickListener placeButtonHolderHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent placeholderIntent = null;
			PlaceHolder placeholder = new PlaceHolder();
			
			placeholderIntent.putExtra("My class", placeholder);
			startActivity(placeholderIntent);
		}
	};
}
