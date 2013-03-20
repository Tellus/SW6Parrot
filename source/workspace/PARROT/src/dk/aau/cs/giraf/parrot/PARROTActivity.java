package dk.aau.cs.giraf.parrot;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


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
		girafIntent = getIntent();
		Helper help = new Helper(this);
		app = help.appsHelper.getAppByPackageName();
		guardianID = girafIntent.getLongExtra("currentGuardianID", -1);
		childID = girafIntent.getLongExtra("currentChildID", -1);
		
		if(guardianID == -1 )
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("guardianID");
			alertDialog.setMessage("Could not find guardian.");
			alertDialog.show();
		}
		else
		{ 
			//this is new testData being written to database, such that we have some categories and colors
			//BEGIN testData
			Setting<String, String, String> setting = help.appsHelper.getSettingByIds(app.getId(), childID);
			//this write test data to database
			if(setting.containsKey("ColourSettings") == false)
			{
				TestData test = new TestData(this);
				test.TESTsaveTestProfile();		
			}
			//END testData
			
			
			dataLoader = new PARROTDataLoader(this);
			
			//If an error occur parrotUser is null which must be cached  
			try
			{
				parrotUser = dataLoader.loadProfile(childID, app.getId());	
			}
			catch(NullPointerException e)
			{
				long id = girafIntent.getLongExtra("currentChildID", -1);
				AlertDialog alertDialog;
				alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("null returned");
				alertDialog.setMessage("childID "+ id);
				alertDialog.show();
				Log.e("MessageParrot", "error null was returned, childID: " + id);
				return;
			}
	
			/* Here all the Tabs in the system is initialized based on whether or not a user
			 * is allowed to use them. If not they will not be initialized.
			 * We wish not make users aware that there exists functionality that they are not
			 * entitled to.
			 * Remember: Make sure the order of the Taps is consistent with the order of their rights in the
			 * 			 Rights array.
			 */
			
			ActionBar actionBar = getActionBar();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			//actionBar.setDisplayShowTitleEnabled(false);	//TODO figure out what this does
	
			//Creating a new Tab, setting the text it is to show and construct and attach a Tab Listener to control it.
			Tab tab = actionBar.newTab() 
					.setText(R.string.firstTab)
					.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard",SpeechBoardFragment.class));
			actionBar.addTab(tab);
		}


	}
	
	@Override
	protected void onPause() {
		AudioPlayer.close();
		if(guardianID != -1)
		{
			dataLoader.saveProfile(getUser());
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		AudioPlayer.open();
		super.onResume();
	}

	public static PARROTProfile getUser()
	{
		return parrotUser;
	}
	public static void setUser(PARROTProfile user) {
		parrotUser = user;
	}
	public static long getGuardianID() {
		return guardianID;
	}
	public static Helper getHelp() {
		return help;
	}
	
	public static App getApp()
	{
		return app;
	}
	public static Intent getGirafIntent() {
		return girafIntent;
	}
	
}