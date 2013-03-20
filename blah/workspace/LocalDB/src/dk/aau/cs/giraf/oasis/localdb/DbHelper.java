package dk.aau.cs.giraf.oasis.localdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "autismDatabase.db";
	private static final int DATABASE_VERSION = 1;
	public static final String AUTHORITY = "dk.aau.cs.giraf.oasis.localdb.AutismProvider";

	public DbHelper(Context c) {
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Creates all the tables in the database
	 */
	public void onCreate(SQLiteDatabase db) {
		AppsTable.onCreate(db);
		AuthUsersTable.onCreate(db);
		DepartmentsTable.onCreate(db);
		HasDepartmentTable.onCreate(db);
		HasGuardianTable.onCreate(db);
		HasLinkTable.onCreate(db);
		HasSubDepartmentTable.onCreate(db);
		HasTagTable.onCreate(db);
		ListOfAppsTable.onCreate(db);
		MediaTable.onCreate(db);
		MediaDepartmentAccessTable.onCreate(db);
		MediaProfileAccessTable.onCreate(db);
		ProfilesTable.onCreate(db);
		TagsTable.onCreate(db);
	}

	/**
	 * Drops the old tables and then creates the tables in the database
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		AppsTable.onUpgrade(db, oldVersion, newVersion);
		AuthUsersTable.onUpgrade(db, oldVersion, newVersion);
		DepartmentsTable.onUpgrade(db, oldVersion, newVersion);
		HasDepartmentTable.onUpgrade(db, oldVersion, newVersion);
		HasGuardianTable.onUpgrade(db, oldVersion, newVersion);
		HasLinkTable.onUpgrade(db, oldVersion, newVersion);
		HasSubDepartmentTable.onUpgrade(db, oldVersion, newVersion);
		HasTagTable.onUpgrade(db, oldVersion, newVersion);
		ListOfAppsTable.onUpgrade(db, oldVersion, newVersion);
		MediaTable.onUpgrade(db, oldVersion, newVersion);
		MediaDepartmentAccessTable.onUpgrade(db, oldVersion, newVersion);
		MediaProfileAccessTable.onUpgrade(db, oldVersion, newVersion);
		ProfilesTable.onUpgrade(db, oldVersion, newVersion);
		TagsTable.onUpgrade(db, oldVersion, newVersion);
	}
}
