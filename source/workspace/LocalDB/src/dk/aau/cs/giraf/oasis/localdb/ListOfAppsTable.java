package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class ListOfAppsTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ ListOfAppsMetaData.Table.TABLE_NAME
			+ "("
			+ ListOfAppsMetaData.Table.COLUMN_IDAPP + " INTEGER NOT NULL, "
			+ ListOfAppsMetaData.Table.COLUMN_IDPROFILE + " INTEGER NOT NULL, "
			+ ListOfAppsMetaData.Table.COLUMN_SETTINGS + " TEXT, "
			+ ListOfAppsMetaData.Table.COLUMN_STATS + " TEXT, "
			+ "PRIMARY KEY(" + ListOfAppsMetaData.Table.COLUMN_IDAPP + ", " + ListOfAppsMetaData.Table.COLUMN_IDPROFILE + ")"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + ListOfAppsMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes a sql string, which creates a new listofapps table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * Executes a sql string, which drop the current table, then the method calls the oncreate, which creates a new table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer referring to the old version number
	 * @param newVersion integer referring to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
