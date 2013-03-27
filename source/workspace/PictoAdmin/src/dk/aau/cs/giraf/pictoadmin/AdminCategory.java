package dk.aau.cs.giraf.pictoadmin;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AdminCategory extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_category);
		if(getIntent().getParcelableArrayListExtra("Pictograms") != null){
		ArrayList<ParcelablePictogram> receiver = getIntent().getParcelableArrayListExtra("Pictograms");
		TextView test = (TextView) findViewById(R.id.currentChildName);
		test.setText(receiver.get(0).getSoundPath());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin_category, menu);
		return true;
	}
	
	public void returnToLastActivity(MenuItem item)
	{
		finish();
	}

}
