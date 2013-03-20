package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class AppsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ AppsMetaData.Table.TABLE_NAME
			+ "("
			+ AppsMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ AppsMetaData.Table.COLUMN_NAME + " TEXT NOT NULL, "
			+ AppsMetaData.Table.COLUMN_VERSION + " TEXT, "
			+ AppsMetaData.Table.COLUMN_ICON + " TEXT, "
			+ AppsMetaData.Table.COLUMN_PACKAGE + " TEXT UNIQUE, "
			+ AppsMetaData.Table.COLUMN_ACTIVITY + " TEXT NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + AppsMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes sql string for creating app table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * executes a sql string which drops the old table and then the method call the oncreate, which creates a new app table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer refering to the old version number
	 * @param newVersion integer refering to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
