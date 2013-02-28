package dk.aau.cs.giraf.oasis.localdb;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * DbProvider is a contentprovider which makes it possible for other apps, on the phone, to interact with the database.
 * @author Admin
 */
public class DbProvider extends ContentProvider {

	private DbHelper dbHelper;
	private static final UriMatcher sUriMatcher;
	private static final int APPS_TYPE_LIST = 1;
	private static final int APPS_TYPE_ONE = 2;
	private static final int AUTHUSERS_TYPE_LIST = 3;
	private static final int AUTHUSERS_TYPE_ONE = 4;
	private static final int DEPARTMENTS_TYPE_LIST = 5;
	private static final int DEPARTMENTS_TYPE_ONE = 6;
	private static final int HASDEPARTMENT_TYPE_LIST = 7;
	private static final int HASGUARDIAN_TYPE_LIST = 8;
	private static final int HASLINK_TYPE_LIST = 9;
	private static final int HASSUBDEPARTMENT_TYPE_LIST = 10;
	private static final int HASTAG_TYPE_LIST = 11;
	private static final int LISTOFAPPS_TYPE_LIST = 12;
	private static final int MEDIA_TYPE_LIST = 13;
	private static final int MEDIA_TYPE_ONE = 14;
	private static final int MEDIADEPARTMENTACCESS_TYPE_LIST = 15;
	private static final int MEDIAPROFILEACCESS_TYPE_LIST = 16;
	private static final int PROFILES_TYPE_LIST = 17;
	private static final int PROFILES_TYPE_ONE = 18;
	private static final int TAGS_TYPE_LIST = 19;
	private static final int TAGS_TYPE_ONE = 20;

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "apps", APPS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "apps/#", APPS_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "authusers", AUTHUSERS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "authusers/#", AUTHUSERS_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "departments", DEPARTMENTS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "departments/#", DEPARTMENTS_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "hasdepartment", HASDEPARTMENT_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "hasguardian", HASGUARDIAN_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "haslink", HASLINK_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "hassubdepartment", HASSUBDEPARTMENT_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "hastag", HASTAG_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "listofapps", LISTOFAPPS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "media", MEDIA_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "media/#", MEDIA_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "mediadepartmentaccess", MEDIADEPARTMENTACCESS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "mediaprofileaccess", MEDIAPROFILEACCESS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "profiles", PROFILES_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "profiles/#", PROFILES_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "tags", TAGS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "tags/#", TAGS_TYPE_ONE);
	}

	private static final HashMap<String, String> appsProjectionMap;
	static {
		appsProjectionMap = new HashMap<String, String>();
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_ID, AppsMetaData.Table.COLUMN_ID);
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_NAME, AppsMetaData.Table.COLUMN_NAME);
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_VERSION, AppsMetaData.Table.COLUMN_VERSION);
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_ICON, AppsMetaData.Table.COLUMN_ICON);
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_PACKAGE, AppsMetaData.Table.COLUMN_PACKAGE);
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_ACTIVITY, AppsMetaData.Table.COLUMN_ACTIVITY);
	}

	private static final HashMap<String, String> authusersProjectionMap;
	static {
		authusersProjectionMap = new HashMap<String, String>();
		authusersProjectionMap.put(AuthUsersMetaData.Table.COLUMN_ID, AuthUsersMetaData.Table.COLUMN_ID);
		authusersProjectionMap.put(AuthUsersMetaData.Table.COLUMN_CERTIFICATE, AuthUsersMetaData.Table.COLUMN_CERTIFICATE);
		authusersProjectionMap.put(AuthUsersMetaData.Table.COLUMN_ROLE, AuthUsersMetaData.Table.COLUMN_ROLE);
	}

	private static final HashMap<String, String> departmentsProjectionMap;
	static {
		departmentsProjectionMap = new HashMap<String, String>();
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_ID, DepartmentsMetaData.Table.COLUMN_ID);
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_NAME, DepartmentsMetaData.Table.COLUMN_NAME);
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_ADDRESS, DepartmentsMetaData.Table.COLUMN_ADDRESS);
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_PHONE, DepartmentsMetaData.Table.COLUMN_PHONE);
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_EMAIL, DepartmentsMetaData.Table.COLUMN_EMAIL);
	}

	private static final HashMap<String, String> hasdepartmentProjectionMap;
	static {
		hasdepartmentProjectionMap = new HashMap<String, String>();
		hasdepartmentProjectionMap.put(HasDepartmentMetaData.Table.COLUMN_IDPROFILE, HasDepartmentMetaData.Table.COLUMN_IDPROFILE);
		hasdepartmentProjectionMap.put(HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT);
	}

	private static final HashMap<String, String> hasguardianProjectionMap;
	static {
		hasguardianProjectionMap = new HashMap<String, String>();
		hasguardianProjectionMap.put(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, HasGuardianMetaData.Table.COLUMN_IDGUARDIAN);
		hasguardianProjectionMap.put(HasGuardianMetaData.Table.COLUMN_IDCHILD, HasGuardianMetaData.Table.COLUMN_IDCHILD);
	}

	private static final HashMap<String, String> haslinkProjectionMap;
	static {
		haslinkProjectionMap = new HashMap<String, String>();
		haslinkProjectionMap.put(HasLinkMetaData.Table.COLUMN_IDMEDIA, HasLinkMetaData.Table.COLUMN_IDMEDIA);
		haslinkProjectionMap.put(HasLinkMetaData.Table.COLUMN_IDSUBMEDIA, HasLinkMetaData.Table.COLUMN_IDSUBMEDIA);
	}

	private static final HashMap<String, String> hassubdepartmentProjectionMap;
	static {
		hassubdepartmentProjectionMap = new HashMap<String, String>();
		hassubdepartmentProjectionMap.put(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT);
		hassubdepartmentProjectionMap.put(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT, HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT);
	}

	private static final HashMap<String, String> hastagProjectionMap;
	static {
		hastagProjectionMap = new HashMap<String, String>();
		hastagProjectionMap.put(HasTagMetaData.Table.COLUMN_IDMEDIA, HasTagMetaData.Table.COLUMN_IDMEDIA);
		hastagProjectionMap.put(HasTagMetaData.Table.COLUMN_IDTAG, HasTagMetaData.Table.COLUMN_IDTAG);
	}

	private static final HashMap<String, String> listofappsProjectionMap;
	static {
		listofappsProjectionMap = new HashMap<String, String>();
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_IDAPP, ListOfAppsMetaData.Table.COLUMN_IDAPP);
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_IDPROFILE, ListOfAppsMetaData.Table.COLUMN_IDPROFILE);
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_SETTINGS, ListOfAppsMetaData.Table.COLUMN_SETTINGS);
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_STATS, ListOfAppsMetaData.Table.COLUMN_STATS);
	}

	private static final HashMap<String, String> mediaProjectionMap;
	static {
		mediaProjectionMap = new HashMap<String, String>();
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_ID, MediaMetaData.Table.COLUMN_ID);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_NAME, MediaMetaData.Table.COLUMN_NAME);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_PATH, MediaMetaData.Table.COLUMN_PATH);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_PUBLIC, MediaMetaData.Table.COLUMN_PUBLIC);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_TYPE, MediaMetaData.Table.COLUMN_TYPE);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_OWNERID, MediaMetaData.Table.COLUMN_OWNERID);
	}

	private static final HashMap<String, String> mediaDepartmentAccessProjectionMap;
	static {
		mediaDepartmentAccessProjectionMap = new HashMap<String, String>();
		mediaDepartmentAccessProjectionMap.put(MediaDepartmentAccessMetaData.Table.COLUMN_IDDEPARTMENT, MediaDepartmentAccessMetaData.Table.COLUMN_IDDEPARTMENT);
		mediaDepartmentAccessProjectionMap.put(MediaDepartmentAccessMetaData.Table.COLUMN_IDMEDIA, MediaDepartmentAccessMetaData.Table.COLUMN_IDMEDIA);
	}

	private static final HashMap<String, String> mediaProfileAccessProjectionMap;
	static {
		mediaProfileAccessProjectionMap = new HashMap<String, String>();
		mediaProfileAccessProjectionMap.put(MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE, MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE);
		mediaProfileAccessProjectionMap.put(MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA, MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA);
	}

	private static final HashMap<String, String> profilesProjectionMap;
	static {
		profilesProjectionMap = new HashMap<String, String>();
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_ID, ProfilesMetaData.Table.COLUMN_ID);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_FIRST_NAME, ProfilesMetaData.Table.COLUMN_FIRST_NAME);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_SUR_NAME, ProfilesMetaData.Table.COLUMN_SUR_NAME);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME, ProfilesMetaData.Table.COLUMN_MIDDLE_NAME);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_ROLE, ProfilesMetaData.Table.COLUMN_ROLE);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_PHONE, ProfilesMetaData.Table.COLUMN_PHONE);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_PICTURE, ProfilesMetaData.Table.COLUMN_PICTURE);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_SETTINGS, ProfilesMetaData.Table.COLUMN_SETTINGS);
	}

	private static final HashMap<String, String> tagsProjectionMap;
	static {
		tagsProjectionMap = new HashMap<String, String>();
		tagsProjectionMap.put(TagsMetaData.Table.COLUMN_ID, TagsMetaData.Table.COLUMN_ID);
		tagsProjectionMap.put(TagsMetaData.Table.COLUMN_CAPTION, TagsMetaData.Table.COLUMN_CAPTION);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return false;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rowsDeleted = 0;
		String rowId;

		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			rowsDeleted = db.delete(AppsMetaData.Table.TABLE_NAME, where, whereArgs); 
			break;
		case APPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(AppsMetaData.Table.TABLE_NAME, 
					AppsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case AUTHUSERS_TYPE_LIST:
			rowsDeleted = db.delete(AuthUsersMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case AUTHUSERS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(AuthUsersMetaData.Table.TABLE_NAME,
					AuthUsersMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case DEPARTMENTS_TYPE_LIST:
			rowsDeleted = db.delete(DepartmentsMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case DEPARTMENTS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(DepartmentsMetaData.Table.TABLE_NAME, 
					DepartmentsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case HASDEPARTMENT_TYPE_LIST:
			rowsDeleted = db.delete(HasDepartmentMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case HASGUARDIAN_TYPE_LIST:
			rowsDeleted = db.delete(HasGuardianMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case HASLINK_TYPE_LIST:
			rowsDeleted = db.delete(HasLinkMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case HASSUBDEPARTMENT_TYPE_LIST:
			rowsDeleted = db.delete(HasSubDepartmentMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case HASTAG_TYPE_LIST:
			rowsDeleted = db.delete(HasTagMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case MEDIA_TYPE_LIST:
			rowsDeleted = db.delete(MediaMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case MEDIA_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(MediaMetaData.Table.TABLE_NAME, 
					MediaMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case MEDIADEPARTMENTACCESS_TYPE_LIST:
			rowsDeleted = db.delete(MediaDepartmentAccessMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case MEDIAPROFILEACCESS_TYPE_LIST:
			rowsDeleted = db.delete(MediaProfileAccessMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case PROFILES_TYPE_LIST:
			rowsDeleted = db.delete(ProfilesMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case PROFILES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(ProfilesMetaData.Table.TABLE_NAME, 
					ProfilesMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case TAGS_TYPE_LIST:
			rowsDeleted = db.delete(TagsMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case TAGS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(TagsMetaData.Table.TABLE_NAME, 
					TagsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case LISTOFAPPS_TYPE_LIST:
			rowsDeleted = db.delete(ListOfAppsMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			return AppsMetaData.CONTENT_TYPE_APPS_LIST;
		case APPS_TYPE_ONE:
			return AppsMetaData.CONTENT_TYPE_APP_ONE;
		case AUTHUSERS_TYPE_LIST:
			return AuthUsersMetaData.CONTENT_TYPE_AUTHUSERS_LIST;
		case AUTHUSERS_TYPE_ONE:
			return AuthUsersMetaData.CONTENT_TYPE_AUTHUSER_ONE;
		case DEPARTMENTS_TYPE_LIST:
			return DepartmentsMetaData.CONTENT_TYPE_DEPARTMENTS_LIST;
		case DEPARTMENTS_TYPE_ONE:
			return DepartmentsMetaData.CONTENT_TYPE_DEPARTMENT_ONE;
		case HASDEPARTMENT_TYPE_LIST:
			return HasDepartmentMetaData.CONTENT_TYPE_HASDEPARTMENTS_LIST;
		case HASGUARDIAN_TYPE_LIST:
			return HasGuardianMetaData.CONTENT_TYPE_HASGUARDIANS_LIST;
		case HASLINK_TYPE_LIST:
			return HasLinkMetaData.CONTENT_TYPE_HASLINKS_LIST;
		case HASSUBDEPARTMENT_TYPE_LIST:
			return HasSubDepartmentMetaData.CONTENT_TYPE_HASSUBDEPARTMENTS_LIST;
		case HASTAG_TYPE_LIST:
			return HasTagMetaData.CONTENT_TYPE_HASTAGS_LIST;
		case LISTOFAPPS_TYPE_LIST:
			return ListOfAppsMetaData.CONTENT_TYPE_LISTOFAPPS_LIST;
		case MEDIA_TYPE_LIST:
			return MediaMetaData.CONTENT_TYPE_MEDIA_LIST;
		case MEDIA_TYPE_ONE:
			return MediaMetaData.CONTENT_TYPE_MEDIA_ONE;
		case MEDIADEPARTMENTACCESS_TYPE_LIST:
			return MediaDepartmentAccessMetaData.CONTENT_TYPE_MEDIADEPARTMENTACCESS_LIST;
		case MEDIAPROFILEACCESS_TYPE_LIST:
			return MediaProfileAccessMetaData.CONTENT_TYPE_MEDIAPROFILEACCESS_LIST;
		case PROFILES_TYPE_LIST:
			return ProfilesMetaData.CONTENT_TYPE_PROFILES_LIST;
		case PROFILES_TYPE_ONE:
			return ProfilesMetaData.CONTENT_TYPE_PROFILE_ONE;
		case TAGS_TYPE_LIST:
			return TagsMetaData.CONTENT_TYPE_TAGS_LIST;
		case TAGS_TYPE_ONE:
			return TagsMetaData.CONTENT_TYPE_TAG_ONE;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId;
		Uri _uri;

		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(AppsMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case AUTHUSERS_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(AuthUsersMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case DEPARTMENTS_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(DepartmentsMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case HASDEPARTMENT_TYPE_LIST:
			try {	
				rowId = db.insertOrThrow(HasDepartmentMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(HasDepartmentMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(HasDepartmentMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case HASGUARDIAN_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(HasGuardianMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(HasGuardianMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(HasGuardianMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case HASLINK_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(HasLinkMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(HasLinkMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(HasLinkMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case HASSUBDEPARTMENT_TYPE_LIST:
			try {	
				rowId = db.insertOrThrow(HasSubDepartmentMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(HasSubDepartmentMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(HasSubDepartmentMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case HASTAG_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(HasTagMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(HasTagMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(HasTagMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case LISTOFAPPS_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(ListOfAppsMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(ListOfAppsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(ListOfAppsMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case MEDIA_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(MediaMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case MEDIADEPARTMENTACCESS_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(MediaDepartmentAccessMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(MediaDepartmentAccessMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(MediaDepartmentAccessMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case MEDIAPROFILEACCESS_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(MediaProfileAccessMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(MediaProfileAccessMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(MediaProfileAccessMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case PROFILES_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(ProfilesMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, -1);
			}
			return _uri;
		case TAGS_TYPE_LIST:
			try {
				rowId = db.insertOrThrow(TagsMetaData.Table.TABLE_NAME, null, values);
				_uri = ContentUris.withAppendedId(TagsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			} catch (SQLiteConstraintException e) {
				_uri = ContentUris.withAppendedId(TagsMetaData.CONTENT_URI, -1);
			}
			return _uri;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			builder.setTables(AppsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(appsProjectionMap);
			break;
		case APPS_TYPE_ONE:
			builder.setTables(AppsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(appsProjectionMap);
			builder.appendWhere(AppsMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case AUTHUSERS_TYPE_LIST:
			builder.setTables(AuthUsersMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(authusersProjectionMap);
			break;
		case AUTHUSERS_TYPE_ONE:
			builder.setTables(AuthUsersMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(authusersProjectionMap);
			builder.appendWhere(AuthUsersMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case DEPARTMENTS_TYPE_LIST:
			builder.setTables(DepartmentsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(departmentsProjectionMap);
			break;
		case DEPARTMENTS_TYPE_ONE:
			builder.setTables(DepartmentsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(departmentsProjectionMap);
			builder.appendWhere(DepartmentsMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case HASDEPARTMENT_TYPE_LIST:
			builder.setTables(HasDepartmentMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(hasdepartmentProjectionMap);
			break;
		case HASGUARDIAN_TYPE_LIST:
			builder.setTables(HasGuardianMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(hasguardianProjectionMap);
			break;
		case HASLINK_TYPE_LIST:
			builder.setTables(HasLinkMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(haslinkProjectionMap);
			break;
		case HASSUBDEPARTMENT_TYPE_LIST:
			builder.setTables(HasSubDepartmentMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(hassubdepartmentProjectionMap);
			break;
		case HASTAG_TYPE_LIST:
			builder.setTables(HasTagMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(hastagProjectionMap);
			break;
		case LISTOFAPPS_TYPE_LIST:
			builder.setTables(ListOfAppsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(listofappsProjectionMap);
			break;
		case MEDIA_TYPE_LIST:
			builder.setTables(MediaMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(mediaProjectionMap);
			break;
		case MEDIA_TYPE_ONE:
			builder.setTables(MediaMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(mediaProjectionMap);
			builder.appendWhere(MediaMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case MEDIADEPARTMENTACCESS_TYPE_LIST:
			builder.setTables(MediaDepartmentAccessMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(mediaDepartmentAccessProjectionMap);
			break;
		case MEDIAPROFILEACCESS_TYPE_LIST:
			builder.setTables(MediaProfileAccessMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(mediaProfileAccessProjectionMap);
			break;
		case PROFILES_TYPE_LIST:
			builder.setTables(ProfilesMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(profilesProjectionMap);
			break;
		case PROFILES_TYPE_ONE:
			builder.setTables(ProfilesMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(profilesProjectionMap);
			builder.appendWhere(ProfilesMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case TAGS_TYPE_LIST:
			builder.setTables(TagsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(tagsProjectionMap);
			break;
		case TAGS_TYPE_ONE:
			builder.setTables(TagsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(tagsProjectionMap);
			builder.appendWhere(TagsMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor queryCursor = builder.query(db, projection, selection, selectionArgs, null, null, null);
		queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return queryCursor;
	}
	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rowsUpdated = 0;
		String rowId;

		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			rowsUpdated = db.update(AppsMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case APPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(AppsMetaData.Table.TABLE_NAME, 
					values, 
					AppsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case AUTHUSERS_TYPE_LIST:
			rowsUpdated = db.update(AuthUsersMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case AUTHUSERS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(AuthUsersMetaData.Table.TABLE_NAME,
					values,
					AuthUsersMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case DEPARTMENTS_TYPE_LIST:
			rowsUpdated = db.update(DepartmentsMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case DEPARTMENTS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(DepartmentsMetaData.Table.TABLE_NAME, 
					values, 
					DepartmentsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case HASDEPARTMENT_TYPE_LIST:
			rowsUpdated = db.update(HasDepartmentMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case HASGUARDIAN_TYPE_LIST:
			rowsUpdated = db.update(HasGuardianMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case HASLINK_TYPE_LIST:
			rowsUpdated = db.update(HasLinkMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case HASSUBDEPARTMENT_TYPE_LIST:
			rowsUpdated = db.update(HasSubDepartmentMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case HASTAG_TYPE_LIST:
			rowsUpdated = db.update(HasTagMetaData.Table.TABLE_NAME, values, where, whereArgs);
		case LISTOFAPPS_TYPE_LIST:
			rowsUpdated = db.update(ListOfAppsMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case MEDIA_TYPE_LIST:
			rowsUpdated = db.update(MediaMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case MEDIA_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(MediaMetaData.Table.TABLE_NAME, 
					values, 
					MediaMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case MEDIADEPARTMENTACCESS_TYPE_LIST:
			rowsUpdated = db.update(MediaDepartmentAccessMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case MEDIAPROFILEACCESS_TYPE_LIST:
			rowsUpdated = db.update(MediaProfileAccessMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case PROFILES_TYPE_LIST:
			rowsUpdated = db.update(ProfilesMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case PROFILES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(ProfilesMetaData.Table.TABLE_NAME, 
					values, 
					ProfilesMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case TAGS_TYPE_LIST:
			rowsUpdated = db.update(TagsMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case TAGS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(TagsMetaData.Table.TABLE_NAME,
					values,
					TagsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}


}
