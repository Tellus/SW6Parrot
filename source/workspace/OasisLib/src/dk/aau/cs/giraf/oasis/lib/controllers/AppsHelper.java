package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.AppsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.ListOfApps;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import dk.aau.cs.giraf.oasis.lib.models.Stat;

/**
 * Helper class for Apps
 * @author Admin
 *
 */
public class AppsHelper {

	private static Context _context;
	private ListOfAppsController loa;
	private String[] columns = new String[] { 
			AppsMetaData.Table.COLUMN_ID, 
			AppsMetaData.Table.COLUMN_NAME,
			AppsMetaData.Table.COLUMN_VERSION,
			AppsMetaData.Table.COLUMN_ICON,
			AppsMetaData.Table.COLUMN_PACKAGE,
			AppsMetaData.Table.COLUMN_ACTIVITY};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public AppsHelper(Context context){
		_context = context;
		loa = new ListOfAppsController(context);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear applications table
//	 */
//	public void clearAppsTable() {
//		_context.getContentResolver().delete(AppsMetaData.CONTENT_URI, null, null);
//	}
	
	/**
	 * Cascade remove app
	 * @param app App to remove
	 * @return Rows affected
	 */
	public int removeApp(App app) {
		if (app == null) {
			return -1;
		}
		
		int rows = 0;
		
		rows += loa.removeListOfAppsByAppId(app.getId());
		rows += _context.getContentResolver().delete(AppsMetaData.CONTENT_URI, AppsMetaData.Table.COLUMN_ID + " = '" + app.getId() + "'", null);

		return rows;
	}
	
	/**
	 * Remove app attachment to profile
	 * @param app App to remove
	 * @param profile Profile to remove attachment from
	 * @return Rows
	 */
	public int removeAppAttachmentToProfile(App app, Profile profile) {
		if (app == null || profile == null) {
			return -1;
		}
		
		return loa.removeListOfApps(new ListOfApps(app.getId(), profile.getId()));
	}
	
	/**
	 * Insert app
	 * @param app Application containing data
	 * @return the app id
	 */
	public long insertApp(App app) {
		if (app == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(app);
		Uri uri = _context.getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
		return Integer.parseInt(uri.getPathSegments().get(1));
	}

	/**
	 * Attach app to Profile
	 * @param app App to attach
	 * @param profile Profile to attach to
	 * @return Rows
	 */
	public int attachAppToProfile(App app, Profile profile) {
		if (app == null || profile == null) {
			return -1;
		}
		
		return loa.insertListOfApps(new ListOfApps(app.getId(), profile.getId(), app.getSettings(), app.getStats()));
	}

	/**
	 * Modify app
	 * @param app Application containing data to modify
	 */
	public int modifyApp(App app) {
		if (app == null) {
			return -1;
		}
		
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, app.getId());
		ContentValues cv = getContentValues(app);
		return _context.getContentResolver().update(uri, cv, null, null);
	}
	
	/**
	 * Modify app by profile
	 * @param app the application containing the data to modify
	 * @param profile the profile the settings belong to
	 */
	public int modifyAppByProfile(App app, Profile profile) {
		if (app == null || profile == null) {
			return -1;
		}
		
		modifyApp(app);
		return loa.modifyListOfApps(new ListOfApps(app.getId(), profile.getId(), app.getSettings(), app.getStats()));
	}

	/**
	 * Get all applications
	 * @return List<App>, containing all applications
	 */
	public List<App> getApps() {
		List<App> apps = new ArrayList<App>();
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, null, null, null);
		
		if (c != null) {
			apps = cursorToAppList(c);
			c.close();
		}

		return apps;
	}

	/**
	 * Get application by id
	 * @param id the id of the application
	 * @return the application or null
	 */
	public App getAppById(long id) {
		App app = null;
		
		if (id <= 0) {
			return null;
		}
		
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if(c.moveToFirst()) {
				app = cursorToApp(c); 
			}
			c.close();
		}
		return app;
	}
	
	/**
	 * Get apps by ids
	 * @param appId App id
	 * @param profileId Profile id
	 * @return App
	 */
	public App getAppByIds(long appId, long profileId) {
		App app;
		
		if (appId <= 0 || profileId <= 0) {
			return null;
		}
		
		ListOfApps listOfApps = loa.getListOfAppByIds(appId, profileId);
		app = getAppById(appId);
		if(listOfApps != null){
			app.setSettings(listOfApps.getSetting());
			app.setStats(listOfApps.getStat());
		}
			
		return app;
	}

	/**
	 * Gets all application with the specified name
	 * @param name the name of the application
	 * @return List<App>, containing all apps with the specific name
	 */
	public List<App> getAppsByName(String name) {
		List<App> apps = new ArrayList<App>();

		if (name == null) {
			return apps;
		}
		
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, AppsMetaData.Table.COLUMN_NAME + " LIKE '%" + name + "%'", null, null);
		
		if (c != null) {
			apps = cursorToAppList(c);
			c.close();
		}

		return apps; 
	}
	
	/**
	 * Get app without settings by packagename
	 * @return App
	 */
	public App getAppByPackageName() {
		App app = null;
		
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, AppsMetaData.Table.COLUMN_PACKAGE + " = '" + _context.getPackageName() + "'", null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
				app = cursorToApp(c);
			}
			c.close();
		}
		
		return app;
	}
	
	/**
	 * Get app with settings by packagename and profile id
	 * @return App
	 */
	public App getAppByPackageNameAndProfileId(long profileId) {
		App app = null;
		
		app = getAppByPackageName();
		app.setSettings(loa.getSettingByAppIdAndByProfileId(app.getId(), profileId));
		app.setStats(loa.getStatByAppIdAndByProfileId(app.getId(), profileId));
		
		return app;
	}
	
	/**
	 * Yields the setting for a specific app and profile
	 * @param appId the app id of the app
	 * @param profileId the profile id of the profile
	 * @return the setting for the profile on the specific app
	 */
	public Setting<String, String, String> getSettingByIds(long appId, long profileId) {
		if (appId <= 0 || profileId <= 0) {
			return null;
		}
		
		return loa.getSettingByAppIdAndByProfileId(appId, profileId);
	}
	
	/**
	 * Yields the stat for a specific app and profile
	 * @param appId the app id of the app
	 * @param profileId the profile id of the profile
	 * @return the stat for the profile on the specific app
	 */
	public Stat<String, String, String> getStatByIds(long appId, long profileId) {
		if (appId <= 0 || profileId <= 0) {
			return null;
		}
		
		return loa.getStatByAppIdAndByProfileId(appId, profileId);
	}
	
	/**
	 * Get apps by profile
	 * @param profile Profile
	 * @return List of apps
	 */
	public List<App> getAppsByProfile(Profile profile) {
		List<App> apps = new ArrayList<App>();
		
		if (profile == null) {
			return apps;
		}
		
		List<ListOfApps> listOfApps = new ArrayList<ListOfApps>();

		listOfApps = loa.getListOfAppsByProfileId(profile.getId());

		for (ListOfApps item : listOfApps) {
			App app = getAppById(item.getIdApp());
			if (app != null) {
				app.setSettings(item.getSetting());
				app.setStats(item.getStat());
				apps.add(app);
			}
		}

		return apps;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - Keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to app
	 * @param cursor the cursor to convert
	 * @return the app
	 */
	private App cursorToApp(Cursor cursor) {
		App app = new App();
		app.setId(cursor.getLong(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_ID)));
		app.setName(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_NAME)));
		app.setVersion(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_VERSION)));
		app.setIcon(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_ICON)));
		app.setaPackage(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_PACKAGE)));
		app.setActivity(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_ACTIVITY)));
		return app;
	}

	/**
	 * Cursor to List<App>
	 * @param cursor the cursor to convert
	 * @return the List<App>
	 */
	private List<App> cursorToAppList(Cursor cursor) {
		List<App> apps = new ArrayList<App>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				apps.add(cursorToApp(cursor));
				cursor.moveToNext();
			}
		}

		return apps;
	}

	/**
	 * @param app the app to put in the database
	 * @return the contentValues
	 */
	private ContentValues getContentValues(App app) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(AppsMetaData.Table.COLUMN_NAME, app.getName());
		contentValues.put(AppsMetaData.Table.COLUMN_VERSION, app.getVersion());
		contentValues.put(AppsMetaData.Table.COLUMN_ICON, app.getIcon());
		contentValues.put(AppsMetaData.Table.COLUMN_PACKAGE, app.getaPackage());
		contentValues.put(AppsMetaData.Table.COLUMN_ACTIVITY, app.getActivity());

		return contentValues;
	}
}