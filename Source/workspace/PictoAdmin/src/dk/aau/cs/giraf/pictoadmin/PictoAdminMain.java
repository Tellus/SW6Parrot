package dk.aau.cs.giraf.pictoadmin;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class PictoAdminMain extends Activity {
	
	Button searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picto_admin_main);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picto_admin_main, menu);
		
		//Setting up the button such that it may be clicked
		searchButton.findViewById(R.id.search_button);
		searchButton.setOnClickListener(searchButtonHandler);
		
		return true;
	}
	
	
	// Button event handlers
	View.OnClickListener searchButtonHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};*/
}
