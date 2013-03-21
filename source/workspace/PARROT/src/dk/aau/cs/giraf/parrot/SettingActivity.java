package dk.aau.cs.giraf.parrot;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingActivity extends Activity {
	private PARROTProfile user;
	private App app;
	private Helper help;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		user = PARROTActivity.getUser();
		app = PARROTActivity.getApp();
		help = new Helper(this);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinnerNoOfsentence);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.SentenceboardNoChoses, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_setting, menu);
		return true;
	}

	public void saveChanges()
	{
		
		Log.v("MessageParrot", "Begin saving in save Profil");
		Setting<String, String, String> profileSetting = new Setting<String, String, String>();
		//save profile settings
		saveSettings(profileSetting, user);

		//after all the changes are made, we save the settings to the database
		app.setSettings(profileSetting);
		Profile prof = help.profilesHelper.getProfileById(user.getProfileID());
		Log.v("MessageParrot", "before saving in db");
		help.appsHelper.modifyAppByProfile(app, prof);
		Log.v("MessageParrot", "End saving in save Profil");
	}

	private Setting<String, String, String> saveSettings(Setting<String, String, String> profileSettings, PARROTProfile user)
	{
		Log.v("MessageParrot", "Begin saving in save saveSettings");
		//First, we save the colour settings
		profileSettings.addValue("ColourSettings", "SuperCategory", String.valueOf(user.getCategoryColor()));
		profileSettings.get("ColourSettings").put("SentenceBoard", String.valueOf(user.getSentenceBoardColor()));
		Log.v("MessageParrot", "saveSetting: save colour");

		return profileSettings;	
	}

}
