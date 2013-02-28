package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class MediaTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ MediaMetaData.Table.TABLE_NAME
			+ "("
			+ MediaMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MediaMetaData.Table.COLUMN_NAME + " TEXT NOT NULL, "
			+ MediaMetaData.Table.COLUMN_PATH + " TEXT NOT NULL, "
			+ MediaMetaData.Table.COLUMN_PUBLIC + " INTEGER NOT NULL, "
			+ MediaMetaData.Table.COLUMN_TYPE + " TEXT NOT NULL, "
			+ MediaMetaData.Table.COLUMN_OWNERID + " INTEGER NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + MediaMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes sql string for creating table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * executes a sql string which drops the old table and then the method call the oncreate, which creates a new table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer referring to the old version number
	 * @param newVersion integer referring to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
