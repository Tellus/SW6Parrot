package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class DepartmentsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ DepartmentsMetaData.Table.TABLE_NAME
			+ "("
			+ DepartmentsMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ DepartmentsMetaData.Table.COLUMN_NAME + " TEXT NOT NULL, "
			+ DepartmentsMetaData.Table.COLUMN_ADDRESS + " TEXT NOT NULL, "
			+ DepartmentsMetaData.Table.COLUMN_PHONE + " INTEGER NOT NULL, "
			+ DepartmentsMetaData.Table.COLUMN_EMAIL + " TEXT NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + DepartmentsMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes a sql string for creating a new departments table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * Executes a sql string which drops the current table and then the methods calls oncreate, which creates a new table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer referring to the old version number
	 * @param newVersion integer referring to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
