package dk.aau.cs.giraf.parrot;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class PARROTActivity extends Activity {

	private static PARROTProfile parrotUser;
	private static long guardianID;
	private PARROTDataLoader dataLoader;
	private static App app;
	private static Helper help;
	private static Intent girafIntent;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		//These lines get the intent from the launcher //TODO use us when testing with the launcher.
//		girafIntent = getIntent();
//		Helper help = new Helper(this);
//		app = help.appsHelper.getAppByPackageName();
//		guardianID = girafIntent.getExtras().getLong("currentGuardianID");
//		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);



		dataLoader = new PARROTDataLoader(this);
		//parrotUser = dataLoader.loadPARROT();			
		//TODO replace the temp lines with the above line
		//START TEMP LINES
		dataLoader.TESTsaveTestProfile();
		parrotUser.setRights(0, true);
		parrotUser.setRights(1, true);
		parrotUser.setRights(2, true);
		//END TEMP LINES

		/* Here all the Tabs in the system is initialized based on whether or not a user
		 * is allowed to use them. If not they will not be initialized.
		 * We wish not make users aware that there exists functionality that they are not
		 * entitled to.
		 * Remember: Make sure the order of the Taps is consistent with the order of their rights in the
		 * 			 Rights array.
		 */
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);	//TODO figure out what this does

		//Creating a new Tab, setting the text it is to show and construct and attach a Tab Listener to control it.
		Tab tab = actionBar.newTab() 
				.setText(R.string.firstTab)
				.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard",SpeechBoardFragment.class));
		actionBar.addTab(tab);
		
		if (parrotUser.getRights(0) == true)
		{
			tab = actionBar.newTab()
					.setText(R.string.secondTab)
					.setTabListener(new TabListener<ManageCategoryFragment>(this,"manage",ManageCategoryFragment.class));
			actionBar.addTab(tab);
		}
		if (parrotUser.getRights(1) == true) 
		{
			tab = actionBar.newTab()
					.setText(R.string.thirdTab)
					.setTabListener(new TabListener<OptionsFragment>(this,"options",OptionsFragment.class));
			actionBar.addTab(tab);
		}
//		if (parrotUser.getRights(2) == true)//TODO put me back in when there's something for me to do.
//		{
//			tab = actionBar.newTab()
//					.setText(R.string.fourthTab)
//					.setTabListener(new TabListener<OptionsFragment>(this,"camera",OptionsFragment.class)); TODO: Find new Fragments to attach to.
//			actionBar.addTab(tab);
//		}

	}
	@Override
	protected void onPause() {
		AudioPlayer.close();
		dataLoader.saveProfile(getUser());
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