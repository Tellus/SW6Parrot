package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * This class is called upon startup
 * 
 * @author Oasis
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button bMyProfile, bAllProfiles, bAllDepartments, bAddDummyData;
	private Intent direct;
	private long guardianId;
	public Helper helper;
	public static Profile guardian;
	public static Profile child;
	public static int color;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View main_layout = findViewById(android.R.id.content).getRootView();
		main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);

		helper = new Helper(this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {        	   
			guardianId = extras.getLong("currentGuardianID");
			color = extras.getInt("appBackgroundColor");
			guardian = helper.profilesHelper.getProfileById(guardianId);
		}			

		setContentView(R.layout.main);

		initializeViews();
	}

	private void initializeViews() {
		findViewById(R.id.UpperLayout).setBackgroundColor(color);

		bMyProfile = (Button) findViewById(R.id.bMyProfile);
		bMyProfile.setOnClickListener(this);
		bAllProfiles = (Button) findViewById(R.id.bAllProfiles);
		bAllProfiles.setOnClickListener(this);
		bAllDepartments = (Button) findViewById(R.id.bAllDepartments);
		bAllDepartments.setOnClickListener(this);
		bAddDummyData = (Button) findViewById(R.id.bAddDummyData);
		if (guardian == null) {
			bAddDummyData.setOnClickListener(this);
		} else {
			bAddDummyData.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		direct = new Intent(this, FragParentTab.class);

		switch (v.getId()) {
		case R.id.bMyProfile:
			if (guardian != null) {
				direct.putExtra("tabView", FragParentTab.TABPROFILE);
				startActivity(direct);
			} else {
				Toast.makeText(this, R.string.noprofile, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.bAllProfiles:
			direct.putExtra("tabView", FragParentTab.TABALLPROFILES);
			startActivity(direct);
			break;
		case R.id.bAllDepartments:
			direct.putExtra("tabView", FragParentTab.TABALLDEPARTMENTS);
			startActivity(direct);
			break;
		case R.id.bAddDummyData:
			helper.CreateDummyData();
			Toast.makeText(this, R.string.addedDummyData, Toast.LENGTH_SHORT).show();
			break;
		}
	}
}