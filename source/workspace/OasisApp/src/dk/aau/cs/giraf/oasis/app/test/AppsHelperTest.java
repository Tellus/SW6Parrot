package dk.aau.cs.giraf.oasis.app.test;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import dk.aau.cs.giraf.oasis.app.MainActivity;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import dk.aau.cs.giraf.oasis.lib.models.Stat;

public class AppsHelperTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;


	public AppsHelperTest() {
		super("dk.aau.cs.giraf.oasis.app", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	public void testRemoveApp() {
		App newApp = new App("TestRemoveApp", "TestRemovePackage", "TestRemoveActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);

		int result = mActivity.helper.appsHelper.removeApp(newApp);
		App a = mActivity.helper.appsHelper.getAppById(id);

		assertNull("Should be null, because App is removed", a);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveAppAttachmentToProfile() {
		App newApp = new App("TestRemoveAttachmentApp", "TestRemoveAttachmentPackage", "TestRemoveAttachmentActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		mActivity.helper.appsHelper.attachAppToProfile(newApp, newProfile);

		int result = mActivity.helper.appsHelper.removeAppAttachmentToProfile(newApp, newProfile);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testInsertApp() {
		App newApp = new App("TestInsertApp", "TestInsertPackage", "TestInsertActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);

		assertNotSame("Should not be -1", -1, id);
	}

	public void testAttachAppToProfile() {
		App newApp = new App("TestAttachApp", "TestAttachPackage", "TestAttachActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);

		int result = mActivity.helper.appsHelper.attachAppToProfile(newApp, newProfile);

		assertNotSame("Should not return -1", -1, result);
	}

	public void testModifyApp() {
		App newApp = new App("TestModifyApp", "TestModifyPackage", "TestModifyActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);

		int result = mActivity.helper.appsHelper.modifyApp(newApp);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testModifyAppByProfile() {
		App newApp = new App("TestModifyByProfileApp", "TestModifyByProfilePackage", "TestModifyByProfileActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);

		mActivity.helper.appsHelper.attachAppToProfile(newApp, newProfile);
		int result = mActivity.helper.appsHelper.modifyAppByProfile(newApp, newProfile);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testGetApps() {
		List<App> list = mActivity.helper.appsHelper.getApps();

		assertNotNull("Should not be empty", list);
	}
	
	public void testGetAppById() {
		App newApp = new App("TestGetByIdApp", "TestGetByIdPackage", "TestGetByIdActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);
	
		App foundApp = mActivity.helper.appsHelper.getAppById(id);

		assertEquals("Should return App; TestGetByIdApp", newApp, foundApp);
	}
	
	public void testGetAppByIds() {
		App newApp = new App("TestGetByIdsApp", "TestGetByIdsPackage", "TestGetByIdsActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		
		App foundApp = mActivity.helper.appsHelper.getAppByIds(id, idP);

		assertEquals("Should return App; TestGetByIdsApp", newApp, foundApp);
	}
	
	public void testGetAppsByName() {
		String name = "Test";
		List<App> list = mActivity.helper.appsHelper.getAppsByName(name);

		assertNotNull("Should not be empty", list);
	}
	
	public void testGetAppByPackageName() {
		App thisApp = new App("Admin", "dk.aau.cs.giraf.oasis.app", "dk.aau.cs.giraf.oasis.app.MainActivity");
		mActivity.helper.appsHelper.insertApp(thisApp);
		App foundApp = mActivity.helper.appsHelper.getAppByPackageName();

		assertNotNull("Should not be empty", foundApp);
	}
	
	public void testGetAppByPackageNameAndProfileId() {
		App thisApp = new App("Admin", "dk.aau.cs.giraf.oasis.app", "dk.aau.cs.giraf.oasis.app.MainActivity");
		mActivity.helper.appsHelper.insertApp(thisApp);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		
		mActivity.helper.appsHelper.attachAppToProfile(thisApp, newProfile);
		
		App foundApp = mActivity.helper.appsHelper.getAppByPackageNameAndProfileId(idP);

		assertNotNull("Should not be empty", foundApp);
	}
	
	public void testGetSettingsByIds() {
		App newApp = new App("TestGetSettingsApp", "TestGetSettingsPackage", "TestGetSettingsActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);
		Setting<String, String, String> setting = new Setting<String, String, String>();
		setting.addValue("Test", "Test", "Test");
		newApp.setSettings(setting);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		
		mActivity.helper.appsHelper.attachAppToProfile(newApp, newProfile);
		
		Setting<String, String, String> foundSettings = mActivity.helper.appsHelper.getSettingByIds(id, idP);
		
		assertNotNull("Should not be empty", foundSettings);
	}
	
	public void testGetStatsByIds() {
		App newApp = new App("TestGetStatsApp", "TestGetStatsPackage", "TestGetStatsActiviy");
		long id = mActivity.helper.appsHelper.insertApp(newApp);
		newApp.setId(id);
		Stat<String, String, String> stat = new Stat<String, String, String>();
		stat.addValue("Test", "Test", "Test");
		newApp.setStats(stat);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		
		mActivity.helper.appsHelper.attachAppToProfile(newApp, newProfile);
		
		Stat<String, String, String> foundStats = mActivity.helper.appsHelper.getStatByIds(id, idP);
		
		assertNotNull("Should not be empty", foundStats);
	}
	
	public void testGetAppsByProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		
		List<App> list = mActivity.helper.appsHelper.getAppsByProfile(newProfile);
		
		assertNotNull("Should not be empty", list);
	}
}