package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class ProfilesTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ ProfilesMetaData.Table.TABLE_NAME
			+ "("
			+ ProfilesMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ ProfilesMetaData.Table.COLUMN_FIRST_NAME + " TEXT NOT NULL, "
			+ ProfilesMetaData.Table.COLUMN_SUR_NAME + " TEXT NOT NULL, "
			+ ProfilesMetaData.Table.COLUMN_MIDDLE_NAME + " TEXT, "
			+ ProfilesMetaData.Table.COLUMN_ROLE + " INTEGER NOT NULL, "
			+ ProfilesMetaData.Table.COLUMN_PHONE + " INTEGER, "
			+ ProfilesMetaData.Table.COLUMN_PICTURE + " TEXT, "
			+ ProfilesMetaData.Table.COLUMN_SETTINGS + " TEXT"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + ProfilesMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes a sql string, which creates a new profile table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * Executes a sql string, which drops the old table, then the method calls the oncreate, which creates a new table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer refering the old version number
	 * @param newVersion integer refering to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
