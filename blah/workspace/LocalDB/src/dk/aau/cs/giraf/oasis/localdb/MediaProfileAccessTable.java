package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class MediaProfileAccessTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ MediaProfileAccessMetaData.Table.TABLE_NAME
			+ "("
			+ MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + " INTEGER NOT NULL, "
			+ MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA + " INTEGER NOT NULL, "
			+ "PRIMARY KEY(" + MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + ", " + MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA + ")"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + MediaProfileAccessMetaData.Table.TABLE_NAME + ";";

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
