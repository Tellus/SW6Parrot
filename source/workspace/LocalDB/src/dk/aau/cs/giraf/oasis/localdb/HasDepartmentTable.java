package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class HasDepartmentTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ HasDepartmentMetaData.Table.TABLE_NAME
			+ "("
			+ HasDepartmentMetaData.Table.COLUMN_IDPROFILE + " INTEGER NOT NULL, "
			+ HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " INTEGER NOT NULL, "
			+ "PRIMARY KEY(" + HasDepartmentMetaData.Table.COLUMN_IDPROFILE + ", " + HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + ")"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + HasDepartmentMetaData.Table.TABLE_NAME + ";";

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
	 * @param oldVersion integer refering to the old version number
	 * @param newVersion integer refering to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
