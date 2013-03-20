package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class TagsTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ TagsMetaData.Table.TABLE_NAME
			+ "("
			+ TagsMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ TagsMetaData.Table.COLUMN_CAPTION + " TEXT UNIQUE"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + TagsMetaData.Table.TABLE_NAME + ";";

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
