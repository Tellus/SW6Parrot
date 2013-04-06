package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AdminCategory extends Activity {
	private String childName;
	private Bundle extras;
	private ArrayList<ParcelablePictogram> pictograms;
	private String testString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_category);
		
		extras = getIntent().getExtras();
		if(extras != null){
			getAllExtras();
		}
			
		TextView displayChild = (TextView) findViewById(R.id.currentChildName);
		displayChild.setText(testString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_category, menu);
		return true;
	}
	
	private void getAllExtras() {
		if(getIntent().hasExtra("childId")){
			childName = extras.get("childId").toString();
		}
		if(getIntent().hasExtra("Pictograms")){
			pictograms = getIntent().getParcelableArrayListExtra("Pictograms");
		}
		if(getIntent().hasExtra("sendTest")) {
			testString = (String) extras.get("sendTest");
		}
	}
	
	public void returnToCaller(MenuItem item) {
		Intent data = this.getIntent();
		data.putParcelableArrayListExtra("testParcel", pictograms);
		if(getParent() == null){
			setResult(Activity.RESULT_OK, data);
		}
		else{
			getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}
}
