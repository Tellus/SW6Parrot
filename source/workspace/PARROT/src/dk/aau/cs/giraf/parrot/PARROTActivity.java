package dk.aau.cs.giraf.parrot;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @author SW605f13-PARROT and PARROT spring 2012.
 *	This is the main Activity Class in Parrot.
 */
public class PARROTActivity extends Activity {

	private static PARROTProfile parrotUser;
	private static long guardianID;
	private static long childID;
	private PARROTDataLoader dataLoader;
	private static App app;
	private static Helper help;
	private static Intent girafIntent;
	

	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);
		
		//These lines get the intent from the launcher //TODO use us when testing with the launcher.
		/*girafIntent = getIntent();
		guardianID = girafIntent.getLongExtra("currentGuardianID", -1);
		childID = girafIntent.getLongExtra("currentChildID", -1);*/
		Helper help = new Helper(this);
		app = help.appsHelper.getAppByPackageName();
		/*don't delete this is for lisbeth and anders when running on our own device*/
		guardianID = 1;
		childID=12;
		
		
		if(guardianID == -1 )
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("guardianID");
			alertDialog.setMessage("Could not find guardian.");
			alertDialog.show();
		}
		else
		{ 	
			dataLoader = new PARROTDataLoader(this, true);
			
			parrotUser = dataLoader.loadProfile(childID, app.getId());
			Log.v("No in sentence", ""+ parrotUser.getNumberOfSentencePictograms());
			Log.v("MessageParrot", "returned");	
			if(parrotUser != null)
			{
						
				/* Here all the Tabs in the system is initialized based on whether or not a user
				 * is allowed to use them. If not they will not be initialized.
				 * We wish not make users aware that there exists functionality that they are not
				 * entitled to.
				 * Remember: Make sure the order of the Taps is consistent with the order of their rights in the
				 * 			 Rights array.
				 */
				ActionBar actionBar = getActionBar();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				//Creating a new Tab, setting the text it is to show and construct and attach a Tab Listener to control it.
				Tab tab = actionBar.newTab() 
						.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard",SpeechBoardFragment.class));
				tab.select();
			}
		}
	}
	
	/**
	 * This is called when exitting the activity 
	 */
	@Override
	protected void onPause() {
		//AudioPlayer.close();
		super.onPause();
	}
	
	/**
	 * This is called when upon returning to the activity or after onCreate
	 */
	@Override
	protected void onResume() {
		//AudioPlayer.open();
		super.onResume();
		
	}
	
	/**
	 * A menu is created upon creation
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parrot_settings, menu);
				
		return true;
	}
	
	/**
	 * Selector for what happens when a menu Item is clicked
	 */
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		switch(item.getItemId()){
		case R.id.clearBoard:
			SpeechBoardFragment.clearSentenceboard(this);
			break;
		case R.id.goToLauncher:
			returnToLauncher();
			break;
		case R.id.goToSettings:	
			goToSettings();
			break;
		}
		return true;
	}
	
	/**
	 * this activating a new  Activity class which handles the settings which can be changed. 
	 */
	public void goToSettings(){
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}
	/**
	 * This exits the PARROTActivity and should return to the giraf-launcher. 
	 */
	public void returnToLauncher()
	{
		finish();
	}
	
	/**
	 * @return the child's user profile.
	 */
	public static PARROTProfile getUser()
	{
		return parrotUser;
	}
	/**
	 * set the current child user profile
	 * @param user, a PARROTProfile that is a childs profile.
	 */
	public static void setUser(PARROTProfile user) {
		parrotUser = user;
	}
	/** 
	 * @return the guardian/parents id.
	 */
	public static long getGuardianID() {
		return guardianID;
	}
	
	/**
	 * @return an instance of Helper.
	 */
	public static Helper getHelp() {
		return help;
	}
	/**
	 * 
	 * @return instance of App with this apps data
	 */
	public static App getApp()
	{
		return app;
	}
	
	/** 
	 * @return The Intent received from the Launcher
	 */
	public static Intent getGirafIntent() {
		return girafIntent;
	}
	

}